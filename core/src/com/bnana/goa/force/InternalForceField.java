package com.bnana.goa.force;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.rendering.ForceRenderer;

/**
 * Created by Luca on 10/18/2015.
 */
public class InternalForceField implements ForceField {
    @Override
    public void update(Array<Vector2> positions, Array<Float> magnitudes) {

    }

    @Override
    public void apply(Body body) {

    }

    @Override
    public void render(ForceRenderer forceRenderer) {

    }

    @Override
    public float valueAtDistance(float distance) {
        return 0;
    }

    @Override
    public float valueAtPoint(Vector2 position) {
        return 0;
    }

    @Override
    public Vector2 direction(Vector2 position) {
        return null;
    }

    @Override
    public Vector2 forceAtPoint(Vector2 bodyPosition) {
        return null;
    }
}
