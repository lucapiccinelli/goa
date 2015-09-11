package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.PositionListener;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.PhysicCell;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.utils.BodyWrapper;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Matchers.any;
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
        physicCell.add(new PhysicCell(body2));

        ForceField field = mock(ForceField.class);

        physicCell.apply(field);
        verify(field).apply(same(body2));
    }

    @Test
    public void APhysicellShouldNotifyAllItsListenerWhenItsPositionChanges(){
        Body body1 = BodyWrapper.getNewBody();
        PhysicElement physicCell = new PhysicCell(body1);

        PositionListener positionListener1 = mock(PositionListener.class);
        PositionListener positionListener2 = mock(PositionListener.class);

        physicCell.addPositionListener(positionListener1);
        physicCell.addPositionListener(positionListener2);
        physicCell.notifyPositionChanged();

        verify(positionListener1).updatePosition(any(PositionChangedEvent.class));
        verify(positionListener2).updatePosition(any(PositionChangedEvent.class));
    }

    @Test
    public void APhysicellShouldNotifyItsElementsWhenItsPositionChanges(){
        PhysicElement physicCell = new PhysicCell();
        PhysicElement physicCell2 = mock(PhysicCell.class);

        physicCell.add(physicCell2);
        physicCell.notifyPositionChanged();

        verify(physicCell2).notifyPositionChanged();
    }

    @Test
    public void AfterBeingStoppedItsLinearVelocityShouldBe0(){
        Body body = BodyWrapper.getNewBodyWithLinearVelocityNotZero();

        PhysicElement physicCell = new PhysicCell(body);
        physicCell.stop();

        Assert.assertEquals(body.getLinearVelocity(), new Vector2(0, 0));
    }
}
