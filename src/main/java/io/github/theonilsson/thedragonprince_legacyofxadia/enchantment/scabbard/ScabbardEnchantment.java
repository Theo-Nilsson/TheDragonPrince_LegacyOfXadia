package io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.scabbard;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ScabbardEnchantment extends Enchantment {
    public ScabbardEnchantment() {
        this(Rarity.UNCOMMON, EnchantmentCategory.WEARABLE);
    }

    public ScabbardEnchantment(Rarity pRarity, EnchantmentCategory pCategory) {
        super(pRarity, pCategory, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
