package com.bnana.goa.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.force.ForceField;

/**
 * Created by Luca on 8/27/2015.
 */
public interface PhysicElement {
    void add(Body body);

    void apply(ForceField forceField);
}
