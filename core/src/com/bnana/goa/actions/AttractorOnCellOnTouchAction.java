package com.bnana.goa.actions;

import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

/**
 * Created by luca.piccinelli on 08/09/2015.
 */
public class AttractorOnCellOnTouchAction implements OnTouchAction {
    private final AttractorOnCell cell;
    private final PhysicElement element;
    private boolean stoppedActing;

    public AttractorOnCellOnTouchAction(AttractorOnCell cell, PhysicElement element) {
        this.cell = cell;
        this.element = element;

        stoppedActing = false;
    }

    @Override
    public void act(OnTouchAction anotherAction) {
        if(stoppedActing) return;

        anotherAction.actOn(cell, element);
        anotherAction.stopActing();
    }

    @Override
    public void actOn(WanderingCell wanderingCell, PhysicElement theOtherElement) {
        theOtherElement.stop();
        OffCell evolvedCell = wanderingCell.evolve();
        cell.integrate(evolvedCell);
    }

    @Override
    public void actOn(OnCell cell, PhysicElement element) {

    }

    @Override
    public void stopActing() {
        stoppedActing = true;
    }
}
