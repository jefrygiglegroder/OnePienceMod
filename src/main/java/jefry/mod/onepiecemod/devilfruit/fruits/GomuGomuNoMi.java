package jefry.mod.onepiecemod.devilfruit.fruits;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import jefry.mod.onepiecemod.Onepiecemod;
import java.util.List;
import java.util.ArrayList;

public class GomuGomuNoMi {
    public static final String ID = "gomu_gomu";
    private static final ResourceLocation BASE_MODEL = new ResourceLocation(Onepiecemod.MODID, "geo/luffy_base.geo.json");
    private static final ResourceLocation GEAR_2_MODEL = new ResourceLocation(Onepiecemod.MODID, "geo/luffy_gear2.geo.json");
    private static final ResourceLocation GEAR_3_MODEL = new ResourceLocation(Onepiecemod.MODID, "geo/luffy_gear3.geo.json");
    private static final ResourceLocation GEAR_4_MODEL = new ResourceLocation(Onepiecemod.MODID, "geo/luffy_gear4.geo.json");
    private static final ResourceLocation GEAR_5_MODEL = new ResourceLocation(Onepiecemod.MODID, "geo/luffy_gear5.geo.json");

    public enum GearState {
        BASE,
        GEAR_SECOND,
        GEAR_THIRD,
        GEAR_FOURTH_BOUNDMAN,
        GEAR_FOURTH_TANKMAN,
        GEAR_FOURTH_SNAKEMAN,
        GEAR_FIFTH
    }

    public static class LuffyState {
        private GearState currentGear = GearState.BASE;
        private long gearActivationTime = 0;
        private boolean isStretching = false;
        private Vec3 stretchTarget = null;
        private float stretchProgress = 0;

        public GearState getCurrentGear() { return currentGear; }
        public void setCurrentGear(GearState gear) {
            this.currentGear = gear;
            this.gearActivationTime = System.currentTimeMillis();
        }

        public ResourceLocation getCurrentModel() {
            return switch(currentGear) {
                case BASE -> BASE_MODEL;
                case GEAR_SECOND -> GEAR_2_MODEL;
                case GEAR_THIRD -> GEAR_3_MODEL;
                case GEAR_FOURTH_BOUNDMAN, GEAR_FOURTH_TANKMAN, GEAR_FOURTH_SNAKEMAN -> GEAR_4_MODEL;
                case GEAR_FIFTH -> GEAR_5_MODEL;
            };
        }
    }

    public static class GomuGomuAbilities {
        // Base Form Abilities
        public static List<Ability> getBaseAbilities() {
            List<Ability> abilities = new ArrayList<>();

            abilities.add(new Ability("Gomu Gomu no Pistol", 20, 100, (player, state) -> {
                if (!state.isStretching) {
                    Vec3 look = player.getLookAngle();
                    state.stretchTarget = player.position().add(look.multiply(10, 10, 10));
                    state.isStretching = true;
                    state.stretchProgress = 0;
                    // Apply stretch animation and hitbox
                }
            }));

            abilities.add(new Ability("Gomu Gomu no Bazooka", 35, 200, (player, state) -> {
                Vec3 look = player.getLookAngle();
                // Double palm strike with knockback
                // Add particle effects
            }));

            abilities.add(new Ability("Gomu Gomu no Rocket", 25, 150, (player, state) -> {
                Vec3 look = player.getLookAngle();
                player.setDeltaMovement(look.x * 2, look.y + 1.5, look.z * 2);
                // Add stretching animation
            }));

            return abilities;
        }

        // Gear Second Abilities
        public static List<Ability> getGearSecondAbilities() {
            List<Ability> abilities = new ArrayList<>();

            abilities.add(new Ability("Gomu Gomu no Jet Pistol", 30, 80, (player, state) -> {
                // High-speed punch with steam effects
            }));

            abilities.add(new Ability("Gomu Gomu no Jet Gatling", 50, 300, (player, state) -> {
                // Rapid-fire punches with steam effects
            }));

            abilities.add(new Ability("Gomu Gomu no Jet Stamp", 35, 150, (player, state) -> {
                // High-speed kick with steam effects
            }));

            return abilities;
        }

