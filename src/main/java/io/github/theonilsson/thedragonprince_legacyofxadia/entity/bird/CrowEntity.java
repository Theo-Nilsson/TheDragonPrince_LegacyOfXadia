package io.github.theonilsson.thedragonprince_legacyofxadia.entity.bird;

import io.github.theonilsson.thedragonprince_legacyofxadia.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CrowEntity extends Animal implements GeoAnimatable, FlyingAnimal {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private Vec3 originalPosition; // To store the original position

    public CrowEntity(EntityType<? extends Animal> entityType, Level level) {
        super(ModEntities.CROW.get(), level);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.FLYING_SPEED, 0.4d)
                .add(Attributes.MOVEMENT_SPEED, 0.2d)
                .add(Attributes.MAX_HEALTH, 10.0d)
                .add(Attributes.FOLLOW_RANGE, 24.0d)
                .build();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.CROW.get().create(serverLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.WHEAT_SEEDS);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(
                new AnimationController<>(this, "predicate", 0, this::predicate)
        );
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.crow.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(RawAnimation.begin().then("animation.crow.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return this.tickCount;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0));
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack bundleStack = stack.copy();

        if (!this.level().isClientSide()) {
            if (!stack.isEmpty() && stack.hasCustomHoverName()) {
                if (stack.getItem() == Items.BUNDLE) {
                    Component customName = stack.getHoverName();
                    String targetPlayerName = customName.getString();
                    ServerPlayer targetPlayer = this.level().getServer().getPlayerList().getPlayerByName(targetPlayerName);

                    if (targetPlayer != null) {

                        this.originalPosition = this.position();

                        this.teleportTo(targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ());

                        bundleStack.setCount(1);
                        this.spawnAtLocation(bundleStack);

                        dropBundleContents(stack, player);
                        stack.shrink(1);

                        player.displayClientMessage(Component.translatable("crow.tdp_lox.bundle.deliver.success", targetPlayer.getDisplayName()), true);

                        Executors.newScheduledThreadPool(1).schedule(() -> {
                            if (this.originalPosition != null) {
                                this.teleportTo(this.originalPosition.x, this.originalPosition.y, this.originalPosition.z);
                            }
                        }, 5, TimeUnit.SECONDS);
                        return InteractionResult.CONSUME;
                    }
                } else {
                    player.displayClientMessage(Component.translatable("crow.tdp_lox.bundle.deliver.fail"), true);
                }
            }
        }

        return super.mobInteract(player, hand);
    }

    private static boolean dropBundleContents(ItemStack pStack, Player pPlayer) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (!compoundtag.contains("Items")) {
            return false;
        } else {
            pStack.removeTagKey("Items");
            return true;
        }
    }
}
