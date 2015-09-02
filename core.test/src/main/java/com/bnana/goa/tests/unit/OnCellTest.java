package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.RepulsorOffCell;
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

    @DataProvider
    public Object[][] pointProvider(){
        return new Object[][]{
                {new AttractorOnCell(new AttractorOffCell(), new Point2D.Float(0, 0), 1), new Point2D.Float(1, 1), 1.4142135f},
                {new AttractorOnCell(new AttractorOffCell(), new Point2D.Float(0, 0), 1), new Point2D.Float(2, 2), 2.828427f},
                {new AttractorOnCell(new AttractorOffCell(), new Point2D.Float(0, 0), 1), new Point2D.Float(0, 5), 5f},
                {new RepulsorOnCell(new RepulsorOffCell(), new Point2D.Float(0, 0), 1), new Point2D.Float(1, 1), 1.4142135f},
                {new RepulsorOnCell(new RepulsorOffCell(), new Point2D.Float(0, 0), 1), new Point2D.Float(2, 2), 2.828427f},
                {new RepulsorOnCell(new RepulsorOffCell(), new Point2D.Float(0, 0), 1), new Point2D.Float(0, 5), 5f},
        };
    }

    @Test(dataProvider = "pointProvider")
    public void TheDistanceBetweenTheseCellsShouldBe(OnCell cell1, Point2D.Float p, float result){
        AttractorOffCell cell2 = new AttractorOffCell(p, 1);
        Assert.assertEquals(cell1.distance(cell2), result);
    }

    @Test(dataProvider = "onCells")
    public void requestingAnOffCellItShouldReturnAItsOffOffCell(OnCell cell){
        Assert.assertSame(offCell, cell.getAnOffCell());
    }
}
