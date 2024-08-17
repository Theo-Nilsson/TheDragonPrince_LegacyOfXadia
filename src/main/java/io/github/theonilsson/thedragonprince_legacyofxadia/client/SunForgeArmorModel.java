package io.github.theonilsson.thedragonprince_legacyofxadia.item.client;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.armor.SunForgeArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SunForgeArmorModel extends GeoModel<SunForgeArmorItem> {
    @Override
    public ResourceLocation getModelResource(SunForgeArmorItem sunForgeArmorItem) {
        return new ResourceLocation(LOX.MOD_ID, "geo/sunfire_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SunForgeArmorItem sunForgeArmorItem) {
        return new ResourceLocation(LOX.MOD_ID, "textures/armor/sunfire_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SunForgeArmorItem sunForgeArmorItem) {
        return new ResourceLocation(LOX.MOD_ID, "animations/sunfire_armor.animation.json");
    }
}
