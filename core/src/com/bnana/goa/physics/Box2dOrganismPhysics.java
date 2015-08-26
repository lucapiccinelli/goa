package com.bnana.goa.physics;

import com.badlogic.gdx.physics.box2d.World;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/26/2015.
 */
public class Box2dOrganismPhysics implements OrganismPhysics {
    private World world;

    public Box2dOrganismPhysics(World world) {

        this.world = world;
    }

    @Override
    public void use(Point2D.Float position, float density) {

    }
}
