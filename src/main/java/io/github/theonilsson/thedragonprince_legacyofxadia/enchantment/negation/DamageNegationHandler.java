package io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.negation;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.ModEnchantments;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(modid = LOX.MOD_ID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DamageNegationHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            DamageSource source = event.getSource();
            if (source.is(DamageTypeTags.IS_FIRE)) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    ItemStack armor = player.getItemBySlot(slot);
                    if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SOLAR_NEGATION.get(), armor) > 0) {
                        event.setCanceled(true);
                        break;
                    }
                }
            }
        }
    }
}
