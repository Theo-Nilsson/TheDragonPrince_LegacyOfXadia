package io.github.theonilsson.thedragonprince_legacyofxadia.entity.render;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.spell.CirculusLuminisEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CirculusLuminisRenderer extends GeoEntityRenderer<CirculusLuminisEntity> {
    public CirculusLuminisRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CirculusLuminisModel());
        this.shadowRadius = 0f;
    }

    @Override
    public ResourceLocation getTextureLocation(CirculusLuminisEntity animatable) {
        return new ResourceLocation(LOX.MOD_ID, "textures/entity/circulus_luminis.png");
    }
}
