package jefry.mod.onepiecemod.capability;

import net.minecraft.world.entity.player.Player;

public interface IDevilFruit {
    void applyPassiveEffects(Player player);
    boolean isLogia();
    String getName();
}