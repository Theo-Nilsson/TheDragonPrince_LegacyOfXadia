package io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.parchment;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class SpellParchment extends Item {
    public SpellParchment(Properties properties) {
        super(properties);
    }

    public CompoundTag setData(@NotNull ItemStack stack, String spell) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString("Spell", spell);
        return tag;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (stack.hasTag() && stack.getTag().contains("Spell")) {
            String spell = stack.getTag().getString("Spell");
            tooltip.add(Component.literal(spell));
        } else {
            tooltip.add(Component.translatable("spellparchment.tdp_lox.default"));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.hasTag() && stack.getTag().contains("Spell")) {
            String spell = stack.getTag().getString("Spell");
            applySpellEffect(spell, level, player, stack);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, stack);
    }

    private void applySpellEffect(String spell, Level level, Player player, ItemStack itemStack) {
        switch (spell.toLowerCase()) {
            case "hsiruolf dna worg, eert ot dees":
                Minecraft.getInstance().gui.setOverlayMessage(Component.literal("Hsiruolf dna worg, eert ot dees"), false);
                itemStack.shrink(1);
                grow(level, player.blockPosition(), itemStack, player);
                break;
            /* case "healing":
                Minecraft.getInstance().gui.setOverlayMessage(Component.literal("test 2"), false);
                itemStack.shrink(1);
                break; */
            default:
                Minecraft.getInstance().gui.setOverlayMessage(Component.literal("No spell selected"), false);
                break;
        }
    }

    private void grow(Level level, BlockPos centerPos, ItemStack itemStack, Player player) {
        int radius = 16; // 32x32 area, so radius is 16

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    /*mutableBlockPos.set(centerPos.getX() + x, centerPos.getY() + y, centerPos.getZ() + z);
                    BlockState blockState = level.getBlockState(mutableBlockPos);
                    Block block = blockState.getBlock();

                    if (block instanceof IPlantable || blockState.hasProperty(BlockStateProperties.AGE_7)) {
                        // This is a plantable block, apply growth
                        growBlock(level, mutableBlockPos, blockState, block);
                    }*/

                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                    BoneMealItem.applyBonemeal(itemStack, level, centerPos.offset(x, y, z), player);
                }
            }
        }
    }

    private void growBlock(Level level, BlockPos pos, BlockState state, Block block) {
        Random random = new Random();
        if (block instanceof IPlantable plantable) {
            PlantType plantType = plantable.getPlantType(level, pos);
            if (plantType == PlantType.CROP || plantType == PlantType.PLAINS) {
                BlockState newState = block.defaultBlockState();
                if (block == Blocks.WHEAT || block == Blocks.CARROTS || block == Blocks.POTATOES || block == Blocks.BEETROOTS) {
                    IntegerProperty ageProperty = BlockStateProperties.AGE_7;
                    if (state.getValue(ageProperty) < 7) {
                        level.setBlock(pos, state.setValue(ageProperty, 7), 2);
                    }
                } else {
                    level.setBlock(pos, newState, 2);
                }
            }
        } else if (state.hasProperty(BlockStateProperties.AGE_7)) {
            IntegerProperty ageProperty = BlockStateProperties.AGE_7;
            if (state.getValue(ageProperty) < 7) {
                level.setBlock(pos, state.setValue(ageProperty, 7), 2);
            }
        }
    }
}
