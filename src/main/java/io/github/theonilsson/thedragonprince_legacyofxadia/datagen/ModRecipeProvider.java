package io.github.theonilsson.thedragonprince_legacyofxadia.datagen;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import io.github.theonilsson.thedragonprince_legacyofxadia.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CORK.get())
                .pattern("BB")
                .pattern("BB")
                .define('B', ModItems.BARK.get())
                .unlockedBy(getHasName(ModItems.BARK.get()), has(ModItems.BARK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMPTY_BOTTLE.get())
                .pattern("#X#")
                .pattern("# #")
                .pattern("###")
                .define('#', Items.GLASS)
                .define('X', ModItems.CORK.get())
                .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRERUNE.get())
                .pattern("###")
                .pattern("ZXZ")
                .pattern("###")
                .define('#', ModItems.SUN_INGOT.get())
                .define('Z', IafItemRegistry.DRAGONSTEEL_FIRE_INGOT.get())
                .define('X', IafItemRegistry.FIRE_DRAGON_BLOOD.get())
                .unlockedBy(getHasName(ModItems.SUN_INGOT.get()), has(ModItems.SUN_INGOT.get()))
                .unlockedBy(getHasName(IafItemRegistry.DRAGONSTEEL_FIRE_INGOT.get()), has(IafItemRegistry.DRAGONSTEEL_FIRE_INGOT.get()))
                .unlockedBy(getHasName(IafItemRegistry.FIRE_DRAGON_BLOOD.get()), has(IafItemRegistry.FIRE_DRAGON_BLOOD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MORTAR_AND_PESTLE.get(), 4)
                .pattern("#Y#")
                .pattern("###")
                .define('#', Items.COBBLESTONE)
                .define('Y', Items.STONE_SHOVEL)
                .unlockedBy(getHasName(Items.COBBLESTONE), has(Items.COBBLESTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SUN_INGOT.get())
                .pattern("###")
                .pattern("#Y#")
                .pattern("###")
                .define('#', ModItems.TINTED_LIGHT_BOTTLE.get())
                .define('Y', ModTags.Items.INGOT);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_STONE.get())
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.SUN_INGOT.get())
                .unlockedBy(getHasName(ModItems.SUN_INGOT.get()), has(ModItems.SUN_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_STONE_STAIRS.get(), 4)
                .pattern("  #")
                .pattern(" ##")
                .pattern("###")
                .define('#', ModBlocks.SUNFIRE_STONE.get())
                .unlockedBy(getHasName(ModBlocks.SUNFIRE_STONE.get()), has(ModBlocks.SUNFIRE_STONE.get()))
                .save(consumer, LOX.MOD_ID + ":sunfire_stone_stairs_left_to_right");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_STONE_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.SUNFIRE_STONE.get())
                .unlockedBy(getHasName(ModBlocks.SUNFIRE_STONE.get()), has(ModBlocks.SUNFIRE_STONE.get()))
                .save(consumer, LOX.MOD_ID + ":sunfire_stone_stairs_right_to_left");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_STONE_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.SUNFIRE_STONE.get())
                .unlockedBy(getHasName(ModBlocks.SUNFIRE_STONE.get()), has(ModBlocks.SUNFIRE_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_STONE_BUTTON.get())
                .requires(ModBlocks.SUNFIRE_STONE.get())
                .unlockedBy(getHasName(ModBlocks.SUNFIRE_STONE.get()), has(ModBlocks.SUNFIRE_STONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_STONE_PRESSURE_PLATE.get())
                .pattern("##")
                .define('#', ModBlocks.SUNFIRE_STONE.get())
                .unlockedBy(getHasName(ModBlocks.SUNFIRE_STONE.get()), has(ModBlocks.SUNFIRE_STONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_STONE_WALL.get(), 9)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.SUNFIRE_STONE.get())
                .unlockedBy(getHasName(ModBlocks.SUNFIRE_STONE.get()), has(ModBlocks.SUNFIRE_STONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_STONE_DOOR.get(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.SUNFIRE_STONE.get())
                .unlockedBy(getHasName(ModBlocks.SUNFIRE_STONE.get()), has(ModBlocks.SUNFIRE_STONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_STONE_TRAPDOOR.get(), 2)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.SUNFIRE_STONE.get())
                .unlockedBy(getHasName(ModBlocks.SUNFIRE_STONE.get()), has(ModBlocks.SUNFIRE_STONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_BRICKS.get())
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.SUNFIRE_STONE.get())
                .unlockedBy(getHasName(ModBlocks.SUNFIRE_STONE.get()), has(ModBlocks.SUNFIRE_STONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNFIRE_FORGE.get())
                .pattern("#Y#")
                .pattern("###")
                .pattern("XXX")
                .define('#', Blocks.BLACKSTONE)
                .define('Y', Items.LAVA_BUCKET)
                .define('X', ModBlocks.SUNFIRE_BRICKS.get())
                .unlockedBy(getHasName(Items.LAVA_BUCKET), has(Items.LAVA_BUCKET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TINTED_EMPTY_BOTTLE.get())
                .pattern("#X#")
                .pattern("# #")
                .pattern("###")
                .define('#', Items.TINTED_GLASS)
                .define('X', ModItems.CORK.get())
                .unlockedBy(getHasName(Items.TINTED_GLASS), has(Items.TINTED_GLASS))
                .save(consumer, LOX.MOD_ID + ":tinted_empty_bottle_from_tinted_glass");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TINTED_EMPTY_BOTTLE.get())
                .pattern("#X#")
                .pattern("#Y#")
                .pattern("###")
                .define('#', Items.GLASS)
                .define('X', ModItems.CORK.get())
                .define('Y', Items.AMETHYST_SHARD)
                .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                .save(consumer, LOX.MOD_ID + ":tinted_empty_bottle_from_amethyst_shard");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.BUNDLE)
                .pattern("XYX")
                .pattern("Y Y")
                .pattern("YYY")
                .define('X', Items.STRING)
                .define('Y', Items.LEATHER)
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(consumer, LOX.MOD_ID + ":bundle_from_leather");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.BUNDLE)
                .pattern("XYX")
                .pattern("Y Y")
                .pattern("YYY")
                .define('X', Items.STRING)
                .define('Y', Items.RABBIT_HIDE)
                .unlockedBy(getHasName(Items.RABBIT_HIDE), has(Items.LEATHER))
                .save(consumer, LOX.MOD_ID + ":bundle_from_rabbit_hide");

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.EMPTY_PRIMAL_STONE.get())
                .pattern("XZX")
                .pattern("ZYZ")
                .pattern("XZX")
                .define('X', Items.LAPIS_LAZULI)
                .define('Y', Items.QUARTZ)
                .define('Z', Items.OBSIDIAN)
                .unlockedBy(getHasName(Items.LAPIS_LAZULI), has(Items.LAPIS_LAZULI))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.PRIMAL_STONE_SUN.get())
                .pattern("XZX")
                .pattern("ZYZ")
                .pattern("XZX")
                .define('X', Items.ANCIENT_DEBRIS)
                .define('Y', ModItems.EMPTY_PRIMAL_STONE.get())
                .define('Z', ModItems.TINTED_LIGHT_BOTTLE.get())
                .unlockedBy(getHasName(ModItems.EMPTY_PRIMAL_STONE.get()), has(ModItems.EMPTY_PRIMAL_STONE.get()))
                .save(consumer);

        /*ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WITHERSTEEL_ROD.get(), 4)
                .pattern("#")
                .pattern("#")
                .define('#', ModItems.WITHERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.WITHERSTEEL_INGOT.get()), has(ModItems.WITHERSTEEL_INGOT.get()))
                .save(consumer);*/

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLD_ROD.get(), 4)
                .pattern("#")
                .pattern("#")
                .define('#', Items.GOLD_INGOT)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SUN_STAFF.get())
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .define('X', ModItems.PRIMAL_STONE_SUN.get())
                .define('#', ModItems.GOLD_ROD.get())
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MAGNIFYING_GLASS.get())
                .pattern("  X")
                .pattern(" # ")
                .pattern("#  ")
                .define('X', Items.GLASS_PANE)
                .define('#', Items.STICK)
                .unlockedBy(getHasName(Items.GLASS_PANE), has(Items.GLASS_PANE))
                .save(consumer);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer, LOX.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
