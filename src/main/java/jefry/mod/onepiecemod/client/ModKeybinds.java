package jefry.mod.onepiecemod.client;

import com.mojang.blaze3d.platform.InputConstants;
import jefry.mod.onepiecemod.Onepiecemod;
import jefry.mod.onepiecemod.network.packets.AbilityKeyPacket;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class ModKeybinds {
    private static final String CATEGORY = "key.categories." + Onepiecemod.MODID;

    // Ability keybinds
    public static final KeyMapping ABILITY_1 = new KeyMapping(
            "key.onepiecemod.ability1",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            CATEGORY
    );

    public static final KeyMapping ABILITY_2 = new KeyMapping(
            "key.onepiecemod.ability2",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            CATEGORY
    );

    public static final KeyMapping ABILITY_3 = new KeyMapping(
            "key.onepiecemod.ability3",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            CATEGORY
    );

    public static final KeyMapping ABILITY_4 = new KeyMapping(
            "key.onepiecemod.ability4",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            CATEGORY
    );

    public static final KeyMapping ABILITY_5 = new KeyMapping(
            "key.onepiecemod.ability5",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            CATEGORY
    );

    public static final KeyMapping ABILITY_6 = new KeyMapping(
            "key.onepiecemod.ability6",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            CATEGORY
    );

    public static final KeyMapping ULTIMATE = new KeyMapping(
            "key.onepiecemod.ultimate",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            CATEGORY
    );

    // Track key states to prevent holding
    private static final Map<KeyMapping, Boolean> previousKeyStates = new HashMap<>();

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new ModKeybinds());
    }

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(ABILITY_1);
        event.register(ABILITY_2);
        event.register(ABILITY_3);
        event.register(ABILITY_4);
        event.register(ABILITY_5);
        event.register(ABILITY_6);
        event.register(ULTIMATE);

        // Initialize previous states
        previousKeyStates.put(ABILITY_1, false);
        previousKeyStates.put(ABILITY_2, false);
        previousKeyStates.put(ABILITY_3, false);
        previousKeyStates.put(ABILITY_4, false);
        previousKeyStates.put(ABILITY_5, false);
        previousKeyStates.put(ABILITY_6, false);
        previousKeyStates.put(ULTIMATE, false);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        checkAndSendKeyPress(ABILITY_1, 0);
        checkAndSendKeyPress(ABILITY_2, 1);
        checkAndSendKeyPress(ABILITY_3, 2);
        checkAndSendKeyPress(ABILITY_4, 3);
        checkAndSendKeyPress(ABILITY_5, 4);
        checkAndSendKeyPress(ABILITY_6, 5);
        checkAndSendKeyPress(ULTIMATE, 6);
    }

    private void checkAndSendKeyPress(KeyMapping key, int abilityIndex) {
        boolean currentState = key.isDown();
        boolean previousState = previousKeyStates.get(key);

        if (currentState && !previousState) {
            // Key was just pressed
            Onepiecemod.sendToServer(new AbilityKeyPacket(abilityIndex));
        }

        previousKeyStates.put(key, currentState);
    }
}