package com.bnana.goa.tests.unit;

import com.bnana.goa.actions.AttractorOnCellOnTouchAction;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by luca.piccinelli on 08/09/2015.
 */
public class AttractionOnCellOnTouchActionTests {
    @Test
    public void TheOtherActionShouldActOnTheAttractorOnCell(){
        AttractorOnCell cell = new AttractorOnCell(mock(AttractorOffCell.class), new Point2D.Float(), 1f);

        OnTouchAction attractorAction = new AttractorOnCellOnTouchAction(cell, mock(PhysicElement.class));
        OnTouchAction anotherAction = mock(OnTouchAction.class);

        attractorAction.act(anotherAction);

        verify(anotherAction).actOn(same(cell), any(PhysicElement.class));
    }
}
