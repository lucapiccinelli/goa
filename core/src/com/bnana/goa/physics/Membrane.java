package com.bnana.goa.physics;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Luca on 11/19/2015.
 */
public interface Membrane extends PhysicElement{
    void integrate(Body start, Body end, PhysicElement physicElement);
}
