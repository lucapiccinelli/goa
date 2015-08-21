package com.bnana.goa.cell;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class AttractorOffCell implements OffCell{

    public static final float DEFAULT_DENSITY = 1;

    private OnCell onCell;
    private Point2D.Float position;
    private float density;

    public AttractorOffCell(Point2D.Float position, float density) {
        this.position = position;
        this.density = density;
    }

    public AttractorOffCell(Point2D.Float position) {
        this(position, 1);
    }

    public AttractorOffCell() {
        this(new Point2D.Float(0, 0), 1);
    }

    @Override
    public OnCell turnOn() {
        onCell = makeCell();
        return onCell;
    }

    private OnCell makeCell() {
        if (onCell == null) onCell = new AttractorOnCell(this, position, density);
        return onCell;
    }
}
