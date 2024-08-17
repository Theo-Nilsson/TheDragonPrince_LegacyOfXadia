package io.github.theonilsson.thedragonprince_legacyofxadia.item;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.ModEntities;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.armor.SunForgeArmorItem;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.bottle.EmptyBottle;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.bottle.TintedEmptyBottle;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.food.ScorchBerries;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.artifacts.MagmaHeart;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.artifacts.Ocarina;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.parchment.SpellParchment;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.primalstone.EmptyPrimalStone;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.primalstone.PrimalStoneSky;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.primalstone.PrimalStoneSun;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.spell_items.IgnisIacto;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.staff.CorruptedSunStaff;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.staff.SunStaff;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.sword.SunForgeBlade;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.tiers.ArmorMaterials;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.tiers.Tiers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LOX.MOD_ID);

    public static final RegistryObject<Item> SUNFIREBLADE = ITEMS.register("sunfireblade",
            () -> new SunForgeBlade(Tiers.SUNFORGE, 20, -2F, new Item.Properties()));

    public static final RegistryObject<Item> FIRERUNE = ITEMS.register("firerune",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SUN_INGOT = ITEMS.register("sun_ingot",
            () -> new Item(new Item.Properties()));

    /* public static final RegistryObject<Item> STEEL_DRAGON_INGOT = ITEMS.register("steel_dragon_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_DRAGON_NUGGET = ITEMS.register("steel_dragon_nugget",
            () -> new Item(new Item.Properties())); */

    public static final RegistryObject<Item> WITHERSTEEL_INGOT = ITEMS.register("withersteel_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WITHERSTEEL_ROD = ITEMS.register("withersteel_rod",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> EMPTY_BOTTLE = ITEMS.register("empty_bottle",
            () -> new EmptyBottle(new Item.Properties()));

    public static final RegistryObject<Item> TINTED_EMPTY_BOTTLE = ITEMS.register("tinted_empty_bottle",
            () -> new TintedEmptyBottle(new Item.Properties()));

    public static final RegistryObject<Item> CLOUD_BOTTLE = ITEMS.register("cloud_bottle",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    pTooltipComponents.add(Component.translatable("tooltip.tdp_lox.cloud_bottle.1"));
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });

    public static final RegistryObject<Item> TINTED_LIGHT_BOTTLE = ITEMS.register("tinted_light_bottle",
            () -> new Item(new Item.Properties()) {
                @Override
                public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
                    return ModItems.TINTED_EMPTY_BOTTLE.get().getDefaultInstance();
                }

                @Override
                public boolean hasCraftingRemainingItem(ItemStack stack) {
                    return true;
                }
            });

    public static final RegistryObject<Item> PRIMAL_STONE_SKY = ITEMS.register("primal_stone_sky",
            () -> new PrimalStoneSky(new Item.Properties().stacksTo(1).durability(75)));

    public static final RegistryObject<Item> PRIMAL_STONE_SUN = ITEMS.register("primal_stone_sun",
            () -> new PrimalStoneSun(new Item.Properties().stacksTo(1).durability(75)));

    public static final RegistryObject<Item> SUNFORGE_HELMET = ITEMS.register("sunforge_helmet",
            () -> new SunForgeArmorItem(ArmorMaterials.SUNFORGE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> SUNFORGE_CHESTPLATE = ITEMS.register("sunforge_chestplate",
            () -> new SunForgeArmorItem(ArmorMaterials.SUNFORGE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> SUNFORGE_LEGGINGS = ITEMS.register("sunforge_leggings",
            () -> new SunForgeArmorItem(ArmorMaterials.SUNFORGE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> SUNFORGE_BOOTS = ITEMS.register("sunforge_boots",
            () -> new SunForgeArmorItem(ArmorMaterials.SUNFORGE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> BARK = ITEMS.register("bark",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CORK = ITEMS.register("cork",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PARCHMENT = ITEMS.register("parchment",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPELL_PARCHMENT = ITEMS.register("spell_parchment",
            () -> new SpellParchment(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SUN_STAFF = ITEMS.register("sun_staff",
            () -> new SunStaff(new Item.Properties().stacksTo(1).durability(500)));

    public static final RegistryObject<Item> CORRUPTED_SUN_STAFF = ITEMS.register("corrupted_sun_staff",
            () -> new CorruptedSunStaff(new Item.Properties().stacksTo(1).durability(500)));

    public static final RegistryObject<Item> OCARINA = ITEMS.register("ocarina",
            () -> new Ocarina(new Item.Properties().stacksTo(1).durability(500)));

    public static final RegistryObject<Item> MAGMA_HEART = ITEMS.register("magma_heart",
            () -> new MagmaHeart(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MAGMA_TITAN_SPAWN_EGG = ITEMS.register("magma_titan_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MAGMA_TITAN, 0xb53119, 0x8a1f0b,
                    new Item.Properties()));

    public static final RegistryObject<Item> SUNFIRE_ELF_WANDERER_SPAWN_EGG = ITEMS.register("sunfire_elf_wanderer_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SUNFIRE_ELF_WANDERER, 0xd11f1f, 0xff9c33,
                    new Item.Properties()));

    public static final RegistryObject<Item> CROW_SPAWN_EGG = ITEMS.register("crow_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CROW, 0x333231, 0x474440,
                    new Item.Properties()));

    public static final RegistryObject<Item> SCORCH_BERRIES = ITEMS.register("scorch_berries",
            () -> new ScorchBerries(ModBlocks.SCORCH_BERRY_BUSH.get(), new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(6)
                    .saturationMod(0.3f)
                    .build())));

    public static final RegistryObject<Item> FINISHED_SCORCH_BERRIES = ITEMS.register("finished_scorch_berries",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MORTAR_AND_PESTLE = ITEMS.register("mortar_and_pestle",
            () -> new Item(new Item.Properties().durability(250)) {
                @Override
                public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
                    ItemStack damagedStack = itemStack.copy();
                    damagedStack.setDamageValue(itemStack.getDamageValue() + 1);

                    if (damagedStack.getDamageValue() >= damagedStack.getMaxDamage()) {
                        return ItemStack.EMPTY;
                    }

                    return damagedStack;
                }

                @Override
                public boolean hasCraftingRemainingItem(ItemStack stack) {
                    return true;
                }
            });

    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SCONES = ITEMS.register("scones",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(8)
                    .saturationMod(0.5f)
                    .build())));

    public static final RegistryObject<Item> SCORCH_BERRY_SCONES = ITEMS.register("scorch_berry_scones",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(14)
                    .saturationMod(0.8f)
                    .build())));

    public static final RegistryObject<Item> SALT = ITEMS.register("salt",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STRAINER = ITEMS.register("strainer",
            () -> new Item(new Item.Properties().durability(500)) {
                @Override
                public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
                    ItemStack damagedStack = itemStack.copy();
                    damagedStack.setDamageValue(itemStack.getDamageValue() + 1);

                    if (damagedStack.getDamageValue() >= damagedStack.getMaxDamage()) {
                        return ItemStack.EMPTY;
                    }

                    return damagedStack;
                }

                @Override
                public boolean hasCraftingRemainingItem(ItemStack stack) {
                    return true;
                }
            });

    public static final RegistryObject<Item> IGNIS_IACTO = ITEMS.register("ignis_lacto",
            () -> new IgnisIacto(new Item.Properties()));

    public static final RegistryObject<Item> ELF_HORNS = ITEMS.register("elf_horns",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    pTooltipComponents.add(Component.translatable("tooltip.tdp_lox.elf_horns.1"));
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });

    public static final RegistryObject<Item> EMPTY_PRIMAL_STONE = ITEMS.register("empty_primal_stone",
            () -> new EmptyPrimalStone(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MAGNIFYING_GLASS = ITEMS.register("magnifying_glass",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> GOLD_ROD = ITEMS.register("gold_rod",
            () -> new Item(new Item.Properties()));

    /* public static final RegistryObject<Item> DARK_EYED_SAILOR_MUSIC_DISC = ITEMS.register("dark_eyed_sailor_music_disc",
            () -> new RecordItem(6, ModSounds.DARK_EYED_SAILOR, new Item.Properties().stacksTo(1), 900)); */ // Unsure of copyright

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
