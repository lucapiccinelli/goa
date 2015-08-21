package com.bnana.goa.utils;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.PositionConsumer;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class EuclideanDistance implements PositionConsumer {
    private Vector2 cashedVector;
    private Point2D.Float source;
    private float distance;

    public EuclideanDistance(Point2D.Float source) {
        this.source = source;
        cashedVector = new Vector2();

        distance = 0;
    }

    @Override
    public void use(Point2D.Float position) {
        distance = cashedVector.set(source.x - position.x, source.y - position.y).len();
    }

    public float getDistance() {
        return distance;
    }
}
