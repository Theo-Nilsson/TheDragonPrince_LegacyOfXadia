package io.github.theonilsson.thedragonprince_legacyofxadia.event;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.ModEntities;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.bird.CrowEntity;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.elf.SunfireElfWandererEntity;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.titan.MagmaTitanEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LOX.MOD_ID)
public class ModEvents {
    @Mod.EventBusSubscriber(modid = LOX.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntities.MAGMA_TITAN.get(), MagmaTitanEntity.setAttributes());
            event.put(ModEntities.SUNFIRE_ELF_WANDERER.get(), SunfireElfWandererEntity.setAttributes());
            event.put(ModEntities.CROW.get(), CrowEntity.setAttributes());
        }
    }
}
