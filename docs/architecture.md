# Architecture

Last updated: 2026-04-14 UTC

## Overview

Better Health Bar is a small client-only Fabric mod with three main responsibilities:

1. Load and save configuration.
2. Provide an in-game configuration screen.
3. Replace vanilla status bar rendering when the custom HUD should be active.

## Runtime Flow

1. Fabric initializes `BetterHealthClient`.
2. `BetterHealthClient` loads the config singleton and registers the `B` keybinding.
3. `MixinMinecraftClient` hooks the client tick and forwards key presses to `BetterHealthClient.handleKeybinds`.
4. `MixinInGameHud` intercepts `InGameHud.renderStatusBars`.
5. If the mod is enabled and the player's max health is above the configured trigger threshold, the mixin cancels vanilla HUD rendering and draws the custom bars and text instead.

## Rendering Model

`MixinInGameHud` is responsible for all custom HUD rendering.

- Full mode uses the standard hotbar-aligned width.
- Half mode uses a compact width.
- Split-half mode divides the compact bar into health and hunger segments.
- Vertical anchor settings decide whether bars are pinned near the top or bottom of the HUD.
- Floating numbers are tracked in memory and rendered each frame until their configured lifetime expires.

## Configuration Model

`BetterHealthConfig` is a singleton serialized to `config/better-health.json` using Gson.

- Defaults live directly on the config fields.
- `load()` reads JSON and applies values onto the singleton.
- `save()` rewrites the file after load and after UI saves, ensuring new fields are materialized into the JSON file.

## Main Tradeoffs

- The mod keeps most HUD logic in a single mixin class, which makes the render path easier to follow against Minecraft's HUD lifecycle.
- Config values are stored as primitive fields instead of nested config objects, which keeps JSON simple and avoids migration complexity.
- Historical max-health tracking and floating numbers are in-memory only and reset on client restart.
