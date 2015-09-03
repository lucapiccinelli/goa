package com.bnana.goa.events;

import java.awt.geom.Point2D;
import java.util.EventObject;

/**
 * Created by luca.piccinelli on 03/09/2015.
 */
public class PositionChangedEvent extends EventObject {
    private Point2D.Float position;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public PositionChangedEvent(Object source, Point2D.Float position) {
        super(source);
        this.position = position;
    }

    public Point2D.Float getPosition() {
        return position;
    }
}
