package io.github.theonilsson.thedragonprince_legacyofxadia.entity.render;

import io.github.theonilsson.thedragonprince_legacyofxadia.entity.elf.SunfireElfWandererEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SunfireElfWandererModel extends GeoModel<SunfireElfWandererEntity> {

	@Override
	public ResourceLocation getModelResource(SunfireElfWandererEntity object) {
		return new ResourceLocation("tdp_lox", "geo/sunfire_elf_wanderer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SunfireElfWandererEntity object) {
		return new ResourceLocation("tdp_lox", "textures/entity/sunfire_elf_wanderer.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SunfireElfWandererEntity animatable) {
		return new ResourceLocation("tdp_lox", "animations/sunfire_elf_wanderer.animation.json");
	}
}
