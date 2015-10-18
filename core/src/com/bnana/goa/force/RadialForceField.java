package com.bnana.goa.force;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.exceptions.GoaArgumentException;
import com.bnana.goa.force.functions.ExponentialValueAtDistanceFunction;
import com.bnana.goa.force.functions.ValueAtDistanceFunction;
import com.bnana.goa.rendering.ForceRenderer;

/**
 * Created by Luca on 8/27/2015.
 */
public class RadialForceField implements ForceField {
    private final float MAGNITUDE_SCALE = 100f;


    private final Vector2 centerOfMass;
    private final ValueAtDistanceFunction valueAtDistanceFunction;
    private float magnitude;

    public RadialForceField(Vector2 centerOfMass, float magnitude, ValueAtDistanceFunction valueAtDistanceFunction) {
        this.valueAtDistanceFunction = valueAtDistanceFunction;
        this.centerOfMass = new Vector2(centerOfMass.x, centerOfMass.y);
        this.magnitude = magnitude * MAGNITUDE_SCALE;
    }

    public RadialForceField() {
        this(new Vector2(0, 0), 0, new ExponentialValueAtDistanceFunction());
    }

    @Override
    public void update(Array<Vector2> positions, Array<Float> magnitudes) {
        if (positions.size < 1)
            throw new GoaArgumentException("You must provide a position as the center of the force in order to update");

        if (magnitudes.size < 1)
            throw new GoaArgumentException("You must provide a magnitude in order to update the force");

        Vector2 position = positions.get(0);
        magnitude = magnitudes.get(0) * MAGNITUDE_SCALE;
        centerOfMass.set(position.x, position.y);
    }

    @Override
    public void apply(Body body) {
        Vector2 force = forceAtPoint(body.getWorldCenter());
        body.applyForceToCenter(force, true);
    }

    @Override
    public void render(ForceRenderer forceRenderer) {
        forceRenderer.renderForce(this, centerOfMass, magnitude);
    }

    @Override
    public float valueAtDistance(float distance) {
        return valueAtDistanceFunction.calculate(distance, magnitude);
    }

    @Override
    public float valueAtPoint(Vector2 position) {
        float distance = centerOfMass.dst(position.x, position.y);
        return valueAtDistance(distance);
    }

    @Override
    public Vector2 direction(Vector2 position) {
        Vector2 force = new Vector2(position.x, position.y);
        force.sub(centerOfMass);
        Vector2 direction = forceAtPoint(position).nor();
        return direction;
    }

    @Override
    public Vector2 forceAtPoint(Vector2 position) {
        float distance = centerOfMass.dst(position);
        float finalMagnitude = valueAtDistance(distance);
        Vector2 force = position.sub(centerOfMass).nor().scl(finalMagnitude);
        return force;
    }
}
