package io.github.theonilsson.thedragonprince_legacyofxadia.entity.shadow;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PlayerShadowEntity extends Mob {

    public PlayerShadowEntity(EntityType<? extends Mob> type, Level world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > 200) { // Shadow entity lasts for 10 seconds (200 ticks)
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D);
    }

    public void mimicPlayer(Player player) {
        // Code to copy player appearance
    }
}
