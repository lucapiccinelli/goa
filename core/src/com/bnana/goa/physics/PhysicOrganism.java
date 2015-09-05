package com.bnana.goa.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.PositionListener;
import com.bnana.goa.force.ForceField;

/**
 * Created by Luca on 8/27/2015.
 */
public class PhysicOrganism implements PhysicElement {

    private Array<PhysicElement> elements;

    public PhysicOrganism() {
        this.elements = new Array<PhysicElement>();
    }

    @Override
    public void add(PhysicElement body) {
        elements.add(body);
    }

    @Override
    public void apply(ForceField forceField) {
        for (PhysicElement element : elements){
            element.apply(forceField);
        }
    }

    @Override
    public void addPositionListener(PositionListener positionListener) {

    }

    @Override
    public void notifyPositionChanged() {

    }
}
