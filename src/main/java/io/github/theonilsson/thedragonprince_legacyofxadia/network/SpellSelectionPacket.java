package io.github.theonilsson.thedragonprince_legacyofxadia.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellSelectionPacket {
    private final int selectedSpell;

    public SpellSelectionPacket(int selectedSpell) {
        this.selectedSpell = selectedSpell;
    }

    public SpellSelectionPacket(FriendlyByteBuf buf) {
        this.selectedSpell = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(selectedSpell);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                player.getPersistentData().putInt("SelectedSpell", selectedSpell);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
