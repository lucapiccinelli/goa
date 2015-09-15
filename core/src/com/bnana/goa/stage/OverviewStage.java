package com.bnana.goa.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bnana.goa.GameOfAttraction;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.actors.ActorsFactoryCellGroup;
import com.bnana.goa.actors.OrganismActor;
import com.bnana.goa.actors.WanderingCellActor;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.cell.generator.RandomCellGenerator;
import com.bnana.goa.force.RadialForceField;

import java.awt.geom.Rectangle2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class OverviewStage extends Stage implements ContactListener{
    private static final float TIME_STEP = 1 /60f;
    private final int VIEWPORT_WIDTH = 80;
    private final int VIEWPORT_HEIGHT = 52;
    private final World world;
    private final Rectangle2D.Float worldBounds;

    private GameOfAttraction game;
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private float accumulator;
    private OrganismActor organism;
    private RadialForceField forceField;

    public OverviewStage(GameOfAttraction game) {
        this.game = game;
        this.world = new World(new Vector2(0f, 0f), true);
        this.world.setContactListener(this);
        accumulator = 0;

        int offset = 10;
        worldBounds = new Rectangle2D.Float(offset, offset, VIEWPORT_WIDTH - offset, VIEWPORT_HEIGHT - offset);

        createForceFields();
        createCamera();
        createOrganism();

        createWanderingCells();
    }

    private void createForceFields() {
        forceField = new RadialForceField();
    }

    private void createWanderingCells() {
        RandomCellGenerator generator = new RandomCellGenerator(null, WanderingCell.MakePrototype(), worldBounds);
        addActor(new WanderingCellActor(world, generator, forceField));
        addActor(new WanderingCellActor(world, generator, forceField));
        addActor(new WanderingCellActor(world, generator, forceField));
    }

    private void createOrganism() {
        ActorsFactoryCellGroup organismInnerActorsFactory = new ActorsFactoryCellGroup(this);

        organism = new OrganismActor(world, worldBounds.x, worldBounds.y, worldBounds.width, worldBounds.height, forceField, organismInnerActorsFactory);
        addActor(organism);

        organismInnerActorsFactory.turnOn();
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

    @Override
    public void beginContact(Contact contact) {
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        OnTouchAction actionA = (OnTouchAction)bodyA.getUserData();
        OnTouchAction actionB = (OnTouchAction)bodyB.getUserData();

        actionA.act(actionB);
        actionB.act(actionA);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
