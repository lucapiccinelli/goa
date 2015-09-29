package com.bnana.goa.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;

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
    public void use(Cell cell, Vector2 position, float density) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x, position.y);

        Body body = world.createBody(bodyDef);

        float absDensity = Math.abs(density);
        CircleShape shape = new CircleShape();

        shape.setRadius(absDensity);
        body.createFixture(shape, absDensity);
        body.resetMassData();

        shape.dispose();

        body.setUserData(cell.createOnTouchAction(this.physicElement));
        PhysicCell cellBody = new PhysicCell(body);
        cellBody.addPositionListener(cell);
        physicElement.add(cellBody);
        physicElement.addPositionListener(cell);
    }

    @Override
    public void useItOff(OffCell cell, Vector2 position, float density) {
        use(cell, position, density);
    }
}
