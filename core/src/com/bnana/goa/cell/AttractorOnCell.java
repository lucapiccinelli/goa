package com.bnana.goa.cell;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class AttractorOnCell implements OnCell {
    private OffCell offCell;
    private Point2D.Float position;
    private float density;

    public AttractorOnCell(OffCell offCell, Point2D.Float position, float density) {
        this.offCell = offCell;
        this.position = position;
        this.density = density;
    }

    @Override
    public OffCell turnOff() {
        return offCell;
    }

    @Override
    public void use(CellConsumer consumer) {
        consumer.use(position, density);
    }
}
