package com.bnana.goa.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.SimpleCellGroup;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.cell.generator.RandomCellGenerator;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.physics.Box2dOrganismPhysics;
import com.bnana.goa.physics.PhysicCell;
import com.bnana.goa.rendering.CellRenderer;

/**
 * Created by Luca on 9/2/2015.
 */
public class WanderingCellActor extends Actor implements CellDestroyListener{

    private final WanderingCell cell;
    private World world;
    private final RandomCellGenerator cellGenerator;
    private final ForceField forceField;
    private final CellRenderer cellRenderer;
    private final PhysicCell physicCell;

    public WanderingCellActor(World world, RandomCellGenerator cellGenerator, ForceField forceField, CellRenderer cellRenderer){
        this.world = world;
        this.cellGenerator = cellGenerator;
        this.forceField = forceField;
        this.cellRenderer = cellRenderer;
        physicCell = new PhysicCell();
        Box2dOrganismPhysics organismPhysics = new Box2dOrganismPhysics(world, physicCell);

        cell = (WanderingCell)cellGenerator.generate();
        cell.use(organismPhysics);
        cell.addDestroyListener(this);
    }

    @Override
    public void act(float delta){
        physicCell.apply(forceField);
        super.act(delta);
    }

    @Override
    public void  draw(Batch batch, float parentAlpha){
        physicCell.notifyPositionChanged();
        cell.use(cellRenderer);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void notifyDestroy(CellDestroyEvent cellDestroyEvent) {
        remove();
    }
}
