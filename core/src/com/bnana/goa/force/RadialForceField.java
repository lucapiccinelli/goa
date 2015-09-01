package com.bnana.goa.force;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.exceptions.GoaArgumentException;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/27/2015.
 */
public class RadialForceField implements ForceField {
    private final float MAGNITUDE_SCALE = 1f;


    private final Vector2 centerOfMass;
    private float magnitude;

    public RadialForceField(Point2D.Float centerOfMass, float magnitude) {
        this.centerOfMass = new Vector2(centerOfMass.x, centerOfMass.y);
        this.magnitude = magnitude * MAGNITUDE_SCALE;
    }

    public RadialForceField() {
        this(new Point2D.Float(0, 0), 0);
    }

    @Override
    public void update(Point2D.Float[] positions, float[] magnitudes) {
        if (positions.length < 1)
            throw new GoaArgumentException("You must provide a position as the center of the force in order to update");

        if (magnitudes.length < 1)
            throw new GoaArgumentException("You must provide a magnitude in order to update the force");

        Point2D.Float position = positions[0];
        magnitude = magnitudes[0];
        centerOfMass.set(position.x, position.y);
    }

    @Override
    public void apply(Body body) {
        Vector2 bodyCenter = body.getWorldCenter();
        float distance = centerOfMass.dst(bodyCenter);

        float finalMagnitude = magnitude / distance;

        Vector2 force = bodyCenter.sub(centerOfMass).nor().scl(finalMagnitude);
        body.applyForceToCenter(force, true);
    }
}