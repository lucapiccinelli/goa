package com.bnana.goa.actions;

import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

/**
 * Created by luca.piccinelli on 08/09/2015.
 */
public class WanderingOnTouchAction implements OnTouchAction {
    private WanderingCell cell;
    private PhysicElement physicElement;

    public WanderingOnTouchAction(WanderingCell cell, PhysicElement physicElement) {
        this.cell = cell;
        this.physicElement = physicElement;
    }

    @Override
    public void act(OnTouchAction anotherAction) {
        anotherAction.actOn(cell, physicElement);
    }

    @Override
    public void actOn(WanderingCell cell, PhysicElement same) {

    }

    @Override
    public void actOn(AttractorOnCell cell, PhysicElement same) {

    }
}
