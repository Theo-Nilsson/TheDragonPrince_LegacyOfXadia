package io.github.theonilsson.thedragonprince_legacyofxadia.entity.spell;

import io.github.theonilsson.thedragonprince_legacyofxadia.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.UUID;

public class CirculusLuminisEntity extends Entity implements GeoAnimatable {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private UUID ownerUUID;
    private BlockPos previousLightPos;

    public CirculusLuminisEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("OwnerUUID")) {
            this.ownerUUID = compoundTag.getUUID("OwnerUUID");
        }
        if (compoundTag.contains("LightPos")) {
            this.previousLightPos = BlockPos.of(compoundTag.getLong("LightPos"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        if (this.ownerUUID != null) {
            compoundTag.putUUID("OwnerUUID", this.ownerUUID);
        }
        if (this.previousLightPos != null) {
            compoundTag.putLong("LightPos", this.previousLightPos.asLong());
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        event.getController().setAnimation(RawAnimation.begin().then("animation.circulus_luminis.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object o) {
        return this.tickCount;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            updateLightPosition();
        }

        if (getOwner() != null && getOwner() instanceof Player player && !this.level().isClientSide()) {
            this.teleportTo(player.getX(), player.getY(), player.getZ());
        }
    }

    public void setOwner(LivingEntity owner) {
        this.ownerUUID = owner.getUUID();
    }

    public LivingEntity getOwner() {
        if (this.ownerUUID == null) {
            return null;
        }
        if (this.level() instanceof ServerLevel) {
            Entity entity = ((ServerLevel) this.level()).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity) {
                return (LivingEntity) entity;
            }
        }
        return null;
    }

    private void updateLightPosition() {
        BlockPos currentPos = this.blockPosition();
        if (previousLightPos != null && !previousLightPos.equals(currentPos)) {
            this.level().setBlockAndUpdate(previousLightPos, Blocks.AIR.defaultBlockState());
        }

        BlockState lightBlock = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15);
        this.level().setBlockAndUpdate(currentPos, lightBlock);

        previousLightPos = currentPos;
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (previousLightPos != null) {
            this.level().setBlockAndUpdate(previousLightPos, Blocks.AIR.defaultBlockState());
        }
    }
}
