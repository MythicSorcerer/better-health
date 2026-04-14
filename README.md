# Better Health Bar

Last updated: 2026-04-14 UTC

Better Health Bar is a client-side Fabric mod for Minecraft 1.21.11. It replaces the vanilla heart HUD with configurable progress bars once the player's maximum health crosses a configurable threshold.

## What It Does

- Replaces vanilla hearts with a health bar when max health is above the configured threshold
- Shows current and maximum health as text above the bar
- Supports optional absorption, hunger, and saturation overlays
- Includes a Cloth Config screen opened with `B` by default
- Runs entirely on the client and does not require server installation

## Supported Environment

- Minecraft `1.21.11`
- Fabric Loader `0.18.6+`
- Java `21`

## Build

```bash
./gradlew build
```

Artifacts are written to `build/libs/`.

## Install

1. Build or download the jar.
2. Place the jar in your Fabric `mods` folder.
3. Launch the game and press `B` to open the config screen.

## Project Layout

- `src/main/java/com/betterhealth/BetterHealthClient.java`: client entrypoint and keybinding registration
- `src/main/java/com/betterhealth/config/BetterHealthConfig.java`: config persistence and defaults
- `src/main/java/com/betterhealth/config/BetterHealthConfigScreen.java`: Cloth Config UI
- `src/main/java/com/betterhealth/mixin/MixinInGameHud.java`: custom HUD rendering
- `src/main/java/com/betterhealth/mixin/MixinMinecraftClient.java`: keybind polling hook

## Additional Docs

- `docs/architecture.md`
- `docs/configuration.md`
