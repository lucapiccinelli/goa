package com.bnana.goa.utils;

import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.PositionConsumer;

import java.awt.geom.Point2D;

/**
 * Created by luca.piccinelli on 28/08/2015.
 */
public interface CellDistance extends PositionConsumer{
    float getDistance(Cell x);
}
