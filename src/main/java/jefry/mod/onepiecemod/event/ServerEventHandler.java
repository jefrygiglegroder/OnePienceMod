package jefry.mod.onepiecemod.event;

import jefry.mod.onepiecemod.Onepiecemod;
import jefry.mod.onepiecemod.capability.IDevilFruitUser;
import jefry.mod.onepiecemod.enchantment.ModEnchantments;
import jefry.mod.onepiecemod.registry.ModTags;
import jefry.mod.onepiecemod.util.HakiHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = Onepiecemod.MODID)
public class ServerEventHandler {
    // Constants for configuration
    private static final float WATER_DAMAGE = 0.5f;
    private static final float MASTERY_XP_CONVERSION = 0.1f;
    private static final float HAKI_DAMAGE_MULTIPLIER = 1.25f;
    private static final float SEASTONE_DAMAGE_MULTIPLIER = 1.5f;
    private static final int WATER_EFFECT_DURATION = 40;
    private static final int SEASTONE_EFFECT_DURATION = 100;

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Player player)) {
            return;
        }

        player.getCapability(Onepiecemod.DEVIL_FRUIT_CAP).ifPresent(cap -> {
            if (cap.hasDevilFruit()) {
                handleDevilFruitEffects(player, cap);
            }
        });
    }

    private static void handleDevilFruitEffects(Player player, IDevilFruitUser cap) {
        if (isInWater(player)) {
            applyWaterEffects(player);
        }

        cap.getDevilFruit().ifPresent(fruit -> fruit.applyPassiveEffects(player));
    }

    private static boolean isInWater(Player player) {
        return player.isInWater() ||
                player.level().getBlockState(player.blockPosition())
                        .getFluidState().is(Fluids.WATER);
    }

    private static void applyWaterEffects(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,
                WATER_EFFECT_DURATION, 1, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                WATER_EFFECT_DURATION, 2, false, false));

        if (player.tickCount % 20 == 0) {
            player.hurt(player.damageSources().drown(), WATER_DAMAGE);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        player.getCapability(Onepiecemod.DEVIL_FRUIT_CAP).ifPresent(cap -> {
            if (cap.hasDevilFruit()) {
                handleDevilFruitDamage(event, player, cap);
            }
        });
    }

    private static void handleDevilFruitDamage(LivingHurtEvent event, Player player, IDevilFruitUser cap) {
        boolean isHakiAttack = isHakiAttack(event);
        boolean isSeaStoneAttack = isSeaStoneAttack(event);

        cap.getDevilFruit().ifPresent(fruit -> {
            if (fruit.isLogia()) {
                handleLogiaDamage(event, player, isHakiAttack, isSeaStoneAttack);
            } else {
                handleNonLogiaDamage(event, player, isSeaStoneAttack);
            }
        });
    }

    private static void handleLogiaDamage(LivingHurtEvent event, Player player,
                                          boolean isHakiAttack, boolean isSeaStoneAttack) {
        if (!isHakiAttack && !isSeaStoneAttack && !event.getSource().isIndirect()) {
            event.setCanceled(true);
            spawnLogiaPassthroughEffects(player);
        } else {
            handleSpecialDamageEffects(event, player, isHakiAttack, isSeaStoneAttack);
        }
    }

    private static void handleNonLogiaDamage(LivingHurtEvent event, Player player, boolean isSeaStoneAttack) {
        if (isSeaStoneAttack) {
            event.setAmount(event.getAmount() * SEASTONE_DAMAGE_MULTIPLIER);
            applySeaStoneDebuffs(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerXpGain(PlayerXpEvent.XpChange event) {
        Player player = event.getEntity();

        player.getCapability(Onepiecemod.DEVIL_FRUIT_CAP).ifPresent(cap -> {
            if (cap.hasDevilFruit() && event.getAmount() > 0) {
                cap.increaseMastery(event.getAmount() * MASTERY_XP_CONVERSION);
            }
        });
    }

    private static boolean isHakiAttack(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof LivingEntity livingAttacker)) {
            return false;
        }

        if (attacker instanceof Player player && HakiHelper.hasActiveHaki(player)) {
            return true;
        }

        ItemStack weaponStack = livingAttacker.getMainHandItem();
        return EnchantmentHelper.getEnchantments(weaponStack)
                .containsKey(ModEnchantments.HAKI_INFUSION) ||
                weaponStack.is(ModTags.Items.HAKI_ITEMS);
    }

    private static boolean isSeaStoneAttack(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof LivingEntity livingAttacker)) {
            return false;
        }

        ItemStack weaponStack = livingAttacker.getMainHandItem();
        return weaponStack.is(ModTags.Items.SEASTONE_ITEMS) ||
                EnchantmentHelper.getEnchantments(weaponStack)
                        .containsKey(ModEnchantments.SEASTONE_INFUSION);
    }

    private static void handleSpecialDamageEffects(LivingHurtEvent event, Player player,
                                                   boolean isHakiAttack, boolean isSeaStoneAttack) {
        if (isHakiAttack) {
            event.setAmount(event.getAmount() * HAKI_DAMAGE_MULTIPLIER);
            spawnHakiHitEffects(player);
        }

        if (isSeaStoneAttack) {
            event.setAmount(event.getAmount() * SEASTONE_DAMAGE_MULTIPLIER);
            applySeaStoneDebuffs(player);
        }
    }

    private static void applySeaStoneDebuffs(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,
                SEASTONE_EFFECT_DURATION, 1));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                SEASTONE_EFFECT_DURATION, 1));
    }

    private static void spawnLogiaPassthroughEffects(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.CLOUD,
                    player.getX(),
                    player.getY() + 1,
                    player.getZ(),
                    10, 0.5, 0.5, 0.5, 0.1
            );

            serverLevel.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.CHORUS_FRUIT_TELEPORT,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }
    }

    private static void spawnHakiHitEffects(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.CRIT,
                    player.getX(),
                    player.getY() + 1,
                    player.getZ(),
                    15, 0.5, 0.5, 0.5, 0.1
            );

            serverLevel.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.ANVIL_LAND,
                    SoundSource.PLAYERS,
                    0.5F,
                    2.0F
            );
        }
    }
}