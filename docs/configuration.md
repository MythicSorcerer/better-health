# Configuration

Last updated: 2026-04-14 UTC

## Config File

The mod stores settings in `config/better-health.json`.

## General

- `enabled`: turns the custom HUD on or off
- `triggerThreshold`: minimum heart count required before replacing vanilla hearts; `0` means always use the custom HUD

## Display

- `showHeartIcon`: prefixes the health text with a heart icon
- `showHealthAsHearts`: displays values in hearts instead of raw health points
- `showPrecision`: enables decimal formatting
- `precisionDigits`: decimal places when precision is enabled

## Bars

- `fullBarMode`: uses a full-width bar aligned with the hotbar
- `barHeight`: height of the health bar
- `barYOffset`: shifts the rendered bars vertically
- `splitBarsInHalfMode`: in compact mode, splits the row into separate health and hunger segments
- `halfModeHealthPercent`: width allocated to the health segment in split-half mode
- `halfModeGapPercent`: center gap used in split-half mode
- `swapBarsLeftRight`: swaps the segment positions in split-half mode
- `hungerAboveHealth`: controls stacked ordering when not using split-half mode
- `horizontalAlignment`: `0 = left`, `1 = center`, `2 = right`
- `verticalAnchorMode`: `0 = bottom`, `1 = top`, `2 = top health only`, `3 = top hunger only`
- `healthFillDirection`: `0 = left to right`, `1 = right to left`

## Hunger And Saturation

- `showHunger`: appends hunger text to the main health label
- `showHungerBar`: enables the hunger bar
- `showSaturationBar`: enables the saturation overlay/bar
- `hungerBarHeight`: hunger bar height
- `saturationBarHeight`: saturation bar height

## Effects And Tracking

- `showDamageFlash`: currently exposed in config but not yet used by the renderer
- `showFloatingNumbers`: shows damage/heal deltas near the health bar
- `floatingNumbersDuration`: lifetime of floating numbers in ticks
- `floatingNumbersFlowDown`: flips the vertical movement direction
- `trackHistoricalMax`: uses the highest recent max-health value for bar scaling
- `historicalMaxMinutes`: lookback window for historical max-health tracking

## Colors

- `healthBarColor`: current health fill
- `spentHealthColor`: missing health fill within the tracked range
- `absorptionColor`: absorption fill
- `backgroundColor`: bar background
- `hungerBarColor`: hunger fill
- `saturationBarColor`: saturation fill
