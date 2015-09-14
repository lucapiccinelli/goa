package com.bnana.goa.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.PositionChangesNotifier;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.OnCellOnTouchAction;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.force.ForceField;

/**
 * Created by Luca on 8/27/2015.
 */
public interface PhysicElement extends PositionChangesNotifier {
    void add(PhysicElement element);

    void apply(ForceField forceField);

    void stop();

    void setAction(OnTouchAction onTouchAction);
}
