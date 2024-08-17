package io.github.theonilsson.thedragonprince_legacyofxadia.item.food;

import io.github.theonilsson.thedragonprince_legacyofxadia.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ScorchBerries extends ItemNameBlockItem {
    public ScorchBerries(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player) {
            entity.setSecondsOnFire(5); // Set the player on fire for 5 seconds

            // Handle the stack size properly
            if (!level.isClientSide) {
                Player player = (Player) entity;
                if (!player.getAbilities().instabuild) {
                    ItemStack bowlStack = new ItemStack(ModItems.FINISHED_SCORCH_BERRIES.get());
                    if (stack.isEmpty()) {
                        return bowlStack;
                    } else {
                        player.getInventory().add(bowlStack);
                    }
                }
            }
        }

        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return new ItemStack(ModItems.FINISHED_SCORCH_BERRIES.get());
    }
}
