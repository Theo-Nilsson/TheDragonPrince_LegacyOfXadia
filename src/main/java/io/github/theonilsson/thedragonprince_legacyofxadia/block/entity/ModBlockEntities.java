package io.github.theonilsson.thedragonprince_legacyofxadia.block.entity;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LOX.MOD_ID);

    public static final RegistryObject<BlockEntityType<SunfireForgeBlockEntity>> SUNFIRE_FORGE_BE =
            BLOCK_ENTITIES.register("sunfire_forge_be", () ->
                    BlockEntityType.Builder.of(SunfireForgeBlockEntity::new,
                            ModBlocks.SUNFIRE_FORGE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
