package com.bnana.goa.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/26/2015.
 */
public class Box2dOrganismPhysics implements OrganismPhysics {
    private World world;
    private PhysicOrganism physicOrganism;

    public Box2dOrganismPhysics(World world, PhysicOrganism physicOrganism) {

        this.world = world;
        this.physicOrganism = physicOrganism;
    }

    @Override
    public void use(Point2D.Float position, float density) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x, position.y);

        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(density);

        body.createFixture(shape, density);

        shape.dispose();

        physicOrganism.add(body);
    }
}
