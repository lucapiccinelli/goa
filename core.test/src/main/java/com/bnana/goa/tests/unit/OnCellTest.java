package com.bnana.goa.tests.unit;

import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.RepulsorOnCell;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.exceptions.InvalidIntegrateRequestException;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;

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
    private OffCell realAttractorOffCell;
    private OffCell realRepulsorOffCell;

    @BeforeClass
    public void fixtureSetUp(){
        offCell = mock(OffCell.class);

        realAttractorOffCell = AttractorOffCell.MakeProtype();
        realRepulsorOffCell = RepulsorOffCell.MakeProtype();
    }

    @DataProvider
    public Object[][] onCells(){
        return new OnCell[][]{
                { new AttractorOnCell(mock(Organism.class), offCell, new Point2D.Float(0, 0), 1)},
                { new RepulsorOnCell(mock(Organism.class), offCell, new Point2D.Float(0, 0), 1)},
        };
    }

    @DataProvider
    public Object[][] onCellsWithouOrganism(){
        return new OnCell[][]{
                { new AttractorOnCell(null, offCell, new Point2D.Float(0, 0), 1)},
                { new RepulsorOnCell(null, offCell, new Point2D.Float(0, 0), 1)},
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
                {new AttractorOnCell(mock(Organism.class), AttractorOffCell.MakeProtype(), new Point2D.Float(0, 0), 1), new Point2D.Float(1, 1), 1.4142135f},
                {new AttractorOnCell(mock(Organism.class), AttractorOffCell.MakeProtype(), new Point2D.Float(0, 0), 1), new Point2D.Float(2, 2), 2.828427f},
                {new AttractorOnCell(mock(Organism.class), AttractorOffCell.MakeProtype(), new Point2D.Float(0, 0), 1), new Point2D.Float(0, 5), 5f},
                {new RepulsorOnCell(mock(Organism.class), RepulsorOffCell.MakeProtype(), new Point2D.Float(0, 0), 1), new Point2D.Float(1, 1), 1.4142135f},
                {new RepulsorOnCell(mock(Organism.class), RepulsorOffCell.MakeProtype(), new Point2D.Float(0, 0), 1), new Point2D.Float(2, 2), 2.828427f},
                {new RepulsorOnCell(mock(Organism.class), RepulsorOffCell.MakeProtype(), new Point2D.Float(0, 0), 1), new Point2D.Float(0, 5), 5f},
        };
    }

    @Test(dataProvider = "pointProvider")
    public void TheDistanceBetweenTheseCellsShouldBe(OnCell cell1, Point2D.Float p, float result){
        AttractorOffCell cell2 = new AttractorOffCell(mock(Organism.class), p, 1);
        Assert.assertEquals(cell1.distance(cell2), result);
    }

    @Test(dataProvider = "onCells")
    public void requestingAnOffCellItShouldReturnAItsOffOffCell(OnCell cell){
        Assert.assertSame(offCell, cell.getAnOffCell());
    }

    @Test(dataProvider = "onCells")
    public void WhenNotifiedThatThePositionHasChangedItShouldBeUpdated(OnCell cell){
        Point2D.Float position = new Point2D.Float(5, 10);
        PositionChangedEvent event = new PositionChangedEvent(this, position);

        cell.updatePosition(event);

        PositionConsumer positionConsumer = mock(PositionConsumer.class);
        cell.usePosition(positionConsumer);

        verify(positionConsumer).use(eq(position));
    }

    @Test(dataProvider = "onCells")
    public void WhenNotifiedThatThePositionHasChangedItsOffCellShouldBeUpdatedEither(OnCell cell){
        Point2D.Float position = new Point2D.Float(5, 10);
        PositionChangedEvent event = new PositionChangedEvent(this, position);

        cell.updatePosition(event);
        verify(offCell).updatePosition(eq(event));
    }

    @Test
    public void AnAttractorIntegratingANewCellShouldAskTheCellToGrowTheOrganism(){
        AttractorOffCell newOffCell = mock(AttractorOffCell.class);
        Organism organism = mock(Organism.class);

        AttractorOnCell cell = new AttractorOnCell(organism, mock(OffCell.class), new Point2D.Float(), 1);
        cell.integrate(newOffCell);

        verify(newOffCell).growOrganism(same(organism));
    }

    @Test
    public void ARepulsorIntegratingANewCellShouldAskTheCellToGrowTheOrganism(){
        OffCell newOffCell = mock(OffCell.class);
        Organism organism = mock(Organism.class);

        RepulsorOnCell cell = new RepulsorOnCell(organism, mock(OffCell.class), new Point2D.Float(), 1);
        cell.integrate(newOffCell);

        verify(newOffCell).growOrganism(same(organism));
    }

    @Test(expectedExceptions = InvalidIntegrateRequestException.class, dataProvider = "onCellsWithouOrganism")
    public void ARepulsorWithoutAnOrganismIntegratingANewCellShouldThrow(OnCell cell){
        OffCell newOffCell = mock(OffCell.class);
        cell.integrate(newOffCell);
    }

    @Test(dataProvider = "onCells")
    public void ShouldCreateAnOnTouchActionThatRefersToItSelfAndToTheGivenPhysicElement(OnCell onCell) {
        PhysicElement element = mock(PhysicElement.class);
        OnTouchAction action = onCell.createOnTouchAction(element);
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        action.act(anotherAction);

        verify(anotherAction).actOn(same(onCell), same(element));
    }
}
