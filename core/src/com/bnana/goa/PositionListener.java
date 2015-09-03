package com.bnana.goa;

import com.bnana.goa.events.PositionChangedEvent;

import java.awt.geom.Point2D;

/**
 * Created by luca.piccinelli on 03/09/2015.
 */
public interface PositionListener {
    void updatePosition(PositionChangedEvent position);
}
