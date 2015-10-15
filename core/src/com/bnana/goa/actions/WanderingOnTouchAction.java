package com.bnana.goa.actions;

import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.SwitchableCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by luca.piccinelli on 08/09/2015.
 */
public class WanderingOnTouchAction implements OnTouchAction {
    private WanderingCell wanderingCell;
    private PhysicElement physicElement;
    private Set<OnTouchAction> stoppedActions;

    public WanderingOnTouchAction(WanderingCell cell, PhysicElement physicElement) {
        this.wanderingCell = cell;
        this.physicElement = physicElement;

        stoppedActions = new HashSet<OnTouchAction>();
    }

    @Override
    public void act(OnTouchAction anotherAction) {
        if(stoppedActions.contains(anotherAction)) return;

        anotherAction.actOn(wanderingCell, physicElement);
        anotherAction.stopActing(this);
    }

    @Override
    public void actOn(WanderingCell wanderingCell, PhysicElement theOtherElement) {

    }

    @Override
    public void actOn(SwitchableCell switchableCell, PhysicElement theOtherElement) {
        physicElement.stop();
        physicElement.removePositionListener(wanderingCell);

        OffCell evolved = wanderingCell.evolve();
        wanderingCell.destroy();

        physicElement.setAction(evolved.createOnTouchAction(physicElement));
        physicElement.addPositionListener(evolved);
        evolved.usePosition(physicElement);
        switchableCell.integrate(evolved);
    }

    @Override
    public void stopActing(OnTouchAction anotherAction) {
        stoppedActions.add(anotherAction);
    }
}
