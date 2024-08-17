package io.github.theonilsson.thedragonprince_legacyofxadia.event;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LOX.MOD_ID)
public class AttackEventHandler {
    @SubscribeEvent
    public static void onHeatBeingAttack(LivingAttackEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            MobEffectInstance effectInstance = attacker.getEffect(ModEffects.HEAT_BEING_EFFECT.get());
            if (effectInstance != null) {
                event.getEntity().setSecondsOnFire(5);
            }
        }
    }

    @SubscribeEvent
    public static void onHeatBeingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (event.getEntity().hasEffect(ModEffects.HEAT_BEING_EFFECT.get())) {
            if (event.getSource() == entity.damageSources().onFire() || event.getSource() == entity.damageSources().inFire() || event.getSource() == entity.damageSources().lava()) {
                event.setCanceled(true);
            }
        }
    }
}
