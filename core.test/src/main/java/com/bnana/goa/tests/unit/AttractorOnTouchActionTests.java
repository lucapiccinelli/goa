package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.physics.PhysicCell;

import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Mockito.mock;

/**
 * Created by luca.piccinelli on 07/09/2015.
 */
public class AttractorOnTouchActionTests {
    @Test
    public void WhenActOnAWanderingCellTheCellShouldEvolve(){
        AttractorOnCell cell = new AttractorOnCell(new AttractorOffCell(), new Point2D.Float(), 1f);
        PhysicCell physicCell = mock(PhysicCell.class);

        OnTouchAction attractorAction = new AttractorOnTouchAction(cell);

        OnTouchAction anotherAction = mock(OnTouchAction.class);

        attractorAction.act(anotherAction);
    }
}
