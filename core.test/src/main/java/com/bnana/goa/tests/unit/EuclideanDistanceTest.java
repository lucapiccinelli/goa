package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.PositionConsumer;
import com.bnana.goa.utils.EuclideanDistance;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class EuclideanDistanceTest {
    @DataProvider
    public Object[][] pointProvider(){
        return new Object[][]{
                {new Vector2(1, 1), 1.4142135f},
                {new Vector2(2, 2), 2.828427f},
                {new Vector2(0, 5), 5f},
        };
    }

    @Test(dataProvider = "pointProvider")
    public void GivenTwoPointsTheirDistanceShouldBe(Vector2 x2, float result){
        Vector2 x1 = new Vector2(0, 0);
        EuclideanDistance distance = new EuclideanDistance(x1);

        distance.use(x2);
        Assert.assertEquals(distance.getDistance(), result);
    }
}
