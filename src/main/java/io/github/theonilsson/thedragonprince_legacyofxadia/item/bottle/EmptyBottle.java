package io.github.theonilsson.thedragonprince_legacyofxadia.item.bottle;

import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EmptyBottle extends Item {
    public EmptyBottle(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.position().y >= 100) {
                ItemStack itemStack = findEmptyBottle(pPlayer);
                if (!itemStack.isEmpty()) {
                    itemStack.shrink(1); // Decrease the count by 1
                    pPlayer.getInventory().add(new ItemStack(ModItems.CLOUD_BOTTLE.get()));
                }
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    private ItemStack findEmptyBottle(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack.getItem() == ModItems.EMPTY_BOTTLE.get()) {
                return itemStack;
            }
        }
        return ItemStack.EMPTY;
    }
}
