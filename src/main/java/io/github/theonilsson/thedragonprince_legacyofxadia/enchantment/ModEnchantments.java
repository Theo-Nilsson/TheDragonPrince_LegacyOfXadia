package io.github.theonilsson.thedragonprince_legacyofxadia.enchantment;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.mender.EarthmenderEnchantment;
import io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.negation.SolarNegation;
import io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.scabbard.ScabbardEnchantment;
import io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.slayer.DragonSlayerEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, LOX.MOD_ID);

    public static final RegistryObject<Enchantment> HEAT_SCABBARD = ENCHANTMENTS.register("heat_scabbard", ScabbardEnchantment::new);
    public static final RegistryObject<Enchantment> SOLAR_NEGATION = ENCHANTMENTS.register("solar_negation", SolarNegation::new);
    public static final RegistryObject<Enchantment> DRAGON_SLAYER = ENCHANTMENTS.register("dragon_slayer", DragonSlayerEnchantment::new);
    public static final RegistryObject<Enchantment> EARTHMENDER = ENCHANTMENTS.register("earthmender", EarthmenderEnchantment::new);

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
