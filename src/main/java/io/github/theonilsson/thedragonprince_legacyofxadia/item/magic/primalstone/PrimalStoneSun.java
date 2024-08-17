package io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.primalstone;

import com.github.alexthe666.iceandfire.event.ServerEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrimalStoneSun extends Item {
    public PrimalStoneSun(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.tdp_lox.primal_stone_sun.1"));
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
                    minecraft.gui.setOverlayMessage(Component.literal("Calor Obscurans!"), false);
                    itemStack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                    applySpellEffect1(pLevel, pPlayer);
                    break;
                case 2:
                    minecraft.gui.setOverlayMessage(Component.literal("Fulmen Ignem!"), false);
                    itemStack.hurtAndBreak(5, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                    applySpellEffect2(pLevel, pPlayer);
                    break;
                case 3:
                    minecraft.gui.setOverlayMessage(Component.literal("Missilem Ignem!"), false);
                    itemStack.hurtAndBreak(2, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
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
        List<Entity> entities = getEntitiesInLookingDirection(level, player, 10.5);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && entity != player) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 300, 0, true, false));
                entity.setSecondsOnFire(15);
            }
        }
    }

    private void applySpellEffect2(Level level, Player player) {
        List<Entity> entities = getEntitiesInLookingDirection(level, player, 10.5);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && entity != player) {
                EntityType.LIGHTNING_BOLT.spawn((ServerLevel) level, BlockPos.containing(entity.position()), MobSpawnType.TRIGGERED).addTag(ServerEvents.BOLT_DONT_DESTROY_LOOT);
                entity.setSecondsOnFire(10);
            }
        }
    }

    private void applySpellEffect3(Level level, Player player) {
        List<Entity> entities = getEntitiesInLookingDirection(level, player, 10.5);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && entity != player) {
                summonFlamingArrows(level, player, (LivingEntity) entity);
            }
        }
    }

    private void summonFlamingArrows(Level level, Player player, LivingEntity target) {
        // Get the position in front of the entity
        double x = target.getX() - Math.sin(Math.toRadians(target.getYRot())) * 1.5;
        double y = target.getY() + target.getEyeHeight() - 0.1;
        double z = target.getZ() + Math.cos(Math.toRadians(target.getYRot())) * 1.5;

        // Summon the arrow
        Arrow arrow = new Arrow(level, x, y, z);
        arrow.setOwner(player);
        arrow.setBaseDamage(15.0);
        arrow.setSecondsOnFire(100); // 5 seconds of fire

        // Set the arrow's motion towards the target
        double deltaX = target.getX() - arrow.getX();
        double deltaY = target.getY(0.3333333333333333D) - arrow.getY();
        double deltaZ = target.getZ() - arrow.getZ();
        arrow.shoot(deltaX, deltaY, deltaZ, 1.6F, 0.0F);

        // Add the arrow to the level
        level.addFreshEntity(arrow);
        level.addFreshEntity(arrow);
        level.addFreshEntity(arrow);
        level.addFreshEntity(arrow);
        level.addFreshEntity(arrow);
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
        return dotProduct > 0.5; // Adjust the threshold value as needed
    }
}
