package io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.mender;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.ModEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LOX.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EarthmenderEnchantment extends Enchantment {
    private static final int TICK_INTERVAL = 60; // 3 seconds (60 ticks)

    public EarthmenderEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            // Check every 60 ticks (3 seconds)
            if (event.player.level().getGameTime() % TICK_INTERVAL == 0 && event.player.onGround()) {
                for (ItemStack itemStack : event.player.getArmorSlots()) {
                    repairItem(itemStack);
                }
                repairItem(event.player.getMainHandItem());
                repairItem(event.player.getOffhandItem());
            }
        }
    }

    private static void repairItem(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return;
        }

        int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.EARTHMENDER.get(), itemStack);
        if (level > 0 && itemStack.isDamaged()) {
            itemStack.setDamageValue(itemStack.getDamageValue() - 1);
        }
    }
}
