package com.bnana.goa.tests.unit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.rendering.CellRenderer;
import com.bnana.goa.rendering.FlatGeneratedGraphicCellRenderer;
import com.bnana.goa.utils.ScaleManager;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Luca on 9/21/2015.
 */
public class FlatGeneratedGraphicCellRendererTests {
    @Test
    public void OnAnOffCellRenderIsCalled(){
        CellRenderer cellRenderer = new FlatGeneratedGraphicCellRenderer(mock(ShapeRenderer.class), mock(ScaleManager.class));
        OffCell attractorOffCell = mock(OffCell.class);

        cellRenderer.useItOff(attractorOffCell, new Point2D.Float(), 1f);

        verify(attractorOffCell).render(same(cellRenderer));
    }

    @Test
    public void OnACellRenderIsCalled(){
        CellRenderer cellRenderer = new FlatGeneratedGraphicCellRenderer(mock(ShapeRenderer.class), mock(ScaleManager.class));
        Cell cell = mock(Cell.class);

        cellRenderer.use(cell, new Point2D.Float(), 1f);

        verify(cell).render(same(cellRenderer));
    }

    @Test
    public void ItRenderAnAttractorOffCellAtTheRightPositionAnDimension(){
        ShapeRenderer shapeRenderer = mock(ShapeRenderer.class);
        ScaleManager scaleManager = mock(ScaleManager.class);
        when(scaleManager.s(anyFloat())).thenAnswer(new Answer<Float>() {
            @Override
            public Float answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (float)args[0];
            }
        });

        CellRenderer cellRenderer = new FlatGeneratedGraphicCellRenderer(shapeRenderer, scaleManager);
        OffCell attractorOffCell = mock(OffCell.class);

        Point2D.Float position = new Point2D.Float();
        cellRenderer.renderAttractorOffCell(attractorOffCell, position, 1f);

        verify(shapeRenderer).circle(eq((float) position.getX()), eq((float) position.getX()), eq(1f));
    }

    @Test
    public void renderingShouldBeBatched(){
        ShapeRenderer shapeRenderer = mock(ShapeRenderer.class);
        CellRenderer cellRenderer = new FlatGeneratedGraphicCellRenderer(shapeRenderer, mock(ScaleManager.class));
        OffCell attractorOffCell = mock(OffCell.class);

        Point2D.Float position = new Point2D.Float();
        cellRenderer.renderAttractorOffCell(attractorOffCell, position, 1f);

        verify(shapeRenderer).begin(same(ShapeRenderer.ShapeType.Filled));
    }
}
