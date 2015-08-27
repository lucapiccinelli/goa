package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.bnana.goa.physics.Box2dOrganismPhysics;
import com.bnana.goa.physics.OrganismPhysics;
import com.bnana.goa.utils.wrappers.WorldWrapper;

import org.junit.runner.RunWith;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Luca on 8/26/2015.
 */
public class Box2dOrganismPhysicsTests {

    @Test
    public void UsingABox2dOrganismPhysicsShouldAddNewBodyToTheWorld() {
        World world = WorldWrapper.GetNewWorldZeroGravity();
        OrganismPhysics physics = new Box2dOrganismPhysics(world);

        physics.use(new Point2D.Float(10, 20), 1);

        Assert.assertEquals(WorldWrapper.countBodies(world), 1);
    }
}
