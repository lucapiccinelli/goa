package com.bnana.goa.cell.generator;

import com.badlogic.gdx.math.MathUtils;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.utils.Const;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by Luca on 8/21/2015.
 */
public class InverseProximityCellGenerator implements CellGenerator, PositionConsumer {
    private final Random random;
    private OffCell generatedCell;
    private OffCell sourceCell;

    public InverseProximityCellGenerator(OffCell sourceCell) {
        this.sourceCell = sourceCell;
        this.generatedCell = null;

        random = new Random();
    }

    @Override
    public OffCell generate() {
        sourceCell.usePosition(this);
        return generatedCell;
    }

    @Override
    public void use(Point2D.Float position) {
        float randomAngle = random.nextFloat() * 360;

        float x = MathUtils.cosDeg(randomAngle) * 2f;
        float y = MathUtils.sinDeg(randomAngle) * 2f;
        generatedCell = sourceCell.opposite(
                new Point2D.Float(
                        position.x + x,
                        position.y + y), Const.DEFAULT_CELL_DENSITY);
    }
}
