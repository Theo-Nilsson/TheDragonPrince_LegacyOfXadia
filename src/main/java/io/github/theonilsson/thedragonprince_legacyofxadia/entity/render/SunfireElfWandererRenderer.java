package io.github.theonilsson.thedragonprince_legacyofxadia.entity.render;

import io.github.theonilsson.thedragonprince_legacyofxadia.entity.elf.SunfireElfWandererEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SunfireElfWandererRenderer extends GeoEntityRenderer<SunfireElfWandererEntity> {

    public SunfireElfWandererRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SunfireElfWandererModel());
        this.shadowRadius = 0.7F; // Set shadow size
    }

    @Override
    public ResourceLocation getTextureLocation(SunfireElfWandererEntity instance) {
        return new ResourceLocation("tdp_lox", "textures/entity/sunfire_elf_wanderer.png");
    }
}
