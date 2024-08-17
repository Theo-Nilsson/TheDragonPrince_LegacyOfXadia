package io.github.theonilsson.thedragonprince_legacyofxadia.recipe;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LOX.MOD_ID);

    public static final RegistryObject<RecipeSerializer<SunForgeRecipe>> SUNFIRE_FORGE_SERIALIZER =
            SERIALIZERS.register("sunfire_forging", () -> SunForgeRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
