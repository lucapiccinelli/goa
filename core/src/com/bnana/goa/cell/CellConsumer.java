package com.bnana.goa.cell;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public interface CellConsumer {
    void use(Cell cell, Point2D.Float position, float density);
    void useItOff(OffCell cell, Point2D.Float position, float density);
}
