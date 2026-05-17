package com.betterhealth.mixin;

import com.betterhealth.config.BetterHealthConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinInGameHud {
    
    @Inject(method = "extractPlayerHealth", at = @At("TAIL"))
    private void betterHealth$renderCustomHealthBar(CallbackInfo ci) {
        BetterHealthConfig config = BetterHealthConfig.getInstance();
        
        if (!config.enabled) {
            return;
        }
        
        Minecraft client = (Minecraft) (Object) this;
        if (client == null || client.player == null) {
            return;
        }

        float maxHealth = client.player.getMaxHealth();
        
        // Check if we should show custom HUD
        boolean shouldShow = config.triggerThreshold == 0 || maxHealth > (config.triggerThreshold * 2);
        if (!shouldShow) {
            return;
        }
        
        // TODO: Port full rendering code for 26.x
        // The 26.x API has significant changes:
        // - GuiGraphicsExtractor instead of DrawContext
        // - Component instead of Text
        // - Different method names for window, hunger, etc.
    }
}