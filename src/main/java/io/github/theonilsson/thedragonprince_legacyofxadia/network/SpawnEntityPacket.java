package io.github.theonilsson.thedragonprince_legacyofxadia.network;

import io.github.theonilsson.thedragonprince_legacyofxadia.entity.ModEntities;
import io.github.theonilsson.thedragonprince_legacyofxadia.entity.spell.CirculusLuminisEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class SpawnEntityPacket {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final BlockPos position;

    public SpawnEntityPacket(BlockPos position) {
        this.position = position;
    }

    public SpawnEntityPacket(FriendlyByteBuf buf) {
        this.position = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(position);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerLevel level = (ServerLevel) context.getSender().level();
            CirculusLuminisEntity entity = new CirculusLuminisEntity(ModEntities.CIRCULUS_LUMINIS.get(), level);
            entity.setOwner(context.getSender());
            entity.setPos(position.getX(), position.getY(), position.getZ());
            level.addFreshEntity(entity);
            scheduler.schedule(entity::kill, 15L, TimeUnit.SECONDS);
        });
        context.setPacketHandled(true);
    }
}
