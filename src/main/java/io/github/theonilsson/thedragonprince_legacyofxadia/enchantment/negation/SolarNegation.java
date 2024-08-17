package io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.negation;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class SolarNegation extends Enchantment {
    public SolarNegation() {
        this(Rarity.UNCOMMON, EnchantmentCategory.ARMOR);
    }

    protected SolarNegation(Rarity pRarity, EnchantmentCategory pCategory) {
        super(pRarity, pCategory, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && pOther != Enchantments.FIRE_PROTECTION;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getDamageProtection(int pLevel, DamageSource pSource) {
        if (pSource.is(DamageTypeTags.IS_FIRE)) {
            return 9999999;
        }
        return 0;
    }
}
