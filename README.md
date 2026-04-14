# Better Health Bar

Client-side Fabric mod for Minecraft 1.21.1 that replaces the heart-based health display with an XP-bar style progress bar when player health exceeds 30 hearts.

## Features
- Displays health as a progress bar (like the XP bar) when max health > 30 hearts
- Shows current/max health as text overlay (e.g., "200/300")
- Client-side only - no server-side requirements

## Building

1. Generate the Gradle wrapper:
```bash
mkdir -p gradle/wrapper
# Download gradle-wrapper.jar manually or use an existing gradle installation
gradle wrapper --gradle-version 8.9
```

2. Build the mod:
```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`.

## Usage
Install the mod in your Fabric mod loader's mods folder. The progress bar will automatically appear when you have more than 30 hearts (e.g., via Health Boost effect at high levels).
