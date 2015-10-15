package com.bnana.goa.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.force.ForceField;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 9/2/2015.
 */
public class PhysicCell implements PhysicElement {
    private final Vector2 positionUsedToNotify;
    private Body body;
    private List<PositionListener> positionListeners;
    private PositionChangedEvent positionChangedEvent;
    private PhysicElement element;

    public PhysicCell() {
        this(null);
    }

    public PhysicCell(Body body) {
        this.body = body;
        this.positionUsedToNotify = new Vector2();
        positionChangedEvent = new PositionChangedEvent(this, positionUsedToNotify);

        positionListeners = new ArrayList<PositionListener>();
    }

    @Override
    public void add(PhysicElement element) {
        this.element = element;
    }

    @Override
    public void apply(ForceField forceField) {
        if(element != null){
            element.apply(forceField);
        }else if(body != null){
            forceField.apply(body);
        }
    }

    @Override
    public void stop() {
        if(body != null){
            body.setAngularVelocity(0);
            body.setLinearVelocity(0, 0);

            body.getWorld().clearForces();
        }else {
            element.stop();
        }
    }

    @Override
    public void setAction(OnTouchAction onTouchAction) {
        if(body != null){
            body.setUserData(onTouchAction);
        }else {
            element.setAction(onTouchAction);
        }
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
        if(body != null)
        {
            Vector2 vectorPosition = body.getWorldCenter();
            positionUsedToNotify.set(vectorPosition.x, vectorPosition.y);
            for (PositionListener positionListener : positionListeners){
                positionListener.updatePosition(positionChangedEvent);
            }
        }

        if(element != null) element.notifyPositionChanged();
    }

    @Override
    public void use(Vector2 position) {
        if(body != null){
            Vector2 direction = position.sub(body.getPosition()).nor();
            body.setTransform(position.sub(direction), body.getAngle());
            notifyPositionChanged();
        }else {
            element.use(position);
        }
    }
}
