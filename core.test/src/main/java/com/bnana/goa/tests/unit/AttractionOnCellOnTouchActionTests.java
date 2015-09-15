package com.bnana.goa.tests.unit;

import com.bnana.goa.actions.OnCellOnTouchAction;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.organism.Organism;
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
 * Created by luca.piccinelli on 08/09/2015.
 */
public class AttractionOnCellOnTouchActionTests {
    @Test
    public void TheOtherActionShouldBeStopped(){
        OnTouchAction attractorAction = new OnCellOnTouchAction(mock(AttractorOnCell.class), mock(PhysicElement.class));
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        attractorAction.act(anotherAction);
        verify(anotherAction).stopActing(same(attractorAction));
    }

    @Test
    public void TheOtherActionShouldActOnTheAttractorOnCell(){
        AttractorOnCell cell = new AttractorOnCell(mock(Organism.class), mock(AttractorOffCell.class), new Point2D.Float(), 1f);

        OnTouchAction attractorAction = new OnCellOnTouchAction(cell, mock(PhysicElement.class));
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        attractorAction.act(anotherAction);

        verify(anotherAction).actOn(same(cell), any(PhysicElement.class));
    }

    @Test
    public void AfterBeingStoppedItShouldntActAnymoreOnTheSameAction(){
        AttractorOnCell cell = mock(AttractorOnCell.class);

        OnTouchAction attractorAction = new OnCellOnTouchAction(cell, mock(PhysicElement.class));
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        attractorAction.stopActing(anotherAction);

        attractorAction.act(anotherAction);

        verify(anotherAction, times(0)).actOn(any(OnCell.class), any(PhysicElement.class));
    }

    @Test
    public void AfterBeingStoppedOnAnActionItShouldActOnOtherActions(){
        AttractorOnCell cell = mock(AttractorOnCell.class);

        OnTouchAction attractorAction = new OnCellOnTouchAction(cell, mock(PhysicElement.class));
        OnTouchAction anotherAction1 = mock(OnTouchAction.class);
        OnTouchAction anotherAction2 = mock(OnTouchAction.class);

        attractorAction.stopActing(anotherAction1);
        attractorAction.act(anotherAction2);

        verify(anotherAction2, times(1)).actOn(any(OnCell.class), any(PhysicElement.class));
    }

    @Test
    public void WhenActingOnAWanderingCellItShouldBeStopped(){
        OnTouchAction attractorAction = new OnCellOnTouchAction(mock(AttractorOnCell.class), mock(PhysicElement.class));

        WanderingCell wanderingCell = mock(WanderingCell.class);
        OffCell offCell = mock(OffCell.class);

        when(wanderingCell.evolve()).thenReturn(offCell);
        when(offCell.turnOn()).thenReturn(mock(OnCell.class));

        PhysicElement wanderingCellElement = mock(PhysicElement.class);
        attractorAction.actOn(wanderingCell, wanderingCellElement);

        verify(wanderingCellElement).stop();
    }

    @Test
    public void WhenActingOnAWanderingCellItShouldBeEvolved(){
        OnTouchAction attractorAction = new OnCellOnTouchAction(mock(AttractorOnCell.class), mock(PhysicElement.class));

        WanderingCell wanderingCell = mock(WanderingCell.class);
        OffCell offCell = mock(OffCell.class);

        when(wanderingCell.evolve()).thenReturn(offCell);
        when(offCell.turnOn()).thenReturn(mock(OnCell.class));

        attractorAction.actOn(wanderingCell, mock(PhysicElement.class));

        verify(wanderingCell).evolve();
    }

    @Test
    public void WhenActingOnAWanderingCellItShouldBeIntegratedIntoTheOrganism(){
        AttractorOnCell attractor = mock(AttractorOnCell.class);
        OnTouchAction attractorAction = new OnCellOnTouchAction(attractor, mock(PhysicElement.class));

        WanderingCell wanderingCell = new WanderingCell(new Point2D.Float(), 1f);
        attractorAction.actOn(wanderingCell, mock(PhysicElement.class));

        verify(attractor).integrate(same(wanderingCell.evolve()));
    }

    @Test
    public void WhenActingOnAWanderingCellItShouldChangeItActionTypeToAnOnCellOnTouchAction(){
        OnTouchAction attractorAction = new OnCellOnTouchAction(mock(AttractorOnCell.class), mock(PhysicElement.class));

        WanderingCell wanderingCell = mock(WanderingCell.class);
        OffCell offCell = mock(OffCell.class);

        when(wanderingCell.evolve()).thenReturn(offCell);
        when(offCell.turnOn()).thenReturn(mock(OnCell.class));

        PhysicElement wanderingCellElement = mock(PhysicElement.class);
        attractorAction.actOn(wanderingCell, wanderingCellElement);

        verify(wanderingCellElement).setAction(any(OnCellOnTouchAction.class));
    }

    @Test
    public void WhenActingOnAWanderingCellItShouldRemoveTheWanderingCellFromPositionListener(){
        OnTouchAction attractorAction = new OnCellOnTouchAction(mock(AttractorOnCell.class), mock(PhysicElement.class));

        WanderingCell wanderingCell = mock(WanderingCell.class);
        OffCell offCell = mock(OffCell.class);

        when(wanderingCell.evolve()).thenReturn(offCell);
        when(offCell.turnOn()).thenReturn(mock(OnCell.class));

        PhysicElement wanderingCellElement = mock(PhysicElement.class);
        attractorAction.actOn(wanderingCell, wanderingCellElement);

        verify(wanderingCellElement).removePositionListener(same(wanderingCell));
    }

    @Test
    public void WhenActingOnAWanderingCellItShouldAddTheEvolvedCellAsAPositionListener(){
        OnTouchAction attractorAction = new OnCellOnTouchAction(mock(AttractorOnCell.class), mock(PhysicElement.class));

        WanderingCell wanderingCell = mock(WanderingCell.class);
        OffCell offCell = mock(OffCell.class);

        when(wanderingCell.evolve()).thenReturn(offCell);
        when(offCell.turnOn()).thenReturn(mock(OnCell.class));

        PhysicElement wanderingCellElement = mock(PhysicElement.class);
        attractorAction.actOn(wanderingCell, wanderingCellElement);

        verify(wanderingCellElement).addPositionListener(same(offCell));
    }
}
