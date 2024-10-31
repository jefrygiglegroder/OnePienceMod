package jefry.mod.onepiecemod.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IDevilFruitUser extends INBTSerializable<CompoundTag> {
    // Get the current Devil Fruit type
    String getDevilFruit();

    // Set the Devil Fruit type
    void setDevilFruit(String fruitType);

    // Check if the player has a Devil Fruit
    boolean hasDevilFruit();

    // Remove the Devil Fruit from the player
    void removeDevilFruit();

    // Activate a specific ability by index
    void activateAbility(int index);

    // Get the cooldown for a specific ability
    int getAbilityCooldown(int index);

    // Set the cooldown for a specific ability
    void setAbilityCooldown(int index, int cooldown);

    // Update method called each tick
    void tick();

    // Get the current stamina
    float getStamina();

    // Set the current stamina
    void setStamina(float stamina);

    // Add or remove stamina
    void modifyStamina(float amount);

    float getMastery();
    void setMastery(float mastery);
    void increaseMastery(float amount);
}