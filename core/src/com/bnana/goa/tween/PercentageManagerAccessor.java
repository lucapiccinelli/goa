package com.bnana.goa.tween;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Luca on 9/28/2015.
 */
public class PercentageManagerAccessor implements TweenAccessor<PercentageManager> {
    @Override
    public int getValues(PercentageManager percentageManager, int tweenType, float[] floats) {
        floats[0] = percentageManager.getPercentage();
        return 1;
    }

    @Override
    public void setValues(PercentageManager percentageManager, int tweenType, float[] floats) {
        percentageManager.setPercentage(floats[0]);
    }
}
