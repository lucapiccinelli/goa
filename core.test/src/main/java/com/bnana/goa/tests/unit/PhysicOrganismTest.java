package com.bnana.goa.tests.unit;

import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.Membrane;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.physics.PhysicOrganism;

import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 8/27/2015.
 */
public class PhysicOrganismTest {

    @Test
    public void ApplyingAForceFieldToAPhysicOrganismAllTheBodiesShouldBeAffected(){
        PhysicElement physicOrganism = new PhysicOrganism(mock(Membrane.class));

        PhysicElement cell = mock(PhysicElement.class);
        physicOrganism.add(cell);

        ForceField forceField = mock(ForceField.class);
        physicOrganism.apply(forceField);

        verify(cell).apply(same(forceField));
    }

    @Test
    public void WhenNotifyingChangeOfPositionsAllItsElementsShouldBeNotified(){
        PhysicElement physicOrganism = new PhysicOrganism(mock(Membrane.class));

        PhysicElement cell = mock(PhysicElement.class);
        physicOrganism.add(cell);
        physicOrganism.notifyPositionChanged();

        verify(cell).notifyPositionChanged();
    }

    @Test
    public void WhenAddingANewElementToTheBodyItShouldBeIntegratedIntoTheMembrane(){
        Membrane membrane = mock(Membrane.class);

        PhysicElement physicOrganism = new PhysicOrganism(membrane);
        PhysicElement element = mock(PhysicElement.class);
        physicOrganism.add(element);

        verify(membrane).integrate(same(element));
    }
}
