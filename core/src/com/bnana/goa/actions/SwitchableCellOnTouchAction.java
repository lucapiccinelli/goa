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
public class SwitchableCellOnTouchAction implements OnTouchAction {
    private final SwitchableCell switchableCell;
    private final PhysicElement element;
    private Set<OnTouchAction> stoppedActions;

    public SwitchableCellOnTouchAction(SwitchableCell cell, PhysicElement element) {
        this.switchableCell = cell;
        this.element = element;

        stoppedActions = new HashSet<OnTouchAction>();
    }

    @Override
    public void act(OnTouchAction anotherAction) {
        if(stoppedActions.contains(anotherAction)) return;
        anotherAction.actOn(switchableCell, element);
        anotherAction.stopActing(this);
    }

    @Override
    public void actOn(WanderingCell wanderingCell, PhysicElement theOtherElement) {
        theOtherElement.stop();
        theOtherElement.removePositionListener(wanderingCell);

        OffCell evolvedCell = wanderingCell.evolve();
        wanderingCell.destroy();

        theOtherElement.setAction(evolvedCell.createOnTouchAction(theOtherElement));
        theOtherElement.addPositionListener(evolvedCell);
        evolvedCell.usePosition(theOtherElement);
        switchableCell.integrate(evolvedCell);
        element.integrate(theOtherElement);
    }

    @Override
    public void actOn(SwitchableCell switchableCell, PhysicElement theOtherElement) {
        theOtherElement.stop();
        element.stop();
    }

    @Override
    public void stopActing(OnTouchAction anotherAction) {
        stoppedActions.add(anotherAction);
    }
}
