package io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.primalstone;

import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;

public class EmptyPrimalStone extends Item {
    public EmptyPrimalStone(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.getBiome(pPlayer.blockPosition()).unwrapKey().orElse(null) == Biomes.JAGGED_PEAKS) {
            if (pLevel.isThundering()) {
                ItemStack heldItem = pPlayer.getItemInHand(pUsedHand);
                heldItem.shrink(1);
                ItemStack newItem = new ItemStack(ModItems.PRIMAL_STONE_SKY.get());
                if (!pPlayer.addItem(newItem)) {
                    if (!pLevel.isClientSide()) {
                        ItemEntity itemEntity = new ItemEntity(pLevel, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), newItem);
                        pLevel.addFreshEntity(itemEntity);
                    }
                }
                return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
