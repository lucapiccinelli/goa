package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.EvolvableCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.RepulsorOnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.rendering.CellRenderer;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by luca.piccinelli on 01/09/2015.
 */
public class WanderingCellTests {
    @Test
    public void AWanderingCellCanEvolveToAnOffCell(){
        EvolvableCell wanderingCell = new WanderingCell(new Vector2(0, 0), 1f);
        Assert.assertNotNull(wanderingCell.evolve());
    }

    @Test
    public void AWanderingCellEvolvesAlwaysToTheSameCell(){
        EvolvableCell wanderingCell = new WanderingCell(new Vector2(0, 0), 1f);

        OffCell evolved = wanderingCell.evolve();
        OffCell evolved2 = wanderingCell.evolve();

        Assert.assertSame(evolved, evolved2);
    }

    @Test
    public void EvolvingAWanderingCellTheProbabilityOfGettingAnAttractorOrARepulsorIs50And50(){
        List<OffCell> evolvedCells = new ArrayList<>();
        int totalRolls = 1000;
        for (int i = 0; i < totalRolls; i++){
            evolvedCells.add(new WanderingCell(new Vector2(0, 0), 1f).evolve());
        }

        final float densityAccumulator[] = {0};
        CellConsumer densityAccumulatorConsumer = new CellConsumer() {
            @Override
            public void use(Cell cell, Vector2 position, float density) {
                densityAccumulator[0] += density;
            }

            @Override
            public void useItOff(OffCell cell, Vector2 position, float density) {

            }
        };

        for (OffCell cell : evolvedCells){
            cell.turnOn().use(densityAccumulatorConsumer);
        }

        float maximumDelta = 0.10f * totalRolls;
        Assert.assertEquals(densityAccumulator[0], 0, maximumDelta);
    }

    @DataProvider
    public Object[][] pointProvider(){
        return new Object[][]{
                {new AttractorOffCell(new Vector2(0, 0), 1), new Vector2(1, 1), 1.4142135f},
                {new AttractorOffCell(new Vector2(0, 0), 1), new Vector2(2, 2), 2.828427f},
                {new AttractorOffCell(new Vector2(0, 0), 1), new Vector2(0, 5), 5f},
                {new WanderingCell(new Vector2(0, 0), 1), new Vector2(1, 1), 1.4142135f},
                {new RepulsorOffCell(new Vector2(0, 0), 1), new Vector2(2, 2), 2.828427f},
                {new WanderingCell(new Vector2(0, 0), 1), new Vector2(0, 5), 5f},
        };
    }

    @Test(dataProvider = "pointProvider")
    public void TheDistanceBetweenTheseCellsShouldBe(Cell cell2, Vector2 p, float result){
        WanderingCell cell1 = new WanderingCell(p, 1);
        Assert.assertEquals(cell1.distance(cell2), result);
    }

    @Test
    public void requestingAnOnCellItShouldReturnANotNullCell(){
        Cell cell = new WanderingCell(new Vector2(0, 0), 1);
        Assert.assertNotNull(cell.getAnOffCell());
    }

    @Test
    public void requestingAnOnCellItShouldReturnANewOffCell(){
        Cell cell = new WanderingCell(new Vector2(0, 0), 1);
        Assert.assertNotSame(cell, cell.getAnOffCell());
    }

    @Test
    public void prototypingShouldReturnAWanderingCell(){
        WanderingCell prototype = WanderingCell.MakePrototype();
        Assert.assertEquals(WanderingCell.class, prototype.prototype(null, new Vector2(3, 2), 1f).getClass());
    }

    @Test
    public void WhenNotifiedThatThePositionHasChangedItShouldBeUpdated(){
        Vector2 position = new Vector2(5, 10);
        WanderingCell cell = new WanderingCell(new Vector2(0, 0), 1);
        PositionChangedEvent event = new PositionChangedEvent(this, position);

        cell.updatePosition(event);

        PositionConsumer positionConsumer = mock(PositionConsumer.class);
        cell.usePosition(positionConsumer);

        verify(positionConsumer).use(eq(position), anyFloat());
    }

    @Test
    public void ShouldCreateAWanderingOnTouchActionThatRefersToItSelfAndToTheGivenPhysicElement() {
        WanderingCell cell = new WanderingCell(new Vector2(0, 0), 1);

        PhysicElement element = mock(PhysicElement.class);
        OnTouchAction action = cell.createOnTouchAction(element);
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        action.act(anotherAction);

        verify(anotherAction).actOn(same(cell), same(element));
    }

    @Test
    public void WhenTItIsDestroyedAllItsDestroyListenersShouldbeNotified(){
        Cell cell = new WanderingCell(new Vector2(0, 0), 1);

        CellDestroyListener destroyListener = mock(CellDestroyListener.class);
        cell.addDestroyListener(destroyListener);
        cell.destroy();

        verify(destroyListener).notifyDestroy(any(CellDestroyEvent.class));
    }

    @Test
    public void RenderingAWanderingCellShouldCallRenderWanderingCell(){
        Vector2 position = new Vector2(2, 3);
        float density = 1f;
        WanderingCell cell = new WanderingCell(position, density);
        CellRenderer cellRenderer = mock(CellRenderer.class);

        cell.render(cellRenderer);

        verify(cellRenderer).renderWanderingCell(same(cell), eq(position), eq(density));
    }
}
