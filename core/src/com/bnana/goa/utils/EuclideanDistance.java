package com.bnana.goa.utils;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.Cell;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class EuclideanDistance implements CellDistance {
    private Vector2 cachedVector;
    private Vector2 source;
    private float distance;

    public EuclideanDistance(Vector2 source) {
        this.source = source;
        cachedVector = new Vector2();

        distance = 0;
    }

    @Override
    public void use(Vector2 position, float radius) {
        distance = cachedVector.set(source.x - position.x, source.y - position.y).len();
    }

    public float getDistance() {
        return distance;
    }

    @Override
    public float getDistance(Cell c) {
        c.usePosition(this);
        return distance;
    }
}
