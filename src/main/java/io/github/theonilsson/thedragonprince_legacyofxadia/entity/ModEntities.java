package io.github.theonilsson.thedragonprince_legacyofxadia.entity;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.bird.CrowEntity;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.decor.ChairEntity;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.elf.SunfireElfWandererEntity;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.projectile.NoctuIgneProjectile;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.spell.CirculusLuminisEntity;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.titan.MagmaTitanEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = LOX.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LOX.MOD_ID);

    public static final RegistryObject<EntityType<NoctuIgneProjectile>> NOCTU_IGNE = ENTITIES.register("noctu_igne", () ->
            EntityType.Builder.<NoctuIgneProjectile>of(NoctuIgneProjectile::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F)
                    .fireImmune()
                    .build(new ResourceLocation(LOX.MOD_ID, "noctu_igne").toString()));

    public static final RegistryObject<EntityType<MagmaTitanEntity>> MAGMA_TITAN = ENTITIES.register("magma_titan", () ->
            EntityType.Builder.<MagmaTitanEntity>of(MagmaTitanEntity::new, MobCategory.MONSTER)
                    .sized(4f, 10f)
                    .fireImmune()
                    .build(new ResourceLocation(LOX.MOD_ID, "magma_titan").toString()));

    public static final RegistryObject<EntityType<SunfireElfWandererEntity>> SUNFIRE_ELF_WANDERER = ENTITIES.register("sunfire_elf_wanderer", () ->
            EntityType.Builder.<SunfireElfWandererEntity>of(SunfireElfWandererEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.9f)
                    .fireImmune()
                    .build(new ResourceLocation(LOX.MOD_ID, "sunfire_elf_wanderer").toString()));

    public static final RegistryObject<EntityType<CrowEntity>> CROW = ENTITIES.register("crow", () ->
            EntityType.Builder.<CrowEntity>of(CrowEntity::new, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(new ResourceLocation(LOX.MOD_ID, "crow").toString()));

    public static final RegistryObject<EntityType<CirculusLuminisEntity>> CIRCULUS_LUMINIS = ENTITIES.register("circulus_luminis", () ->
            EntityType.Builder.<CirculusLuminisEntity>of(CirculusLuminisEntity::new, MobCategory.CREATURE)
                    .sized(0.3f, 0.4f)
                    .build(new ResourceLocation(LOX.MOD_ID, "circulus_luminis").toString()));

    public static final RegistryObject<EntityType<ChairEntity>> CHAIR_ENTITY = ENTITIES.register("chair_entity",
            () -> EntityType.Builder.<ChairEntity>of(ChairEntity::new, MobCategory.MISC)
                    .sized(0.001F, 0.001F)
                    .build(new ResourceLocation(LOX.MOD_ID, "chair_entity").toString()));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
