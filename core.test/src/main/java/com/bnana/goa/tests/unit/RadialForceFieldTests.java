package com.bnana.goa.tests.unit;

import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RadialForceField;
import com.bnana.goa.utils.BodyWrapper;
import com.bnana.goa.utils.EuclideanDistance;
import com.bnana.goa.utils.wrappers.WorldWrapper;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/27/2015.
 */
public class RadialForceFieldTests {
    @Test
    public void ApplyingARadialForceFieldToABodyItsPositionShouldChange(){
        ForceField radialForceField = new RadialForceField(new Point2D.Float(3, 3), 3f);

        Point2D.Float startingPosition = new Point2D.Float(2, 2);
        Body body = BodyWrapper.getNewCircleBody(startingPosition, 1);
        radialForceField.apply(body);

        WorldWrapper.StepTheWorld(body);

        Point2D.Float finalPosition = BodyWrapper.getBodyPosition(body);
        Assert.assertNotEquals(finalPosition, startingPosition);
    }

    @Test
    public void ApplyingANegativeRadialForceFieldToABodyItsFinalPositionDistanceShouldBeMoreFarFrom0(){
        ForceField radialForceField = new RadialForceField(new Point2D.Float(3, 3), -3f);

        Point2D.Float startingPosition = new Point2D.Float(2, 2);
        Body body = BodyWrapper.getNewCircleBody(startingPosition, 1);
        radialForceField.apply(body);

        WorldWrapper.StepTheWorld(body);

        Point2D.Float finalPosition = BodyWrapper.getBodyPosition(body);

        EuclideanDistance startDistance = new EuclideanDistance(new Point2D.Float(0, 0));
        startDistance.use(startingPosition);

        EuclideanDistance finalDistance = new EuclideanDistance(new Point2D.Float(0, 0));
        finalDistance.use(finalPosition);

        MatcherAssert.assertThat(finalDistance.getDistance(), Matchers.greaterThan(startDistance.getDistance()));
    }
}
