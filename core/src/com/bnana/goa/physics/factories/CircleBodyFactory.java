package com.bnana.goa.physics.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bnana.goa.physics.factories.BodyFactory;

/**
 * Created by Luca on 11/19/2015.
 */
public class CircleBodyFactory implements BodyFactory {

    private World world;

    public CircleBodyFactory(World world) {
        this.world = world;
    }

    @Override
    public Body create(Vector2 position, float density) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x, position.y);

        Body body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();

        shape.setRadius(density);
        body.createFixture(shape, density);
        body.resetMassData();

        shape.dispose();

        return body;
    }
}
