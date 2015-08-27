package com.bnana.goa.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.force.ForceField;

import java.util.List;

/**
 * Created by Luca on 8/27/2015.
 */
public class PhysicOrganismImpl implements PhysicOrganism {

    private Array<Body> bodies;

    public PhysicOrganismImpl() {
        this.bodies = new Array<Body>();
    }

    @Override
    public void add(Body body) {
        bodies.add(body);
    }

    @Override
    public void apply(ForceField forceField) {
        for (Body body : bodies){
            forceField.apply(body);
        }
    }
}
