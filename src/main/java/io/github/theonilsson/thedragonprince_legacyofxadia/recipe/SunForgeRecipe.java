package io.github.theonilsson.thedragonprince_legacyofxadia.recipe;

import com.google.gson.JsonElement;
import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

public class SunForgeRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public SunForgeRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        for (int i = 0; i < 9; i++) {
            Ingredient ingredient = inputItems.get(i);
            if (!ingredient.isEmpty() && !ingredient.test(container.getItem(i))) {
                return false;
            }

            if (ingredient.isEmpty() && !container.getItem(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SunForgeRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "sunfire_forging";
    }

    public static class Serializer implements RecipeSerializer<SunForgeRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(LOX.MOD_ID, "sunfire_forging");

        @Override
        public SunForgeRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(9, Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++) {
                JsonElement element = ingredients.get(i);
                inputs.set(i, element.isJsonObject() && element.getAsJsonObject().entrySet().isEmpty()
                        ? Ingredient.EMPTY
                        : Ingredient.fromJson(element));
            }

            return new SunForgeRecipe(inputs, output, resourceLocation);
        }

        @Override
        public @Nullable SunForgeRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(friendlyByteBuf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(friendlyByteBuf));
            }

            ItemStack output = friendlyByteBuf.readItem();
            return new SunForgeRecipe(inputs, output, resourceLocation);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SunForgeRecipe sunForgeRecipe) {
            pBuffer.writeInt(sunForgeRecipe.inputItems.size());

            for (Ingredient ingredient : sunForgeRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(sunForgeRecipe.getResultItem(null), false);
        }
    }
}
