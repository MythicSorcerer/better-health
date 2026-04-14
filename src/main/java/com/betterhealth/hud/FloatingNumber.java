package com.betterhealth.hud;

public class FloatingNumber {
    public final String text;
    public final int baseX;
    public int life;
    public final int maxLife;
    public final boolean positive;

    public FloatingNumber(String text, int baseX, boolean positive, int maxLife) {
        this.text = text;
        this.baseX = baseX;
        this.positive = positive;
        this.maxLife = maxLife;
        this.life = maxLife;
    }
}
