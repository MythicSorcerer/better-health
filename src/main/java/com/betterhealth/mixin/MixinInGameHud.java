package com.betterhealth.mixin;

import com.betterhealth.config.BetterHealthConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    private static final int HUD_MARGIN = 8;
    private static final int TOP_ANCHOR_Y = 18;
    private static final int FULL_BAR_WIDTH = 182;
    private static final int HALF_BAR_WIDTH = 81;
    private static final List<FloatingNumber> floatingNumbers = new ArrayList<>();
    private static final List<HistoricalMaxEntry> historicalMax = new ArrayList<>();
    private static int lastHealth = 0;
    private static final DecimalFormat HEART_FORMAT = new DecimalFormat("0.0#");
    
    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void betterHealth$renderCustomHealthBar(DrawContext context, CallbackInfo ci) {
        BetterHealthConfig config = BetterHealthConfig.getInstance();
        
        if (!config.enabled) {
            return;
        }
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        float maxHealth = client.player.getMaxHealth();
        if (shouldUseCustomHud(config, maxHealth)) {
            ci.cancel();

            int screenWidth = client.getWindow().getScaledWidth();
            int screenHeight = client.getWindow().getScaledHeight();

            float currentHealth = client.player.getHealth();
            float absorption = client.player.getAbsorptionAmount();
            int hunger = MathHelper.ceil(client.player.getHungerManager().getFoodLevel());
            float saturation = client.player.getHungerManager().getSaturationLevel();
            
            float displayMax = maxHealth;
            if (config.trackHistoricalMax) {
                displayMax = getHistoricalMax(maxHealth, config.historicalMaxMinutes);
            }

            int barWidth = config.fullBarMode ? FULL_BAR_WIDTH : HALF_BAR_WIDTH;
            int x = getAlignedX(config, screenWidth, barWidth);

            int barSpacing = 1;

            boolean showHungerBar = config.showHungerBar && config.hungerBarHeight > 0;
            boolean showSaturationBar = config.showSaturationBar && config.saturationBarHeight > 0;
            int healthHeight = Math.max(2, config.barHeight);
            int hungerHeight = Math.max(0, config.hungerBarHeight);
            int saturationHeight = Math.max(0, config.saturationBarHeight);

            int minBarY = Integer.MAX_VALUE;
            int maxBarY = Integer.MIN_VALUE;
            int healthCenterX = x + (barWidth / 2);

            boolean splitHalfMode = !config.fullBarMode && config.splitBarsInHalfMode && showHungerBar;
            if (splitHalfMode) {
                int splitTopY = getSplitTopY(config, screenHeight, healthHeight);
                int gapWidth = Math.round(barWidth * (config.halfModeGapPercent / 100f));
                int healthSegmentWidth = Math.round(barWidth * (config.halfModeHealthPercent / 100f));
                int hungerSegmentWidth = Math.max(0, barWidth - gapWidth - healthSegmentWidth);

                if (healthSegmentWidth + gapWidth > barWidth) {
                    healthSegmentWidth = Math.max(0, barWidth - gapWidth);
                }

                int healthSegmentX = x;
                int hungerSegmentX = x + healthSegmentWidth + gapWidth;
                if (config.swapBarsLeftRight) {
                    hungerSegmentX = x;
                    healthSegmentX = x + hungerSegmentWidth + gapWidth;
                }

                renderHealthBar(context, config, healthSegmentX, splitTopY, healthSegmentWidth, healthHeight, currentHealth, maxHealth, absorption, displayMax);
                renderHungerSegment(context, config, hungerSegmentX, splitTopY, hungerSegmentWidth, healthHeight, hunger, saturation, showSaturationBar);

                minBarY = Math.min(minBarY, splitTopY);
                maxBarY = Math.max(maxBarY, splitTopY + healthHeight - 1);
                healthCenterX = healthSegmentX + (healthSegmentWidth / 2);
            } else {
                int stackedTotalHeight = getStackedTotalHeight(config, healthHeight, hungerHeight, saturationHeight, barSpacing, showHungerBar, showSaturationBar);

                int healthY;
                int hungerTopY = Integer.MIN_VALUE;

                if (config.verticalAnchorMode == 2) {
                    healthY = TOP_ANCHOR_Y + config.barYOffset;
                    if (showHungerBar || showSaturationBar) {
                        hungerTopY = (screenHeight - 36 - (stackedTotalHeight - healthHeight) + 1) + config.barYOffset;
                    }
                } else if (config.verticalAnchorMode == 3) {
                    healthY = (screenHeight - 36 - healthHeight + 1) + config.barYOffset;
                    if (showHungerBar || showSaturationBar) {
                        hungerTopY = TOP_ANCHOR_Y + config.barYOffset;
                    }
                } else {
                    int topY = (config.verticalAnchorMode == 1 ? TOP_ANCHOR_Y : (screenHeight - 36 - stackedTotalHeight + 1)) + config.barYOffset;
                    int currentY = topY;

                    if (config.hungerAboveHealth) {
                        if (showSaturationBar) {
                            renderSimpleBar(context, config, x, currentY, barWidth, saturationHeight, Math.min(saturation / 20f, 1.0f), config.saturationBarColor);
                            minBarY = Math.min(minBarY, currentY);
                            maxBarY = Math.max(maxBarY, currentY + saturationHeight - 1);
                            currentY += saturationHeight + barSpacing;
                        }
                        if (showHungerBar) {
                            renderSimpleBar(context, config, x, currentY, barWidth, hungerHeight, Math.min(hunger / 20f, 1.0f), config.hungerBarColor);
                            minBarY = Math.min(minBarY, currentY);
                            maxBarY = Math.max(maxBarY, currentY + hungerHeight - 1);
                            currentY += hungerHeight + barSpacing;
                        }
                        healthY = currentY;
                    } else {
                        healthY = currentY;
                        currentY += healthHeight + barSpacing;

                        if (showSaturationBar) {
                            renderSimpleBar(context, config, x, currentY, barWidth, saturationHeight, Math.min(saturation / 20f, 1.0f), config.saturationBarColor);
                            minBarY = Math.min(minBarY, currentY);
                            maxBarY = Math.max(maxBarY, currentY + saturationHeight - 1);
                            currentY += saturationHeight + barSpacing;
                        }
                        if (showHungerBar) {
                            renderSimpleBar(context, config, x, currentY, barWidth, hungerHeight, Math.min(hunger / 20f, 1.0f), config.hungerBarColor);
                            minBarY = Math.min(minBarY, currentY);
                            maxBarY = Math.max(maxBarY, currentY + hungerHeight - 1);
                        }
                    }

                    renderHealthBar(context, config, x, healthY, barWidth, healthHeight, currentHealth, maxHealth, absorption, displayMax);
                    minBarY = Math.min(minBarY, healthY);
                    maxBarY = Math.max(maxBarY, healthY + healthHeight - 1);
                    healthCenterX = x + (barWidth / 2);
                    hungerTopY = Integer.MIN_VALUE;
                }

                if (config.verticalAnchorMode == 2 || config.verticalAnchorMode == 3) {
                    if (showSaturationBar || showHungerBar) {
                        int currentY = hungerTopY;
                        if (showSaturationBar) {
                            renderSimpleBar(context, config, x, currentY, barWidth, saturationHeight, Math.min(saturation / 20f, 1.0f), config.saturationBarColor);
                            minBarY = Math.min(minBarY, currentY);
                            maxBarY = Math.max(maxBarY, currentY + saturationHeight - 1);
                            currentY += saturationHeight + barSpacing;
                        }
                        if (showHungerBar) {
                            renderSimpleBar(context, config, x, currentY, barWidth, hungerHeight, Math.min(hunger / 20f, 1.0f), config.hungerBarColor);
                            minBarY = Math.min(minBarY, currentY);
                            maxBarY = Math.max(maxBarY, currentY + hungerHeight - 1);
                        }
                    }

                    renderHealthBar(context, config, x, healthY, barWidth, healthHeight, currentHealth, maxHealth, absorption, displayMax);
                    minBarY = Math.min(minBarY, healthY);
                    maxBarY = Math.max(maxBarY, healthY + healthHeight - 1);
                    healthCenterX = x + (barWidth / 2);
                }
            }

            String healthText = getHealthText(config, currentHealth, maxHealth, absorption, hunger);

            int textX = getTextX(config, client, screenWidth, x, barWidth, healthText);
            int textY = Math.max(2, minBarY - 10);
            if (minBarY == Integer.MAX_VALUE) {
                textY = TOP_ANCHOR_Y;
            }
            context.drawTextWithShadow(client.textRenderer, healthText, textX, textY, 0xFFFFFFFF);

            if (config.trackHistoricalMax) {
                trackMaxHealth(maxHealth);
            }

            if (config.showFloatingNumbers) {
                updateFloatingNumbers(currentHealth, healthCenterX);
                int flowDirection = healthCenterX >= (screenWidth / 2) ? -1 : 1;
                renderFloatingNumbers(context, client, maxBarY + 1, flowDirection, config.floatingNumbersFlowDown);
            }
        }
    }

    private boolean shouldUseCustomHud(BetterHealthConfig config, float maxHealth) {
        return config.triggerThreshold == 0 || maxHealth > (config.triggerThreshold * 2);
    }

    private String getHealthText(BetterHealthConfig config, float currentHealth, float maxHealth, float absorption, int hunger) {
        String healthText = formatHealth(currentHealth, config) + "/" + formatHealth(maxHealth, config);
        if (absorption > 0) {
            healthText += " +" + formatHealth(absorption, config);
        }
        if (config.showHeartIcon) {
            healthText = "\u2665 " + healthText;
        }
        if (config.showHunger) {
            healthText += " \u00a7f\u2665" + hunger;
        }
        return healthText;
    }

    private void updateFloatingNumbers(float currentHealth, int healthCenterX) {
        if (lastHealth == 0) {
            lastHealth = (int) currentHealth;
        }

        int healthChange = (int) (lastHealth - currentHealth);
        if (healthChange > 0) {
            floatingNumbers.add(new FloatingNumber("-" + healthChange, healthCenterX, false));
        } else if (healthChange < 0) {
            floatingNumbers.add(new FloatingNumber("+" + (-healthChange), healthCenterX, true));
        }

        lastHealth = (int) currentHealth;
    }

    private int getAlignedX(BetterHealthConfig config, int screenWidth, int barWidth) {
        if (config.horizontalAlignment == 0) {
            return HUD_MARGIN;
        }
        if (config.horizontalAlignment == 2) {
            return screenWidth - HUD_MARGIN - barWidth;
        }
        return (screenWidth - barWidth) / 2;
    }

    private int getTextX(BetterHealthConfig config, MinecraftClient client, int screenWidth, int barX, int barWidth, String text) {
        int textWidth = client.textRenderer.getWidth(text);
        if (config.horizontalAlignment == 0) {
            return barX;
        }
        if (config.horizontalAlignment == 2) {
            return barX + barWidth - textWidth;
        }
        return (screenWidth - textWidth) / 2;
    }

    private int getSplitTopY(BetterHealthConfig config, int screenHeight, int lineHeight) {
        if (config.verticalAnchorMode == 1 || config.verticalAnchorMode == 2 || config.verticalAnchorMode == 3) {
            return TOP_ANCHOR_Y + config.barYOffset;
        }
        return (screenHeight - 36 - lineHeight + 1) + config.barYOffset;
    }

    private int getStackedTotalHeight(
            BetterHealthConfig config,
            int healthHeight,
            int hungerHeight,
            int saturationHeight,
            int barSpacing,
            boolean showHungerBar,
            boolean showSaturationBar
    ) {
        int total = healthHeight;
        if (showHungerBar) {
            total += hungerHeight + barSpacing;
        }
        if (showSaturationBar) {
            total += saturationHeight + barSpacing;
        }
        return total;
    }

    private void renderSimpleBar(DrawContext context, BetterHealthConfig config, int x, int y, int width, int height, float percent, int fillColor) {
        if (height <= 0 || width <= 0) {
            return;
        }

        context.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xC0000000);
        context.fill(x, y, x + width, y + height, config.backgroundColor);

        int fillWidth = Math.round(width * MathHelper.clamp(percent, 0.0f, 1.0f));
        if (fillWidth > 0) {
            context.fill(x, y, x + fillWidth, y + height, fillColor);
        }
    }

    private void renderHungerSegment(
            DrawContext context,
            BetterHealthConfig config,
            int x,
            int y,
            int width,
            int height,
            int hunger,
            float saturation,
            boolean showSaturationBar
    ) {
        if (width <= 0 || height <= 0) {
            return;
        }

        context.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xC0000000);
        context.fill(x, y, x + width, y + height, config.backgroundColor);

        int hungerWidth = Math.round(width * MathHelper.clamp(hunger / 20f, 0.0f, 1.0f));
        if (hungerWidth > 0) {
            context.fill(x, y, x + hungerWidth, y + height, config.hungerBarColor);
        }

        if (showSaturationBar && saturation > 0f) {
            int satWidth = Math.round(width * MathHelper.clamp(saturation / 20f, 0.0f, 1.0f));
            int satHeight = Math.max(1, height / 2);
            if (satWidth > 0) {
                context.fill(x, y, x + satWidth, y + satHeight, config.saturationBarColor);
            }
        }
    }

    private void renderHealthBar(
            DrawContext context,
            BetterHealthConfig config,
            int x,
            int y,
            int width,
            int height,
            float currentHealth,
            float maxHealth,
            float absorption,
            float displayMax
    ) {
        if (width <= 0 || height <= 0 || displayMax <= 0f) {
            return;
        }

        float healthPercent = Math.min(currentHealth / displayMax, 1.0f);
        float spentPercent = Math.max(0, Math.min((maxHealth - currentHealth) / displayMax, 1.0f - healthPercent));
        float absorptionPercent = Math.min(absorption / displayMax, 1.0f - healthPercent - spentPercent);

        int healthWidth = Math.round(width * healthPercent);
        int spentWidth = Math.round(width * spentPercent);
        int absorptionWidth = Math.round(width * absorptionPercent);

        context.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xC0000000);
        context.fill(x, y, x + width, y + height, config.backgroundColor);

        if (config.healthFillDirection == 0) {
            int currentX = x;
            if (healthWidth > 0) {
                context.fill(currentX, y, currentX + healthWidth, y + height, config.healthBarColor);
                currentX += healthWidth;
            }
            if (spentWidth > 0) {
                context.fill(currentX, y, currentX + spentWidth, y + height, config.spentHealthColor);
                currentX += spentWidth;
            }
            if (absorptionWidth > 0) {
                context.fill(currentX, y, currentX + absorptionWidth, y + height, config.absorptionColor);
            }
        } else {
            int currentX = x + width;
            if (healthWidth > 0) {
                context.fill(currentX - healthWidth, y, currentX, y + height, config.healthBarColor);
                currentX -= healthWidth;
            }
            if (spentWidth > 0) {
                context.fill(currentX - spentWidth, y, currentX, y + height, config.spentHealthColor);
                currentX -= spentWidth;
            }
            if (absorptionWidth > 0) {
                context.fill(currentX - absorptionWidth, y, currentX, y + height, config.absorptionColor);
            }
        }
    }
    
    private String formatHealth(float health, BetterHealthConfig config) {
        if (config.showHealthAsHearts) {
            float hearts = health / 2.0f;
            if (config.showPrecision) {
                return HEART_FORMAT.format(hearts);
            } else {
                return String.valueOf((int) Math.ceil(hearts * 10) / 10.0);
            }
        } else {
            if (config.showPrecision) {
                return String.format("%." + config.precisionDigits + "f", health);
            } else {
                return String.valueOf((int) health);
            }
        }
    }
    
    private float getHistoricalMax(float currentMax, int minutes) {
        long cutoff = System.currentTimeMillis() - (minutes * 60 * 1000L);
        float max = currentMax;
        
        Iterator<HistoricalMaxEntry> iterator = historicalMax.iterator();
        while (iterator.hasNext()) {
            HistoricalMaxEntry entry = iterator.next();
            if (entry.timestamp < cutoff) {
                iterator.remove();
            } else if (entry.maxHealth > max) {
                max = entry.maxHealth;
            }
        }
        
        return Math.max(max, currentMax);
    }
    
    private void trackMaxHealth(float maxHealth) {
        if (historicalMax.isEmpty() || historicalMax.get(historicalMax.size() - 1).maxHealth != maxHealth) {
            historicalMax.add(new HistoricalMaxEntry(maxHealth, System.currentTimeMillis()));
        }
    }
    
    private void renderFloatingNumbers(
            DrawContext context,
            MinecraftClient client,
            int barBottomY,
            int horizontalFlowDirection,
            boolean flowDown
    ) {
        Iterator<FloatingNumber> iterator = floatingNumbers.iterator();
        while (iterator.hasNext()) {
            FloatingNumber num = iterator.next();
            num.life--;
            
            if (num.life <= 0) {
                iterator.remove();
                continue;
            }
            
            float alpha = Math.min(1.0f, num.life / 20f);
            int color = (((int) (alpha * 255)) << 24) | (num.positive ? 0x00FF55 : 0xFF3333);
            
            float progress = 1.0f - (num.life / (float) num.maxLife);
            int offsetY = (int) (progress * 30) * (flowDown ? 1 : -1);
            float offsetX = (float) Math.sin(progress * Math.PI * 2) * 15 * horizontalFlowDirection;
            
            int textWidth = client.textRenderer.getWidth(num.text);
            int x = num.baseX - textWidth / 2 + (int) offsetX;
            int y = (flowDown ? barBottomY + 8 : barBottomY - 25) + offsetY;
            
            context.drawTextWithShadow(client.textRenderer, num.text, x, y, color);
        }
    }
    
    private static class FloatingNumber {
        String text;
        int baseX;
        int life;
        int maxLife;
        boolean positive;
        
        FloatingNumber(String text, int baseX, boolean positive) {
            this.text = text;
            this.baseX = baseX;
            this.positive = positive;
            this.maxLife = BetterHealthConfig.getInstance().floatingNumbersDuration;
            this.life = this.maxLife;
        }
    }
    
    private static class HistoricalMaxEntry {
        float maxHealth;
        long timestamp;
        
        HistoricalMaxEntry(float maxHealth, long timestamp) {
            this.maxHealth = maxHealth;
            this.timestamp = timestamp;
        }
    }
}
