package com.bnana.goa.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.factories.BodyFactory;
import com.bnana.goa.physics.factories.CircleBodyFactory;

/**
 * Created by luca.piccinelli on 16/11/2015.
 */
public class Box2dMembrane implements Membrane {
    private float radius;
    private Body start;
    private Body end;
    private CircleBodyFactory circleBodyFactory;

    public Box2dMembrane(CircleBodyFactory circleBodyFactory) {
        this.circleBodyFactory = circleBodyFactory;
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
        physicElement.integrateIntoMebrane(this);
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
        if(start == null && end == null){
            start = circleBodyFactory.create(position, radius);
            end = start;
        }
    }

    @Override
    public void integrate(Body start, Body end, PhysicElement physicElement) {
        this.start = start;
        this.end = end;
        integrate(physicElement);
    }
}
