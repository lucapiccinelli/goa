package com.bnana.goa.tween;

import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Luca on 9/28/2015.
 */
public class Vector2Accessor implements TweenAccessor<Vector2>{
    @Override
    public int getValues(Vector2 vector2, int i, float[] floats) {
        floats[0] = vector2.x;
        floats[1] = vector2.y;
        return 2;
    }

    @Override
    public void setValues(Vector2 vector2, int i, float[] floats) {
        vector2.set(floats[0], floats[1]);
    }
}
