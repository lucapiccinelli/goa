package com.bnana.goa.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bnana.goa.cell.Cell;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/26/2015.
 */
public class Box2dOrganismPhysics implements OrganismPhysics {
    private World world;
    private PhysicElement physicElement;

    public Box2dOrganismPhysics(World world, PhysicElement physicElement) {

        this.world = world;
        this.physicElement = physicElement;
    }

    @Override
    public void use(Cell cell, Point2D.Float position, float density) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x, position.y);

        Body body = world.createBody(bodyDef);

        float absDensity = Math.abs(density);
        CircleShape shape = new CircleShape();

        shape.setRadius(absDensity);
        body.createFixture(shape, absDensity);

        shape.dispose();

        body.setUserData(cell);
        PhysicCell cellBody = new PhysicCell(body);
        cellBody.addPositionListener(cell);
        physicElement.add(cellBody);
        physicElement.addPositionListener(cell);
    }
}
