package com.bnana.goa.force;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/27/2015.
 */
public class RadialForceField implements ForceField {
    private final float MAGNITUDE_SCALE = 1f;


    private final Vector2 centerOfMass;
    private final float magnitude;

    public RadialForceField(Point2D.Float centerOfMass, float magnitude) {
        this.centerOfMass = new Vector2(centerOfMass.x, centerOfMass.y);
        this.magnitude = magnitude * MAGNITUDE_SCALE;
    }

    @Override
    public void update(Point2D.Float[] positions, float[] magnitudes) {
    }

    @Override
    public void apply(Body body) {
        Vector2 bodyCenter = body.getWorldCenter();
        float distance = centerOfMass.dst(bodyCenter);

        float finalMagnitude = magnitude / distance;

        Vector2 force = new Vector2(bodyCenter.x - centerOfMass.x, bodyCenter.y - centerOfMass.y);
        force.scl(finalMagnitude);
        Vector2 worldForce = body.getWorldVector(force);

        body.applyForceToCenter(worldForce, true);
    }
}
