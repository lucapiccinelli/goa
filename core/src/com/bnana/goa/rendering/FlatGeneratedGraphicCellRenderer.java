package com.bnana.goa.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.RepulsorOnCell;
import com.bnana.goa.cell.WanderingCell;
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle((float)position.getX(), (float)position.getY(), Math.abs(density), 15);
        shapeRenderer.end();
    }

    @Override
    public void useItOff(OffCell cell, Point2D.Float position, float density) {
        this.use(cell, position, density);
    }

    @Override
    public void renderAttractorOffCell(AttractorOffCell attractorOffCell, Point2D.Float position, float density) {
        shapeRenderer.setColor(0.776f, 0.313f, 0.313f, 1);
    }

    @Override
    public void renderRepulsorOffCell(RepulsorOffCell repulsorOffCell, Point2D.Float position, float density) {
        shapeRenderer.setColor(0.545f, 0.698f, 0.823f, 1);
    }

    @Override
    public void renderAttractorOnCell(AttractorOnCell attractorOnCell, Point2D.Float position, float density) {
        shapeRenderer.setColor(Color.RED);
    }

    @Override
    public void renderRepulsorOnCell(RepulsorOnCell repulsorOnCell, Point2D.Float position, float density) {
        shapeRenderer.setColor(Color.BLUE);
    }

    @Override
    public void renderWanderingCell(WanderingCell wanderingCell, Point2D.Float position, float density) {
        shapeRenderer.setColor(0.58f, 0.823f, 0.545f, 1);
    }
}
