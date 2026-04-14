package com.betterhealth;

import com.betterhealth.config.BetterHealthConfig;
import com.betterhealth.config.BetterHealthConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class BetterHealthClient implements ClientModInitializer {
    private static KeyBinding openConfigKey;
    
    @Override
    public void onInitializeClient() {
        BetterHealthConfig.getInstance();
        
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.better-health.config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                null
        ));
    }
    
    public static void handleKeybinds(MinecraftClient client) {
        while (openConfigKey.wasPressed()) {
            client.setScreen(BetterHealthConfigScreen.createScreen(client.currentScreen));
        }
    }
}
