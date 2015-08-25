package com.bnana.goa.cell;

import com.bnana.goa.cell.generator.Cell;
import com.bnana.goa.utils.EuclideanDistance;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class RepulsorOffCell implements OffCell {
    public static final float DEFAULT_DENSITY = 1;
    private final EuclideanDistance distanceCalculator;

    private OnCell onCell;
    private Point2D.Float position;
    private float density;

    public RepulsorOffCell (Point2D.Float position, float density) {
        this.position = position;
        this.density = Math.abs(density);

        distanceCalculator = new EuclideanDistance(position);
    }

    public RepulsorOffCell (Point2D.Float position) {
        this(position, 1);
    }

    public RepulsorOffCell () {
        this(new Point2D.Float(0, 0), 1);
    }

    @Override
    public OnCell turnOn() {
        return makeCell();
    }

    @Override
    public OffCell prototype(Point2D.Float position, float density) {
        return new RepulsorOffCell(position, density);
    }

    @Override
    public OffCell opposite(Point2D.Float position, float density) {
        return new AttractorOffCell(position, density);
    }

    private OnCell makeCell() {
        if (onCell == null) onCell = new RepulsorOnCell(this, position, density);
        return onCell;
    }

    public static OffCell MakeProtype(){
        return new RepulsorOffCell();
    }

    @Override
    public void usePosition(PositionConsumer positionConsumer) {
        positionConsumer.use(position);
    }

    @Override
    public float distance(Cell cell) {
        cell.usePosition(distanceCalculator);
        return distanceCalculator.getDistance();
    }
}
