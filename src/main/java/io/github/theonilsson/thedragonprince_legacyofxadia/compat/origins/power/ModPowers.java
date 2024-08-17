package io.github.theonilsson.thedragonprince_legacyofxadia.compat.origins.power;

import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.compat.origins.power.factory.ArcanumPowerFactory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPowers {
    public static final DeferredRegister<PowerFactory<?>> POWER_FACTORIES = DeferredRegister.create(ApoliRegistries.POWER_FACTORY_KEY, LOX.MOD_ID);

    public static final RegistryObject<ArcanumPowerFactory> ARCANUM = POWER_FACTORIES.register("arcanum", ArcanumPowerFactory::new);

    public static void register (IEventBus eventBus) {
        POWER_FACTORIES.register(eventBus);
    }
}
