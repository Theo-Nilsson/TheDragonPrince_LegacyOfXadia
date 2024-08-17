package io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.staff;

import io.github.theonilsson.thedragonprince_legacyofxadia.block.ModBlocks;
import io.github.theonilsson.thedragonprince_legacyofxadia.effect.ModEffects;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.projectile.NoctuIgneProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CorruptedSunStaff extends Item {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Map<Integer, Long> spellLastUsedTimes = new HashMap<>();

    public CorruptedSunStaff(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        Minecraft minecraft = Minecraft.getInstance();

        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        int maxSpellInt = 4;
        CompoundTag tag = itemStack.getOrCreateTag();

        int selectedSpell = tag.getInt("SelectedSpell");

        if (!pLevel.isClientSide()) {
            if (pPlayer.isCrouching()) {
                if (selectedSpell < maxSpellInt) {
                    selectedSpell++;
                } else {
                    selectedSpell = 1;
                }

                tag.putInt("SelectedSpell", selectedSpell);
            } else {
                switch (selectedSpell) {
                    case 1:
                        minecraft.gui.setOverlayMessage(Component.literal(getSpellName(selectedSpell)), false);
                        itemStack.hurtAndBreak(10, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                        applySpellEffect1(pLevel, pPlayer);
                        break;
                    case 2:
                        minecraft.gui.setOverlayMessage(Component.literal(getSpellName(selectedSpell)), false);
                        itemStack.hurtAndBreak(15, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                        applySpellEffect2(pLevel, pPlayer);
                        break;
                    case 3:
                        minecraft.gui.setOverlayMessage(Component.literal(getSpellName(selectedSpell)), false);
                        itemStack.hurtAndBreak(3, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                        applySpellEffect3(pLevel, pPlayer);
                        break;
                    case 4:
                        minecraft.gui.setOverlayMessage(Component.literal(getSpellName(selectedSpell)), false);
                        itemStack.hurtAndBreak(6, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                        applySpellEffect4(pLevel, pPlayer);
                        break;
                    default:
                        minecraft.gui.setOverlayMessage(Component.literal(getSpellName(selectedSpell)), false);
                        break;
                }
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        int selectedSpell;
        if (!tag.contains("SelectedSpell")) {
            selectedSpell = 0;
        } else {
            selectedSpell = tag.getInt("SelectedSpell");
        }
        pTooltipComponents.add(Component.literal(getSpellName(selectedSpell)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public String getSpellName(int spellNumber) {
        return switch (spellNumber) {
            case 1 -> "§cNrub tonnac rednic fo straeh!";
            case 2 -> "§4Noctu Igne!";
            case 3 -> "§5Tenebris Praesidium!";
            case 4 -> "§5Umbra Chorum!";
            default -> "No spell selected.";
        };
    }

    private void applySpellEffect1(Level pLevel, Player pPlayer) {
        pPlayer.addEffect(new MobEffectInstance(ModEffects.HEAT_BEING_EFFECT.get(), 50000, 0));
    }

    private void applySpellEffect2(Level pLevel, Player pPlayer) {
        NoctuIgneProjectile fireball = new NoctuIgneProjectile(pLevel, pPlayer.getX(), pPlayer.getEyeY() - 0.1, pPlayer.getZ(), pPlayer.getLookAngle().x, pPlayer.getLookAngle().y, pPlayer.getLookAngle().z);
        fireball.setExplosionRadius(12.5f);
        fireball.setOwner(pPlayer);
        pLevel.addFreshEntity(fireball);
    }

    private void applySpellEffect3(Level pLevel, Player pPlayer) {
        BlockPos center = pPlayer.blockPosition();
        int radius = 10;

        if (!pLevel.isClientSide()) {
            createProtectiveDome(pLevel, center, radius);

            if (pLevel instanceof ServerLevel serverLevel) {
                removeProtectiveDome(serverLevel, center, radius);
            }
        }
    }

    private void applySpellEffect4(Level pLevel, Player pPlayer) {
        spellLastUsedTimes.put(4, System.currentTimeMillis());
    }

    public static boolean umbraChorumUsed(int spellNumber, long currentTime) {
        if (!spellLastUsedTimes.containsKey(spellNumber)) {
            return false;
        }

        long lastUsedTime = spellLastUsedTimes.get(spellNumber);
        return (currentTime - lastUsedTime) <= 15000;
    }

    private void createProtectiveDome(Level pLevel, BlockPos center, int radius) {
        int shellThickness = 1; // Thickness of the shell

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (Math.abs(x) == radius || Math.abs(y) == radius || Math.abs(z) == radius) {
                        BlockPos pos = center.offset(x, y, z);
                        if (pLevel.isEmptyBlock(pos)) {
                            pLevel.setBlockAndUpdate(pos, ModBlocks.TENEBRIS_PRAESIDIUM.get().defaultBlockState());
                        }
                    }
                }
            }
        }
    }

    private void removeProtectiveDome(ServerLevel pLevel, BlockPos center, int radius) {
        scheduler.schedule(() -> {
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos pos = center.offset(x, y, z);
                        if (pLevel.getBlockState(pos).getBlock() == ModBlocks.TENEBRIS_PRAESIDIUM.get()) {
                            pLevel.removeBlock(pos, false);
                        }
                    }
                }
            }
        }, 10, TimeUnit.SECONDS); // Schedule for 10 seconds later
    }
}
