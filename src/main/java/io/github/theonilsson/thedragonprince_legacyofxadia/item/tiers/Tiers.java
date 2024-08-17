package io.github.theonilsson.thedragonprince_legacyofxadia.item.tiers;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class Tiers {
    public static final Tier SUNFORGE = TierSortingRegistry.registerTier(
            new ForgeTier(9, 2546, 10.0F, 6.0F, 17,
                    ModTags.Blocks.NEEDS_SUNFORGE_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(LOX.MOD_ID, "sunforge"), List.of(net.minecraft.world.item.Tiers.NETHERITE), List.of()
    );
}
