package com.bnana.goa.tests.unit;

import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.PhysicOrganism;
import com.bnana.goa.physics.PhysicOrganismImpl;
import com.bnana.goa.utils.BodyWrapper;
import com.bnana.goa.utils.wrappers.WorldWrapper;

import org.testng.annotations.Test;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 8/27/2015.
 */
public class PhysicOrganismImplTest {

    @Test
    public void ApplyingAForceFieldToAPhysicOrganismAllTheBodiesShouldBeAffected(){
        PhysicOrganism physicOrganism = new PhysicOrganismImpl();

        Body body = BodyWrapper.getNewBody();
        physicOrganism.add(body);

        ForceField forceField = mock(ForceField.class);
        physicOrganism.apply(forceField);

        verify(forceField).apply(same(body));
    }
}
