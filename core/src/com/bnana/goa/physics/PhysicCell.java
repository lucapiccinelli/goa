package com.bnana.goa.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.force.ForceField;

/**
 * Created by Luca on 9/2/2015.
 */
public class PhysicCell implements PhysicElement {
    private Body body;

    public PhysicCell(Body body) {
        this.body = body;
    }

    @Override
    public void add(Body body) {
        this.body = body;
    }

    @Override
    public void apply(ForceField forceField) {
        forceField.apply(body);
    }
}
