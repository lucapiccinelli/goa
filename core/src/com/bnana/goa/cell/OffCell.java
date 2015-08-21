package com.bnana.goa.cell;

import com.bnana.goa.cell.generator.Cell;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public interface OffCell extends Cell{
    OnCell turnOn();
    OffCell prototype(Point2D.Float position, float density);
}
