package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.generator.Cell;
import com.bnana.goa.cell.generator.CellGenerator;
import com.bnana.goa.cell.generator.ProximityCellGenerator;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Luca on 8/21/2015.
 */
public class ProximityCellGeneratorTest {
    @Test
    public void TheDistanceBeetweenTheSourceCellAndTheNewCellShouldBeExactlyTheDensityOfTheSource(){
        OffCell sourceCell = AttractorOffCell.MakeProtype();
        CellGenerator proximityCellGenerator = new ProximityCellGenerator(sourceCell);

        Cell generatedCell = proximityCellGenerator.generate();

        Assert.assertEquals(sourceCell.distance(generatedCell), 1);
    }
}
