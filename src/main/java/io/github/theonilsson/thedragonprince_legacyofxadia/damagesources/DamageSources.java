package io.github.theonilsson.thedragonprince_legacyofxadia.damagesources;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class DamageSources {
    public static final ResourceKey<DamageType> SCORCH_BERRY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(LOX.MOD_ID, "scorch_berry"));

    public static void bootstrap(BootstapContext<DamageType> context) { context.register(SCORCH_BERRY, new DamageType("scorch_berry", 0.1f)); }
}