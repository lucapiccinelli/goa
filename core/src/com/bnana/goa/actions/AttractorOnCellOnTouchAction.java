package com.bnana.goa.actions;

import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

/**
 * Created by luca.piccinelli on 08/09/2015.
 */
public class AttractorOnCellOnTouchAction implements OnTouchAction {
    public AttractorOnCellOnTouchAction(AttractorOnCell cell, PhysicElement mock) {
    }

    @Override
    public void act(OnTouchAction anotherAction) {

    }

    @Override
    public void actOn(WanderingCell cell, PhysicElement same) {

    }

    @Override
    public void actOn(AttractorOnCell cell, PhysicElement same) {

    }
}
