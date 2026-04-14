package com.betterhealth.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    private static final int MAX_HEARTS_THRESHOLD = 30;

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void betterhealth$renderHealthBar(DrawContext context, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options == null || client.options.hudHidden) {
            return;
        }

        PlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();

        if (maxHealth > MAX_HEARTS_THRESHOLD * 2.0f) {
            this.renderXpBarHealth(context, client, currentHealth, maxHealth);
            ci.cancel();
        }
    }

    private void renderXpBarHealth(DrawContext context, MinecraftClient client, float currentHealth, float maxHealth) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int barWidth = 182;
        int barHeight = 5;
        int x = (screenWidth - barWidth) / 2;
        int y = screenHeight - 36;

        float fillPercent = Math.min(currentHealth / maxHealth, 1.0f);
        int filledWidth = (int) (barWidth * fillPercent);

        int colorBackground = 0xC0000000;
        int colorFill = 0xCC1E9E1E;
        int colorBorder = 0xC0000000;

        context.fill(x - 1, y - 1, x + barWidth + 1, y + barHeight + 1, colorBorder);
        context.fill(x, y, x + barWidth, y + barHeight, colorBackground);
        context.fill(x, y, x + filledWidth, y + barHeight, colorFill);

        String healthText = (int) currentHealth + "/" + (int) maxHealth;
        int textX = (screenWidth - client.textRenderer.getWidth(healthText)) / 2;
        int textY = y - 12;

        context.drawTextWithShadow(client.textRenderer, healthText, textX, textY, 0xFFFFFFFF);
    }
}
