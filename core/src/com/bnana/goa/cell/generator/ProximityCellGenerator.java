package com.bnana.goa.cell.generator;

import com.badlogic.gdx.math.MathUtils;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.utils.Const;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by Luca on 8/21/2015.
 */
public class ProximityCellGenerator implements CellGenerator, PositionConsumer {
    private final Random random;
    private Cell generatedCell;
    private OffCell sourceCell;
    private float x;
    private float y;

    public ProximityCellGenerator(OffCell sourceCell) {
        this.sourceCell = sourceCell;
        this.generatedCell = null;

        random = new Random();

        x = 0;
        y = 0;
    }

    @Override
    public Cell generate() {
        float randomRadius = random.nextFloat() * 360;

        x = MathUtils.cosDeg(randomRadius);
        y = MathUtils.sinDeg(randomRadius);

        sourceCell.usePosition(this);

        return generatedCell;
    }

    @Override
    public void use(Point2D.Float position) {
        generatedCell = sourceCell.opposite(
                new Point2D.Float(
                        position.x + x,
                        position.y + y), Const.DEFAULT_CELL_DENSITY);
    }
}
