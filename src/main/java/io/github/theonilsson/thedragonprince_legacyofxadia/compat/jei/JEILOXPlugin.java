package io.github.theonilsson.thedragonprince_legacyofxadia.compat.jei;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import io.github.theonilsson.thedragonprince_legacyofxadia.recipe.SunForgeRecipe;
import io.github.theonilsson.thedragonprince_legacyofxadia.screen.SunfireForgeScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEILOXPlugin implements IModPlugin {
    private void addDescription(IRecipeRegistration registry, ItemStack itemStack, String translateableDescription) {
        registry.addIngredientInfo(itemStack, VanillaTypes.ITEM_STACK, Component.translatable(translateableDescription));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(LOX.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SunForgeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<SunForgeRecipe> forgingRecipes = recipeManager.getAllRecipesFor(SunForgeRecipe.Type.INSTANCE);
        registration.addRecipes(SunForgeCategory.SUN_FORGE_TYPE, forgingRecipes);

        //addDescription(registration, new ItemStack(ModItems.SUN_INGOT.get()), "jei.tdp_lox.test");
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SunfireForgeScreen.class, 83, 36, 22, 15,
                SunForgeCategory.SUN_FORGE_TYPE);
    }
}
