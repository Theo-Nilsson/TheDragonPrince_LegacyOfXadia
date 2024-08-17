package io.github.theonilsson.thedragonprince_legacyofxadia.item;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.parchment.SpellParchment;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LOX.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LOX_TAB = CREATIVE_MODE_TABS.register("lox_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FIRERUNE.get()))
                    .title(Component.translatable("creativetab.lox_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        ItemStack spell_parchment = new ItemStack(ModItems.SPELL_PARCHMENT.get());
                        SpellParchment grow = (SpellParchment) spell_parchment.getItem();
                        grow.setData(spell_parchment, "Hsiruolf dna worg, eert ot dees");
                        pOutput.accept(ModItems.FIRERUNE.get());
                        pOutput.accept(ModItems.SUNFIREBLADE.get());
                        pOutput.accept(ModItems.STEEL_INGOT.get());
                        pOutput.accept(ModItems.SUN_INGOT.get());
                        /*pOutput.accept(ModItems.STEEL_DRAGON_INGOT.get());
                        pOutput.accept(ModItems.STEEL_DRAGON_NUGGET.get());*/
                        pOutput.accept(ModItems.WITHERSTEEL_INGOT.get());
                        pOutput.accept(ModItems.WITHERSTEEL_ROD.get());
                        pOutput.accept(ModItems.GOLD_ROD.get());
                        pOutput.accept(ModItems.EMPTY_BOTTLE.get());
                        pOutput.accept(ModItems.TINTED_EMPTY_BOTTLE.get());
                        pOutput.accept(ModItems.CLOUD_BOTTLE.get());
                        pOutput.accept(ModItems.TINTED_LIGHT_BOTTLE.get());
                        pOutput.accept(ModItems.PRIMAL_STONE_SKY.get());
                        pOutput.accept(ModItems.PRIMAL_STONE_SUN.get());
                        pOutput.accept(ModItems.EMPTY_PRIMAL_STONE.get());
                        pOutput.accept(ModItems.SUNFORGE_HELMET.get());
                        pOutput.accept(ModItems.SUNFORGE_CHESTPLATE.get());
                        pOutput.accept(ModItems.SUNFORGE_LEGGINGS.get());
                        pOutput.accept(ModItems.SUNFORGE_BOOTS.get());
                        pOutput.accept(ModItems.BARK.get());
                        pOutput.accept(ModItems.CORK.get());
                        pOutput.accept(ModItems.PARCHMENT.get());
                        pOutput.accept(ModItems.SPELL_PARCHMENT.get());
                        pOutput.accept(grow);
                        pOutput.accept(ModItems.SUN_STAFF.get());
                        pOutput.accept(ModItems.CORRUPTED_SUN_STAFF.get());
                        pOutput.accept(ModItems.OCARINA.get());
                        pOutput.accept(ModItems.MAGMA_HEART.get());
                        pOutput.accept(ModItems.SCORCH_BERRIES.get());
                        pOutput.accept(ModItems.FINISHED_SCORCH_BERRIES.get());;
                        pOutput.accept(ModItems.MORTAR_AND_PESTLE.get());
                        pOutput.accept(ModItems.FLOUR.get());
                        pOutput.accept(ModItems.SCONES.get());
                        pOutput.accept(ModItems.SCORCH_BERRY_SCONES.get());
                        pOutput.accept(ModItems.SALT.get());
                        pOutput.accept(ModItems.STRAINER.get());
                        pOutput.accept(ModItems.IGNIS_IACTO.get());
                        pOutput.accept(ModItems.ELF_HORNS.get());
                        pOutput.accept(ModItems.MAGNIFYING_GLASS.get());
                        //pOutput.accept(ModItems.DARK_EYED_SAILOR_MUSIC_DISC.get());

                        pOutput.accept(ModBlocks.SUNFIRE_FORGE.get());
                        pOutput.accept(ModBlocks.SUNFORGE.get());
                        pOutput.accept(ModBlocks.SCORCH_BERRY_BUSH.get());

                        pOutput.accept(ModBlocks.SUNFIRE_BRICKS.get());
                        pOutput.accept(ModBlocks.SUNFIRE_STONE.get());
                        pOutput.accept(ModBlocks.SUNFIRE_STONE_STAIRS.get());
                        pOutput.accept(ModBlocks.SUNFIRE_STONE_SLAB.get());
                        pOutput.accept(ModBlocks.SUNFIRE_STONE_BUTTON.get());
                        pOutput.accept(ModBlocks.SUNFIRE_STONE_PRESSURE_PLATE.get());
                        pOutput.accept(ModBlocks.SUNFIRE_STONE_WALL.get());
                        pOutput.accept(ModBlocks.SUNFIRE_STONE_DOOR.get());
                        pOutput.accept(ModBlocks.SUNFIRE_STONE_TRAPDOOR.get());

                        //pOutput.accept(ModBlocks.SUNFIRE_CHAIR.get());

                        pOutput.accept(ModItems.MAGMA_TITAN_SPAWN_EGG.get());
                        pOutput.accept(ModItems.SUNFIRE_ELF_WANDERER_SPAWN_EGG.get());
                        pOutput.accept(ModItems.CROW_SPAWN_EGG.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
