package com.bnana.goa.actions;

import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

/**
 * Created by luca.piccinelli on 08/09/2015.
 */
public class WanderingOnTouchAction implements OnTouchAction {
    private WanderingCell cell;
    private PhysicElement physicElement;
    private boolean stoppedActing;

    public WanderingOnTouchAction(WanderingCell cell, PhysicElement physicElement) {
        this.cell = cell;
        this.physicElement = physicElement;

        stoppedActing = false;
    }

    @Override
    public void act(OnTouchAction anotherAction) {
        if(stoppedActing) return;

        anotherAction.actOn(cell, physicElement);
        anotherAction.stopActing();
    }

    @Override
    public void actOn(WanderingCell wanderingCell, PhysicElement theOtherElement) {

    }

    @Override
    public void actOn(OnCell onCell, PhysicElement theOtherElement) {
        physicElement.stop();
        OffCell evolved = cell.evolve();
        onCell.integrate(evolved);
    }

    @Override
    public void stopActing() {
        stoppedActing = true;
    }
}
