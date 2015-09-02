package com.bnana.goa.tests.unit;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.physics.Box2dOrganismPhysics;
import com.bnana.goa.physics.OrganismPhysics;
import com.bnana.goa.utils.wrappers.WorldWrapper;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 8/26/2015.
 */
public class Box2dOrganismPhysicsTests {

    @Test
    public void UsingABox2dOrganismPhysicsShouldAddNewBodyToTheWorld() {
        World world = WorldWrapper.GetNewWorldZeroGravity();
        PhysicElement physicElement = mock(PhysicElement.class);
        OrganismPhysics physics = new Box2dOrganismPhysics(world, physicElement);

        physics.use(new Point2D.Float(10, 20), 1);

        Assert.assertEquals(WorldWrapper.countBodies(world), 1);
    }

    @Test
    public void AfterUsingABox2dOrganismPhysicsAPhysicOrganismMustBeGrown(){
        World world = WorldWrapper.GetNewWorldZeroGravity();
        PhysicElement physicElement = mock(PhysicElement.class);
        OrganismPhysics physics = new Box2dOrganismPhysics(world, physicElement);

        physics.use(new Point2D.Float(10, 20), 1);

        verify(physicElement, times(1)).add(any(Body.class));
    }
}
