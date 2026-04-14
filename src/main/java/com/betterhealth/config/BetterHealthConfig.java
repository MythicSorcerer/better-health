package com.betterhealth.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BetterHealthConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("better-health.json");
    
    private static BetterHealthConfig INSTANCE;
    
    public static BetterHealthConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BetterHealthConfig();
            INSTANCE.load();
        }
        return INSTANCE;
    }
    
    public boolean enabled = true;
    public int triggerThreshold = 30;
    
    public boolean fullBarMode = true;
    public int barHeight = 5;
    public int barYOffset = 0;
    public int hungerBarHeight = 2;
    public int saturationBarHeight = 2;
    public boolean splitBarsInHalfMode = false;
    public int halfModeHealthPercent = 65;
    public int halfModeGapPercent = 8;
    public boolean swapBarsLeftRight = false;
    public boolean hungerAboveHealth = true;
    public int horizontalAlignment = 1;
    public int verticalAnchorMode = 0;
    public int healthFillDirection = 0;
    
    public boolean showHeartIcon = true;
    public boolean showHealthAsHearts = true;
    public boolean showPrecision = false;
    public int precisionDigits = 1;
    
    public boolean showHunger = false;
    public boolean showHungerBar = true;
    public boolean showSaturationBar = true;
    
    public boolean showDamageFlash = true;
    public boolean showFloatingNumbers = true;
    public int floatingNumbersDuration = 60;
    public boolean floatingNumbersFlowDown = false;
    
    public int healthBarColor = 0xCC1E9E1E;
    public int spentHealthColor = 0xCC1A1A1A;
    public int absorptionColor = 0xCCFFAA00;
    public int backgroundColor = 0xC0000000;
    public int hungerBarColor = 0xCCCC4400;
    public int saturationBarColor = 0xCCDD7700;
    
    public boolean trackHistoricalMax = false;
    public int historicalMaxMinutes = 5;
    
    public void load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                String json = Files.readString(CONFIG_PATH);
                BetterHealthConfig loaded = GSON.fromJson(json, BetterHealthConfig.class);
                if (loaded != null) {
                    this.enabled = loaded.enabled;
                    this.triggerThreshold = loaded.triggerThreshold;
                    this.fullBarMode = loaded.fullBarMode;
                    this.barHeight = loaded.barHeight;
                    this.barYOffset = loaded.barYOffset;
                    this.hungerBarHeight = loaded.hungerBarHeight;
                    this.saturationBarHeight = loaded.saturationBarHeight;
                    this.splitBarsInHalfMode = loaded.splitBarsInHalfMode;
                    this.halfModeHealthPercent = loaded.halfModeHealthPercent;
                    this.halfModeGapPercent = loaded.halfModeGapPercent;
                    this.swapBarsLeftRight = loaded.swapBarsLeftRight;
                    this.hungerAboveHealth = loaded.hungerAboveHealth;
                    this.horizontalAlignment = loaded.horizontalAlignment;
                    this.verticalAnchorMode = loaded.verticalAnchorMode;
                    this.healthFillDirection = loaded.healthFillDirection;
                    this.showHeartIcon = loaded.showHeartIcon;
                    this.showHealthAsHearts = loaded.showHealthAsHearts;
                    this.showPrecision = loaded.showPrecision;
                    this.precisionDigits = loaded.precisionDigits;
                    this.showHunger = loaded.showHunger;
                    this.showHungerBar = loaded.showHungerBar;
                    this.showSaturationBar = loaded.showSaturationBar;
                    this.showDamageFlash = loaded.showDamageFlash;
                    this.showFloatingNumbers = loaded.showFloatingNumbers;
                    this.floatingNumbersDuration = loaded.floatingNumbersDuration;
                    this.floatingNumbersFlowDown = loaded.floatingNumbersFlowDown;
                    this.healthBarColor = loaded.healthBarColor;
                    this.spentHealthColor = loaded.spentHealthColor;
                    this.absorptionColor = loaded.absorptionColor;
                    this.backgroundColor = loaded.backgroundColor;
                    this.hungerBarColor = loaded.hungerBarColor;
                    this.saturationBarColor = loaded.saturationBarColor;
                    this.trackHistoricalMax = loaded.trackHistoricalMax;
                    this.historicalMaxMinutes = loaded.historicalMaxMinutes;

                    this.barHeight = Math.max(2, this.barHeight);
                    this.hungerBarHeight = Math.max(0, this.hungerBarHeight);
                    this.saturationBarHeight = Math.max(0, this.saturationBarHeight);
                    this.halfModeHealthPercent = Math.max(10, Math.min(90, this.halfModeHealthPercent));
                    this.halfModeGapPercent = Math.max(0, Math.min(50, this.halfModeGapPercent));
                    this.horizontalAlignment = Math.max(0, Math.min(2, this.horizontalAlignment));
                    this.verticalAnchorMode = Math.max(0, Math.min(3, this.verticalAnchorMode));
                    this.healthFillDirection = Math.max(0, Math.min(1, this.healthFillDirection));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        save();
    }
    
    public void save() {
        try {
            String json = GSON.toJson(this);
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
