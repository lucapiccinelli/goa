package com.bnana.goa.utils.wrappers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by luca.piccinelli on 21/09/2015.
 */
public class CameraWrapper {

    public static Camera getCamera(int viewportWidth, int viewportHeight) {
        return new OrthographicCamera(viewportWidth, viewportHeight);
    }
}
