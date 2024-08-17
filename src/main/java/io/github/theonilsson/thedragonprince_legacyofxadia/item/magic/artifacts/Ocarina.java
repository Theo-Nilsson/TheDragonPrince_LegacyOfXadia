package io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.artifacts;

import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.staff.CorruptedSunStaff;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Ocarina extends Item {
    public Ocarina(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        itemStack.hurtAndBreak(3, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
        if (CorruptedSunStaff.umbraChorumUsed(4, System.currentTimeMillis())) {
            umbraSleep(pLevel, pPlayer);
        } else {
            sleep(pLevel, pPlayer);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    private void sleep(Level level, Player player) {
        Optional<LivingEntity> closestEntity = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10))
                .stream()
                .filter(entity -> entity != player)
                .min(Comparator.comparingDouble(player::distanceTo));

        closestEntity.ifPresent(entity -> {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 4));
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 4));
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600, 4));
        });
    }

    private void umbraSleep(Level level, Player player) {
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10));

        for (LivingEntity entity : entities) {
            if (entity != player) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 4));
                entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 4));
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600, 4));
            }
        }
    }
}
