package com.bnana.goa.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bnana.goa.GameOfAttraction;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.actors.ForceActor;
import com.bnana.goa.actors.OrganismActor;
import com.bnana.goa.actors.WanderingCellActor;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.cell.generator.RandomCellGenerator;
import com.bnana.goa.force.RadialForceField;
import com.bnana.goa.rendering.CellRenderer;
import com.bnana.goa.rendering.FlatGeneratedGraphicCellRenderer;
import com.bnana.goa.rendering.GeneratedGraphicForceRenderer;
import com.bnana.goa.utils.Box2dScaleManager;
import com.bnana.goa.utils.Const;

import java.awt.geom.Rectangle2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class OverviewStage extends Stage implements ContactListener{
    private static final float TIME_STEP = 1 /60f;
    private final int VIEWPORT_WIDTH = Const.VIEWPORT_WIDTH;
    private final int VIEWPORT_HEIGHT = Const.VIEWPORT_HEIGHT;
    private final World world;
    private final Rectangle worldBounds;

    private GameOfAttraction game;
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private float accumulator;
    private OrganismActor organism;
    private RadialForceField forceField;
    private Box2dScaleManager scaleManager;
    private ShapeRenderer shapeRenderer;
    private CellRenderer cellRenderer;

    public OverviewStage(GameOfAttraction game, ShapeRenderer shapeRenderer) {
        super(new ScalingViewport(Scaling.stretch, Const.VIEWPORT_WIDTH, Const.VIEWPORT_HEIGHT, new OrthographicCamera(Const.VIEWPORT_WIDTH, Const.VIEWPORT_HEIGHT)));

        this.game = game;
        this.shapeRenderer = shapeRenderer;
        this.world = new World(new Vector2(0f, 0f), true);
        this.world.setContactListener(this);
        accumulator = 0;

        int offset = 10;
        worldBounds = new Rectangle(offset, offset, VIEWPORT_WIDTH - offset, VIEWPORT_HEIGHT - offset);

        createCamera();

        createForceFields();
        cellRenderer = new FlatGeneratedGraphicCellRenderer(shapeRenderer, scaleManager);
        createOrganism();
        createWanderingCells();

        Gdx.input.setInputProcessor(this);
    }

    private void createForceFields() {
        forceField = new RadialForceField();
        ForceActor forceActor = new ForceActor(forceField, new GeneratedGraphicForceRenderer(shapeRenderer, scaleManager));
        addActor(forceActor);
    }

    private void createWanderingCells() {
        RandomCellGenerator generator = new RandomCellGenerator(null, WanderingCell.MakePrototype(), worldBounds);
        addActor(new WanderingCellActor(world, generator, forceField, cellRenderer, getBatch()));
        addActor(new WanderingCellActor(world, generator, forceField, cellRenderer, getBatch()));
        addActor(new WanderingCellActor(world, generator, forceField, cellRenderer, getBatch()));
    }

    private void createOrganism() {
        organism = new OrganismActor(world, worldBounds.x, worldBounds.y, worldBounds.width, worldBounds.height, forceField, scaleManager, cellRenderer);
        addActor(organism);
    }

    private void createCamera() {
        this.renderer = new Box2DDebugRenderer();
        this.camera = (OrthographicCamera) getCamera();
        //camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();

        scaleManager = new Box2dScaleManager(camera, Const.APP_WIDTH, Const.APP_HEIGHT);
    }

    @Override
    public void draw(){
        shapeRenderer.setProjectionMatrix(camera.combined);
        super.draw();
        //renderer.render(world, camera.combined);
    }

    @Override
    public void act(float delta){
        super.act(delta);

        accumulator += delta;

        while (accumulator >= delta){
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        if(Gdx.input.isTouched()){
            System.out.println(Gdx.input.getX() + " - " + Gdx.input.getY());
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
