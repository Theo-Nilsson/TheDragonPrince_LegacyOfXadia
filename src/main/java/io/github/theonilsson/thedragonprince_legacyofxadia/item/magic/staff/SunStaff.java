package io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.staff;

import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SunStaff extends Item {
    public SunStaff(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        Minecraft minecraft = Minecraft.getInstance();

        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        int maxSpellInt = 3;
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
                        itemStack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                        applySpellEffect1(pLevel, pPlayer);
                        break;
                    case 2:
                        minecraft.gui.setOverlayMessage(Component.literal(getSpellName(selectedSpell)), false);
                        itemStack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                        applySpellEffect2(pLevel, pPlayer);
                        break;
                    case 3:
                        minecraft.gui.setOverlayMessage(Component.literal(getSpellName(selectedSpell)), false);
                        itemStack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                        applySpellEffect3(pLevel, pPlayer);
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
            case 1 -> "Ignis Iacto!";
            case 2 -> "Finales Funkeln!";
            case 3 -> "Vires Ardens!";
            default -> "No spell selected.";
        };
    }

    private void applySpellEffect1(Level pLevel, Player pPlayer) {
        pPlayer.addItem(new ItemStack(ModItems.IGNIS_IACTO.get()));
    }

    private void applySpellEffect2(Level pLevel, Player pPlayer) {
        List<Entity> entities = getEntitiesInLookingDirection(pLevel, pPlayer, 10.5);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && entity != pPlayer) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 2, false, false, false));
            }
        }
    }

    private void applySpellEffect3(Level pLevel, Player pPlayer) {
        pPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 500, 2));
        pPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 500, 2));
    }

    private List<Entity> getEntitiesInLookingDirection(Level level, Player player, double radius) {
        List<Entity> entities = level.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(radius));
        return entities.stream().filter(entity -> isEntityInLookingDirection(player, entity)).toList();
    }

    private boolean isEntityInLookingDirection(Player player, Entity entity) {
        double dx = entity.getX() - player.getX();
        double dy = entity.getEyeY() - player.getEyeY();
        double dz = entity.getZ() - player.getZ();
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (distance > 0) {
            dx /= distance;
            dy /= distance;
            dz /= distance;
        }

        double dotProduct = dx * player.getLookAngle().x + dy * player.getLookAngle().y + dz * player.getLookAngle().z;
        return dotProduct > 0.5;
    }
}
