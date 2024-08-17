package io.github.theonilsson.thedragonprince_legacyofxadia.effect;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.effect.race.HeatBeingEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, LOX.MOD_ID);

    public static final RegistryObject<HeatBeingEffect> HEAT_BEING_EFFECT = EFFECTS.register("heat_being",
            () -> new HeatBeingEffect(MobEffectCategory.BENEFICIAL, Color.RED.getRGB()));

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
