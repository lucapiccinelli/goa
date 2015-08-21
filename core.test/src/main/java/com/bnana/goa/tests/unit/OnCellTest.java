package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class OnCellTest {
    @Test
    public void AnAttractorOffCellCanBeTurnedOff(){
        OnCell onCell = new AttractorOnCell(Mockito.mock(OffCell.class), new Point2D.Float(0, 0), 1);
        OffCell offCell = onCell.turnOff();

        Assert.assertNotNull(offCell);
    }
}
