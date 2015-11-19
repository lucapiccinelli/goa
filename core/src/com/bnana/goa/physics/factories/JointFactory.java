package com.bnana.goa.physics.factories;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;

/**
 * Created by Luca on 11/19/2015.
 */
public interface JointFactory {
    void make(Body a, Body b);
}
