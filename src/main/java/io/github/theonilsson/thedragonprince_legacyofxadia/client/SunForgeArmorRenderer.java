package io.github.theonilsson.thedragonprince_legacyofxadia.item.client;

import io.github.theonilsson.thedragonprince_legacyofxadia.item.armor.SunForgeArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SunForgeArmorRenderer extends GeoArmorRenderer<SunForgeArmorItem> {
    public SunForgeArmorRenderer() {
        super(new SunForgeArmorModel());
    }
}
