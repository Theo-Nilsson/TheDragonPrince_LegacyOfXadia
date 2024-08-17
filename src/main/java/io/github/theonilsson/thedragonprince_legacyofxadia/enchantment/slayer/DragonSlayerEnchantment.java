package io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.slayer;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class DragonSlayerEnchantment extends Enchantment {
    public DragonSlayerEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 10 + (level - 1) * 7;
    }

    @Override
    public int getMaxCost(int level) {
        return super.getMinCost(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean checkCompatibility(Enchantment ench) {
        return super.checkCompatibility(ench) && ench != Enchantments.BANE_OF_ARTHROPODS
                && ench != Enchantments.SMITE && ench != Enchantments.SHARPNESS;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        System.out.println(target.getClass().getName());
        System.out.println(target instanceof EntityDragonBase);
        if (target instanceof EntityDragonBase) {
            target.hurt(user.level().damageSources().playerAttack((Player) user), (float) (level * 7.5));
        }
        super.doPostAttack(user, target, level);
    }
}
