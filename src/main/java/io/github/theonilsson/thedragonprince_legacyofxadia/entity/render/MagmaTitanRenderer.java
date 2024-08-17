package io.github.theonilsson.thedragonprince_legacyofxadia.entity.render;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.titan.MagmaTitanEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MagmaTitanRenderer extends GeoEntityRenderer<MagmaTitanEntity> {
    public MagmaTitanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MagmaTitanModel());
        this.shadowRadius = 3f;
    }


    @Override
    public ResourceLocation getTextureLocation(MagmaTitanEntity animatable) {
        return new ResourceLocation(LOX.MOD_ID, "textures/entity/magma_titain.png");
    }

    @Override
    public void render(MagmaTitanEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(2, 2, 2);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
