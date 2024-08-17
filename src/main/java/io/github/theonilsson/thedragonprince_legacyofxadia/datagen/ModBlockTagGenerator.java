package io.github.theonilsson.thedragonprince_legacyofxadia.datagen;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, LOX.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SUNFIRE_BRICKS.get(),
                        ModBlocks.SUNFIRE_STONE.get(),
                        ModBlocks.SUNFIRE_FORGE.get(),
                        ModBlocks.SUNFORGE.get(),
                        ModBlocks.SUNFIRE_STONE_STAIRS.get(),
                        ModBlocks.SUNFIRE_STONE_SLAB.get(),
                        ModBlocks.SUNFIRE_STONE_PRESSURE_PLATE.get(),
                        ModBlocks.SUNFIRE_STONE_BUTTON.get(),
                        ModBlocks.SUNFIRE_STONE_WALL.get(),
                        ModBlocks.SUNFIRE_STONE_DOOR.get(),
                        ModBlocks.SUNFIRE_STONE_TRAPDOOR.get());

        this.tag(BlockTags.WALLS)
                .add(ModBlocks.SUNFIRE_STONE_WALL.get());
    }
}
