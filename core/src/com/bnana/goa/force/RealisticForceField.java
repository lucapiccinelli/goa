package com.bnana.goa.force;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.rendering.ForceRenderer;

/**
 * Created by Luca on 9/29/2015.
 */
public class RealisticForceField implements ForceField {
    private final Vector2 resultingForce;
    private Array<ForceField> radialForces;

    public RealisticForceField() {
        this.radialForces = new Array<ForceField>();
        resultingForce = new Vector2();
    }

    @Override
    public void update(Array<Vector2> positions, Array<Float> magnitudes) {
        radialForces.clear();
        int i = 0;
        for (Vector2 position : positions){
            radialForces.add(new RadialForceField(position, magnitudes.get(i++)));
        }
    }

    @Override
    public void apply(Body body) {
        Vector2 bodyPosition = body.getWorldCenter();
        forceAtPoint(bodyPosition);

        if (resultingForce.x != 0 || resultingForce.y != 0){
            body.applyForceToCenter(resultingForce, true);
        }
    }

    @Override
    public void render(ForceRenderer forceRenderer) {
        for (ForceField f : radialForces){
            f.render(forceRenderer);
        }
    }

    @Override
    public float valueAtDistance(float distance) {
        float value = 0;
        for (ForceField f : radialForces){
            value += f.valueAtDistance(distance);
        }
        return value;
    }

    @Override
    public float valueAtPoint(Vector2 position) {
        return forceAtPoint(position).len();
    }

    @Override
    public Vector2 direction(Vector2 position) {
        return forceAtPoint(position).nor();
    }

    @Override
    public Vector2 forceAtPoint(Vector2 bodyPosition) {
        resultingForce.set(0, 0);
        for (ForceField f : radialForces){
            Vector2 force = f.forceAtPoint(bodyPosition.cpy());
            resultingForce.add(force);
        }

        return resultingForce;
    }
}
