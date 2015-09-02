package com.bnana.goa.tests.unit;

import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.PhysicCell;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.utils.BodyWrapper;

import org.testng.annotations.Test;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 9/2/2015.
 */
public class PhysicCellTests {
    @Test
    public void APhysicCellCanReferToOnlyABodyAtTime(){
        Body body1 = BodyWrapper.getNewBody();
        PhysicElement physicCell = new PhysicCell(body1);

        Body body2 = BodyWrapper.getNewBody();
        physicCell.add(body2);

        ForceField field = mock(ForceField.class);

        physicCell.apply(field);
        verify(field).apply(same(body2));
    }
}
