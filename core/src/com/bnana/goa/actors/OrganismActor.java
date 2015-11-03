package com.bnana.goa.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.bnana.goa.actors.factories.CellActorControllerGroupFactory;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RealisticForceFieldUpdater;
import com.bnana.goa.organism.StartingOrganism;
import com.bnana.goa.organism.listeners.OrganismGrowListener;
import com.bnana.goa.physics.Box2dOrganismPhysics;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.physics.PhysicOrganism;
import com.bnana.goa.rendering.CellForceFieldRenderer;
import com.bnana.goa.rendering.FlatGeneratedGraphicCellRenderer;
import com.bnana.goa.rendering.ForceRenderer;
import com.bnana.goa.rendering.GeneratedGraphicMultiForceRenderer;
import com.bnana.goa.stage.OverviewStage;
import com.bnana.goa.utils.ScaleManager;

/**
 * Created by luca.piccinelli on 31/08/2015.
 */
public class OrganismActor extends Group implements ForceSubject {

    private final StartingOrganism organism;
    private final PhysicElement physicOrganism;
    private final World world;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final GeneratedGraphicMultiForceRenderer forceFieldRenderer;
    private final CellConsumer forceFieldArrowRenderer;
    private ForceField forceField;
    private final ScaleManager sm;
    private final RealisticForceFieldUpdater fieldUpdater;
    private CellConsumer cellRenderer;

    public OrganismActor(World world, float x, float y, float width, float height, ForceField forceField, ScaleManager sm, ShapeRenderer shapeRenderer, CellConsumer forceFieldArrowRenderer){
        super();
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.forceField = forceField;
        this.sm = sm;
        this.cellRenderer = new FlatGeneratedGraphicCellRenderer(shapeRenderer, sm);
        this.forceFieldRenderer = new GeneratedGraphicMultiForceRenderer(sm, shapeRenderer.getProjectionMatrix());
        this.forceFieldArrowRenderer = forceFieldArrowRenderer;

        organism = new StartingOrganism(new Rectangle(x, y, width, height), new CellActorControllerGroupFactory(this, sm));
        physicOrganism = new PhysicOrganism();

        Box2dOrganismPhysics organismPhysics = new Box2dOrganismPhysics(world, physicOrganism, 0.7f);
        organism.use(organismPhysics);
        physicOrganism.addPositionListener(organism);

        fieldUpdater = new RealisticForceFieldUpdater(forceField);
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
        batch.end();

        organism.use(forceFieldRenderer);
        organism.use(cellRenderer);
        batch.begin();
        organism.use(forceFieldArrowRenderer);
    }

    public void addGrowingListener(OrganismGrowListener organismGrowListener) {
        organism.addGrowingListeners(organismGrowListener);
    }

    @Override
    public void setAsForceSubject(OverviewStage stage) {
        stage.setPhysicForceSubject(physicOrganism);
    }

    @Override
    public void setForceField(ForceField forceField) {
        this.forceField = forceField;
        fieldUpdater.setField(forceField);
    }
}
