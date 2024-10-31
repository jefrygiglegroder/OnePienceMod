package jefry.mod.onepiecemod.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraft.world.entity.player.Player;
import java.util.HashMap;
import java.util.Map;

@AutoRegisterCapability
public class DevilFruitUser implements IDevilFruitUser {

    private IDevilFruit devilFruit;
    private float mastery;
    private String currentFruit = "";
    private float stamina = 100.0f;
    private final Map<Integer, Integer> abilityCooldowns = new HashMap<>();
    private final LazyOptional<IDevilFruitUser> holder = LazyOptional.of(() -> this);
    private Player player;

    public DevilFruitUser() {
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String getDevilFruit() {
        return currentFruit;
    }

    @Override
    public void setDevilFruit(String fruitType) {
        this.currentFruit = fruitType;
        // Reset cooldowns when getting a new fruit
        abilityCooldowns.clear();
    }

    @Override
    public boolean hasDevilFruit() {
        return !currentFruit.isEmpty();
    }

    @Override
    public void removeDevilFruit() {
        currentFruit = "";
        abilityCooldowns.clear();
    }

    @Override
    public void activateAbility(int index) {
        if (!hasDevilFruit() || getAbilityCooldown(index) > 0 || player == null) {
            return;
        }

        // Get the ability cost and cooldown from your DevilFruits registry/system
        float abilityCost = 20.0f; // Example value, replace with actual cost
        int cooldown = 100; // Example value, replace with actual cooldown

        if (stamina >= abilityCost) {
            modifyStamina(-abilityCost);
            setAbilityCooldown(index, cooldown);
            // Implement actual ability effect here
        }
    }

    @Override
    public int getAbilityCooldown(int index) {
        return abilityCooldowns.getOrDefault(index, 0);
    }

    @Override
    public void setAbilityCooldown(int index, int cooldown) {
        abilityCooldowns.put(index, cooldown);
    }

    @Override
    public void tick() {
        // Regenerate stamina
        if (stamina < 100.0f) {
            modifyStamina(0.1f); // Adjust regeneration rate as needed
        }

        // Update cooldowns
        abilityCooldowns.entrySet().forEach(entry -> {
            if (entry.getValue() > 0) {
                abilityCooldowns.put(entry.getKey(), entry.getValue() - 1);
            }
        });
    }

    @Override
    public float getStamina() {
        return stamina;
    }

    @Override
    public void setStamina(float value) {
        this.stamina = Math.max(0, Math.min(100, value));
    }

    @Override
    public void modifyStamina(float amount) {
        setStamina(stamina + amount);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("DevilFruit", currentFruit);
        tag.putFloat("Stamina", stamina);

        CompoundTag cooldowns = new CompoundTag();
        abilityCooldowns.forEach((index, cooldown) ->
                cooldowns.putInt(String.valueOf(index), cooldown));
        tag.put("Cooldowns", cooldowns);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        currentFruit = tag.getString("DevilFruit");
        stamina = tag.getFloat("Stamina");

        CompoundTag cooldowns = tag.getCompound("Cooldowns");
        abilityCooldowns.clear();
        cooldowns.getAllKeys().forEach(key ->
                abilityCooldowns.put(Integer.parseInt(key), cooldowns.getInt(key)));
    }

    public LazyOptional<IDevilFruitUser> getHolder() {
        return holder;
    }

    public void invalidate() {
        holder.invalidate();
    }
    @Override
    public float getMastery() {
        return mastery;
    }

    @Override
    public void setMastery(float mastery) {
        this.mastery = mastery;
    }

    @Override
    public void increaseMastery(float amount) {
        this.mastery = Math.min(100.0f, this.mastery + amount);
    }

    public void saveNBTData(CompoundTag nbt) {
        if (hasDevilFruit()) {
            nbt.putString("DevilFruit", devilFruit.getName());
        }
        nbt.putFloat("Mastery", mastery);
    }

    public void loadNBTData(CompoundTag nbt) {
        if (nbt.contains("DevilFruit")) {
            // You'll need to implement a way to get devil fruit from name
            // This is just a placeholder - implement your own devil fruit registry
            // setDevilFruit(DevilFruitRegistry.getFromName(nbt.getString("DevilFruit")));
        }
        mastery = nbt.getFloat("Mastery");
    }
}