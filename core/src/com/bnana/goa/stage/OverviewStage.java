package com.bnana.goa.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bnana.goa.GameOfAttraction;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RadialForceField;
import com.bnana.goa.organism.StartingOrganism;
import com.bnana.goa.physics.Box2dOrganismPhysics;
import com.bnana.goa.physics.PhysicOrganism;
import com.bnana.goa.physics.PhysicOrganismImpl;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class OverviewStage extends Stage {
    private static final float TIME_STEP = 1 / 300f;
    private final int VIEWPORT_WIDTH = 80;
    private final int VIEWPORT_HEIGHT = 52;
    private final World world;

    private GameOfAttraction game;
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private float accumulator;

    public OverviewStage(GameOfAttraction game) {
        this.game = game;
        this.world = new World(new Vector2(0, 0), true);
        accumulator = 0;

        createCamera();

        createOrganism();
    }

    private void createOrganism() {
        StartingOrganism organism = new StartingOrganism(new Rectangle2D.Float(5, 5, VIEWPORT_WIDTH - 5, VIEWPORT_HEIGHT - 5));

        PhysicOrganism physicOrganism = new PhysicOrganismImpl();
        Box2dOrganismPhysics organismPhysics = new Box2dOrganismPhysics(world, physicOrganism);
        organism.groupAllCells().use(organismPhysics);

        ForceField force = new RadialForceField(new Point2D.Float(40, 26), 1000);
        physicOrganism.apply(force);
    }

    private void createCamera() {
        this.renderer = new Box2DDebugRenderer();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    @Override
    public void draw(){
        super.draw();
        renderer.render(world, camera.combined);
    }

    @Override
    public void act(float delta){
        super.act(delta);

        accumulator += delta;

        while (accumulator >= delta){
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void dispose(){
        world.dispose();
        super.dispose();
    }
}
