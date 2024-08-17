package io.github.theonilsson.thedragonprince_legacyofxadia.compat.jei;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import io.github.theonilsson.thedragonprince_legacyofxadia.recipe.SunForgeRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SunForgeCategory implements IRecipeCategory<SunForgeRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(LOX.MOD_ID, "sun_forge");
    public static final ResourceLocation TEXTURE = new ResourceLocation(LOX.MOD_ID,
            "textures/gui/sunfire_forge_gui_jei.png");

    public static final RecipeType<SunForgeRecipe> SUN_FORGE_TYPE =
            new RecipeType<>(UID, SunForgeRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SunForgeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 86);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SUNFIRE_FORGE.get()));
    }

    @Override
    public RecipeType<SunForgeRecipe> getRecipeType() {
        return SUN_FORGE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.tdp_lox.sunfire_forge");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SunForgeRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 17).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 43, 17).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 61, 17).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 35).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 43, 35).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 61, 35).addIngredients(recipe.getIngredients().get(5));
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 53).addIngredients(recipe.getIngredients().get(6));
        builder.addSlot(RecipeIngredientRole.INPUT, 43, 53).addIngredients(recipe.getIngredients().get(7));
        builder.addSlot(RecipeIngredientRole.INPUT, 61, 53).addIngredients(recipe.getIngredients().get(8));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 115, 35).addItemStack(recipe.getResultItem(null));
    }
}
