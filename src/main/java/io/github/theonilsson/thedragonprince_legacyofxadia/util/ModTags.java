package io.github.theonilsson.thedragonprince_legacyofxadia.util;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeMod;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_SUNFORGE_TOOL = tag("needs_sunforge_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(LOX.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> REPAIR_SUNFORGE_TOOL = tag("repair_sunforge_tool");
        public static final TagKey<Item> INGOT = tag("ingot");
        public static final TagKey<Item> STEEL = tag("forge", "ingots/steel");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(LOX.MOD_ID, name));
        }
        private static TagKey<Item> tag(String id, String name) {
            return ItemTags.create(new ResourceLocation(id, name));
        }
    }
}
