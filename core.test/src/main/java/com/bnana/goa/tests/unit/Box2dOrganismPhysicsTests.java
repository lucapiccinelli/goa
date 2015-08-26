package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarytweed.test.DelegateRunningTo;
import com.binarytweed.test.Quarantine;
import com.binarytweed.test.QuarantiningRunner;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.Box2dOrganismPhysics;
import com.bnana.goa.physics.OrganismPhysics;

import org.junit.runner.RunWith;
import org.mockito.internal.stubbing.answers.Returns;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Luca on 8/26/2015.
 */
@RunWith(QuarantiningRunner.class)
@Quarantine({"com.badlogic", "com.binarytweed.libgdx.test", "com.yourgame.package"})
@DelegateRunningTo(LibGdxTestRunner.class)
public class Box2dOrganismPhysicsTests {

    @Test
    public void UsingABox2dOrganismPhysicsShouldAddNewBodyToTheWorld() {
        World world = new World(new Vector2(0, 0f), true);
        OrganismPhysics physics = new Box2dOrganismPhysics(world);

        physics.use(new Point2D.Float(10, 20), 1);

        Assert.assertEquals(world.getBodyCount(), 1);
    }
}
