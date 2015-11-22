package com.bnana.goa.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.IntegrateIntoMembraneAction;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.creationDestruction.CreationDestructionHandler;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.factories.CircleBodyFactory;

/**
 * Created by luca.piccinelli on 16/11/2015.
 */
public class Box2dMembrane implements Membrane {
    private final Vector2 tmpPosition;
    private final float membraneSensorsDensity;
    private Array<Body> startList;
    private Array<Body> endList;
    private CircleBodyFactory circleBodyFactory;
    private CreationDestructionHandler creationDestructionHandler;
    private Array<Box2dMembraneSensor> membraneSensors;

    public Box2dMembrane(CircleBodyFactory circleBodyFactory, CreationDestructionHandler creationDestructionHandler) {
        this.circleBodyFactory = circleBodyFactory;
        this.creationDestructionHandler = creationDestructionHandler;
        this.tmpPosition = new Vector2();

        membraneSensorsDensity = 35f;
        membraneSensors = new Array<Box2dMembraneSensor>();

        startList = new Array<Body>();
        endList = new Array<Body>();
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
        integrate(null, null, physicElement);
    }

    @Override
    public void integrate(Body start, Body end, PhysicElement physicElement) {
        this.startList.add(start);
        this.endList.add(end);
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
        creationDestructionHandler.register(new IntegrateIntoMembraneAction(
                circleBodyFactory,
                this,
                startList,
                endList,
                radius,
                position,
                creationDestructionHandler));
    }
}
