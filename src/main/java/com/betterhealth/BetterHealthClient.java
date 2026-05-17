package com.betterhealth;

import com.betterhealth.config.BetterHealthConfig;
import com.betterhealth.config.BetterHealthConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

public class BetterHealthClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        BetterHealthConfig.getInstance();
    }
    
    public static void handleKeybinds(Minecraft client) {
        // Keybinds disabled for 26.x - needs API update
        // TODO: Update to new keybinding API
    }
}