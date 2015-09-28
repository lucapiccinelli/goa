package com.bnana.goa.tween;

/**
 * Created by Luca on 9/28/2015.
 */
public class PercentageManager {
    private float percentage;

    public PercentageManager(float percentage) {
        this.percentage = percentage;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public float scale(float n){
        return n * percentage;
    }
}
