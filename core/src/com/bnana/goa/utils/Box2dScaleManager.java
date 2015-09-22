package com.bnana.goa.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by luca.piccinelli on 21/09/2015.
 */
public class Box2dScaleManager implements ScaleManager {
    private final Camera camera;
    private final int width;
    private final int height;
    private final float scale;

    public Box2dScaleManager(Camera camera, int width, int height) {
        this.camera = camera;
        this.width = width;
        this.height = height;

        this.scale = width / camera.viewportWidth;
    }

    @Override
    public float s(float n) {
        return n;
    }
}
