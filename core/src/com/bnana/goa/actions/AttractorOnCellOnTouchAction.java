package com.bnana.goa.actions;

import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

/**
 * Created by luca.piccinelli on 08/09/2015.
 */
public class AttractorOnCellOnTouchAction implements OnTouchAction {
    private final AttractorOnCell cell;
    private final PhysicElement element;

    public AttractorOnCellOnTouchAction(AttractorOnCell cell, PhysicElement element) {
        this.cell = cell;
        this.element = element;
    }

    @Override
    public void act(OnTouchAction anotherAction) {
        anotherAction.actOn(cell, element);
    }

    @Override
    public void actOn(WanderingCell cell, PhysicElement element) {
        element.stop();
        OffCell evolvedCell = cell.evolve();
    }

    @Override
    public void actOn(AttractorOnCell cell, PhysicElement element) {

    }
}
