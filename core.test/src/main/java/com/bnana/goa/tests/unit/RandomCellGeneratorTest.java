package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.generator.CellGenerator;
import com.bnana.goa.cell.generator.RandomCellGenerator;
import com.bnana.goa.organism.Organism;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.mockito.Mockito.mock;

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
        Rectangle bounds = new Rectangle(0, 0, 20, 30);
        CellGenerator cellGenerator = new RandomCellGenerator(mock(Organism.class), offCellPrototype, bounds);
        Cell offCell = cellGenerator.generate();

        final float[] x = {-1};
        final float[] y = {-1};
        PositionConsumer consumer = new PositionConsumer () {
            @Override
            public void use(Vector2 position) {
                x[0] = (float) position.x;
                y[0] = (float) position.y;
            }
        };

        offCell.usePosition(consumer);

        MatcherAssert
                .assertThat(x[0],
                        Matchers.allOf(
                                Is.is(Matchers.greaterThanOrEqualTo(bounds.x)),
                                Is.is(Matchers.lessThan((float) bounds.x + bounds.width))));
        MatcherAssert
                .assertThat(y[0],
                        Matchers.allOf(
                                Is.is(Matchers.greaterThanOrEqualTo(bounds.y)),
                                Is.is(Matchers.lessThan((float) bounds.y + bounds.height))));
    }

    @Test(dataProvider="offCellsProvider")
    public void TheCellReturnedMustBeOfTheSameConcreteClassOfThePrototype(OffCell offCellPrototype){
        Rectangle bounds = new Rectangle(0, 0, 20, 30);
        CellGenerator cellGenerator = new RandomCellGenerator(mock(Organism.class), offCellPrototype, bounds);

        Cell generatedCell = cellGenerator.generate();

        Assert.assertEquals(generatedCell.getClass(), offCellPrototype.getClass());
    }
}
