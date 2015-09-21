package com.bnana.goa.tests.unit;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bnana.goa.GameOfAttraction;
import com.bnana.goa.stage.OverviewStage;
import com.bnana.goa.utils.Box2dScaleManager;
import com.bnana.goa.utils.wrappers.CameraWrapper;
import com.bnana.goa.utils.ScaleManager;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by luca.piccinelli on 21/09/2015.
 */
public class Box2dScaleManagerTests {
    @DataProvider
    public Object[][] scaleValues(){
        return new Object[][]{
                {80f, 48f, 100f},
        };
    }

    @Test(dataProvider = "scaleValues")
    public void ItMustHandleScalingBetweenViewportAndBox2dUnitMetrics(float viewportWidth, float viewportHeight, float expected){
        Camera camera = mock(Camera.class);
        camera.viewportHeight = viewportHeight;
        camera.viewportWidth = viewportWidth;

        ScaleManager sm = new Box2dScaleManager(camera, 800, 480);

        Assert.assertEquals(sm.s(10), expected);
    }
}
