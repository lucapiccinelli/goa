package com.bnana.goa.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.factories.CircleBodyFactory;
import com.bnana.goa.physics.factories.ElasticJointFactory;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

/**
 * Created by Luca on 11/19/2015.
 */
public class Box2dMembraneSensor implements PhysicElement {
    private final Box2dMembrane membrane;
    private final Body start;
    private final Body center;
    private final Body end;
    private final ElasticJointFactory jointFactory;


    public Box2dMembraneSensor(Box2dMembrane membrane, Body start, Body center, Body end){
        this.membrane = membrane;
        this.start = start;
        this.center = center;
        this.end = end;

        jointFactory = new ElasticJointFactory();
        jointFactory.make(start, center);
        jointFactory.make(center, end);
    }

    @Override
    public void add(PhysicElement element) {
    }

    @Override
    public void apply(ForceField forceField) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void setAction(OnTouchAction onTouchAction) {

    }

    @Override
    public void integrate(PhysicElement physicElement) {
        World world = center.getWorld();
        for(JointEdge je : center.getJointList()){
            world.destroyJoint(je.joint);
        }

        world.destroyBody(center);
        membrane.integrate(start, end, physicElement);
    }

    @Override
    public void setParent(PhysicElement parent) {

    }

    @Override
    public void integrateIntoMebrane(Box2dMembrane membrane) {
    }

    @Override
    public void addPositionListener(PositionListener positionListener) {

    }

    @Override
    public void removePositionListener(PositionListener positionListener) {

    }

    @Override
    public void notifyPositionChanged() {

    }

    @Override
    public void use(Vector2 position, float radius) {

    }
}
