package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.actions.*;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by luca.piccinelli on 07/09/2015.
 */
public class WanderingOnTouchActionTests {
    @Test
    public void TheOtherActionShouldActOnTheWanderingCell(){
        WanderingCell cell = new WanderingCell(new Vector2(), 1f);

        OnTouchAction wanderingOnTouchAction = new WanderingOnTouchAction(cell, mock(PhysicElement.class));
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        wanderingOnTouchAction.act(anotherAction);

        verify(anotherAction).actOn(same(cell), any(PhysicElement.class));
    }

    @Test
    public void AfterBeingStoppedItShouldntActAnymoreOnTheSameAction(){
        WanderingCell cell = mock(WanderingCell.class);

        OnTouchAction wanderingOnTouchAction = new WanderingOnTouchAction(cell, mock(PhysicElement.class));
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        wanderingOnTouchAction.stopActing(anotherAction);
        wanderingOnTouchAction.act(anotherAction);

        verify(anotherAction, times(0)).actOn(any(WanderingCell.class), any(PhysicElement.class));
    }

    @Test
    public void AfterBeingStoppedOnAnActionItShouldActOnOtherActions(){
        WanderingCell cell = mock(WanderingCell.class);

        OnTouchAction wanderingOnTouchAction = new WanderingOnTouchAction(cell, mock(PhysicElement.class));
        OnTouchAction anotherAction1 = mock(OnTouchAction.class);
        OnTouchAction anotherAction2 = mock(OnTouchAction.class);

        wanderingOnTouchAction.stopActing(anotherAction1);
        wanderingOnTouchAction.act(anotherAction2);

        verify(anotherAction2, times(1)).actOn(any(WanderingCell.class), any(PhysicElement.class));
    }

    @Test
    public void TheOtherActionShouldBeStopped(){
        OnTouchAction wanderingOnTouchAction = new WanderingOnTouchAction(mock(WanderingCell.class), mock(PhysicElement.class));
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        wanderingOnTouchAction.act(anotherAction);
        verify(anotherAction).stopActing(same(wanderingOnTouchAction));
    }

    @Test
    public void TheOtherActionShouldActOnTheWanderingCellAndOnItsPhysicElement(){
        PhysicElement physicElement = mock(PhysicElement.class);
        OnTouchAction wanderingOnTouchAction = new WanderingOnTouchAction(mock(WanderingCell.class), physicElement);

        OnTouchAction anotherAction = mock(OnTouchAction.class);
        wanderingOnTouchAction.act(anotherAction);

        verify(anotherAction).actOn(any(WanderingCell.class), same(physicElement));
    }

    @Test
    public void WhenActingOnAnAttractorWanderingCellShouldBeStopped(){
        PhysicElement element = mock(PhysicElement.class);

        WanderingCell wanderingCell = mock(WanderingCell.class);
        OffCell offCell = mock(OffCell.class);

        when(wanderingCell.evolve()).thenReturn(offCell);
        when(offCell.turnOn()).thenReturn(mock(OnCell.class));

        OnTouchAction action = new WanderingOnTouchAction(wanderingCell, element);

        action.actOn(mock(AttractorOnCell.class), mock(PhysicElement.class));

        verify(element).stop();
    }

    @Test
    public void WhenActingOnAnAttractorWanderingCellShouldBeEvolved(){
        WanderingCell wanderingCell = mock(WanderingCell.class);
        OffCell offCell = mock(OffCell.class);

        when(wanderingCell.evolve()).thenReturn(offCell);
        when(offCell.turnOn()).thenReturn(mock(OnCell.class));

        OnTouchAction action = new WanderingOnTouchAction(wanderingCell, mock(PhysicElement.class));
        action.actOn(mock(AttractorOnCell.class), mock(PhysicElement.class));

        verify(wanderingCell).evolve();
    }

    @Test
    public void WhenActingOnAnAttractorWanderingCellShouldBeIntegratedIntoTheOrganism(){
        PhysicElement element = mock(PhysicElement.class);
        WanderingCell wanderingCell = new WanderingCell(new Vector2(), 1f);
        OnTouchAction action = new WanderingOnTouchAction(wanderingCell, element);

        OnCell attractor = mock(OnCell.class);
        action.actOn(attractor, mock(PhysicElement.class));

        verify(attractor).integrate(same(wanderingCell.evolve()));
    }

    @Test
    public void WhenActingOnAnOnCellItShouldChangeItsActionTypeToAnOnCellOnTouchAction(){
        PhysicElement element = mock(PhysicElement.class);
        WanderingCell wanderingCell = new WanderingCell(new Vector2(), 1f);
        OnTouchAction action = new WanderingOnTouchAction(wanderingCell, element);

        action.actOn(mock(OnCell.class), mock(PhysicElement.class));

        verify(element).setAction(any(OnTouchAction.class));
    }

    @Test
    public void WhenActingOnAnOnCellItShouldRemoveTheWanderingCellFromPositionListeners(){
        PhysicElement element = mock(PhysicElement.class);
        WanderingCell wanderingCell = new WanderingCell(new Vector2(), 1f);
        OnTouchAction action = new WanderingOnTouchAction(wanderingCell, element);

        action.actOn(mock(OnCell.class), mock(PhysicElement.class));

        verify(element).removePositionListener(same(wanderingCell));
    }

    @Test
    public void WhenActingOnAnOnCellItShouldAddTheEvolvedCellToPositionListeners(){
        PhysicElement element = mock(PhysicElement.class);
        WanderingCell wanderingCell = new WanderingCell(new Vector2(), 1f);
        OnTouchAction action = new WanderingOnTouchAction(wanderingCell, element);

        action.actOn(mock(OnCell.class), mock(PhysicElement.class));

        verify(element).addPositionListener(same(wanderingCell.evolve()));
    }

    @Test
    public void WhenActingOnAnOnCellTheWanderingShouldBeDestroyed(){
        PhysicElement element = mock(PhysicElement.class);
        WanderingCell wanderingCell = mock(WanderingCell.class);
        OffCell offCell = mock(OffCell.class);

        when(wanderingCell.evolve()).thenReturn(offCell);
        when(offCell.turnOn()).thenReturn(mock(OnCell.class));

        OnTouchAction action = new WanderingOnTouchAction(wanderingCell, element);

        action.actOn(mock(OnCell.class), mock(PhysicElement.class));

        verify(wanderingCell).destroy();
    }
}
