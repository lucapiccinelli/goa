package com.bnana.goa.events;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.physics.PhysicCell;

import java.awt.geom.Point2D;
import java.util.EventObject;

/**
 * Created by luca.piccinelli on 03/09/2015.
 */
public class PositionChangedEvent extends EventObject {
    private Vector2 position;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public PositionChangedEvent(Object source, Vector2 position) {
        super(source);
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(Object source, Vector2 position) {
        this.source = source;
        this.position = position;
    }
}
