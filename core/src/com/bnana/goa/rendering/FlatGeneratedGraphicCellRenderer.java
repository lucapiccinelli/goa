package com.bnana.goa.rendering;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.utils.ScaleManager;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 9/21/2015.
 */
public class FlatGeneratedGraphicCellRenderer implements CellRenderer {
    private ShapeRenderer shapeRenderer;
    private ScaleManager sm;

    public FlatGeneratedGraphicCellRenderer(ShapeRenderer shapeRenderer, ScaleManager sm) {

        this.shapeRenderer = shapeRenderer;
        this.sm = sm;
    }

    @Override
    public void use(Cell cell, Point2D.Float position, float density) {
        cell.render(this);
    }

    @Override
    public void useItOff(OffCell cell, Point2D.Float position, float density) {
        this.use(cell, position, density);
    }

    @Override
    public void renderAttractorOffCell(AttractorOffCell attractorOffCell, Point2D.Float position, float density) {
        shapeRenderer.setColor(0.776f, 0.313f, 0.313f, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(sm.s((float) position.getX()), sm.s((float) position.getY()), sm.s(density));
        shapeRenderer.end();
    }
}
