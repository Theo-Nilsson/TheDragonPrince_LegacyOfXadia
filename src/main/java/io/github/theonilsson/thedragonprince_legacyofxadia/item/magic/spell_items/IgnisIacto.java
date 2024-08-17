package io.github.theonilsson.thedragonprince_legacyofxadia.item.magic.spell_items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IgnisIacto extends Item {
    public IgnisIacto(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        // Get the direction the player is looking
        double dirX = pPlayer.getLookAngle().x;
        double dirY = pPlayer.getLookAngle().y;
        double dirZ = pPlayer.getLookAngle().z;

        // Create a new fireball with the player's position and direction
        LargeFireball fireball = new LargeFireball(pLevel, pPlayer, dirX, dirY, dirZ, 9);
        fireball.setPos(pPlayer.getX(), pPlayer.getEyeY() - 0.1, pPlayer.getZ());

        // Add the fireball to the level
        pLevel.addFreshEntity(fireball);

        // Decrease the item stack by 1
        pPlayer.getItemInHand(pUsedHand).shrink(1);

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
