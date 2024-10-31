package jefry.mod.onepiecemod.client;

import jefry.mod.onepiecemod.Onepiecemod;
import jefry.mod.onepiecemod.network.packets.AbilityKeyPacket;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static final KeyMapping[] ABILITY_KEYS = new KeyMapping[4];

    public static void register(RegisterKeyMappingsEvent event) {
        // Register key bindings for abilities
        for (int i = 0; i < ABILITY_KEYS.length; i++) {
            ABILITY_KEYS[i] = new KeyMapping(
                    "key.onepiecemod.ability" + (i + 1),
                    KeyConflictContext.IN_GAME,
                    KeyModifier.NONE,
                    GLFW.GLFW_KEY_1 + i,  // Defaults to 1,2,3,4
                    "key.categories.onepiecemod"
            );
            event.register(ABILITY_KEYS[i]);
        }
    }

    public static KeyMapping getAbilityKeyBinding(int index) {
        return ABILITY_KEYS[index];
    }

    public static void checkKeys() {
        for (int i = 0; i < ABILITY_KEYS.length; i++) {
            while (ABILITY_KEYS[i].consumeClick()) {
                Onepiecemod.sendToServer(new AbilityKeyPacket(i));
            }
        }
    }
}