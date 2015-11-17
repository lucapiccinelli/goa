package com.bnana.goa.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.force.ForceField;

/**
 * Created by luca.piccinelli on 16/11/2015.
 */
public class Box2dMembrane implements PhysicElement {
    private final Array<Body> membraneBodies;

    public Box2dMembrane() {
        membraneBodies = new Array<Body>();
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

    }

    @Override
    public void setParent(PhysicElement parent) {

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
