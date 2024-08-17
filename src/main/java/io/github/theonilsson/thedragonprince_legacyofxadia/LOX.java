package io.github.theonilsson.thedragonprince_legacyofxadia;

import com.mojang.logging.LogUtils;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import io.github.theonilsson.thedragonprince_legacyofxadia.block.entity.ModBlockEntities;
import io.github.theonilsson.thedragonprince_legacyofxadia.compat.origins.power.ModPowers;
import io.github.theonilsson.thedragonprince_legacyofxadia.datagen.DataGenerators;
import io.github.theonilsson.thedragonprince_legacyofxadia.effect.ModEffects;
import io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.ModEnchantments;
import io.github.theonilsson.thedragonprince_legacyofxadia.enchantment.negation.DamageNegationHandler;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.ModEntities;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.render.*;
import io.github.theonilsson.thedragonprince_legacyofxadia.event.AttackEventHandler;
import io.github.theonilsson.thedragonprince_legacyofxadia.event.ForgeCommandRegisterEvent;
import io.github.theonilsson.thedragonprince_legacyofxadia.event.ModEvents;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModCreativeModeTabs;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import io.github.theonilsson.thedragonprince_legacyofxadia.network.RetrievePlayerDataPacket;
import io.github.theonilsson.thedragonprince_legacyofxadia.network.SpawnEntityPacket;
import io.github.theonilsson.thedragonprince_legacyofxadia.network.SpellSelectionPacket;
import io.github.theonilsson.thedragonprince_legacyofxadia.recipe.ModRecipes;
import io.github.theonilsson.thedragonprince_legacyofxadia.screen.ModMenuTypes;
import io.github.theonilsson.thedragonprince_legacyofxadia.screen.SunfireForgeScreen;
import io.github.theonilsson.thedragonprince_legacyofxadia.sound.ModSounds;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LOX.MOD_ID)
public class LOX {
    public static final String MOD_ID = "tdp_lox";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public LOX() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);
        ModRecipes.register(modEventBus);

        ModEnchantments.register(modEventBus);
        ModEffects.register(modEventBus);

        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);

        if (ModList.get().isLoaded("origins")) {
            ModPowers.register(modEventBus);
        }

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::loadComplete);
        modEventBus.addListener(this::onModConfigEvent);
        modEventBus.addListener(this::addCreative);
        modEventBus.register(DataGenerators.class);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(AttackEventHandler.class);
        MinecraftForge.EVENT_BUS.register(ModEvents.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(DamageNegationHandler.class);
        int packetId = 0;
        CHANNEL.registerMessage(packetId ++, SpellSelectionPacket.class, SpellSelectionPacket::toBytes, SpellSelectionPacket::new, SpellSelectionPacket::handle);
        CHANNEL.registerMessage(packetId++, SpawnEntityPacket.class, SpawnEntityPacket::toBytes, SpawnEntityPacket::new, SpawnEntityPacket::handle);
        CHANNEL.registerMessage(packetId++, RetrievePlayerDataPacket.class, RetrievePlayerDataPacket::toBytes, RetrievePlayerDataPacket::new, RetrievePlayerDataPacket::handle);
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
        MinecraftForge.EVENT_BUS.register(new ForgeCommandRegisterEvent());
    }

    private void onModConfigEvent(final ModConfigEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(Items.BUNDLE);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.NOCTU_IGNE.get(), NoctuIgneRenderer::new);
            EntityRenderers.register(ModEntities.MAGMA_TITAN.get(), MagmaTitanRenderer::new);
            EntityRenderers.register(ModEntities.SUNFIRE_ELF_WANDERER.get(), SunfireElfWandererRenderer::new);
            EntityRenderers.register(ModEntities.CROW.get(), CrowRenderer::new);
            EntityRenderers.register(ModEntities.CIRCULUS_LUMINIS.get(), CirculusLuminisRenderer::new);

            MenuScreens.register(ModMenuTypes.SUNFIRE_FORGE_MENU.get(), SunfireForgeScreen::new);
        }

        /*@SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.CHANGE_SPELL_KEY.consumeClick()) {
                Minecraft.getInstance().gui.setOverlayMessage(Component.literal("test"), false);
            }
        }*/
    }
}
