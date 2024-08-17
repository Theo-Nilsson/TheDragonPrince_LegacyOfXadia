package io.github.theonilsson.thedragonprince_legacyofxadia.event;

import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.parchment.SpellParchment;
import io.github.theonilsson.thedragonprince_legacyofxadia.keybinds.KeyBinding;
import io.github.theonilsson.thedragonprince_legacyofxadia.network.RetrievePlayerDataPacket;
import io.github.theonilsson.thedragonprince_legacyofxadia.network.SpawnEntityPacket;
import io.github.theonilsson.thedragonprince_legacyofxadia.network.SpellSelectionPacket;
import io.github.theonilsson.thedragonprince_legacyofxadia.network.client.ClientReceivedData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static io.github.theonilsson.thedragonprince_legacyofxadia.LOX.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ClientModEvents {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        CompoundTag playerData = player.getPersistentData();

        if (KeyBinding.CHANGE_SPELL_KEY.consumeClick()) {
            int maxSpellInt = 3;
            if (!playerData.contains("SelectedSpell")) {
                playerData.putInt("SelectedSpell", 1);
            }

            int selectedSpell = playerData.getInt("SelectedSpell");
            // Debug Log
            System.out.println("Current Selected Spell: " + selectedSpell);

            if (selectedSpell < maxSpellInt) {
                selectedSpell++;
            } else {
                selectedSpell = 1;
            }

            playerData.putInt("SelectedSpell", selectedSpell);
            LOX.CHANNEL.send(PacketDistributor.SERVER.noArg(), new SpellSelectionPacket(selectedSpell));

            // Debug Log
            System.out.println("New Selected Spell: " + selectedSpell);

            Minecraft.getInstance().gui.setOverlayMessage(Component.translatable("change_spell.tdp_lox", selectedSpell), false);
        } else if (KeyBinding.INNATE_SPELL_KEY.consumeClick()) {
            LOX.CHANNEL.send(PacketDistributor.SERVER.noArg(), new RetrievePlayerDataPacket(player.getUUID()));
            scheduler.schedule(() -> {
                System.out.println(ClientReceivedData.retrievedPlayerData);
                if (ClientReceivedData.retrievedPlayerData == null) return;
                CompoundTag tag = ClientReceivedData.retrievedPlayerData;
                System.out.println(tag.contains("arcanumList"));
                ListTag arcanumList = tag.getList("arcanumList", Tag.TAG_STRING);
                if (tag.contains("arcanumList",9)) {
                    for (Tag arcanumTag : arcanumList) {
                        System.out.println("arcanumTag: " + arcanumTag + " (Type: " + arcanumTag.getType() + ")");
                        if (arcanumTag.getAsString().equals("sun")) {
                            LOX.CHANNEL.send(PacketDistributor.SERVER.noArg(), new SpawnEntityPacket(player.blockPosition()));
                            break;
                        }
                    }

                }
            }, 1L, TimeUnit.SECONDS);
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();
        ItemStack itemStack = event.getCrafting();

        if (itemStack.getItem() instanceof SpellParchment) {
            Container inventory = event.getInventory();

            Map<String, Integer> componentCounts = new HashMap<>();
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack component = inventory.getItem(i);
                if (!component.isEmpty()) {
                    String componentName = component.getItem().toString();
                    componentCounts.put(componentName, componentCounts.getOrDefault(componentName, 0) + 1);
                }
            }

            if (componentCounts.containsKey(ModItems.MAGMA_HEART.get().toString()) && componentCounts.containsKey(Items.FIRE_CHARGE.toString())) {
                ((SpellParchment) itemStack.getItem()).setData(itemStack, "Hsiruolf dna worg, eert ot dees");
            } else if (componentCounts.containsKey(Items.GHAST_TEAR.toString())) {
                ((SpellParchment) itemStack.getItem()).setData(itemStack, "Healing");
            }
        }
    }

    @SubscribeEvent
    public static void onLogStripped(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        ServerLevel level = (ServerLevel) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ItemStack heldItem = event.getItemStack();

        if (heldItem.getItem() instanceof AxeItem) {
            if (state.is(Blocks.OAK_LOG) || state.is(Blocks.OAK_WOOD)) {
                ItemStack bark = new ItemStack(ModItems.BARK.get());
                Block.popResource(level, pos, bark);

                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
}
