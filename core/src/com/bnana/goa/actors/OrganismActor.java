package com.bnana.goa.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.bnana.goa.actors.factories.CellActorControllerGroupFactory;
import com.bnana.goa.cell.CellController;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RadialForceFieldUpdater;
import com.bnana.goa.organism.StartingOrganism;
import com.bnana.goa.physics.Box2dOrganismPhysics;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.physics.PhysicOrganism;

import java.awt.geom.Rectangle2D;

/**
 * Created by luca.piccinelli on 31/08/2015.
 */
public class OrganismActor extends Group {

    private final StartingOrganism organism;
    private final PhysicElement physicOrganism;
    private ForceField forceField;
    private final RadialForceFieldUpdater fieldUpdater;

    public OrganismActor(World world, float x, float y, float width, float height, ForceField forceField){
        super();
        this.forceField = forceField;

        organism = new StartingOrganism(new Rectangle2D.Float(x, y, width, height), new CellActorControllerGroupFactory(this));
        physicOrganism = new PhysicOrganism();

        Box2dOrganismPhysics organismPhysics = new Box2dOrganismPhysics(world, physicOrganism);
        organism.use(organismPhysics);
        physicOrganism.addPositionListener(organism);

        fieldUpdater = new RadialForceFieldUpdater(forceField);
    }

    @Override
    public void act(float delta){
        fieldUpdater.reset();
        organism.use(fieldUpdater);
        super.act(delta);
    }

    @Override
    public void  draw(Batch batch, float parentAlpha){
        physicOrganism.notifyPositionChanged();
        super.draw(batch, parentAlpha);
    }
}
