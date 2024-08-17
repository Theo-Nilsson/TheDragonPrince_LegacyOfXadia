package io.github.theonilsson.thedragonprince_legacyofxadia.entity.render;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class CrowModel extends GeoModel {
    @Override
    public ResourceLocation getModelResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(LOX.MOD_ID, "geo/crow.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(LOX.MOD_ID, "textures/entity/crow.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(LOX.MOD_ID, "animations/crow.animation.json");
    }
}
