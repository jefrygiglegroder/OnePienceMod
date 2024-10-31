package jefry.mod.onepiecemod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import jefry.mod.onepiecemod.Onepiecemod;
import jefry.mod.onepiecemod.capability.IDevilFruitUser;
import jefry.mod.onepiecemod.client.KeyBindings;
import jefry.mod.onepiecemod.network.packets.AbilityKeyPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.util.LazyOptional;
import org.lwjgl.glfw.GLFW;

import static javax.swing.plaf.basic.BasicGraphicsUtils.drawString;

public class GameOverlay extends GuiComponent implements IGuiOverlay {
    private static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(Onepiecemod.MODID, "textures/gui/overlay.png");
    private static final int BAR_WIDTH = 82;
    private static final int BAR_HEIGHT = 8;
    private static final int ABILITY_ICON_SIZE = 20;

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) return;

        LazyOptional<IDevilFruitUser> cap = minecraft.player.getCapability(Onepiecemod.DEVIL_FRUIT_CAP);
        cap.ifPresent(devilFruitUser -> {
            renderStaminaBar(poseStack, screenWidth, screenHeight, devilFruitUser);
            renderAbilityIcons(poseStack, screenWidth, screenHeight, devilFruitUser);
        });
    }

    private void renderStaminaBar(PoseStack poseStack, int screenWidth, int screenHeight, IDevilFruitUser cap) {
        RenderSystem.setShaderTexture(0, OVERLAY_TEXTURE);
        RenderSystem.enableBlend();

        // Position the stamina bar above the experience bar
        int x = screenWidth / 2 - BAR_WIDTH / 2;
        int y = screenHeight - 29;

        // Draw background
        blit(poseStack, x, y, 0, 0, BAR_WIDTH, BAR_HEIGHT);

        // Draw foreground (filled portion)
        float staminaPercentage = cap.getStamina() / cap.getMaxStamina();
        int fillWidth = (int)(BAR_WIDTH * staminaPercentage);
        blit(poseStack, x, y, 0, BAR_HEIGHT, fillWidth, BAR_HEIGHT);

        // Draw stamina text
        String staminaText = String.format("%.1f/%.1f", cap.getStamina(), cap.getMaxStamina());
        drawCenteredString(poseStack, Minecraft.getInstance().font, staminaText,
                x + BAR_WIDTH / 2, y - 10, 0xFFFFFF);
    }

    private void renderAbilityIcons(PoseStack poseStack, int screenWidth, int screenHeight, IDevilFruitUser cap) {
        RenderSystem.setShaderTexture(0, OVERLAY_TEXTURE);

        // Position ability icons in a row on the right side
        int startX = screenWidth - ABILITY_ICON_SIZE - 10;
        int startY = screenHeight / 2;

        for (int i = 0; i < cap.getAbilities().size(); i++) {
            int x = startX;
            int y = startY + (ABILITY_ICON_SIZE + 5) * i;

            // Draw ability background
            blit(poseStack, x, y, 0, 16, ABILITY_ICON_SIZE, ABILITY_ICON_SIZE);

            // Draw cooldown overlay if ability is on cooldown
            if (cap.isAbilityOnCooldown(i)) {
                float cooldownPercentage = cap.getAbilityCooldownPercent(i);
                int cooldownHeight = (int)(ABILITY_ICON_SIZE * cooldownPercentage);
                blit(poseStack, x, y + (ABILITY_ICON_SIZE - cooldownHeight),
                        20, 16, ABILITY_ICON_SIZE, cooldownHeight);
            }

            // Draw key binding
            Component keyName = KeyBindings.getAbilityKeyBinding(i).getKey();
            drawString(poseStack, Minecraft.getInstance().font, keyName,
                    x - 15, y + 5, 0xFFFFFF);
        }
    }

    @Override
    public void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float v, int i, int i1) {

    }
}
