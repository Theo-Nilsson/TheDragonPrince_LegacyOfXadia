package io.github.theonilsson.thedragonprince_legacyofxadia.item.bottle;

import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;

public class TintedEmptyBottle extends Item {
    public TintedEmptyBottle(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            if (pLevel.isDay()) {
                ItemStack itemStack = findEmptyBottle(pPlayer);
                System.out.println(pPlayer.getOffhandItem());
                if (!itemStack.isEmpty() && pPlayer.getOffhandItem().getItem() == ModItems.MAGNIFYING_GLASS.get() && pLevel.getBiome(pPlayer.blockPosition()).unwrapKey().orElse(null) == Biomes.DESERT) {
                    itemStack.shrink(1);
                    pPlayer.getInventory().add(new ItemStack(ModItems.TINTED_LIGHT_BOTTLE.get()));
                }
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    private ItemStack findEmptyBottle(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack.getItem() == ModItems.TINTED_EMPTY_BOTTLE.get()) {
                return itemStack;
            }
        }
        return ItemStack.EMPTY;
    }

    public boolean day(Level pLevel) {
        long time = pLevel.getDayTime();
        return time < 12300 || time > 23850;
    }
}
