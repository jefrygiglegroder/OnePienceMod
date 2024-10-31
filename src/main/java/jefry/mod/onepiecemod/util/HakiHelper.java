package jefry.mod.onepiecemod.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class HakiHelper {
    public static final Capability<IHakiUser> HAKI_CAP = CapabilityManager.get(new CapabilityToken<>() {});

    public static boolean hasActiveHaki(Player player) {
        return player.getCapability(HAKI_CAP).map(IHakiUser::isHakiActive).orElse(false);
    }

    public interface IHakiUser {
        boolean isHakiActive();
        void setHakiActive(boolean active);
        int getHakiLevel();
        void setHakiLevel(int level);
        void incrementHakiLevel();
    }

    public static class HakiUser implements IHakiUser {
        private boolean hakiActive = false;
        private int hakiLevel = 1;

        @Override
        public boolean isHakiActive() {
            return hakiActive;
        }

        @Override
        public void setHakiActive(boolean active) {
            this.hakiActive = active;
        }

        @Override
        public int getHakiLevel() {
            return hakiLevel;
        }

        @Override
        public void setHakiLevel(int level) {
            this.hakiLevel = Math.max(1, Math.min(5, level)); // Limit between 1 and 5
        }

        @Override
        public void incrementHakiLevel() {
            if (hakiLevel < 5) {
                hakiLevel++;
            }
        }

        // NBT handling for persistence
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean("hakiActive", hakiActive);
            tag.putInt("hakiLevel", hakiLevel);
            return tag;
        }

        public void deserializeNBT(CompoundTag tag) {
            hakiActive = tag.getBoolean("hakiActive");
            hakiLevel = tag.getInt("hakiLevel");
        }
    }
}