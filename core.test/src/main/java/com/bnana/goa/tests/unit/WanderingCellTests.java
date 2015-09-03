package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.EvolvableCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.events.PositionChangedEvent;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by luca.piccinelli on 01/09/2015.
 */
public class WanderingCellTests {
    @Test
    public void AWanderingCellCanEvolveToAnOffCell(){
        EvolvableCell wanderingCell = new WanderingCell(new Point2D.Float(0, 0), 1f);
        Assert.assertNotNull(wanderingCell.evolve());
    }

    @Test
    public void EvolvingAWanderingCellTheProbabilityOfGettingAnAttractorOrARepulsorIs50And50(){
        EvolvableCell wanderingCell = new WanderingCell(new Point2D.Float(0, 0), 1f);

        List<OffCell> evolvedCells = new ArrayList<>();
        int totalRolls = 1000;
        for (int i = 0; i < totalRolls; i++){
            evolvedCells.add(wanderingCell.evolve());
        }

        final float densityAccumulator[] = {0};
        CellConsumer densityAccumulatorConsumer = new CellConsumer() {
            @Override
            public void use(Point2D.Float position, float density) {
                densityAccumulator[0] += density;
            }
        };

        for (OffCell cell : evolvedCells){
            cell.turnOn().use(densityAccumulatorConsumer);
        }

        float maximumDelta = 0.05f * totalRolls;
        Assert.assertEquals(densityAccumulator[0], 0, maximumDelta);
    }

    @DataProvider
    public Object[][] pointProvider(){
        return new Object[][]{
                {new AttractorOffCell(new Point2D.Float(0, 0), 1), new Point2D.Float(1, 1), 1.4142135f},
                {new AttractorOffCell(new Point2D.Float(0, 0), 1), new Point2D.Float(2, 2), 2.828427f},
                {new AttractorOffCell(new Point2D.Float(0, 0), 1), new Point2D.Float(0, 5), 5f},
                {new WanderingCell(new Point2D.Float(0, 0), 1), new Point2D.Float(1, 1), 1.4142135f},
                {new RepulsorOffCell(new Point2D.Float(0, 0), 1), new Point2D.Float(2, 2), 2.828427f},
                {new WanderingCell(new Point2D.Float(0, 0), 1), new Point2D.Float(0, 5), 5f},
        };
    }

    @Test(dataProvider = "pointProvider")
    public void TheDistanceBetweenTheseCellsShouldBe(Cell cell2, Point2D.Float p, float result){
        WanderingCell cell1 = new WanderingCell(p, 1);
        Assert.assertEquals(cell1.distance(cell2), result);
    }

    @Test
    public void requestingAnOnCellItShouldReturnANotNullCell(){
        Cell cell = new WanderingCell(new Point2D.Float(0, 0), 1);
        Assert.assertNotNull(cell.getAnOffCell());
    }

    @Test
    public void requestingAnOnCellItShouldReturnANewOffCell(){
        Cell cell = new WanderingCell(new Point2D.Float(0, 0), 1);
        Assert.assertNotSame(cell, cell.getAnOffCell());
    }

    @Test
    public void prototypingShouldReturnAWanderingCell(){
        WanderingCell prototype = WanderingCell.MakePrototype();
        Assert.assertEquals(WanderingCell.class, prototype.prototype(new Point2D.Float(3, 2), 1f).getClass());
    }

    @Test
    public void WhenNotifiedThatThePositionHasChangedItShouldBeUpdated(){
        Point2D.Float position = new Point2D.Float(5, 10);
        WanderingCell cell = new WanderingCell(new Point2D.Float(0, 0), 1);
        PositionChangedEvent event = new PositionChangedEvent(this, position);

        cell.updatePosition(event);

        PositionConsumer positionConsumer = mock(PositionConsumer.class);
        cell.usePosition(positionConsumer);

        verify(positionConsumer).use(eq(position));
    }
}
