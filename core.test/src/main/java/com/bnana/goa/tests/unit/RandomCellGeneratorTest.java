package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.generator.Cell;
import com.bnana.goa.cell.generator.CellGenerator;
import com.bnana.goa.cell.generator.RandomCellGenerator;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class RandomCellGeneratorTest {

    @DataProvider
    public Object[][] offCellsProvider(){
        return new OffCell[][]{
                {AttractorOffCell.MakeProtype()},
                {RepulsorOffCell.MakeProtype()}
        };
    }
    @Test(dataProvider = "offCellsProvider")
    public void ARandomCellShouldBeGeneratedBetweenBounds(OffCell offCellPrototype){
        Rectangle2D.Float bounds = new Rectangle2D.Float(0, 0, 20, 30);
        CellGenerator cellGenerator = new RandomCellGenerator(offCellPrototype, bounds);
        Cell offCell = cellGenerator.generate();

        final float[] x = {-1};
        final float[] y = {-1};
        PositionConsumer consumer = new PositionConsumer () {
            @Override
            public void use(Point2D.Float position) {
                x[0] = (float) position.getX();
                y[0] = (float) position.getY();
            }
        };

        offCell.usePosition(consumer);

        MatcherAssert
                .assertThat(x[0],
                        Matchers.allOf(
                                Is.is(Matchers.greaterThanOrEqualTo(bounds.x)),
                                Is.is(Matchers.lessThan((float) bounds.getMaxX()))));
        MatcherAssert
                .assertThat(y[0],
                        Matchers.allOf(
                                Is.is(Matchers.greaterThanOrEqualTo(bounds.y)),
                                Is.is(Matchers.lessThan((float) bounds.getMaxY()))));
    }

    @Test(dataProvider="offCellsProvider")
    public void TheCellReturnedMustBeOfTheSameConcreteClassOfThePrototype(OffCell offCellPrototype){
        Rectangle2D.Float bounds = new Rectangle2D.Float(0, 0, 20, 30);
        CellGenerator cellGenerator = new RandomCellGenerator(offCellPrototype, bounds);

        Cell generatedCell = cellGenerator.generate();

        Assert.assertEquals(generatedCell.getClass(), offCellPrototype.getClass());
    }
}
