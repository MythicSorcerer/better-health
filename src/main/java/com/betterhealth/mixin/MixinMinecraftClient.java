package com.betterhealth.mixin;

import com.betterhealth.BetterHealthClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraftClient {
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void betterHealth$onTick(CallbackInfo ci) {
        BetterHealthClient.handleKeybinds((Minecraft) (Object) this);
    }
}