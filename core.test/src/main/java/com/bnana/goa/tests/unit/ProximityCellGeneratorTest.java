package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.generator.Cell;
import com.bnana.goa.cell.generator.CellGenerator;
import com.bnana.goa.cell.generator.ProximityCellGenerator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.lang.reflect.Type;

/**
 * Created by Luca on 8/21/2015.
 */
public class ProximityCellGeneratorTest {
    @Test
    public void TheDistanceBeetweenTheSourceCellAndTheNewCellShouldBeExactly1(){
        OffCell sourceCell = new AttractorOffCell(new Point2D.Float(8, 13));
        CellGenerator proximityCellGenerator = new ProximityCellGenerator(sourceCell);

        Cell generatedCell = proximityCellGenerator.generate();

        Assert.assertEquals(sourceCell.distance(generatedCell), 1f, 0.000001f);
    }

    @DataProvider
    public Object[][] cellSources(){
        return new Object[][]{
                {new AttractorOffCell(), RepulsorOffCell.class},
                {new RepulsorOffCell(), AttractorOffCell.class},
        };
    }

    @Test(dataProvider = "cellSources")
    public void TheTypeOfTheNewGeneratedCellShouldBeOppositeOfTheSourceOne(OffCell sourceCell, Type expectedGeneratedClass){
        CellGenerator proximityCellGenerator = new ProximityCellGenerator(sourceCell);

        Cell generatedCell = proximityCellGenerator.generate();
        Assert.assertEquals(generatedCell.getClass(), expectedGeneratedClass);
    }
}
