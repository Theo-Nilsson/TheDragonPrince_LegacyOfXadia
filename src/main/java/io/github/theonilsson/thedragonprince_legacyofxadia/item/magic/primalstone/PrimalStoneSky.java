package io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.primalstone;

import io.github.theonilsson.thedragonprince_legacyofxadia.effect.ModEffects;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.event.ServerEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrimalStoneSky extends Item {
    public PrimalStoneSky(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.tdp_lox.primal_stone_sky.1"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            CompoundTag playerData = pPlayer.getPersistentData();
            ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
            if (!playerData.contains("SelectedSpell")) {
                playerData.putInt("SelectedSpell", 1);
            }

            int selectedSpell = playerData.getInt("SelectedSpell");
            Minecraft minecraft = Minecraft.getInstance();
            switch (selectedSpell) {
                case 1:
                    minecraft.gui.setOverlayMessage(Component.literal("Aspiro!"), false);
                    itemStack.hurtAndBreak(2, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                    applySpellEffect1(pLevel, pPlayer);
                    break;
                case 2:
                    minecraft.gui.setOverlayMessage(Component.literal("Fulminis!"), false);
                    itemStack.hurtAndBreak(5, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                    applySpellEffect2(pLevel, pPlayer);
                    break;
                case 3:
                    minecraft.gui.setOverlayMessage(Component.literal("Aspiro Frigis!"), false);
                    itemStack.hurtAndBreak(3, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                    applySpellEffect3(pLevel, pPlayer);
                    break;
                default:
                    minecraft.gui.setOverlayMessage(Component.literal("No Spell."), false);
                    break;
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    private void applySpellEffect1(Level level, Player player) {
        // Implement spell effect 1
        // For example, push entities away
        List<Entity> entities = getEntitiesInLookingDirection(level, player, 10.5);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && entity != player) {
                double motionX = entity.getX() - player.getX();
                double motionZ = entity.getZ() - player.getZ();
                double magnitude = Math.sqrt(motionX * motionX + motionZ * motionZ);
                double force = 2; // Adjust this value as needed
                entity.push(force * motionX / magnitude, 0.5, force * motionZ / magnitude);
            }
        }
    }

    private void applySpellEffect2(Level level, Player player) {
        List<Entity> entities = getEntitiesInLookingDirection(level, player, 10.5);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && entity != player) {
                for (int i = 0; i < 15; i++) {
                    EntityType.LIGHTNING_BOLT.spawn((ServerLevel) level, BlockPos.containing(entity.position()), MobSpawnType.TRIGGERED).addTag(ServerEvents.BOLT_DONT_DESTROY_LOOT);
                }
            }
        }
    }

    private void applySpellEffect3(Level level, Player player) {
        List<Entity> entities = getEntitiesInLookingDirection(level, player, 10.5);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity target && entity != player) {
                if (!target.hasEffect(ModEffects.HEAT_BEING_EFFECT.get())) {
                    BlockPos entityPos = target.blockPosition();
                    EntityDataProvider.getCapability(target).ifPresent(data -> data.frozenData.setFrozen(target, 900));
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 900, 2));
                    target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 900, 2));
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 2; dy++) {
                            for (int dz = -1; dz <= 1; dz++) {
                                BlockPos targetPos = entityPos.offset(dx, dy, dz);
                                if (level.getBlockState(targetPos).isAir()) {
                                    level.setBlock(targetPos, Blocks.ICE.defaultBlockState(), 3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private List<Entity> getEntitiesInLookingDirection(Level level, Player player, double radius) {
        List<Entity> entities = level.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(radius));
        return entities.stream().filter(entity -> isEntityInLookingDirection(player, entity)).toList();
    }

    private boolean isEntityInLookingDirection(Player player, Entity entity) {
        double dx = entity.getX() - player.getX();
        double dy = entity.getEyeY() - player.getEyeY();
        double dz = entity.getZ() - player.getZ();
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (distance > 0) {
            dx /= distance;
            dy /= distance;
            dz /= distance;
        }

        double dotProduct = dx * player.getLookAngle().x + dy * player.getLookAngle().y + dz * player.getLookAngle().z;
        return dotProduct > 0.5;
    }
}
