package io.github.theonilsson.thedragonprince_legacyofxadia.datagen;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LOX.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.SUNFIRE_BRICKS);
        blockWithItem(ModBlocks.SUNFIRE_STONE);
        //blockWithItem(ModBlocks.SUNFIRE_FORGE);

        blockWithItem(ModBlocks.SUNFORGE);
        stairsBlock(((StairBlock) ModBlocks.SUNFIRE_STONE_STAIRS.get()), blockTexture(ModBlocks.SUNFIRE_STONE.get()));
        slabBlock(((SlabBlock) ModBlocks.SUNFIRE_STONE_SLAB.get()), blockTexture(ModBlocks.SUNFIRE_STONE.get()), blockTexture(ModBlocks.SUNFIRE_STONE.get()));

        buttonBlock((ButtonBlock) ModBlocks.SUNFIRE_STONE_BUTTON.get(), blockTexture(ModBlocks.SUNFIRE_STONE.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.SUNFIRE_STONE_PRESSURE_PLATE.get()), blockTexture(ModBlocks.SUNFIRE_STONE.get()));

        wallBlock((WallBlock) ModBlocks.SUNFIRE_STONE_WALL.get(), blockTexture(ModBlocks.SUNFIRE_STONE.get()));

        doorBlockWithRenderType((DoorBlock) ModBlocks.SUNFIRE_STONE_DOOR.get(), modLoc("block/sunfire_stone_door_bottom"), modLoc("block/sunfire_stone_door_top"), "cutout");
        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.SUNFIRE_STONE_TRAPDOOR.get(), modLoc("block/sunfire_stone_trapdoor"), true, "cutout");
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
