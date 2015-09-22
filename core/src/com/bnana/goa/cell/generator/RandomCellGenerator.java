package com.bnana.goa.cell.generator;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.organism.Organism;
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
    private Organism organism;
    private Cell cellPrototype;
    private Rectangle bounds;

    public RandomCellGenerator(Organism organism, Cell cellPrototype, Rectangle bounds) {
        this.organism = organism;
        this.cellPrototype = cellPrototype;
        this.bounds = bounds;
        rand = new Random();
    }

    @Override
    public Cell generate() {
        float x = rand.nextFloat() * bounds.width + bounds.x;
        float y = rand.nextFloat() * bounds.height + bounds.y;

        return cellPrototype.prototype(organism, new Vector2(x,y), Const.DEFAULT_CELL_DENSITY);
    }
}
