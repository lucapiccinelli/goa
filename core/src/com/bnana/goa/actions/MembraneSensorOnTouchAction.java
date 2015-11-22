package com.bnana.goa.actions;

import com.bnana.goa.cell.SwitchableCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.physics.Box2dMembraneSensor;
import com.bnana.goa.physics.PhysicElement;

/**
 * Created by Luca on 11/22/2015.
 */
public class MembraneSensorOnTouchAction implements OnTouchAction{
    private Box2dMembraneSensor membraneSensor;

    public MembraneSensorOnTouchAction(Box2dMembraneSensor membraneSensor) {

        this.membraneSensor = membraneSensor;
    }

    @Override
    public void act(OnTouchAction anotherAction) {

    }

    @Override
    public void actOn(WanderingCell cell, PhysicElement element) {
        membraneSensor.integrate(element);
    }

    @Override
    public void actOn(SwitchableCell switchableCell, PhysicElement element) {

    }

    @Override
    public void stopActing(OnTouchAction anotherAction) {

    }
}
