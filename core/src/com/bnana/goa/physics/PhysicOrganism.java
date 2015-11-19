package com.bnana.goa.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.exceptions.InvalidIntegrateRequestException;
import com.bnana.goa.force.ForceField;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 8/27/2015.
 */
public class PhysicOrganism implements PhysicElement {

    private Array<PhysicElement> elements;
    private List<PositionListener> positionListeners;
    private PhysicElement parent;

    public PhysicOrganism() {
        this.elements = new Array<PhysicElement>();
        this.positionListeners = new ArrayList<PositionListener>();
    }

    @Override
    public void add(PhysicElement body) {
        elements.add(body);
    }

    @Override
    public void apply(ForceField forceField) {
        for (PhysicElement element : elements){
            element.apply(forceField);
        }
    }

    @Override
    public void stop() {
        for (PhysicElement element : elements){
            element.stop();
        }
    }

    @Override
    public void setAction(OnTouchAction onTouchAction) {

    }

    @Override
    public void integrate(PhysicElement physicElement) {
        if(parent == null) throw new InvalidIntegrateRequestException(String.format("No parent on this %s", this));
        parent.add(physicElement);
    }

    @Override
    public void setParent(PhysicElement parent) {

        this.parent = parent;
    }

    @Override
    public void integrateIntoMebrane(Box2dMembrane membrane) {

    }

    @Override
    public void addPositionListener(PositionListener positionListener) {
        positionListeners.add(positionListener);
    }

    @Override
    public void removePositionListener(PositionListener positionListener) {
        positionListeners.remove(positionListener);
    }

    @Override
    public void notifyPositionChanged() {
        for (PhysicElement element : elements) {
            element.notifyPositionChanged();
        }
    }

    @Override
    public void use(Vector2 position, float radius) {

    }
}
