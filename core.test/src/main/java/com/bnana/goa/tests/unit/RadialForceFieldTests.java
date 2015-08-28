package com.bnana.goa.tests.unit;

import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RadialForceField;
import com.bnana.goa.utils.BodyWrapper;
import com.bnana.goa.utils.wrappers.WorldWrapper;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/27/2015.
 */
public class RadialForceFieldTests {
    @Test
    public void ApplyingARadialForceFieldToABodyItsPositionShouldChange(){
        ForceField radialForceField = new RadialForceField();

        Point2D.Float startingPosition = new Point2D.Float(2, 2);
        Body body = BodyWrapper.getNewCircleBody(startingPosition, 1);
        radialForceField.apply(body);

        WorldWrapper.StepTheWorld(body);

        Assert.assertNotEquals(BodyWrapper.getBodyPosition(body), startingPosition);
    }
}
