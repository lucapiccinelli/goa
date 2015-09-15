package com.bnana.goa.actions;

import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.SwitchableCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.PhysicElement;

/**
 * Created by luca.piccinelli on 08/09/2015.
 */
public interface OnTouchAction {
    void act(OnTouchAction anotherAction);

    void actOn(WanderingCell cell, PhysicElement same);
    void actOn(SwitchableCell switchableCell, PhysicElement same);

    void stopActing(OnTouchAction anotherAction);
}
