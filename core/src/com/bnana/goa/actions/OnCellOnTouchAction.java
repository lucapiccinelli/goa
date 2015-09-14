package com.bnana.goa.actions;

import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by luca.piccinelli on 08/09/2015.
 */
public class OnCellOnTouchAction implements OnTouchAction {
    private final OnCell onCell;
    private final PhysicElement element;
    private Set<OnTouchAction> stoppedActions;

    public OnCellOnTouchAction(OnCell cell, PhysicElement element) {
        this.onCell = cell;
        this.element = element;

        stoppedActions = new HashSet<OnTouchAction>();
    }

    @Override
    public void act(OnTouchAction anotherAction) {
        if(stoppedActions.contains(anotherAction)) return;
        anotherAction.actOn(onCell, element);
        anotherAction.stopActing(this);
    }

    @Override
    public void actOn(WanderingCell wanderingCell, PhysicElement theOtherElement) {
        theOtherElement.stop();
        OffCell evolvedCell = wanderingCell.evolve();
        theOtherElement.setAction(evolvedCell.turnOn().createOnTouchAction(theOtherElement));
        onCell.integrate(evolvedCell);
    }

    @Override
    public void actOn(OnCell cell, PhysicElement element) {

    }

    @Override
    public void stopActing(OnTouchAction anotherAction) {
        stoppedActions.add(anotherAction);
    }
}
