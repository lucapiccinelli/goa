package com.bnana.goa.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.PositionListener;
import com.bnana.goa.force.ForceField;

/**
 * Created by Luca on 8/27/2015.
 */
public class PhysicOrganism implements PhysicElement {

    private Array<Body> bodies;

    public PhysicOrganism() {
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

    @Override
    public void addPositionListener(PositionListener positionListener) {

    }

    @Override
    public void notifyPositionChanged() {

    }
}
