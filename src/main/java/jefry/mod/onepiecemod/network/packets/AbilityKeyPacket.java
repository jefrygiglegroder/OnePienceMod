package jefry.mod.onepiecemod.network.packets;

import jefry.mod.onepiecemod.capability.IDevilFruitUser;
import jefry.mod.onepiecemod.Onepiecemod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityKeyPacket {
    private final int abilityIndex;

    public AbilityKeyPacket(int abilityIndex) {
        this.abilityIndex = abilityIndex;
    }

    public static void encode(AbilityKeyPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.abilityIndex);
    }

    public static AbilityKeyPacket decode(FriendlyByteBuf buffer) {
        return new AbilityKeyPacket(buffer.readInt());
    }

    public static void handle(AbilityKeyPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                player.getCapability(Onepiecemod.DEVIL_FRUIT_CAP).ifPresent(cap -> {
                    cap.activateAbility(packet.abilityIndex);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}