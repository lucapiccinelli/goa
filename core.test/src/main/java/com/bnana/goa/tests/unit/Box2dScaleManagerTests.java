package com.bnana.goa.tests.unit;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bnana.goa.utils.Box2dScaleManager;
import com.bnana.goa.utils.wrappers.CameraWrapper;
import com.bnana.goa.utils.ScaleManager;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;

/**
 * Created by luca.piccinelli on 21/09/2015.
 */
public class Box2dScaleManagerTests {
    @DataProvider
    public Object[][] scaleValues(){
        return new Object[][]{
                {80, 48, 100},
        };
    }

    @Test(dataProvider = "scaleValues")
    public void ItMustHandleScalingBetweenViewportAndBox2dUnitMetrics(int viewportWidth, int viewportHeight, float expected){
        OrthographicCamera camera = CameraWrapper.getCamera(viewportWidth, viewportHeight);
        ScaleManager sm = new Box2dScaleManager(camera, 800, 480);

        Assert.assertEquals(sm.s(10), expected);
    }
}
