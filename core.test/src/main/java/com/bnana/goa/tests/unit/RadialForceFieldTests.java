package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.exceptions.GoaArgumentException;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RadialForceField;
import com.bnana.goa.force.functions.ExponentialValueAtDistanceFunction;
import com.bnana.goa.utils.BodyWrapper;
import com.bnana.goa.utils.EuclideanDistance;
import com.bnana.goa.utils.wrappers.WorldWrapper;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created by Luca on 8/27/2015.
 */
public class RadialForceFieldTests {
    @Test
    public void ApplyingARadialForceFieldToABodyItsPositionShouldChange(){
        ForceField radialForceField = new RadialForceField(new Vector2(3, 3), 3f, new ExponentialValueAtDistanceFunction());

        Vector2 startingPosition = new Vector2(2, 2);
        Body body = BodyWrapper.getNewCircleBody(startingPosition, 1);
        radialForceField.apply(body);

        WorldWrapper.StepTheWorld(body);

        Vector2 finalPosition = BodyWrapper.getBodyPosition(body);
        Assert.assertNotEquals(finalPosition, startingPosition);
    }

    @Test
    public void ApplyingANegativeRadialForceFieldToABodyItsFinalPositionDistanceShouldBeFartherFrom0(){
        ForceField radialForceField = new RadialForceField(new Vector2(5, 5), -3f, new ExponentialValueAtDistanceFunction());

        Vector2 startingPosition = new Vector2(2, 2);
        Body body = BodyWrapper.getNewCircleBody(startingPosition, 1);
        radialForceField.apply(body);

        WorldWrapper.StepTheWorld(body);

        Vector2 finalPosition = BodyWrapper.getBodyPosition(body);

        EuclideanDistance startDistance = new EuclideanDistance(new Vector2(0, 0));
        startDistance.use(startingPosition, 0);

        EuclideanDistance finalDistance = new EuclideanDistance(new Vector2(0, 0));
        finalDistance.use(finalPosition, 0);

        assertThat(finalDistance.getDistance(), greaterThan(startDistance.getDistance()));
    }

    @Test
    public void ApplyingAPositiveRadialForceFieldToABodyItsFinalPositionDistanceShouldBeCloserTo0(){
        ForceField radialForceField = new RadialForceField(new Vector2(3, 3), 3f, new ExponentialValueAtDistanceFunction());

        Vector2 startingPosition = new Vector2(2, 2);
        Body body = BodyWrapper.getNewCircleBody(startingPosition, 1);
        radialForceField.apply(body);

        WorldWrapper.StepTheWorld(body);

        Vector2 finalPosition = BodyWrapper.getBodyPosition(body);

        EuclideanDistance startDistance = new EuclideanDistance(new Vector2(0, 0));
        startDistance.use(startingPosition, 0);

        EuclideanDistance finalDistance = new EuclideanDistance(new Vector2(0, 0));
        finalDistance.use(finalPosition, 0);

        assertThat(finalDistance.getDistance(), lessThan(startDistance.getDistance()));
    }

    @Test(expectedExceptions = GoaArgumentException.class)
    public void UpdatingARadialForceFieldWithAZeroLengthPositionArrayShouldThrow(){
        ForceField radialForceField = new RadialForceField();
        radialForceField.update(new Array<Vector2>(), new Array<Float>(1));
    }

    @Test(expectedExceptions = GoaArgumentException.class)
    public void UpdatingARadialForceFieldWithAZeroLengthMAgnitudeArrayShouldThrow(){
        ForceField radialForceField = new RadialForceField();
        radialForceField.update(new Array<Vector2>(1), new Array<Float>(0));
    }

    @Test
    public void YouCanMeasureTheForceAtACertainDistance(){
        ForceField radialForceField = new RadialForceField();
        radialForceField.update(new Array<Vector2>(new Vector2[]{new Vector2()}), new Array<Float>(new Float[]{3f}));

        Assert.assertEquals(radialForceField.valueAtDistance(10f), 3f);
    }

    @Test
    public void YouCanMeasureTheForceAtACertainPoint(){
        ForceField radialForceField = new RadialForceField();
        radialForceField.update(new Array<Vector2>(new Vector2[]{new Vector2()}), new Array<Float>(new Float[]{3f}));

        Assert.assertEquals(radialForceField.valueAtPoint(new Vector2(0, 10)), 3f);
    }

    @DataProvider
    public Object[][] directions(){
        return new Object[][]{
                {new Vector2(0,  10), new Vector2(0, 1)},
                {new Vector2(10,  0), new Vector2(1, 0)},
                {new Vector2(0, -10), new Vector2(0, -1)},
                {new Vector2(-10, 0), new Vector2(-1, 0)},
                {new Vector2(10, 5), new Vector2(0.8944272f, 0.4472136f)},
        };
    }

    @Test(dataProvider = "directions")
    public void YouCanAskToAForceWhereItPushesAtACertainPoint(Vector2 point, Vector2 expected){
        ForceField radialForceField = new RadialForceField();
        radialForceField.update(new Array<Vector2>(new Vector2[]{new Vector2()}), new Array<Float>(new Float[]{3f}));

        Assert.assertEquals(radialForceField.direction(point), expected);
    }
}
