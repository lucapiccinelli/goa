package com.bnana.goa.cell.generator;

import com.bnana.goa.utils.Const;
import com.bnana.goa.cell.Cell;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Created by Luca on 8/21/2015.
 */
public class RandomCellGenerator implements CellGenerator {
    private final Random rand;
    private Cell cellPrototype;
    private Rectangle2D.Float bounds;

    public RandomCellGenerator(Cell cellPrototype, Rectangle2D.Float bounds) {
        this.cellPrototype = cellPrototype;
        this.bounds = bounds;
        rand = new Random();
    }

    @Override
    public Cell generate() {
        float x = rand.nextFloat() * bounds.width + bounds.x;
        float y = rand.nextFloat() * bounds.height + bounds.y;

        return cellPrototype.prototype(new Point2D.Float(x,y), Const.DEFAULT_CELL_DENSITY);
    }
}
