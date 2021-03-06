package com.bnana.goa.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bnana.goa.utils.wrappers.WorldWrapper;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/27/2015.
 */
public class BodyWrapper {
    public static Body getNewBody() {
        return WorldWrapper.GetNewWorldZeroGravity().createBody(new BodyDef());
    }

    public static Body getNewCircleBody(Vector2 position, int mass) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x, position.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        Body body = WorldWrapper.GetNewWorldZeroGravity().createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(mass);

        body.createFixture(circleShape, mass);

        circleShape.dispose();

        return body;
    }

    public static Vector2 getBodyPosition(Body body) {
        Vector2 position = body.getPosition();
        return new Vector2(position.x, position.y);
    }

    public static Body getNewBodyWithLinearVelocityNotZero() {
        Body body = getNewCircleBody(new Vector2(), 1);
        body.setLinearVelocity(10, 10);

        return body;
    }
}
