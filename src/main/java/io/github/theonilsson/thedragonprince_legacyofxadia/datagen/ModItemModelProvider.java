package io.github.theonilsson.thedragonprince_legacyofxadia.datagen;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LOX.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.FIRERUNE);
        simpleItem(ModItems.STEEL_INGOT);
        simpleItem(ModItems.SUN_INGOT);
        simpleItem(ModItems.WITHERSTEEL_INGOT);
        simpleItem(ModItems.WITHERSTEEL_ROD);
        simpleItem(ModItems.EMPTY_BOTTLE);
        simpleItem(ModItems.TINTED_EMPTY_BOTTLE);
        simpleItem(ModItems.TINTED_LIGHT_BOTTLE);
        simpleItem(ModItems.PRIMAL_STONE_SKY);
        simpleItem(ModItems.PRIMAL_STONE_SUN);
        simpleItem(ModItems.SUNFORGE_HELMET);
        simpleItem(ModItems.SUNFORGE_CHESTPLATE);
        simpleItem(ModItems.SUNFORGE_LEGGINGS);
        simpleItem(ModItems.SUNFORGE_BOOTS);
        simpleItem(ModItems.BARK);
        simpleItem(ModItems.CORK);
        simpleItem(ModItems.PARCHMENT);
        simpleItem(ModItems.SPELL_PARCHMENT);
        simpleItem(ModItems.OCARINA);
        simpleItem(ModItems.MAGMA_HEART);
        simpleItem(ModItems.SCORCH_BERRIES);
        simpleItem(ModItems.FINISHED_SCORCH_BERRIES);
        simpleItem(ModItems.MORTAR_AND_PESTLE);
        simpleItem(ModItems.FLOUR);
        simpleItem(ModItems.SCONES);
        simpleItem(ModItems.SCORCH_BERRY_SCONES);
        simpleItem(ModItems.STRAINER);
        simpleItem(ModItems.IGNIS_IACTO);
        simpleItem(ModItems.EMPTY_PRIMAL_STONE);
        simpleItem(ModItems.GOLD_ROD);

        simpleBlockItem(ModBlocks.SUNFIRE_STONE_DOOR);
        wallItem(ModBlocks.SUNFIRE_STONE_WALL, ModBlocks.SUNFIRE_STONE);
        buttonItem(ModBlocks.SUNFIRE_STONE_BUTTON, ModBlocks.SUNFIRE_STONE);

        evenSimplerBlockItem(ModBlocks.SUNFIRE_STONE_STAIRS);
        evenSimplerBlockItem(ModBlocks.SUNFIRE_STONE_SLAB);
        evenSimplerBlockItem(ModBlocks.SUNFIRE_STONE_PRESSURE_PLATE);

        trapdoorItem(ModBlocks.SUNFIRE_STONE_TRAPDOOR);
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(LOX.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(LOX.MOD_ID, "item/" + item.getId().getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(LOX.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(LOX.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(LOX.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(LOX.MOD_ID,"item/" + item.getId().getPath()));
    }
}
