package com.bnana.goa.tests.unit;

import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.PhysicCell;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.physics.PhysicOrganism;
import com.bnana.goa.utils.BodyWrapper;

import org.testng.annotations.Test;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 8/27/2015.
 */
public class PhysicOrganismTest {

    @Test
    public void ApplyingAForceFieldToAPhysicOrganismAllTheBodiesShouldBeAffected(){
        PhysicElement physicElement = new PhysicOrganism();

        PhysicElement cell = mock(PhysicElement.class);
        physicElement.add(cell);

        ForceField forceField = mock(ForceField.class);
        physicElement.apply(forceField);

        verify(cell).apply(same(forceField));
    }
}
