package com.betterhealth.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class BetterHealthConfigScreen {
    
    public static Screen createScreen(Screen parent) {
        BetterHealthConfig config = BetterHealthConfig.getInstance();
        
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Better Health"))
                .setSavingRunnable(config::save);
        
        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
        ConfigCategory display = builder.getOrCreateCategory(Component.literal("Display"));
        ConfigCategory bars = builder.getOrCreateCategory(Component.literal("Bar Settings"));
        ConfigCategory hunger = builder.getOrCreateCategory(Component.literal("Hunger Bar"));
        ConfigCategory colors = builder.getOrCreateCategory(Component.literal("Colors"));
        ConfigCategory tuning = builder.getOrCreateCategory(Component.literal("Tuning"));
        ConfigCategory advanced = builder.getOrCreateCategory(Component.literal("Advanced"));
        
        ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();
        
        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enabled"), config.enabled)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Enable or disable the custom health bar"))
                .setSaveConsumer(value -> config.enabled = value)
                .build());
        
        general.addEntry(entryBuilder.startIntSlider(Component.literal("Trigger Threshold (Hearts)"), config.triggerThreshold, 0, 100)
                .setDefaultValue(30)
                .setTooltip(Component.literal("Show custom bar when max health exceeds this many hearts"))
                .setTextGetter(value -> value == 0
                        ? Component.literal("Always")
                        : Component.literal(value + " hearts"))
                .setSaveConsumer(value -> config.triggerThreshold = value)
                .build());
        
        display.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Heart Icon"), config.showHeartIcon)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Show heart icon at start of health text"))
                .setSaveConsumer(value -> config.showHeartIcon = value)
                .build());
        
        display.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Health as Hearts"), config.showHealthAsHearts)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Show health in hearts (0.5) instead of HP (1)"))
                .setSaveConsumer(value -> config.showHealthAsHearts = value)
                .build());
        
        display.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Decimal Precision"), config.showPrecision)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Show decimal precision (34.5 instead of 34)"))
                .setSaveConsumer(value -> config.showPrecision = value)
                .build());
        
        display.addEntry(entryBuilder.startIntSlider(Component.literal("Precision Digits"), config.precisionDigits, 1, 3)
                .setDefaultValue(1)
                .setTooltip(Component.literal("Number of decimal places to show"))
                .setSaveConsumer(value -> config.precisionDigits = value)
                .build());

        tuning.addEntry(entryBuilder.startIntSlider(Component.literal("Text Gap"), config.textGap, 0, 24)
                .setDefaultValue(4)
                .setTooltip(Component.literal("Padding between the health text and the nearest bar block"))
                .setSaveConsumer(value -> config.textGap = value)
                .build());

        tuning.addEntry(entryBuilder.startIntSlider(Component.literal("Text Y Offset"), config.textYOffset, -40, 40)
                .setDefaultValue(0)
                .setTooltip(Component.literal("Moves the health text up or down for visual calibration"))
                .setSaveConsumer(value -> config.textYOffset = value)
                .build());
        
        bars.addEntry(entryBuilder.startBooleanToggle(Component.literal("Full Bar Mode"), config.fullBarMode)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Full: Match hotbar width | Half: 10 hearts wide"))
                .setSaveConsumer(value -> config.fullBarMode = value)
                .build());

        bars.addEntry(entryBuilder.startBooleanToggle(Component.literal("Split Bars In Half Mode"), config.splitBarsInHalfMode)
                .setDefaultValue(false)
                .setTooltip(Component.literal("In half mode, split one row into health segment + gap + hunger segment"))
                .setSaveConsumer(value -> config.splitBarsInHalfMode = value)
                .build());
        
        bars.addEntry(entryBuilder.startIntSlider(Component.literal("Health Bar Height"), config.barHeight, 2, 20)
                .setDefaultValue(5)
                .setTooltip(Component.literal("Height of the main health bar"))
                .setSaveConsumer(value -> config.barHeight = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Component.literal("Bar Y Offset"), config.barYOffset, -80, 80)
                .setDefaultValue(0)
                .setTooltip(Component.literal("Moves bars up/down for quick visual tuning"))
                .setSaveConsumer(value -> config.barYOffset = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Component.literal("Row Spacing"), config.barSpacing, 0, 8)
                .setDefaultValue(1)
                .setTooltip(Component.literal("Spacing between stacked saturation, hunger, and health rows"))
                .setSaveConsumer(value -> config.barSpacing = value)
                .build());
        
        bars.addEntry(entryBuilder.startIntSlider(Component.literal("Hunger Bar Height"), config.hungerBarHeight, 0, 10)
                .setDefaultValue(2)
                .setTooltip(Component.literal("Height of the hunger bar (0 to disable)"))
                .setSaveConsumer(value -> config.hungerBarHeight = value)
                .build());
        
        bars.addEntry(entryBuilder.startIntSlider(Component.literal("Saturation Bar Height"), config.saturationBarHeight, 0, 10)
                .setDefaultValue(2)
                .setTooltip(Component.literal("Height of the saturation bar (0 to disable)"))
                .setSaveConsumer(value -> config.saturationBarHeight = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Component.literal("Half Mode Health %"), config.halfModeHealthPercent, 10, 90)
                .setDefaultValue(65)
                .setTooltip(Component.literal("Width percentage reserved for health segment in split half mode"))
                .setTextGetter(value -> Component.literal(value + "%"))
                .setSaveConsumer(value -> config.halfModeHealthPercent = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Component.literal("Half Mode Gap %"), config.halfModeGapPercent, 0, 50)
                .setDefaultValue(8)
                .setTooltip(Component.literal("Percentage of total width used as center gap in split half mode"))
                .setTextGetter(value -> Component.literal(value + "%"))
                .setSaveConsumer(value -> config.halfModeGapPercent = value)
                .build());

        bars.addEntry(entryBuilder.startBooleanToggle(Component.literal("Swap Left/Right Segments"), config.swapBarsLeftRight)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Swap health and hunger segment positions in split half mode"))
                .setSaveConsumer(value -> config.swapBarsLeftRight = value)
                .build());

        bars.addEntry(entryBuilder.startBooleanToggle(Component.literal("Hunger Above Health"), config.hungerAboveHealth)
                .setDefaultValue(true)
                .setTooltip(Component.literal("When stacked, place hunger/saturation above health"))
                .setSaveConsumer(value -> config.hungerAboveHealth = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Component.literal("Horizontal Alignment"), config.horizontalAlignment, 0, 2)
                .setDefaultValue(1)
                .setTooltip(Component.literal("Align bars to left, center, or right"))
                .setTextGetter(value -> switch (value) {
                    case 0 -> Component.literal("Left");
                    case 2 -> Component.literal("Right");
                    default -> Component.literal("Center");
                })
                .setSaveConsumer(value -> config.horizontalAlignment = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Component.literal("Vertical Anchor"), config.verticalAnchorMode, 0, 3)
                .setDefaultValue(0)
                .setTooltip(Component.literal("Bottom, top, top health only, or top hunger only"))
                .setTextGetter(value -> switch (value) {
                    case 1 -> Component.literal("Top (Both)");
                    case 2 -> Component.literal("Top Health Only");
                    case 3 -> Component.literal("Top Hunger Only");
                    default -> Component.literal("Bottom (Both)");
                })
                .setSaveConsumer(value -> config.verticalAnchorMode = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Component.literal("Health Fill Direction"), config.healthFillDirection, 0, 1)
                .setDefaultValue(0)
                .setTooltip(Component.literal("Choose which side health fills from"))
                .setTextGetter(value -> value == 0 ? Component.literal("Left to Right") : Component.literal("Right to Left"))
                .setSaveConsumer(value -> config.healthFillDirection = value)
                .build());
        
        hunger.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Hunger Text"), config.showHunger)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Show hunger number next to health"))
                .setSaveConsumer(value -> config.showHunger = value)
                .build());
        
        hunger.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Hunger Bar"), config.showHungerBar)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Show hunger bar above health bar"))
                .setSaveConsumer(value -> config.showHungerBar = value)
                .build());
        
        hunger.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Saturation Bar"), config.showSaturationBar)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Show saturation bar above hunger bar"))
                .setSaveConsumer(value -> config.showSaturationBar = value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Component.literal("Health Color"), config.healthBarColor & 0x00FFFFFF)
                .setDefaultValue(0xCC1E9E1E & 0x00FFFFFF)
                .setTooltip(Component.literal("Color of the current health portion"))
                .setSaveConsumer(value -> config.healthBarColor = 0xCC000000 | value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Component.literal("Spent Health Color"), config.spentHealthColor & 0x00FFFFFF)
                .setDefaultValue(0xCC1A1A1A & 0x00FFFFFF)
                .setTooltip(Component.literal("Color of the lost health portion"))
                .setSaveConsumer(value -> config.spentHealthColor = 0xCC000000 | value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Component.literal("Absorption Color"), config.absorptionColor & 0x00FFFFFF)
                .setDefaultValue(0xCCFFAA00 & 0x00FFFFFF)
                .setTooltip(Component.literal("Color of absorption health (yellow)"))
                .setSaveConsumer(value -> config.absorptionColor = 0xCC000000 | value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Component.literal("Hunger Bar Color"), config.hungerBarColor & 0x00FFFFFF)
                .setDefaultValue(0xCCCC4400 & 0x00FFFFFF)
                .setTooltip(Component.literal("Color of the hunger bar"))
                .setSaveConsumer(value -> config.hungerBarColor = 0xCC000000 | value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Component.literal("Saturation Bar Color"), config.saturationBarColor & 0x00FFFFFF)
                .setDefaultValue(0xCCDD7700 & 0x00FFFFFF)
                .setTooltip(Component.literal("Color of the saturation bar"))
                .setSaveConsumer(value -> config.saturationBarColor = 0xCC000000 | value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Component.literal("Background Color"), config.backgroundColor & 0x00FFFFFF)
                .setDefaultValue(0xC0000000 & 0x00FFFFFF)
                .setTooltip(Component.literal("Background color of the bars"))
                .setSaveConsumer(value -> config.backgroundColor = 0xC0000000 | value)
                .build());
        
        advanced.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Damage Flash"), config.showDamageFlash)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Show flashing effect when taking damage"))
                .setSaveConsumer(value -> config.showDamageFlash = value)
                .build());
        
        advanced.addEntry(entryBuilder.startBooleanToggle(Component.literal("Show Floating Numbers"), config.showFloatingNumbers)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Show damage/healing numbers floating up"))
                .setSaveConsumer(value -> config.showFloatingNumbers = value)
                .build());
        
        advanced.addEntry(entryBuilder.startIntSlider(Component.literal("Floating Numbers Duration"), config.floatingNumbersDuration, 20, 120)
                .setDefaultValue(60)
                .setTooltip(Component.literal("How long floating numbers stay visible (ticks)"))
                .setSaveConsumer(value -> config.floatingNumbersDuration = value)
                .build());

        advanced.addEntry(entryBuilder.startBooleanToggle(Component.literal("Floating Numbers Flow Down"), config.floatingNumbersFlowDown)
                .setDefaultValue(false)
                .setTooltip(Component.literal("When enabled, floating numbers move downward instead of upward"))
                .setSaveConsumer(value -> config.floatingNumbersFlowDown = value)
                .build());

        advanced.addEntry(entryBuilder.startIntSlider(Component.literal("Floating Number Sway"), config.floatingNumbersSway, 0, 40)
                .setDefaultValue(15)
                .setTooltip(Component.literal("How wide the floating number S-curve drifts sideways"))
                .setSaveConsumer(value -> config.floatingNumbersSway = value)
                .build());
        
        advanced.addEntry(entryBuilder.startBooleanToggle(Component.literal("Track Historical Max"), config.trackHistoricalMax)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Track your max health over time"))
                .setSaveConsumer(value -> config.trackHistoricalMax = value)
                .build());
        
        advanced.addEntry(entryBuilder.startIntSlider(Component.literal("Historical Max Time (Minutes)"), config.historicalMaxMinutes, 1, 30)
                .setDefaultValue(5)
                .setTooltip(Component.literal("How far back to track max health"))
                .setSaveConsumer(value -> config.historicalMaxMinutes = value)
                .build());
        
        return builder.build();
    }
}