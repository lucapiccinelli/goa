package com.bnana.goa.cell;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public interface PositionConsumer {
    void use(Vector2 position, float radius);
}
