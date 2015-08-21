package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class OffCellTest {
    @Test
    public void AnAttractorOffCellCanBeSwitchedOn(){
        OffCell offCell = new AttractorOffCell();
        OnCell onCell = offCell.turnOn();

        Assert.assertNotNull(onCell);
    }

    @Test void AnAttractorOffCellTurnedOnShouldMaintainTheSamePosition(){
        Point2D.Float position = new Point2D.Float(2f, 3f);
        OffCell offCell = new AttractorOffCell(position);
        OnCell onCell = offCell.turnOn();

        CellConsumer mock = Mockito.mock(CellConsumer.class);
        onCell.use(mock);

        Mockito.verify(mock).use(Mockito.same(position), Mockito.any(float.class));
    }

    @Test void AnAttractorOffCellTurnedOnShouldMaintainTheSameDensity(){
        float density = AttractorOffCell.DEFAULT_DENSITY;

        Point2D.Float position = new Point2D.Float(2f, 3f);
        OffCell offCell = new AttractorOffCell(position, density);
        OnCell onCell = offCell.turnOn();

        CellConsumer mock = Mockito.mock(CellConsumer.class);
        onCell.use(mock);

        Mockito.verify(mock).use(Mockito.any(Point2D.Float.class), Mockito.eq(density));
    }
}