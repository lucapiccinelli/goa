package com.bnana.goa.tests.unit;

import com.badlogic.gdx.physics.box2d.Body;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RadialForceField;
import com.bnana.goa.utils.BodyWrapper;

import org.testng.annotations.Test;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/27/2015.
 */
public class RadialForceFieldTests {
    @Test
    public void ApplyingARadialForceFieldToABodyItsPositionShouldChange(){
        ForceField radialForceField = new RadialForceField();

        Body body = BodyWrapper.getNewCircleBody(new Point2D.Float(2, 2), 1);
    }
}
