package jefry.mod.onepiecemod.registry;

import jefry.mod.onepiecemod.Onepiecemod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> HAKI_ITEMS = createItemTag("haki_items");
        public static final TagKey<Item> SEASTONE_ITEMS = createItemTag("seastone_items");
        public static final TagKey<Item> DEVIL_FRUITS = createItemTag("devil_fruits");
        public static final TagKey<Item> HAKI_CRAFTABLE = createItemTag("haki_craftable");
        public static final TagKey<Item> SEASTONE_CRAFTABLE = createItemTag("seastone_craftable");

        private static TagKey<Item> createItemTag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(Onepiecemod.MODID, name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> SEASTONE_BLOCKS = createBlockTag("seastone_blocks");
        public static final TagKey<Block> HAKI_INFUSED_BLOCKS = createBlockTag("haki_infused_blocks");

        private static TagKey<Block> createBlockTag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(Onepiecemod.MODID, name));
        }
    }
}