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
    private static final String CONFIG_KEY_TRANSLATION = "key.better-health.config";
    private static final int DEFAULT_CONFIG_KEY = GLFW.GLFW_KEY_B;
    private static KeyBinding openConfigKey;
    
    @Override
    public void onInitializeClient() {
        BetterHealthConfig.getInstance();
        
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                CONFIG_KEY_TRANSLATION,
                InputUtil.Type.KEYSYM,
                DEFAULT_CONFIG_KEY,
                null
        ));
    }
    
    public static void handleKeybinds(MinecraftClient client) {
        while (openConfigKey.wasPressed()) {
            client.setScreen(BetterHealthConfigScreen.createScreen(client.currentScreen));
        }
    }
}
