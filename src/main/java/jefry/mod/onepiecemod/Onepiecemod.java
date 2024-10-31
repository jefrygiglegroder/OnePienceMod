package jefry.mod.onepiecemod;

import jefry.mod.onepiecemod.capability.DevilFruitUser;
import jefry.mod.onepiecemod.capability.IDevilFruitUser;
import jefry.mod.onepiecemod.config.ModConfig;
import jefry.mod.onepiecemod.devilfruit.DevilFruits;
import jefry.mod.onepiecemod.entity.ModEntities;
import jefry.mod.onepiecemod.event.ServerEventHandler;
import jefry.mod.onepiecemod.network.NetworkHandler;
import jefry.mod.onepiecemod.network.packets.AbilityKeyPacket;
import jefry.mod.onepiecemod.world.IslandGenerator;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Onepiecemod.MODID)
public class Onepiecemod {
    public static final String MODID = "onepiecemod";

    // Network protocol version
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static final Capability<IDevilFruitUser> DEVIL_FRUIT_CAP = CapabilityManager.get(new CapabilityToken<IDevilFruitUser>() {});

    // Mod instance
    public static Onepiecemod instance;

    public Onepiecemod(IEventBus modEventBus, ModContainer modContainer) {
        instance = this;

        // Register configs using the modern approach
        ConfigTracker.INSTANCE.loadConfigs(Type.SERVER, FMLPaths.CONFIGDIR.get());

        // Register ourselves for mod events
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::registerCapabilities);

        // Register deferred registers
        ModEntities.ENTITIES.register(modEventBus);

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());

        // Initialize systems
        DevilFruits.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register network messages
            int id = 0;
            NETWORK.registerMessage(id++,
                    AbilityKeyPacket.class,
                    AbilityKeyPacket::encode,
                    AbilityKeyPacket::decode,
                    AbilityKeyPacket::handle);

            NETWORK.registerMessage(id++,
                    BountyUpdatePacket.class,
                    BountyUpdatePacket::encode,
                    BountyUpdatePacket::decode,
                    BountyUpdatePacket::handle);

            NETWORK.registerMessage(id++,
                    SyncDevilFruitPacket.class,
                    SyncDevilFruitPacket::encode,
                    SyncDevilFruitPacket::decode,
                    SyncDevilFruitPacket::handle);
        });

        // Initialize Network Handler
        NetworkHandler.init();
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IDevilFruitUser.class);
    }

    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            // Create a new capability provider
            ICapabilityProvider provider = new ICapabilityProvider() {
                private final DevilFruitUser devilFruitUser = new DevilFruitUser();
                private final LazyOptional<IDevilFruitUser> lazyDevilFruitUser = LazyOptional.of(() -> devilFruitUser);

                @Override
                public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
                    if (cap == DEVIL_FRUIT_CAP) {
                        return lazyDevilFruitUser.cast();
                    }
                    return LazyOptional.empty();
                }

                @Override
                public void invalidate() {
                    lazyDevilFruitUser.invalidate();
                }
            };

            event.addCapability(new ResourceLocation(MODID, "devil_fruit"), provider);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Initialize server-side systems
        IslandGenerator.init(event.getServer());
    }

    // Utility methods for mod components
    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static void sendToServer(Object packet) {
        NETWORK.sendToServer(packet);
    }

    public static void sendToPlayer(Object packet, Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            NETWORK.sendTo(packet, serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendToAll(Object packet) {
        NETWORK.send(PacketDistributor.ALL.noArg(), packet);
    }

    // Config access methods
    public static int getMaxBounty() {
        return ModConfig.SERVER.maxBounty.get();
    }

    public static double getAbilityDamageMultiplier() {
        return ModConfig.SERVER.abilityDamageMultiplier.get();
    }

    public static int getAbilityCooldownReduction() {
        return ModConfig.SERVER.abilityCooldownReduction.get();
    }

    // Debug logging
    public static void debug(String message, Object... params) {
        if (ModConfig.SERVER.debugMode.get()) {
            LOGGER.debug(message, params);
        }
    }

    private static final Logger LOGGER = LogManager.getLogger();
}