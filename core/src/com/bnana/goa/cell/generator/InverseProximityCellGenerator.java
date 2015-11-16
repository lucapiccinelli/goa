package com.bnana.goa.cell.generator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.utils.Const;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by Luca on 8/21/2015.
 */
public class InverseProximityCellGenerator implements CellGenerator, PositionConsumer {
    private final Random random;
    private Cell generatedCell;
    private Cell sourceCell;

    public InverseProximityCellGenerator(Cell sourceCell) {
        this.sourceCell = sourceCell;
        this.generatedCell = null;

        random = new Random();
    }

    @Override
    public Cell generate() {
        sourceCell.usePosition(this);
        return generatedCell;
    }

    @Override
    public void use(Vector2 position, float radius) {
        float randomAngle = random.nextFloat() * 360;

        float x = MathUtils.cosDeg(randomAngle) * 2f;
        float y = MathUtils.sinDeg(randomAngle) * 2f;
        generatedCell = sourceCell.opposite(
                new Vector2(
                        position.x + x,
                        position.y + y), Const.DEFAULT_CELL_DENSITY);
    }
}
