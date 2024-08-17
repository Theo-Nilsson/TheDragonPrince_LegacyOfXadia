package io.github.theonilsson.thedragonprince_legacyofxadia.network;

import io.github.theonilsson.thedragonprince_legacyofxadia.network.client.ClientReceivedData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class RetrievePlayerDataPacket {
    private final UUID playerUUID;

    public RetrievePlayerDataPacket(@Nullable UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public RetrievePlayerDataPacket(FriendlyByteBuf buf) {
        this.playerUUID = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(playerUUID);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = null;
            CompoundTag tag;
            if (playerUUID != null) {
                for (Player player_ : Objects.requireNonNull(ctx.get().getSender()).level().players()) {
                    if (player_.getUUID().equals(playerUUID)) {
                        player = player_;
                        break;
                    }
                }
            } else {
                player = ctx.get().getSender();
            }
            if (player != null) {
                tag = player.getPersistentData();
                ClientReceivedData.retrievedPlayerData = tag;
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
