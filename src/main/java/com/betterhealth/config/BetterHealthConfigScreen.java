package com.betterhealth.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class BetterHealthConfigScreen {
    
    public static Screen createScreen(Screen parent) {
        BetterHealthConfig config = BetterHealthConfig.getInstance();
        
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal("Better Health"))
                .setSavingRunnable(config::save);
        
        ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
        ConfigCategory display = builder.getOrCreateCategory(Text.literal("Display"));
        ConfigCategory bars = builder.getOrCreateCategory(Text.literal("Bar Settings"));
        ConfigCategory hunger = builder.getOrCreateCategory(Text.literal("Hunger Bar"));
        ConfigCategory colors = builder.getOrCreateCategory(Text.literal("Colors"));
        ConfigCategory advanced = builder.getOrCreateCategory(Text.literal("Advanced"));
        
        ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();
        
        general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Enabled"), config.enabled)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Enable or disable the custom health bar"))
                .setSaveConsumer(value -> config.enabled = value)
                .build());
        
        general.addEntry(entryBuilder.startIntSlider(Text.literal("Trigger Threshold (Hearts)"), config.triggerThreshold, 0, 100)
                .setDefaultValue(30)
                .setTooltip(Text.literal("Show custom bar when max health exceeds this many hearts"))
                .setTextGetter(value -> value == 0
                        ? Text.literal("Always")
                        : Text.literal(value + " hearts"))
                .setSaveConsumer(value -> config.triggerThreshold = value)
                .build());
        
        display.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Heart Icon"), config.showHeartIcon)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Show heart icon at start of health text"))
                .setSaveConsumer(value -> config.showHeartIcon = value)
                .build());
        
        display.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Health as Hearts"), config.showHealthAsHearts)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Show health in hearts (0.5) instead of HP (1)"))
                .setSaveConsumer(value -> config.showHealthAsHearts = value)
                .build());
        
        display.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Decimal Precision"), config.showPrecision)
                .setDefaultValue(false)
                .setTooltip(Text.literal("Show decimal precision (34.5 instead of 34)"))
                .setSaveConsumer(value -> config.showPrecision = value)
                .build());
        
        display.addEntry(entryBuilder.startIntSlider(Text.literal("Precision Digits"), config.precisionDigits, 1, 3)
                .setDefaultValue(1)
                .setTooltip(Text.literal("Number of decimal places to show"))
                .setSaveConsumer(value -> config.precisionDigits = value)
                .build());
        
        bars.addEntry(entryBuilder.startBooleanToggle(Text.literal("Full Bar Mode"), config.fullBarMode)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Full: Match hotbar width | Half: 10 hearts wide"))
                .setSaveConsumer(value -> config.fullBarMode = value)
                .build());

        bars.addEntry(entryBuilder.startBooleanToggle(Text.literal("Split Bars In Half Mode"), config.splitBarsInHalfMode)
                .setDefaultValue(false)
                .setTooltip(Text.literal("In half mode, split one row into health segment + gap + hunger segment"))
                .setSaveConsumer(value -> config.splitBarsInHalfMode = value)
                .build());
        
        bars.addEntry(entryBuilder.startIntSlider(Text.literal("Health Bar Height"), config.barHeight, 2, 20)
                .setDefaultValue(5)
                .setTooltip(Text.literal("Height of the main health bar"))
                .setSaveConsumer(value -> config.barHeight = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Text.literal("Bar Y Offset"), config.barYOffset, -80, 80)
                .setDefaultValue(0)
                .setTooltip(Text.literal("Moves bars up/down for quick visual tuning"))
                .setSaveConsumer(value -> config.barYOffset = value)
                .build());
        
        bars.addEntry(entryBuilder.startIntSlider(Text.literal("Hunger Bar Height"), config.hungerBarHeight, 0, 10)
                .setDefaultValue(2)
                .setTooltip(Text.literal("Height of the hunger bar (0 to disable)"))
                .setSaveConsumer(value -> config.hungerBarHeight = value)
                .build());
        
        bars.addEntry(entryBuilder.startIntSlider(Text.literal("Saturation Bar Height"), config.saturationBarHeight, 0, 10)
                .setDefaultValue(2)
                .setTooltip(Text.literal("Height of the saturation bar (0 to disable)"))
                .setSaveConsumer(value -> config.saturationBarHeight = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Text.literal("Half Mode Health %"), config.halfModeHealthPercent, 10, 90)
                .setDefaultValue(65)
                .setTooltip(Text.literal("Width percentage reserved for health segment in split half mode"))
                .setTextGetter(value -> Text.literal(value + "%"))
                .setSaveConsumer(value -> config.halfModeHealthPercent = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Text.literal("Half Mode Gap %"), config.halfModeGapPercent, 0, 50)
                .setDefaultValue(8)
                .setTooltip(Text.literal("Percentage of total width used as center gap in split half mode"))
                .setTextGetter(value -> Text.literal(value + "%"))
                .setSaveConsumer(value -> config.halfModeGapPercent = value)
                .build());

        bars.addEntry(entryBuilder.startBooleanToggle(Text.literal("Swap Left/Right Segments"), config.swapBarsLeftRight)
                .setDefaultValue(false)
                .setTooltip(Text.literal("Swap health and hunger segment positions in split half mode"))
                .setSaveConsumer(value -> config.swapBarsLeftRight = value)
                .build());

        bars.addEntry(entryBuilder.startBooleanToggle(Text.literal("Hunger Above Health"), config.hungerAboveHealth)
                .setDefaultValue(true)
                .setTooltip(Text.literal("When stacked, place hunger/saturation above health"))
                .setSaveConsumer(value -> config.hungerAboveHealth = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Text.literal("Horizontal Alignment"), config.horizontalAlignment, 0, 2)
                .setDefaultValue(1)
                .setTooltip(Text.literal("Align bars to left, center, or right"))
                .setTextGetter(value -> switch (value) {
                    case 0 -> Text.literal("Left");
                    case 2 -> Text.literal("Right");
                    default -> Text.literal("Center");
                })
                .setSaveConsumer(value -> config.horizontalAlignment = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Text.literal("Vertical Anchor"), config.verticalAnchorMode, 0, 3)
                .setDefaultValue(0)
                .setTooltip(Text.literal("Bottom, top, top health only, or top hunger only"))
                .setTextGetter(value -> switch (value) {
                    case 1 -> Text.literal("Top (Both)");
                    case 2 -> Text.literal("Top Health Only");
                    case 3 -> Text.literal("Top Hunger Only");
                    default -> Text.literal("Bottom (Both)");
                })
                .setSaveConsumer(value -> config.verticalAnchorMode = value)
                .build());

        bars.addEntry(entryBuilder.startIntSlider(Text.literal("Health Fill Direction"), config.healthFillDirection, 0, 1)
                .setDefaultValue(0)
                .setTooltip(Text.literal("Choose which side health fills from"))
                .setTextGetter(value -> value == 0 ? Text.literal("Left to Right") : Text.literal("Right to Left"))
                .setSaveConsumer(value -> config.healthFillDirection = value)
                .build());
        
        hunger.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Hunger Text"), config.showHunger)
                .setDefaultValue(false)
                .setTooltip(Text.literal("Show hunger number next to health"))
                .setSaveConsumer(value -> config.showHunger = value)
                .build());
        
        hunger.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Hunger Bar"), config.showHungerBar)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Show hunger bar above health bar"))
                .setSaveConsumer(value -> config.showHungerBar = value)
                .build());
        
        hunger.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Saturation Bar"), config.showSaturationBar)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Show saturation bar above hunger bar"))
                .setSaveConsumer(value -> config.showSaturationBar = value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Text.literal("Health Color"), config.healthBarColor & 0x00FFFFFF)
                .setDefaultValue(0xCC1E9E1E & 0x00FFFFFF)
                .setTooltip(Text.literal("Color of the current health portion"))
                .setSaveConsumer(value -> config.healthBarColor = 0xCC000000 | value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Text.literal("Spent Health Color"), config.spentHealthColor & 0x00FFFFFF)
                .setDefaultValue(0xCC1A1A1A & 0x00FFFFFF)
                .setTooltip(Text.literal("Color of the lost health portion"))
                .setSaveConsumer(value -> config.spentHealthColor = 0xCC000000 | value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Text.literal("Absorption Color"), config.absorptionColor & 0x00FFFFFF)
                .setDefaultValue(0xCCFFAA00 & 0x00FFFFFF)
                .setTooltip(Text.literal("Color of absorption health (yellow)"))
                .setSaveConsumer(value -> config.absorptionColor = 0xCC000000 | value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Text.literal("Hunger Bar Color"), config.hungerBarColor & 0x00FFFFFF)
                .setDefaultValue(0xCCCC4400 & 0x00FFFFFF)
                .setTooltip(Text.literal("Color of the hunger bar"))
                .setSaveConsumer(value -> config.hungerBarColor = 0xCC000000 | value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Text.literal("Saturation Bar Color"), config.saturationBarColor & 0x00FFFFFF)
                .setDefaultValue(0xCCDD7700 & 0x00FFFFFF)
                .setTooltip(Text.literal("Color of the saturation bar"))
                .setSaveConsumer(value -> config.saturationBarColor = 0xCC000000 | value)
                .build());
        
        colors.addEntry(entryBuilder.startColorField(Text.literal("Background Color"), config.backgroundColor & 0x00FFFFFF)
                .setDefaultValue(0xC0000000 & 0x00FFFFFF)
                .setTooltip(Text.literal("Background color of the bars"))
                .setSaveConsumer(value -> config.backgroundColor = 0xC0000000 | value)
                .build());
        
        advanced.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Damage Flash"), config.showDamageFlash)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Show flashing effect when taking damage"))
                .setSaveConsumer(value -> config.showDamageFlash = value)
                .build());
        
        advanced.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Floating Numbers"), config.showFloatingNumbers)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Show damage/healing numbers floating up"))
                .setSaveConsumer(value -> config.showFloatingNumbers = value)
                .build());
        
        advanced.addEntry(entryBuilder.startIntSlider(Text.literal("Floating Numbers Duration"), config.floatingNumbersDuration, 20, 120)
                .setDefaultValue(60)
                .setTooltip(Text.literal("How long floating numbers stay visible (ticks)"))
                .setSaveConsumer(value -> config.floatingNumbersDuration = value)
                .build());

        advanced.addEntry(entryBuilder.startBooleanToggle(Text.literal("Floating Numbers Flow Down"), config.floatingNumbersFlowDown)
                .setDefaultValue(false)
                .setTooltip(Text.literal("When enabled, floating numbers move downward instead of upward"))
                .setSaveConsumer(value -> config.floatingNumbersFlowDown = value)
                .build());
        
        advanced.addEntry(entryBuilder.startBooleanToggle(Text.literal("Track Historical Max"), config.trackHistoricalMax)
                .setDefaultValue(false)
                .setTooltip(Text.literal("Track your max health over time"))
                .setSaveConsumer(value -> config.trackHistoricalMax = value)
                .build());
        
        advanced.addEntry(entryBuilder.startIntSlider(Text.literal("Historical Max Time (Minutes)"), config.historicalMaxMinutes, 1, 30)
                .setDefaultValue(5)
                .setTooltip(Text.literal("How far back to track max health"))
                .setSaveConsumer(value -> config.historicalMaxMinutes = value)
                .build());
        
        return builder.build();
    }
}
