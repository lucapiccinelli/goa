package com.bnana.goa.physics;

import com.bnana.goa.PositionChangesNotifier;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.force.ForceField;

/**
 * Created by Luca on 8/27/2015.
 */
public interface PhysicElement extends PositionChangesNotifier, PositionConsumer {
    void add(PhysicElement element);

    void apply(ForceField forceField);

    void stop();

    void setAction(OnTouchAction onTouchAction);

    void integrate(PhysicElement physicElement);

    void setParent(PhysicElement parent);
}
