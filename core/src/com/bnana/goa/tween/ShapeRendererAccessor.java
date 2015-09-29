package com.bnana.goa.tween;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Luca on 9/22/2015.
 */
public class ShapeRendererAccessor implements TweenAccessor<ShapeRenderer> {
    public static final int COLOR_ALPHA = 0;

    @Override
    public int getValues(ShapeRenderer target, int tweenType, float[] floats) {
        switch (tweenType){
            case COLOR_ALPHA:
                floats[0] = target.getColor().a;
                return 1;
        }

        return 0;
    }

    @Override
    public void setValues(ShapeRenderer target, int tweenType, float[] floats) {
        switch (tweenType){
            case COLOR_ALPHA:

                target.getColor().a = floats[0];
                break;
        }
    }
}
