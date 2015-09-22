package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.generator.CellGenerator;
import com.bnana.goa.cell.generator.InverseProximityCellGenerator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.lang.reflect.Type;

/**
 * Created by Luca on 8/21/2015.
 */
public class InverseProximityCellGeneratorTest {
    @Test
    public void TheDistanceBeetweenTheSourceCellAndTheNewCellShouldBeExactly1(){
        OffCell sourceCell = new AttractorOffCell(new Vector2(8, 13), 1f);
        CellGenerator proximityCellGenerator = new InverseProximityCellGenerator(sourceCell);

        Cell generatedCell = proximityCellGenerator.generate();

        Assert.assertEquals(sourceCell.distance(generatedCell), 2f, 0.000001f);
    }

    @DataProvider
    public Object[][] cellSources(){
        return new Object[][]{
                {AttractorOffCell.MakeProtype(), RepulsorOffCell.class},
                {RepulsorOffCell.MakeProtype(), AttractorOffCell.class},
        };
    }

    @Test(dataProvider = "cellSources")
    public void TheTypeOfTheNewGeneratedCellShouldBeOppositeOfTheSourceOne(OffCell sourceCell, Type expectedGeneratedClass){
        CellGenerator proximityCellGenerator = new InverseProximityCellGenerator(sourceCell);

        Cell generatedCell = proximityCellGenerator.generate();
        Assert.assertEquals(generatedCell.getClass(), expectedGeneratedClass);
    }
}