        // Gear Third Abilities
        public static List<Ability> getGearThirdAbilities() {
            List<Ability> abilities = new ArrayList<>();

            abilities.add(new Ability("Gomu Gomu no Elephant Gun", 60, 400, (player, state) -> {
                // Giant armament hardened fist attack
            }));

            abilities.add(new Ability("Gomu Gomu no Grizzly Magnum", 80, 500, (player, state) -> {
                // Double giant hardened fist attack
            }));

            abilities.add(new Ability("Gomu Gomu no Elephant Gatling", 100, 600, (player, state) -> {
                // Rapid-fire giant fist attacks
            }));

            return abilities;
        }

        // Gear Fourth Abilities (Boundman)
        public static List<Ability> getGearFourthBoundmanAbilities() {
            List<Ability> abilities = new ArrayList<>();

            abilities.add(new Ability("Gomu Gomu no Kong Gun", 70, 400, (player, state) -> {
                // Compressed muscular punch
            }));

            abilities.add(new Ability("Gomu Gomu no Culverin", 65, 350, (player, state) -> {
                // Homing punch attack
            }));

            abilities.add(new Ability("Python", 60, 300, (player, state) -> {
                // Changing direction punch
            }));

            return abilities;
        }

        // Gear Fourth Abilities (Tankman)
        public static List<Ability> getGearFourthTankmanAbilities() {
            List<Ability> abilities = new ArrayList<>();

            abilities.add(new Ability("Gomu Gomu no Cannonball", 90, 500, (player, state) -> {
                // Powerful defensive counterattack
            }));

            return abilities;
        }

        // Gear Fourth Abilities (Snakeman)
        public static List<Ability> getGearFourthSnakemanAbilities() {
            List<Ability> abilities = new ArrayList<>();

            abilities.add(new Ability("Gomu Gomu no Black Mamba", 85, 450, (player, state) -> {
                // Multiple high-speed attacks
            }));

            abilities.add(new Ability("Gomu Gomu no King Cobra", 100, 600, (player, state) -> {
                // Powerful expanding attack
            }));

            return abilities;
        }

        // Gear Fifth Abilities
        public static List<Ability> getGearFifthAbilities() {
            List<Ability> abilities = new ArrayList<>();

            abilities.add(new Ability("Gomu Gomu no Bajrang Gun", 100, 1000, (player, state) -> {
                // Giant fist with lightning effects
            }));

            abilities.add(new Ability("Giant Form", 80, 800, (player, state) -> {
                // Transform into giant size
            }));

            abilities.add(new Ability("World Bending", 90, 900, (player, state) -> {
                // Environment manipulation effects
            }));

            return abilities;
        }
    }

    public static class Ability {
        private final String name;
        private final int staminaCost;
        private final int cooldown;
        private final AbilityEffect effect;

        public Ability(String name, int staminaCost, int cooldown, AbilityEffect effect) {
            this.name = name;
            this.staminaCost = staminaCost;
            this.cooldown = cooldown;
            this.effect = effect;
        }

        public void activate(Player player, LuffyState state) {
            effect.apply(player, state);
        }

        public String getName() { return name; }
        public int getStaminaCost() { return staminaCost; }
        public int getCooldown() { return cooldown; }
    }

    @FunctionalInterface
    public interface AbilityEffect {
        void apply(Player player, LuffyState state);
    }

    // Gear Transformation Methods
    public static void activateGearSecond(Player player, LuffyState state) {
        state.setCurrentGear(GearState.GEAR_SECOND);
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 2));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1));
        // Add steam particles
    }

    public static void activateGearThird(Player player, LuffyState state) {
        state.setCurrentGear(GearState.GEAR_THIRD);
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 2));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 0));
        // Scale player model
    }

    public static void activateGearFourth(Player player, LuffyState state, GearState form) {
        state.setCurrentGear(form);
        switch(form) {
            case GEAR_FOURTH_BOUNDMAN:
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 800, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 800, 3));
                break;
            case GEAR_FOURTH_TANKMAN:
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 800, 2));
                break;
            case GEAR_FOURTH_SNAKEMAN:
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 800, 3));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 800, 2));
                break;
        }
        // Add bounce physics
    }

    public static void activateGearFifth(Player player, LuffyState state) {
        state.setCurrentGear(GearState.GEAR_FIFTH);
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 4));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 2));
        // Add heartbeat sound effect and white transformation
    }
}