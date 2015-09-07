package com.bnana.goa.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.SimpleCellGroup;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.cell.generator.RandomCellGenerator;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.Box2dOrganismPhysics;
import com.bnana.goa.physics.PhysicCell;

/**
 * Created by Luca on 9/2/2015.
 */
public class WanderingCellActor extends Actor{

    private final Cell cell;
    private World world;
    private final ForceField forceField;
    private final PhysicCell physicCell;

    public WanderingCellActor(World world, RandomCellGenerator cellGenerator, ForceField forceField){
        this.world = world;
        this.forceField = forceField;
        physicCell = new PhysicCell();
        Box2dOrganismPhysics organismPhysics = new Box2dOrganismPhysics(world, physicCell);

        cell = cellGenerator.generate();
        cell.getAnOffCell().turnOn().use(organismPhysics);
    }

    @Override
    public void act(float delta){
        physicCell.apply(forceField);
        super.act(delta);
    }

    @Override
    public void  draw(Batch batch, float parentAlpha){
        physicCell.notifyPositionChanged();
        super.draw(batch, parentAlpha);
    }
}
