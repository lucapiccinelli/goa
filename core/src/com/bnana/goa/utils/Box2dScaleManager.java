package com.bnana.goa.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by luca.piccinelli on 21/09/2015.
 */
public class Box2dScaleManager implements ScaleManager {
    public Box2dScaleManager(OrthographicCamera camera, int width, int height) {
    }

    @Override
    public float s(int n) {
        return 100;
    }
}
