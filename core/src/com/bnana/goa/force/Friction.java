package com.bnana.goa.force;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.rendering.ForceRenderer;

/**
 * Created by luca.piccinelli on 27/10/2015.
 */
public class Friction implements ForceField {
    private float scale;

    public Friction(float scale) {
        this.scale = scale;
    }

    @Override
    public void update(Array<Vector2> positions, Array<Float> magnitudes) {

    }

    @Override
    public void apply(Body body) {
        Vector2 direction = body.getLinearVelocity();
        Vector2 normal = direction.nor();
        Vector2 friction = normal.scl(-scale);

        body.applyForceToCenter(friction, true);
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
        return Vector2.Zero;
    }

    @Override
    public Vector2 forceAtPoint(Vector2 bodyPosition) {
        return Vector2.Zero;
    }

    @Override
    public void reset() {

    }
}
