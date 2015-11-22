package com.bnana.goa.actions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.creationDestruction.CreationDestructionHandler;
import com.bnana.goa.creationDestruction.CreationDestructionSubscriber;
import com.bnana.goa.physics.Box2dMembrane;
import com.bnana.goa.physics.Box2dMembraneSensor;
import com.bnana.goa.physics.factories.CircleBodyFactory;

/**
 * Created by Luca on 11/22/2015.
 */
public class IntegrateIntoMembraneAction implements CreationDestructionSubscriber {
    private CircleBodyFactory circleBodyFactory;
    private Box2dMembrane membrane;
    private final Array<Body> startList;
    private final Array<Body> endList;
    private final float radius;
    private final Vector2 position;
    private CreationDestructionHandler creationDestructionHandler;
    private Vector2 tmpPosition;


    public IntegrateIntoMembraneAction(CircleBodyFactory circleBodyFactory, Box2dMembrane membrane, Array<Body> startList, Array<Body> endList, float radius, Vector2 position, CreationDestructionHandler creationDestructionHandler) {
        this.circleBodyFactory = circleBodyFactory;
        this.membrane = membrane;
        this.startList = startList;
        this.endList = endList;
        this.radius = radius;
        this.position = position;
        this.creationDestructionHandler = creationDestructionHandler;
        tmpPosition = new Vector2();
    }

    @Override
    public void doActions() {

        float sensorRadius = 0.1f * radius;

        float endAlpha = endList.size > 0 ? end.getPosition().angle(position) + 180 : 360;
        float startAlpha = startList.size > 0 ? start.getPosition().angle(position) + 180 : 0;

        Array<Body> sensorBodies = new Array<Body>();
        float membraneSensorsDensity = 35f;
        for(float alpha = startAlpha; alpha < endAlpha; alpha += membraneSensorsDensity){
            tmpPosition.set(
                    position.x + MathUtils.cosDeg(alpha) * radius,
                    position.y + MathUtils.sinDeg(alpha) * radius
            );

            sensorBodies.add(circleBodyFactory.create(tmpPosition, sensorRadius));
        }

        for (int i = 0; i < sensorBodies.size; i++){
            new Box2dMembraneSensor(
                    membrane,
                    sensorBodies.get(i),
                    sensorBodies.get((i + 1) % sensorBodies.size),
                    sensorBodies.get((i + 2) % sensorBodies.size),
                    creationDestructionHandler
            );
        }

    }
}
