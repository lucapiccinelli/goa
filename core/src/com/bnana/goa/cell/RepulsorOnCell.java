package com.bnana.goa.cell;

import com.bnana.goa.cell.generator.Cell;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class RepulsorOnCell implements OnCell {
    private final OffCell offCell;
    private final Point2D.Float position;
    private final float density;

    public RepulsorOnCell(OffCell offCell, Point2D.Float position, float density) {
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

    @Override
    public void usePosition(PositionConsumer positionConsumer) {
        positionConsumer.use(position);
    }

    @Override
    public float distance(Cell cell) {
        return this.offCell.distance(cell);
    }
}
