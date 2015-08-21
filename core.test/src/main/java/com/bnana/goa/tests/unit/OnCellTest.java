package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.RepulsorOnCell;

import static org.mockito.Mockito.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class OnCellTest {
    private OffCell offCell;

    @BeforeClass
    public void fixtureSetUp(){
        offCell = mock(OffCell.class);
    }

    @DataProvider
    public Object[][] onCells(){
        return new OnCell[][]{
                { new AttractorOnCell(offCell, new Point2D.Float(0, 0), 1)},
                { new RepulsorOnCell(offCell, new Point2D.Float(0, 0), 1)},
        };
    }

    @Test(dataProvider = "onCells")
    public void AnAttractorOffCellCanBeTurnedOff(OnCell onCell){
        OffCell offCell = onCell.turnOff();

        Assert.assertNotNull(offCell);
    }

    @Test(dataProvider = "onCells")
    void TurningCellsOnAndOffShouldReturnAlwaysTheSameCellReference(OnCell onCell){
        OffCell offCell2 = onCell.turnOff();
        Assert.assertEquals(offCell, offCell2);
    }
}
