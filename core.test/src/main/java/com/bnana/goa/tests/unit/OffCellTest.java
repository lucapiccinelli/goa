package com.bnana.goa.tests.unit;

import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.organism.Organism;

import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.lang.reflect.Type;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 8/21/2015.
 */
public class OffCellTest {

    @DataProvider
    public Object[][] offCells(){
        return new OffCell[][]{
                {AttractorOffCell.MakeProtype()},
                {RepulsorOffCell.MakeProtype()}};
    }

    @DataProvider
    public Object[][] positionedOffCells(){
        Point2D.Float position = new Point2D.Float(2f, 3f);
        return new Object[][]{
                { new AttractorOffCell(mock(Organism.class), position), position },
                { new RepulsorOffCell(mock(Organism.class), position), position }
        };
    }

    @DataProvider
    public Object[][] densOffCells(){
        Point2D.Float position = new Point2D.Float(2f, 3f);
        return new Object[][]{
                { new AttractorOffCell(mock(Organism.class), position, AttractorOffCell.DEFAULT_DENSITY ), position, AttractorOffCell.DEFAULT_DENSITY, -AttractorOffCell.DEFAULT_DENSITY },
                { new RepulsorOffCell(mock(Organism.class), position, RepulsorOffCell.DEFAULT_DENSITY ), position, RepulsorOffCell.DEFAULT_DENSITY, RepulsorOffCell.DEFAULT_DENSITY }
        };
    }


    @Test(dataProvider = "offCells")
    public void AnOffCellCanBeSwitchedOn(OffCell offCell){
        OnCell onCell = offCell.turnOn();
        Assert.assertNotNull(onCell);
    }

    @Test(dataProvider = "positionedOffCells")
    void AnOffCellTurnedOnShouldMaintainTheSamePosition(OffCell offCell, Point2D.Float position){
        OnCell onCell = offCell.turnOn();

        CellConsumer mock = Mockito.mock(CellConsumer.class);
        onCell.use(mock);

        Mockito.verify(mock).use(Mockito.same(onCell), Mockito.same(position), Mockito.any(float.class));
    }

    @Test(dataProvider = "densOffCells")
    void AnOffCellTurnedOnShouldMaintainTheSameDensity(OffCell offCell, Point2D.Float position, float density, float densityOut){
        OnCell onCell = offCell.turnOn();

        CellConsumer mock = Mockito.mock(CellConsumer.class);
        onCell.use(mock);

        Mockito.verify(mock).use(Mockito.same(onCell), Mockito.any(Point2D.Float.class), eq(densityOut));
    }

    @Test(dataProvider = "offCells")
    void TurningCellsOnAndOffShouldReturnAlwaysTheSameCelleReferences(OffCell offCell){
        OnCell onCell1 = offCell.turnOn();
        OnCell onCell2 = offCell.turnOn();

        Assert.assertEquals(onCell1, onCell2);
    }

    @DataProvider
    public Object[][] densityProvider() { return new Object[][]{{1f}, {-1f}}; }
    @Test(dataProvider = "densityProvider")
    public void AnAttractorCellDensityShouldAlwaysBeNegative(float density){
        Point2D.Float position = new Point2D.Float(0, 0);
        OffCell offCell = new AttractorOffCell(mock(Organism.class), position, density);

        CellConsumer mock = Mockito.mock(CellConsumer.class);
        offCell.turnOn().use(mock);
        Mockito.verify(mock).use(any(OnCell.class), Mockito.any(Point2D.Float.class), AdditionalMatchers.leq(0f));
    }

    @Test(dataProvider = "densityProvider")
    public void ARepulsorCellDensityShouldAlwaysBePositive(float density){
        Point2D.Float position = new Point2D.Float(0, 0);
        OffCell offCell = new RepulsorOffCell(mock(Organism.class), position, density);

        CellConsumer mock = Mockito.mock(CellConsumer.class);
        offCell.turnOn().use(mock);
        Mockito.verify(mock).use(any(OnCell.class), Mockito.any(Point2D.Float.class), AdditionalMatchers.geq(0f));
    }

    @DataProvider
    public Object[][] pointProvider(){
        return new Object[][]{
                {new AttractorOffCell(mock(Organism.class), new Point2D.Float(0, 0), 1), new Point2D.Float(1, 1), 1.4142135f},
                {new AttractorOffCell(mock(Organism.class), new Point2D.Float(0, 0), 1), new Point2D.Float(2, 2), 2.828427f},
                {new AttractorOffCell(mock(Organism.class), new Point2D.Float(0, 0), 1), new Point2D.Float(0, 5), 5f},
                {new RepulsorOffCell(mock(Organism.class), new Point2D.Float(0, 0), 1), new Point2D.Float(1, 1), 1.4142135f},
                {new RepulsorOffCell(mock(Organism.class), new Point2D.Float(0, 0), 1), new Point2D.Float(2, 2), 2.828427f},
                {new RepulsorOffCell(mock(Organism.class), new Point2D.Float(0, 0), 1), new Point2D.Float(0, 5), 5f},
        };
    }

    @Test(dataProvider = "pointProvider")
    public void TheDistanceBetweenTheseCellsShouldBe(OffCell cell1, Point2D.Float p, float result){
        RepulsorOffCell cell2 = new RepulsorOffCell(mock(Organism.class), p, 1);
        Assert.assertEquals(cell1.distance(cell2), result);
    }


    @DataProvider
    public Object[][] cellSources(){
        return new Object[][]{
                {AttractorOffCell.MakeProtype(), RepulsorOffCell.class},
                {RepulsorOffCell.MakeProtype(), AttractorOffCell.class},
        };
    }
    @Test(dataProvider = "cellSources")
    public void AnOffCellShouldBeAbleToGenerateItsOpposite(OffCell cell, Type expectedOpposite){
        Assert.assertEquals(cell.opposite(new Point2D.Float(0, 0), 1f).getClass(), expectedOpposite);
    }

    @Test(dataProvider = "offCells")
    public void requestingAnOffCellItShouldReturnACopyOfItSelf(OffCell cell){
        Assert.assertSame(cell, cell.getAnOffCell());
    }

    @Test(dataProvider = "offCells")
    public void WhenNotifiedThatThePositionHasChangedItShouldBeUpdated(OffCell cell){
        Point2D.Float position = new Point2D.Float(5, 10);
        PositionChangedEvent event = new PositionChangedEvent(this, position);

        cell.updatePosition(event);

        PositionConsumer positionConsumer = mock(PositionConsumer.class);
        cell.usePosition(positionConsumer);

        verify(positionConsumer).use(eq(position));
    }

    @Test
    public void AnAttractorGrowingAnOrganismShouldGrowItsAttractors(){
        AttractorOffCell attractorOffCell = new AttractorOffCell(new Point2D.Float(), 1);
        Organism organism = mock(Organism.class);

        attractorOffCell.growOrganism(organism);

        verify(organism).growAttractors(same(attractorOffCell));
    }

    @Test(dataProvider = "offCells")
    public void WhenTItIsDestroyedAllItsDestroyListenersShouldbeNotified(OffCell cell){
        CellDestroyListener destroyListener = mock(CellDestroyListener.class);
        cell.addDestroyListener(destroyListener);
        cell.destroy();

        verify(destroyListener).notifyDestroy(any(CellDestroyEvent.class));
    }
}