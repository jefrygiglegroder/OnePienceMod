package jefry.mod.onepiecemod.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import jefry.mod.onepiecemod.Onepiecemod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Onepiecemod.MODID);

    // Register enchantments
    public static final RegistryObject<Enchantment> HAKI_INFUSION = ENCHANTMENTS.register(
            "haki_infusion", () -> new HakiInfusionEnchantment());

    public static final RegistryObject<Enchantment> SEASTONE_INFUSION = ENCHANTMENTS.register(
            "seastone_infusion", () -> new SeastoneInfusionEnchantment());

    // Haki Infusion Enchantment
    public static class HakiInfusionEnchantment extends Enchantment {
        public HakiInfusionEnchantment() {
            super(Rarity.RARE, EnchantmentCategory.WEAPON,
                    new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        }

        @Override
        public int getMinLevel() {
            return 1;
        }

        @Override
        public int getMaxLevel() {
            return 3;
        }

        @Override
        public int getMinCost(int level) {
            return 15 + (level - 1) * 10;
        }

        @Override
        public int getMaxCost(int level) {
            return getMinCost(level) + 20;
        }

        @Override
        public boolean isTreasureOnly() {
            return true;
        }

        // Incompatible with Seastone Infusion
        @Override
        protected boolean checkCompatibility(Enchantment other) {
            return super.checkCompatibility(other) &&
                    !(other instanceof SeastoneInfusionEnchantment);
        }
    }

    // Seastone Infusion Enchantment
    public static class SeastoneInfusionEnchantment extends Enchantment {
        public SeastoneInfusionEnchantment() {
            super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON,
                    new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        }

        @Override
        public int getMinLevel() {
            return 1;
        }

        @Override
        public int getMaxLevel() {
            return 2;
        }

        @Override
        public int getMinCost(int level) {
            return 20 + (level - 1) * 15;
        }

        @Override
        public int getMaxCost(int level) {
            return getMinCost(level) + 25;
        }

        @Override
        public boolean isTreasureOnly() {
            return true;
        }

        // Incompatible with Haki Infusion
        @Override
        protected boolean checkCompatibility(Enchantment other) {
            return super.checkCompatibility(other) &&
                    !(other instanceof HakiInfusionEnchantment);
        }
    }
}