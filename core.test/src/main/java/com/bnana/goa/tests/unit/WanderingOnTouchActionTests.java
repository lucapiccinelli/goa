package com.bnana.goa.tests.unit;

import com.bnana.goa.actions.*;
import com.bnana.goa.cell.AttractorOnCell;
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

/**
 * Created by luca.piccinelli on 07/09/2015.
 */
public class WanderingOnTouchActionTests {
    @Test
    public void TheOtherActionShouldActOnTheWanderingCell(){
        WanderingCell cell = new WanderingCell(new Point2D.Float(), 1f);

        OnTouchAction wanderingOnTouchAction = new WanderingOnTouchAction(cell, mock(PhysicElement.class));
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        wanderingOnTouchAction.act(anotherAction);

        verify(anotherAction).actOn(same(cell), any(PhysicElement.class));
    }

    @Test
    public void AfterBeingStoppedItShouldntActAnymore(){
        WanderingCell cell = mock(WanderingCell.class);

        OnTouchAction wanderingOnTouchAction = new WanderingOnTouchAction(cell, mock(PhysicElement.class));
        wanderingOnTouchAction.stopActing();

        OnTouchAction anotherAction = mock(OnTouchAction.class);
        wanderingOnTouchAction.act(anotherAction);

        verify(anotherAction, times(0)).actOn(any(WanderingCell.class), any(PhysicElement.class));
    }

    @Test
    public void TheOtherActionShouldBeStopped(){
        OnTouchAction wanderingOnTouchAction = new WanderingOnTouchAction(mock(WanderingCell.class), mock(PhysicElement.class));
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        wanderingOnTouchAction.act(anotherAction);
        verify(anotherAction).stopActing();
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
        OnTouchAction action = new WanderingOnTouchAction(mock(WanderingCell.class), element);

        action.actOn(mock(AttractorOnCell.class), mock(PhysicElement.class));

        verify(element).stop();
    }

    @Test
    public void WhenActingOnAnAttractorWanderingCellShouldBeEvolved(){
        PhysicElement element = mock(PhysicElement.class);
        WanderingCell wanderingCell = mock(WanderingCell.class);
        OnTouchAction action = new WanderingOnTouchAction(wanderingCell, element);

        action.actOn(mock(AttractorOnCell.class), mock(PhysicElement.class));

        verify(wanderingCell).evolve();
    }

    @Test
    public void WhenActingOnAnAttractorWanderingCellShouldBeIntegratedIntoTheOrganism(){
        PhysicElement element = mock(PhysicElement.class);
        WanderingCell wanderingCell = new WanderingCell(new Point2D.Float(), 1f);
        OnTouchAction action = new WanderingOnTouchAction(wanderingCell, element);

        OnCell attractor = mock(OnCell.class);
        action.actOn(attractor, mock(PhysicElement.class));

        verify(attractor).integrate(same(wanderingCell.evolve()));
    }
}
