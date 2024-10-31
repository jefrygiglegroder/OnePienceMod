package jefry.mod.onepiecemod.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import org.apache.commons.lang3.tuple.Pair;

public class ModConfig {
    public static class Server {
        // Devil Fruit General Settings
        public final BooleanValue enableDevilFruits;
        public final BooleanValue enableSeawater;
        public final BooleanValue enableSeastone;
        public final BooleanValue enableHaki;

        // Devil Fruit Spawn Settings
        public final DoubleValue devilFruitSpawnRate;
        public final IntValue devilFruitSpawnMinY;
        public final IntValue devilFruitSpawnMaxY;

        // Combat Settings
        public final DoubleValue abilityDamageMultiplier;
        public final DoubleValue seawaterWeaknessMultiplier;
        public final DoubleValue seastoneWeaknessMultiplier;
        public final IntValue abilityCooldownReduction;

        // Stamina Settings
        public final IntValue baseStamina;
        public final DoubleValue staminaRegenRate;
        public final DoubleValue staminaRegenDelay;

        // Gear Settings
        public final IntValue gearSecondDuration;
        public final IntValue gearSecondCooldown;
        public final DoubleValue gearSecondSpeedMultiplier;
        public final DoubleValue gearSecondDamageMultiplier;

        public final IntValue gearThirdDuration;
        public final IntValue gearThirdCooldown;
        public final DoubleValue gearThirdSizeMultiplier;
        public final DoubleValue gearThirdDamageMultiplier;

        public final IntValue gearFourthDuration;
        public final IntValue gearFourthCooldown;
        public final DoubleValue gearFourthDamageMultiplier;
        public final DoubleValue gearFourthKnockbackMultiplier;

        public final IntValue gearFifthDuration;
        public final IntValue gearFifthCooldown;
        public final DoubleValue gearFifthDamageMultiplier;
        public final DoubleValue gearFifthSizeMultiplier;

        // World Generation Settings
        public final BooleanValue generateIslands;
        public final IntValue islandSpacing;
        public final IntValue islandSeparation;
        public final IntValue oceanHeight;

        // Bounty Settings
        public final IntValue maxBounty;
        public final IntValue minBountyIncrease;
        public final IntValue maxBountyIncrease;
        public final BooleanValue enableBountyHunters;

        // Debug Settings
        public final BooleanValue debugMode;
        public final BooleanValue showAbilityRanges;
        public final BooleanValue showCooldowns;

