package com.bnana.goa.force;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public interface ForceField {
    void update(Point2D.Float[] positions, float[] magnitudes);
}
