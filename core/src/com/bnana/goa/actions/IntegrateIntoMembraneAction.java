package com.bnana.goa.actions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.creationDestruction.CreationDestructionHandler;
import com.bnana.goa.creationDestruction.CreationDestructionSubscriber;
import com.bnana.goa.physics.Box2dMembrane;
import com.bnana.goa.physics.Box2dMembraneSensor;
import com.bnana.goa.physics.factories.CircleBodyFactory;

import java.io.Console;

/**
 * Created by Luca on 11/22/2015.
 */
public class IntegrateIntoMembraneAction implements CreationDestructionSubscriber {
    private final Vector2 avgPosition;
    private CircleBodyFactory circleBodyFactory;
    private Box2dMembrane membrane;
    private CreationDestructionHandler creationDestructionHandler;
    private Vector2 tmpPosition;
    private Array<Body> sensorBodies;
    private Array<Vector2> positions;
    private Array<Float> radii;


    public IntegrateIntoMembraneAction(Box2dMembrane membrane, CircleBodyFactory circleBodyFactory, CreationDestructionHandler creationDestructionHandler) {
        this.circleBodyFactory = circleBodyFactory;
        this.membrane = membrane;
        this.creationDestructionHandler = creationDestructionHandler;
        tmpPosition = new Vector2();
        avgPosition = new Vector2();

        sensorBodies = new Array<Body>();
        positions = new Array<Vector2>();
        radii = new Array<Float>();
    }

    @Override
    public void doActions() {
        if(positions.size > 0 && radii.size > 0){
            if(sensorBodies.size == 0)surroundPosition(0, 360);
            else {
                Body body = sensorBodies.first();
                World world = body.getWorld();

                int i = 0;
                Body[] otherBodies = new Body[2];
                for (JointEdge e : body.getJointList()){
                    if(i < 2)otherBodies[i++] = e.other;
                    world.destroyJoint(e.joint);
                }
                world.destroyBody(body);

                float startAlpha = ((positions.first().cpy().sub(otherBodies[0].getWorldCenter()).angle() + 30));
                float endAlpha = ((positions.first().cpy().sub(otherBodies[1].getWorldCenter()).angle() - 30));
                surroundPosition(startAlpha, endAlpha);

                /*Array<Body> bodies = new Array();

                bodies.add(otherBodies[0]);
                float sensorRadius = 0.1f * radii.first();
                float x = bodies.first().getPosition().x;
                float y = bodies.first().getPosition().y;

                tmpPosition.set(x, y);
                for (i = 0; i < 8; i++){
                    bodies.add(circleBodyFactory.create(tmpPosition, sensorRadius));
                }*/
                /*bodies.add(otherBodies[1]);
                Vector2 direction = positions.first().cpy().sub(body.getWorldCenter()).nor().scl(0.01f);
                for (i = 0; i < bodies.size; i++){
                    Body b = bodies.get(i);
                    Vector2 bodyPosition = b.getWorldCenter();
                    direction.rotate((i - bodies.size * 0.5f) * 10);

                    b.applyLinearImpulse(direction.x, direction.y, bodyPosition.x, bodyPosition.y, true);

                    new Box2dMembraneSensor(
                            membrane,
                            bodies.get(i),
                            bodies.get((i + 1) % bodies.size),
                            bodies.get((i + 2) % bodies.size),
                            creationDestructionHandler
                    );
                }*/
            }
        }

        sensorBodies.clear();
        positions.clear();
        radii.clear();
    }

    private void surroundPosition(float startAlpha, float endAlpha) {
        Array<Body> bodies = new Array();

        Float radius = radii.first() * radii.size * 0.6f;

        float x = 0;
        float y = 0;
        for (Vector2 position : positions){
            x += position.x;
            y += position.y;
        }
        avgPosition.set(x / positions.size, y / positions.size);

        float sensorRadius = 0.1f * radii.first();
        
        float membraneSensorsDensity = 30f / positions.size;
        if(startAlpha > endAlpha){
            for(float alpha = startAlpha; alpha > endAlpha; alpha -= membraneSensorsDensity){
                tmpPosition.set(
                        avgPosition.x + MathUtils.cosDeg(alpha) * radius,
                        avgPosition.y + MathUtils.sinDeg(alpha) * radius
                );

                bodies.add(circleBodyFactory.create(tmpPosition, sensorRadius));
            }
        }else{
            for(float alpha = startAlpha; alpha < endAlpha; alpha += membraneSensorsDensity){
                tmpPosition.set(
                        avgPosition.x + MathUtils.cosDeg(alpha) * radius,
                        avgPosition.y + MathUtils.sinDeg(alpha) * radius
                );

                bodies.add(circleBodyFactory.create(tmpPosition, sensorRadius));
            }
        }

        for (int i = 0; i < bodies.size; i++){
            new Box2dMembraneSensor(
                    membrane,
                    bodies.get(i),
                    bodies.get((i + 1) % bodies.size),
                    bodies.get((i + 2) % bodies.size),
                    creationDestructionHandler
            );
        }
    }

    public void add(Body sensorBody) {
        sensorBodies.add(sensorBody);
    }

    public void add(Vector2 position, float radius) {
        positions.add(position);
        radii.add(radius);
    }
}
