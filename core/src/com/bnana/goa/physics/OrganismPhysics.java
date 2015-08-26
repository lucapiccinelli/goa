package com.bnana.goa.physics;

import com.bnana.goa.cell.CellConsumer;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/26/2015.
 */
public interface OrganismPhysics extends CellConsumer {
    @Override
    void use(Point2D.Float position, float density);
}
