package com.bnana.goa.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.physics.factories.CircleBodyFactory;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/26/2015.
 */
public class Box2dOrganismPhysics implements OrganismPhysics {
    private final CircleBodyFactory bodyFactory;
    private World world;
    private PhysicElement physicElement;
    private float damping;

    public Box2dOrganismPhysics(World world, PhysicElement physicElement) {
        this(world, physicElement, 0);
    }

    public Box2dOrganismPhysics(World world, PhysicElement physicElement, float damping){
        this.world = world;
        this.physicElement = physicElement;
        this.damping = damping;

        bodyFactory = new CircleBodyFactory(world);
    }

    @Override
    public void use(Cell cell, Vector2 position, float density) {
        float absDensity = Math.abs(density);
        Body body = bodyFactory.create(position, absDensity);
        body.setLinearDamping(damping);

        PhysicCell cellBody = new PhysicCell(body);
        cellBody.setParent(physicElement);
        body.setUserData(cell.createOnTouchAction(cellBody));
        cellBody.addPositionListener(cell);

        physicElement.add(cellBody);
        physicElement.addPositionListener(cell);
    }

    @Override
    public void useItOff(OffCell cell, Vector2 position, float density) {
        use(cell, position, density);
    }
}
