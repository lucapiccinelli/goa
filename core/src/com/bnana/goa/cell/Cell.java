package com.bnana.goa.cell;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public interface Cell {
    void usePosition(PositionConsumer positionConsumer);
    float distance(Cell cell);
    Cell prototype(Point2D.Float position, float density);
    Cell opposite(Point2D.Float position, float density);

    OffCell getAnOffCell();
}
