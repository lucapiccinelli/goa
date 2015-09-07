package com.bnana.goa.tests.unit;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bnana.goa.PositionListener;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.PhysicCell;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.physics.PhysicOrganism;
import com.bnana.goa.utils.BodyWrapper;
import com.bnana.goa.utils.wrappers.WorldWrapper;

import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 8/27/2015.
 */
public class PhysicOrganismTest {

    @Test
    public void ApplyingAForceFieldToAPhysicOrganismAllTheBodiesShouldBeAffected(){
        PhysicElement physicOrganism = new PhysicOrganism();

        PhysicElement cell = mock(PhysicElement.class);
        physicOrganism.add(cell);

        ForceField forceField = mock(ForceField.class);
        physicOrganism.apply(forceField);

        verify(cell).apply(same(forceField));
    }

    @Test
    public void WhenNotifyingChangeOfPositionsAllItsElementsShouldBeNotified(){
        PhysicElement physicOrganism = new PhysicOrganism();

        PhysicElement cell = mock(PhysicElement.class);
        physicOrganism.add(cell);
        physicOrganism.notifyPositionChanged();

        verify(cell).notifyPositionChanged();
    }
}
