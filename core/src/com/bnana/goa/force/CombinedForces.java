package com.bnana.goa.force;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.rendering.ForceRenderer;

/**
 * Created by luca.piccinelli on 27/10/2015.
 */
public class CombinedForces implements ForceField{
    private ForceField[] forces;

    public CombinedForces(ForceField[] forces) {
        this.forces = forces;
    }

    @Override
    public void update(Array<Vector2> positions, Array<Float> magnitudes) {
        for (ForceField f : forces){
            f.update(positions, magnitudes);
        }
    }

    @Override
    public void apply(Body body) {
        for (ForceField f : forces){
            f.apply(body);
        }
    }

    @Override
    public void render(ForceRenderer forceRenderer) {
        for (ForceField f : forces){
            f.render(forceRenderer);
        }
    }

    @Override
    public float valueAtDistance(float distance) {
        float value = 0;
        for (ForceField f : forces){
            value += f.valueAtDistance(distance);
        }
        return value;
    }

    @Override
    public float valueAtPoint(Vector2 position) {
        float value = 0;
        for (ForceField f : forces){
            value += f.valueAtPoint(position);
        }
        return value;
    }

    @Override
    public Vector2 direction(Vector2 position) {
        Vector2 direction = new Vector2(0, 0);
        for (ForceField f : forces){
            direction.add(f.direction(position));
        }
        return direction;
    }

    @Override
    public Vector2 forceAtPoint(Vector2 bodyPosition) {
        Vector2 force = new Vector2(0, 0);
        for (ForceField f : forces){
            force.add(f.forceAtPoint(bodyPosition));
        }
        return force;
    }
}
