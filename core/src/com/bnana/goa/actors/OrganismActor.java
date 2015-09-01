package com.bnana.goa.actors;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RadialForceField;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.organism.StartingOrganism;
import com.bnana.goa.physics.Box2dOrganismPhysics;
import com.bnana.goa.physics.PhysicOrganism;
import com.bnana.goa.physics.PhysicOrganismImpl;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by luca.piccinelli on 31/08/2015.
 */
public class OrganismActor extends Actor{

    private final StartingOrganism organism;
    private final PhysicOrganism physicOrganism;
//    private final ForceField force;

    public OrganismActor(World world, float x, float y, float width, float height){
        super();

        organism = new StartingOrganism(new Rectangle2D.Float(x, y, width, height));
        physicOrganism = new PhysicOrganismImpl();

        Box2dOrganismPhysics organismPhysics = new Box2dOrganismPhysics(world, physicOrganism);
        organism.groupAllCells().use(organismPhysics);

//        force = new RadialForceField(new Point2D.Float(40, 26), 100);
    }

    @Override
    public void act(float delta){
        super.act(delta);
//        physicOrganism.apply(force);
    }
}
