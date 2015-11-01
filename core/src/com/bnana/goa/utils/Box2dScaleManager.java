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
    private float scale;
    private float unscale;

    public Box2dScaleManager(Camera camera, int width, int height) {
        this.camera = camera;
        this.width = width;
        this.height = height;

        this.scale = width / camera.viewportWidth;
        this.unscale = 1.0f / scale;
    }

    @Override
    public float s(float n) {
        return scale * n;
    }
    public float us(float n) {
        return unscale * n;
    }

    @Override
    public void update() {
        this.scale = width / camera.viewportWidth;
        this.unscale = 1.0f / scale;
    }
}
