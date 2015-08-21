package com.bnana.goa.tests.unit;

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
                {new Point2D.Float(1, 1), 1.4142135f},
                {new Point2D.Float(2, 2), 2.828427f},
                {new Point2D.Float(0, 5), 5f},
        };
    }

    @Test(dataProvider = "pointProvider")
    public void GivenTwoPointsTheirDistanceShouldBe(Point2D.Float x2, float result){
        Point2D.Float x1 = new Point2D.Float(0, 0);
        EuclideanDistance distance = new EuclideanDistance(x1);

        distance.use(x2);
        Assert.assertEquals(distance.getDistance(), result);
    }
}
