package io.github.theonilsson.thedragonprince_legacyofxadia.entity.elf;

import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import java.util.OptionalInt;
import java.util.UUID;

public class SunfireElfWandererEntity extends AgeableMob implements NeutralMob, GeoAnimatable, Merchant {
    private static final UniformInt PERSISTENT_ANGER_TIME;
    private int remainingPersistentAngerTime;
    @Nullable private UUID persistentAngerTarget;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final MerchantOffers offers = new MerchantOffers();
    private Player tradingPlayer;

    public SunfireElfWandererEntity(EntityType<? extends AgeableMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        this.offers.add(new MerchantOffer(new ItemStack(ModItems.SCORCH_BERRIES.get(), 9), new ItemStack(ModItems.SUN_INGOT.get(), 1), 16, 5, 0.05f));
        this.offers.add(new MerchantOffer(new ItemStack(ModItems.SUN_INGOT.get(), 5), new ItemStack(ModItems.SCONES.get(), 1), 16, 5, 0.05f));
        this.offers.add(new MerchantOffer(new ItemStack(ModItems.SUN_INGOT.get(), 7), new ItemStack(ModItems.SCORCH_BERRY_SCONES.get(), 1), 16, 5, 0.05f));
        this.offers.add(new MerchantOffer(new ItemStack(ModItems.SUN_INGOT.get(), 15), new ItemStack(ModBlocks.SUNFIRE_STONE.get(), 16), 16, 5, 0.05f));
        this.offers.add(new MerchantOffer(new ItemStack(ModItems.SUN_INGOT.get(), 23), new ItemStack(ModBlocks.SUNFIRE_BRICKS.get(), 16), 16, 5, 0.05f));
        this.offers.add(new MerchantOffer(new ItemStack(ModItems.SUN_INGOT.get(), 34), new ItemStack(Items.EMERALD, 23), 16, 5, 0.05f));
        this.offers.add(new MerchantOffer(new ItemStack(ModItems.SUN_INGOT.get(), 63), new ItemStack(ModItems.SUNFIREBLADE.get(), 1), 1, 5, 0.05f));
        this.offers.add(new MerchantOffer(new ItemStack(ModItems.SUN_INGOT.get(), 63), new ItemStack(ModBlocks.SUNFORGE.get(), 1), 1, 5, 0.05f));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, LivingEntity.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
        super.registerGoals();
    }

    public static AttributeSupplier setAttributes() {
        return AgeableMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 40.0d)
                .add(Attributes.ATTACK_DAMAGE, 6.0d)
                .add(Attributes.MOVEMENT_SPEED, 0.35d)
                .add(Attributes.FOLLOW_RANGE, 64.0d)
                .add(Attributes.ATTACK_KNOCKBACK, 3.0d)
                .build();
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int pTime) {
        this.remainingPersistentAngerTime = pTime;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    @Override
    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.sunfire_elf_wanderer.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends GeoAnimatable> PlayState attackPredicate(AnimationState<E> event) {
        if (this.swinging) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().then("animation.sunfire_elf_wanderer.attack", Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object o) {
        // Return the current tick or time as needed for animation logic
        return this.tickCount;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null; // Implement breeding logic if necessary
    }

    static {
        PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) {
            return new ItemStack(ModItems.ELF_HORNS.get());
        }
        return super.getItemBySlot(slot);
    }

    @Override
    public void onAddedToWorld() {
        if (!getCommandSenderWorld().isClientSide()) {
            this.setItemSlot(EquipmentSlot.HEAD, getItemBySlot(EquipmentSlot.HEAD));
        }
        super.onAddedToWorld();
    }

    @Override
    public void setTradingPlayer(Player player) {
        if (player != null) {
            this.tradingPlayer = player;
            openTradingScreen(player, this.getName(), this.getVillagerXp());
        }
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    @Override
    public MerchantOffers getOffers() {
        return this.offers;
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        // Handle what happens when a trade is made
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {
        // Handle updates to trade offers
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int i) {

    }

    @Override
    public void overrideOffers(MerchantOffers offers) {
        this.offers.clear();
        this.offers.addAll(offers);
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return null;
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide();
    }

    @Nonnull
    @Override
    public Component getName() {
        return this.hasCustomName() ? this.getCustomName() : Component.translatable("entity.tdp_lox.sunfire_elf_wanderer");
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (!this.level().isClientSide()) {
            this.setTradingPlayer(pPlayer);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide());
    }

    public void openTradingScreen(Player pPlayer, Component pDisplayName, int pLevel) {
        OptionalInt $$3 = pPlayer.openMenu(new SimpleMenuProvider((p_45298_, p_45299_, p_45300_) -> {
            return new MerchantMenu(p_45298_, p_45299_, this);
        }, pDisplayName));
        if ($$3.isPresent()) {
            MerchantOffers $$4 = this.getOffers();
            if (!$$4.isEmpty()) {
                pPlayer.sendMerchantOffers($$3.getAsInt(), $$4, pLevel, this.getVillagerXp(), this.showProgressBar(), this.canRestock());
            }
        }

    }
}
