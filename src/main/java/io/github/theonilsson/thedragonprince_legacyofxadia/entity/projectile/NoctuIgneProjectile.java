package io.github.theonilsson.thedragonprince_legacyofxadia.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class NoctuIgneProjectile extends AbstractHurtingProjectile {
    private static final EntityDataAccessor<Float> DATA_EXPLOSION_RADIUS = SynchedEntityData.defineId(NoctuIgneProjectile.class, EntityDataSerializers.FLOAT);

    public NoctuIgneProjectile(EntityType<? extends NoctuIgneProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public NoctuIgneProjectile(Level level, double x, double y, double z, double dirX, double dirY, double dirZ) {
        super(EntityType.DRAGON_FIREBALL, level);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        double d0 = Mth.sqrt((float)(dirX * dirX + dirY * dirY + dirZ * dirZ));
        this.xPower = dirX / d0 * 0.1;
        this.yPower = dirY / d0 * 0.1;
        this.zPower = dirZ / d0 * 0.1;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_EXPLOSION_RADIUS, 1.0f);
    }

    public void setExplosionRadius(float radius) {
        this.entityData.set(DATA_EXPLOSION_RADIUS, radius);
    }

    public float getExplosionRadius() {
        return this.entityData.get(DATA_EXPLOSION_RADIUS);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            this.level().explode(null, this.getX(), this.getY(), this.getZ(), this.getExplosionRadius(), Level.ExplosionInteraction.BLOCK);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.level().explode(null, this.getX(), this.getY(), this.getZ(), this.getExplosionRadius(), Level.ExplosionInteraction.BLOCK);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        } else if (this.tickCount > 100) {
            this.discard();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
