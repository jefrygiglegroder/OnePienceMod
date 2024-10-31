package jefry.mod.onepiecemod.devilfruit;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class DevilFruits {
    private static final Map<String, DevilFruit> DEVIL_FRUITS = new HashMap<>();

    public static void init() {
        // Paramecia Type
        registerGomuGomuNoMi();
        registerBaraBaraNoMi();
        registerOpeOpeNoMi();
        registerGoroGoroNoMi();

        // Logia Type
        registerMeraMeraNoMi();
        registerHieHieNoMi();
        registerPikaPikaNoMi();

        // Zoan Type
        registerHitoHitoNoMi();
        // Add more devil fruits as needed
    }

    private static void registerGomuGomuNoMi() {
        List<Ability> abilities = new ArrayList<>();

        // Base Abilities
        abilities.add(new Ability("Gomu Gomu no Pistol", 20, 200, (player) -> {
            // Implement stretching punch attack
            // Example implementation:
            Vec3 look = player.getLookAngle();
            // Add attack logic
        }));

        abilities.add(new Ability("Gomu Gomu no Bazooka", 30, 300, (player) -> {
            // Implement double palm strike
        }));

        abilities.add(new Ability("Gomu Gomu no Rocket", 25, 250, (player) -> {
            // Implement launching player
            Vec3 look = player.getLookAngle();
            player.setDeltaMovement(look.x * 2, look.y + 1, look.z * 2);
        }));

        // Gear Second
        abilities.add(new Ability("Gear Second", 50, 600, (player) -> {
            // Implement speed boost and attack power increase
            // Example: Add potion effects
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1));
        }));

        // Gear Third
        abilities.add(new Ability("Gear Third", 60, 800, (player) -> {
            // Implement giant limb attacks
            // Add size increase effect and strength boost
        }));

        // Gear Fourth
        abilities.add(new Ability("Gear Fourth - Boundman", 80, 1000, (player) -> {
            // Implement bounce physics and power boost
            // Add significant attack boost and mobility
        }));

        abilities.add(new Ability("Gear Fourth - Tankman", 80, 1000, (player) -> {
            // Implement defensive capabilities
            // Add resistance and counterattack abilities
        }));

        abilities.add(new Ability("Gear Fourth - Snakeman", 80, 1000, (player) -> {
            // Implement high-speed attacks
            // Add speed and attack combinations
        }));

        // Gear Fifth - Awakening
        abilities.add(new Ability("Gear Fifth - Joy Boy", 100, 1500, (player) -> {
            // Implement full awakening abilities
            // Add toon physics and reality manipulation
        }));

        DEVIL_FRUITS.put("gomu_gomu", new DevilFruit("Gomu Gomu no Mi", "Paramecia", abilities));
    }

    private static void registerBaraBaraNoMi() {
        List<Ability> abilities = new ArrayList<>();

        abilities.add(new Ability("Bara Bara Festival", 40, 400, (player) -> {
            // Implement body separation attacks
        }));

        DEVIL_FRUITS.put("bara_bara", new DevilFruit("Bara Bara no Mi", "Paramecia", abilities));
    }

    private static void registerOpeOpeNoMi() {
        List<Ability> abilities = new ArrayList<>();

        abilities.add(new Ability("Room", 50, 500, (player) -> {
            // Create operation room
        }));

        abilities.add(new Ability("Shambles", 30, 300, (player) -> {
            // Teleport objects/entities within room
        }));

        DEVIL_FRUITS.put("ope_ope", new DevilFruit("Ope Ope no Mi", "Paramecia", abilities));
    }

    private static void registerGoroGoroNoMi() {
        List<Ability> abilities = new ArrayList<>();

        abilities.add(new Ability("El Thor", 60, 600, (player) -> {
            // Implement lightning strike
        }));

        DEVIL_FRUITS.put("goro_goro", new DevilFruit("Goro Goro no Mi", "Logia", abilities));
    }

    private static void registerMeraMeraNoMi() {
        List<Ability> abilities = new ArrayList<>();

        abilities.add(new Ability("Hiken", 50, 500, (player) -> {
            // Implement fire fist attack
        }));

        DEVIL_FRUITS.put("mera_mera", new DevilFruit("Mera Mera no Mi", "Logia", abilities));
    }

    private static void registerHieHieNoMi() {
        List<Ability> abilities = new ArrayList<>();

        abilities.add(new Ability("Ice Age", 70, 700, (player) -> {
            // Implement freezing area effect
        }));

        DEVIL_FRUITS.put("hie_hie", new DevilFruit("Hie Hie no Mi", "Logia", abilities));
    }

    private static void registerPikaPikaNoMi() {
        List<Ability> abilities = new ArrayList<>();

        abilities.add(new Ability("Yata no Kagami", 40, 400, (player) -> {
            // Implement light speed movement
        }));

        DEVIL_FRUITS.put("pika_pika", new DevilFruit("Pika Pika no Mi", "Logia", abilities));
    }

    private static void registerHitoHitoNoMi() {
        List<Ability> abilities = new ArrayList<>();

        abilities.add(new Ability("Heavy Point", 30, 300, (player) -> {
            // Implement human form transformation
        }));

        abilities.add(new Ability("Brain Point", 20, 200, (player) -> {
            // Implement scanning ability
        }));

        DEVIL_FRUITS.put("hito_hito", new DevilFruit("Hito Hito no Mi", "Zoan", abilities));
    }

    public static class DevilFruit {
        private final String name;
        private final String type;
        private final List<Ability> abilities;

        public DevilFruit(String name, String type, List<Ability> abilities) {
            this.name = name;
            this.type = type;
            this.abilities = abilities;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public List<Ability> getAbilities() {
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

        public String getName() {
            return name;
        }

        public int getStaminaCost() {
            return staminaCost;
        }

        public int getCooldown() {
            return cooldown;
        }

        public void activate(Player player) {
            effect.apply(player);
        }
    }

    @FunctionalInterface
    public interface AbilityEffect {
        void apply(Player player);
    }

    // Utility methods
    public static DevilFruit getDevilFruit(String id) {
        return DEVIL_FRUITS.get(id);
    }

    public static boolean isValidDevilFruit(String id) {
        return DEVIL_FRUITS.containsKey(id);
    }

    public static Set<String> getAllDevilFruits() {
        return DEVIL_FRUITS.keySet();
    }
}