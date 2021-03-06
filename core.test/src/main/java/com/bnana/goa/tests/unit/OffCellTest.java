package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellController;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.SwitchableCell;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.exceptions.InvalidIntegrateRequestException;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.rendering.CellRenderer;

import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Attr;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.lang.reflect.Type;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 8/21/2015.
 */
public class OffCellTest {

    private Organism organism;

    @BeforeClass
    public void setUp(){
        organism = mock(Organism.class);
    }

    @DataProvider
    public Object[][] offCellsWithoutBody(){
        return new OffCell[][]{
                {AttractorOffCell.MakeProtype()},
                {RepulsorOffCell.MakeProtype()}};
    }


    @DataProvider
    public Object[][] offCells(){
        return new OffCell[][]{
                {new AttractorOffCell(organism, new Vector2())},
                {new RepulsorOffCell(organism, new Vector2())}};
    }

    @DataProvider
    public Object[][] positionedOffCells(){
        Vector2 position = new Vector2(2f, 3f);
        return new Object[][]{
                { new AttractorOffCell(organism, position), position },
                { new RepulsorOffCell(organism, position), position }
        };
    }

    @DataProvider
    public Object[][] densOffCells(){
        Vector2 position = new Vector2(2f, 3f);
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
    void AnOffCellTurnedOnShouldMaintainTheSamePosition(OffCell offCell, Vector2 position){
        OnCell onCell = offCell.turnOn();

        CellConsumer mock = Mockito.mock(CellConsumer.class);
        onCell.use(mock);

        Mockito.verify(mock).use(Mockito.same(onCell), Mockito.same(position), Mockito.any(float.class));
    }

    @Test(dataProvider = "densOffCells")
    void AnOffCellTurnedOnShouldMaintainTheSameDensity(OffCell offCell, Vector2 position, float density, float densityOut){
        OnCell onCell = offCell.turnOn();

        CellConsumer mock = Mockito.mock(CellConsumer.class);
        onCell.use(mock);

        Mockito.verify(mock).use(Mockito.same(onCell), Mockito.any(Vector2.class), eq(densityOut));
    }

    @Test(dataProvider = "offCells")
    void TurningCellsOnAndOffShouldReturnAlwaysTheSameCellReferences(OffCell offCell){
        OnCell onCell1 = offCell.turnOn();
        OnCell onCell2 = offCell.turnOn();

        Assert.assertSame(onCell1, onCell2);
    }

    @Test(dataProvider = "offCells")
    void SwitchingCellsShouldReturnNotNull(OffCell offCell){
        SwitchableCell onCell1 = offCell.sswitch();
        Assert.assertNotNull(onCell1);
    }

    @Test(dataProvider = "offCells")
    void SwitchingCellsShouldReturnAlwaysTheSameCellReferences(OffCell offCell){
        SwitchableCell onCell1 = offCell.sswitch();
        SwitchableCell onCell2 = offCell.sswitch();

        Assert.assertSame(onCell1, onCell2);
    }

    @Test(dataProvider = "offCells")
    public void AnOffCellMustTellTheCellConsumerThatItIsOff(OffCell offCell){
        CellConsumer consumer = mock(CellConsumer.class);
        offCell.use(consumer);

        verify(consumer).useItOff(same(offCell), any(Vector2.class), anyFloat());
    }


    @DataProvider
    public Object[][] densityProvider() { return new Object[][]{{1f}, {-1f}}; }
    @Test(dataProvider = "densityProvider")
    public void AnAttractorCellDensityShouldAlwaysBeNegative(float density){
        Vector2 position = new Vector2(0, 0);
        OffCell offCell = new AttractorOffCell(mock(Organism.class), position, density);

        CellConsumer mock = Mockito.mock(CellConsumer.class);
        offCell.turnOn().use(mock);
        Mockito.verify(mock).use(any(OnCell.class), Mockito.any(Vector2.class), AdditionalMatchers.leq(0f));
    }

    @Test(dataProvider = "densityProvider")
    public void ARepulsorCellDensityShouldAlwaysBePositive(float density){
        Vector2 position = new Vector2(0, 0);
        OffCell offCell = new RepulsorOffCell(mock(Organism.class), position, density);

        CellConsumer mock = Mockito.mock(CellConsumer.class);
        offCell.turnOn().use(mock);
        Mockito.verify(mock).use(any(OnCell.class), Mockito.any(Vector2.class), AdditionalMatchers.geq(0f));
    }

    @DataProvider
    public Object[][] pointProvider(){
        return new Object[][]{
                {new AttractorOffCell(mock(Organism.class), new Vector2(0, 0), 1), new Vector2(1, 1), 1.4142135f},
                {new AttractorOffCell(mock(Organism.class), new Vector2(0, 0), 1), new Vector2(2, 2), 2.828427f},
                {new AttractorOffCell(mock(Organism.class), new Vector2(0, 0), 1), new Vector2(0, 5), 5f},
                {new RepulsorOffCell(mock(Organism.class), new Vector2(0, 0), 1), new Vector2(1, 1), 1.4142135f},
                {new RepulsorOffCell(mock(Organism.class), new Vector2(0, 0), 1), new Vector2(2, 2), 2.828427f},
                {new RepulsorOffCell(mock(Organism.class), new Vector2(0, 0), 1), new Vector2(0, 5), 5f},
        };
    }

    @Test(dataProvider = "pointProvider")
    public void TheDistanceBetweenTheseCellsShouldBe(OffCell cell1, Vector2 p, float result){
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
        Assert.assertEquals(cell.opposite(new Vector2(0, 0), 1f).getClass(), expectedOpposite);
    }

    @Test(dataProvider = "offCells")
    public void requestingAnOffCellItShouldReturnACopyOfItSelf(OffCell cell){
        Assert.assertSame(cell, cell.getAnOffCell());
    }

    @Test(dataProvider = "offCells")
    public void WhenNotifiedThatThePositionHasChangedItShouldBeUpdated(OffCell cell){
        Vector2 position = new Vector2(5, 10);
        PositionChangedEvent event = new PositionChangedEvent(this, position);

        cell.updatePosition(event);

        PositionConsumer positionConsumer = mock(PositionConsumer.class);
        cell.usePosition(positionConsumer);

        verify(positionConsumer).use(eq(position), anyFloat());
    }

    @Test(dataProvider = "offCells")
    public void WhenNotifiedThatThePositionHasChangedAlsoTheOnCellShouldBeUpdated(OffCell cell){
        Vector2 position = new Vector2(5, 10);
        PositionChangedEvent event = new PositionChangedEvent(this, position);

        OnCell onCell = cell.turnOn();
        cell.updatePosition(event);

        PositionConsumer positionConsumer = mock(PositionConsumer.class);
        onCell.usePosition(positionConsumer);

        verify(positionConsumer).use(eq(position), anyFloat());
    }

    @Test
    public void AnAttractorGrowingAnOrganismShouldGrowItsAttractors(){
        AttractorOffCell attractorOffCell = new AttractorOffCell(new Vector2(), 1);
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

    @Test(dataProvider = "offCells")
    public void WhenIntegratingANewCellShouldAskTheCellToGrowTheOrganism(OffCell cell){
        OffCell newOffCell = mock(OffCell.class);
        cell.integrate(newOffCell);

        verify(newOffCell).growOrganism(same(organism));
    }

    @Test(dataProvider = "offCellsWithoutBody", expectedExceptions = InvalidIntegrateRequestException.class)
    public void WhenIntegratingWithACellWithoutOrganismShouldThrow(OffCell cell){
        cell.integrate(mock(OffCell.class));
    }

    @Test(dataProvider = "offCells")
    public void ShouldCreateAnOnTouchActionThatRefersToItSelfAndToTheGivenPhysicElement(OffCell offCell) {
        PhysicElement element = mock(PhysicElement.class);
        OnTouchAction action = offCell.createOnTouchAction(element);

        OnTouchAction anotherAction = mock(OnTouchAction.class);
        action.act(anotherAction);

        verify(anotherAction).actOn(same(offCell), same(element));
    }

    @Test
    public void RenderingAnAttractorShouldCallRenderAttractor(){
        Vector2 position = new Vector2(2, 3);
        float density = 1f;
        AttractorOffCell cell = new AttractorOffCell(mock(Organism.class), position, density);
        CellRenderer cellRenderer = mock(CellRenderer.class);

        cell.render(cellRenderer);

        verify(cellRenderer).renderAttractorOffCell(same(cell), eq(position), eq(density));
    }

    @Test
    public void RenderingARepulsorShouldCallRenderRepulsor(){
        Vector2 position = new Vector2(2, 3);
        float density = 1f;
        RepulsorOffCell cell = new RepulsorOffCell(mock(Organism.class), position, density);
        CellRenderer cellRenderer = mock(CellRenderer.class);

        cell.render(cellRenderer);

        verify(cellRenderer).renderRepulsorOffCell(same(cell), eq(position), eq(density));
    }
}