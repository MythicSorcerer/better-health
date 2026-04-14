package com.betterhealth.hud;

public class HistoricalMaxEntry {
    public final float maxHealth;
    public final long timestamp;

    public HistoricalMaxEntry(float maxHealth, long timestamp) {
        this.maxHealth = maxHealth;
        this.timestamp = timestamp;
    }
}
