package com.bnana.goa.tests.unit;

import com.bnana.goa.actions.*;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by luca.piccinelli on 07/09/2015.
 */
public class WanderingOnTouchActionTests {
    @Test
    public void TheOtherActionShouldActOnTheWanderingCell(){
        WanderingCell cell = new WanderingCell(new Point2D.Float(), 1f);

        OnTouchAction attractorAction = new WanderingOnTouchAction(cell, mock(PhysicElement.class));
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        attractorAction.act(anotherAction);

        verify(anotherAction).actOn(same(cell), any(PhysicElement.class));
    }

    @Test
    public void TheOtherActionShouldActOnTheWanderingCellAndOnItsPhysicElement(){
        PhysicElement physicElement = mock(PhysicElement.class);
        OnTouchAction attractorAction = new WanderingOnTouchAction(mock(WanderingCell.class), physicElement);

        OnTouchAction anotherAction = mock(OnTouchAction.class);
        attractorAction.act(anotherAction);

        verify(anotherAction).actOn(any(WanderingCell.class), same(physicElement));
    }
}
