package com.bnana.goa.cell.generator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.utils.Const;

import java.util.Random;

/**
 * Created by Luca on 10/30/2015.
 */
public class RadialProximityCellGenerator implements CellGenerator, PositionConsumer {
    private final Random random;
    private Cell generatedCell;
    private Cell sourceCell;
    private final Cell prototype;
    private final int totalGeneration;
    private float lastGeneratedAngle;
    private final float angleOffset;

    public RadialProximityCellGenerator(Cell sourceCell, Cell prototype, int totalGeneration) {
        this.sourceCell = sourceCell;
        this.prototype = prototype;
        this.totalGeneration = totalGeneration;
        this.generatedCell = null;
        lastGeneratedAngle = -1;
        angleOffset = 360f / totalGeneration;

        random = new Random();
    }

    @Override
    public Cell generate() {
        sourceCell.usePosition(this);
        return generatedCell;
    }

    @Override
    public void use(Vector2 position) {
        if(lastGeneratedAngle < 0) {
            lastGeneratedAngle = random.nextFloat() * 360;
        }else {
            lastGeneratedAngle = (lastGeneratedAngle + angleOffset) % 360f;
        }

        float x = MathUtils.cosDeg(lastGeneratedAngle) * 2f;
        float y = MathUtils.sinDeg(lastGeneratedAngle) * 2f;
        generatedCell = sourceCell.opposite(
                new Vector2(
                        position.x + x,
                        position.y + y), Const.DEFAULT_CELL_DENSITY);
    }
}
