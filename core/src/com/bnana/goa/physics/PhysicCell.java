package com.bnana.goa.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.exceptions.InvalidIntegrateRequestException;
import com.bnana.goa.force.ForceField;

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
    private Vector2 newPosition;
    private PhysicElement parent;

    public PhysicCell() {
        this(null);
    }

    public PhysicCell(Body body) {
        this.body = body;
        this.positionUsedToNotify = new Vector2();
        positionChangedEvent = new PositionChangedEvent(this, positionUsedToNotify);

        positionListeners = new ArrayList<PositionListener>();
        this.newPosition = null;
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
    public void adjustPosition(){
        if(body != null){
            if(newPosition != null){
                body.setTransform(newPosition, body.getAngle());
                newPosition = null;
                notifyPositionChanged();
            }
        }else {
            element.adjustPosition();
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
    public void integrate(PhysicElement physicElement) {
        if(parent == null) throw new InvalidIntegrateRequestException(String.format("No parent on this %s", this));
        physicElement.setParent(parent);
        parent.add(physicElement);
    }

    @Override
    public void setParent(PhysicElement parent) {
        this.parent = parent;
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
    public void use(Vector2 position, float radius) {
        if(body != null){
            Vector2 bodyPosition = body.getPosition();
            Vector2 direction = new Vector2(position.x - bodyPosition.x, position.y - bodyPosition.y);
            direction.nor();
            schedulePositionChange(new Vector2(position.x + direction.x, position.y + direction.y));
        }else {
            element.use(position, radius);
        }
    }

    private void schedulePositionChange(Vector2 newPosition) {
        this.newPosition = newPosition;
    }
}
