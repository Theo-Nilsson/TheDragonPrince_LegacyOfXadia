package io.github.theonilsson.thedragonprince_legacyofxadia.entity.render;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.titan.MagmaTitanEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MagmaTitanModel extends GeoModel<MagmaTitanEntity> {
    @Override
    public ResourceLocation getModelResource(MagmaTitanEntity magmaTitanEntity) {
        return new ResourceLocation(LOX.MOD_ID, "geo/magma_titain.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MagmaTitanEntity magmaTitanEntity) {
        return new ResourceLocation(LOX.MOD_ID, "textures/entity/magma_titain.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MagmaTitanEntity magmaTitanEntity) {
        return new ResourceLocation(LOX.MOD_ID, "animations/magma_titain.animation.json");
    }

    @Override
    public void setCustomAnimations(MagmaTitanEntity animatable, long instanceId, AnimationState<MagmaTitanEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("body").getChildBones().get(0).getChildBones().get(0).getChildBones().get(0).getChildBones().get(0);

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