        Server(ForgeConfigSpec.Builder builder) {
            builder.comment("One Piece Mod Server Configuration");
            builder.push("general");

            enableDevilFruits = builder
                    .comment("Enable Devil Fruit powers")
                    .define("enableDevilFruits", true);

            enableSeawater = builder
                    .comment("Enable seawater weakness for Devil Fruit users")
                    .define("enableSeawater", true);

            enableSeastone = builder
                    .comment("Enable Seastone items and blocks")
                    .define("enableSeastone", true);

            enableHaki = builder
                    .comment("Enable Haki system")
                    .define("enableHaki", true);

            builder.pop();

            builder.push("devil_fruit_spawn");

            devilFruitSpawnRate = builder
                    .comment("Rate at which Devil Fruits spawn in the world (0.0-1.0)")
                    .defineInRange("devilFruitSpawnRate", 0.05D, 0.0D, 1.0D);

            devilFruitSpawnMinY = builder
                    .comment("Minimum Y level for Devil Fruit spawns")
                    .defineInRange("devilFruitSpawnMinY", -64, -64, 320);

            devilFruitSpawnMaxY = builder
                    .comment("Maximum Y level for Devil Fruit spawns")
                    .defineInRange("devilFruitSpawnMaxY", 64, -64, 320);

            builder.pop();

            builder.push("combat");

            abilityDamageMultiplier = builder
                    .comment("Global multiplier for ability damage")
                    .defineInRange("abilityDamageMultiplier", 1.0D, 0.0D, 10.0D);

            seawaterWeaknessMultiplier = builder
                    .comment("Weakness multiplier when in seawater")
                    .defineInRange("seawaterWeaknessMultiplier", 2.0D, 1.0D, 10.0D);

            seastoneWeaknessMultiplier = builder
                    .comment("Weakness multiplier when touching seastone")
                    .defineInRange("seastoneWeaknessMultiplier", 1.5D, 1.0D, 10.0D);

            abilityCooldownReduction = builder
                    .comment("Global cooldown reduction percentage")
                    .defineInRange("abilityCooldownReduction", 0, 0, 90);

            builder.pop();

            builder.push("stamina");

            baseStamina = builder
                    .comment("Base stamina amount")
                    .defineInRange("baseStamina", 100, 10, 1000);

            staminaRegenRate = builder
                    .comment("Stamina regeneration rate per tick")
                    .defineInRange("staminaRegenRate", 0.1D, 0.0D, 10.0D);

            staminaRegenDelay = builder
                    .comment("Delay in ticks before stamina starts regenerating")
                    .defineInRange("staminaRegenDelay", 60D, 0D, 200D);

            builder.pop();

            builder.push("gear_settings");

            // Gear Second
            gearSecondDuration = builder
                    .comment("Duration of Gear Second in ticks")
                    .defineInRange("gearSecondDuration", 600, 100, 2400);

            gearSecondCooldown = builder
                    .comment("Cooldown of Gear Second in ticks")
                    .defineInRange("gearSecondCooldown", 1200, 200, 4800);

            gearSecondSpeedMultiplier = builder
                    .comment("Speed multiplier in Gear Second")
                    .defineInRange("gearSecondSpeedMultiplier", 1.5D, 1.0D, 3.0D);

            gearSecondDamageMultiplier = builder
                    .comment("Damage multiplier in Gear Second")
                    .defineInRange("gearSecondDamageMultiplier", 1.3D, 1.0D, 3.0D);

            // Gear Third
            gearThirdDuration = builder
                    .comment("Duration of Gear Third in ticks")
                    .defineInRange("gearThirdDuration", 400, 100, 2400);

            gearThirdCooldown = builder
                    .comment("Cooldown of Gear Third in ticks")
                    .defineInRange("gearThirdCooldown", 1800, 200, 4800);

            gearThirdSizeMultiplier = builder
                    .comment("Size multiplier in Gear Third")
                    .defineInRange("gearThirdSizeMultiplier", 2.0D, 1.0D, 5.0D);

            gearThirdDamageMultiplier = builder
                    .comment("Damage multiplier in Gear Third")
                    .defineInRange("gearThirdDamageMultiplier", 2.0D, 1.0D, 5.0D);

            // Gear Fourth
            gearFourthDuration = builder
                    .comment("Duration of Gear Fourth in ticks")
                    .defineInRange("gearFourthDuration", 800, 100, 2400);

            gearFourthCooldown = builder
                    .comment("Cooldown of Gear Fourth in ticks")
                    .defineInRange("gearFourthCooldown", 2400, 200, 6000);

            gearFourthDamageMultiplier = builder
                    .comment("Damage multiplier in Gear Fourth")
                    .defineInRange("gearFourthDamageMultiplier", 2.5D, 1.0D, 5.0D);

            gearFourthKnockbackMultiplier = builder
                    .comment("Knockback multiplier in Gear Fourth")
                    .defineInRange("gearFourthKnockbackMultiplier", 2.0D, 1.0D, 5.0D);

            // Gear Fifth
            gearFifthDuration = builder
                    .comment("Duration of Gear Fifth in ticks")
                    .defineInRange("gearFifthDuration", 1200, 100, 3600);

            gearFifthCooldown = builder
                    .comment("Cooldown of Gear Fifth in ticks")
                    .defineInRange("gearFifthCooldown", 4800, 200, 12000);

            gearFifthDamageMultiplier = builder
                    .comment("Damage multiplier in Gear Fifth")
                    .defineInRange("gearFifthDamageMultiplier", 3.0D, 1.0D, 10.0D);

            gearFifthSizeMultiplier = builder
                    .comment("Size multiplier in Gear Fifth")
                    .defineInRange("gearFifthSizeMultiplier", 4.0D, 1.0D, 10.0D);

            builder.pop();

            builder.push("world_generation");

            generateIslands = builder
                    .comment("Enable custom island generation")
                    .define("generateIslands", true);

            islandSpacing = builder
                    .comment("Average spacing between islands")
                    .defineInRange("islandSpacing", 32, 16, 64);

            islandSeparation = builder
                    .comment("Minimum separation between islands")
                    .defineInRange("islandSeparation", 16, 8, 32);

            oceanHeight = builder
                    .comment("Ocean height level")
                    .defineInRange("oceanHeight", 63, -64, 320);

            builder.pop();

            builder.push("bounty");

            maxBounty = builder
                    .comment("Maximum possible bounty")
                    .defineInRange("maxBounty", 1000000, 10000, 10000000);

            minBountyIncrease = builder
                    .comment("Minimum bounty increase per kill")
                    .defineInRange("minBountyIncrease", 1000, 100, 10000);

            maxBountyIncrease = builder
                    .comment("Maximum bounty increase per kill")
                    .defineInRange("maxBountyIncrease", 10000, 1000, 100000);

            enableBountyHunters = builder
                    .comment("Enable bounty hunter NPCs")
                    .define("enableBountyHunters", true);

            builder.pop();

            builder.push("debug");

            debugMode = builder
                    .comment("Enable debug logging")
                    .define("debugMode", false);

            showAbilityRanges = builder
                    .comment("Show ability range indicators")
                    .define("showAbilityRanges", false);

            showCooldowns = builder
                    .comment("Show ability cooldown indicators")
                    .define("showCooldowns", true);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }
}