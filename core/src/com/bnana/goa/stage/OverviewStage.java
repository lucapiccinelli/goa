package com.bnana.goa.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bnana.goa.GameOfAttraction;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.actors.ForceActor;
import com.bnana.goa.actors.OrganismActor;
import com.bnana.goa.actors.WanderingCellActor;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.cell.generator.RandomCellGenerator;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RealisticForceField;
import com.bnana.goa.organism.events.OrganismGrownEvent;
import com.bnana.goa.organism.listeners.OrganismGrowListener;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.rendering.CellRenderer;
import com.bnana.goa.rendering.GeneratedGraphicMultiForceRenderer;
import com.bnana.goa.tween.PercentageManager;
import com.bnana.goa.tween.PercentageManagerAccessor;
import com.bnana.goa.tween.ShapeRendererAccessor;
import com.bnana.goa.tween.Vector2Accessor;
import com.bnana.goa.ui.ForceTypeSwitch;
import com.bnana.goa.utils.Box2dScaleManager;
import com.bnana.goa.utils.Const;

import java.util.Comparator;

import aurelienribon.tweenengine.Tween;

/**
 * Created by Luca on 8/21/2015.
 */
public class OverviewStage extends Stage implements ContactListener, OrganismGrowListener{
    private static final float TIME_STEP = 1 /60f;
    private final int VIEWPORT_WIDTH = Const.VIEWPORT_WIDTH;
    private final int VIEWPORT_HEIGHT = Const.VIEWPORT_HEIGHT;
    private final World world;
    private final Rectangle worldBounds;
    private final Rectangle wanderingWorldBounds;

    private GameOfAttraction game;
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private float accumulator;
    private OrganismActor organism;
    private ForceField forceField;
    private Box2dScaleManager scaleManager;
    private ShapeRenderer shapeRenderer;
    private CellRenderer cellRenderer;
    private ShapeRenderer forceActorShapeRenderer;
    private boolean wanderingCellCreationIsScheduled;
    private ForceTypeSwitch forceTypeSwitch;

    private PhysicElement physicForceSubject;

    public OverviewStage(GameOfAttraction game, ShapeRenderer shapeRenderer){
        super(new ScalingViewport(Scaling.stretch, Const.VIEWPORT_WIDTH, Const.VIEWPORT_HEIGHT, new OrthographicCamera(Const.VIEWPORT_WIDTH, Const.VIEWPORT_HEIGHT)));


        Tween.registerAccessor(ShapeRenderer.class, new ShapeRendererAccessor());
        Tween.registerAccessor(PercentageManager.class, new PercentageManagerAccessor());
        Tween.registerAccessor(Vector2.class, new Vector2Accessor());

        this.game = game;
        this.shapeRenderer = shapeRenderer;
        this.forceActorShapeRenderer = new ShapeRenderer();
        this.world = new World(new Vector2(0f, 0f), true);
        this.world.setContactListener(this);
        accumulator = 0;

        int offset1 = 15;
        int offset2 = 1;
        worldBounds = new Rectangle(offset1, offset1, VIEWPORT_WIDTH - offset1 * 2, VIEWPORT_HEIGHT - offset1 * 2);
        wanderingWorldBounds = new Rectangle(offset2, offset2, VIEWPORT_WIDTH - offset2 * 2, VIEWPORT_HEIGHT - offset2 * 2);

        createCamera();

        createForceFields();
        createOrganism();

        createUi();

        wanderingCellCreationIsScheduled = true;

        Gdx.input.setInputProcessor(this);
    }

    private void createUi() {
        forceTypeSwitch = new ForceTypeSwitch(new Vector2(19, 1), new Vector2(4.5f, 4.5f), this, organism);
        addActor(forceTypeSwitch);
        forceTypeSwitch.setZIndex(100);
    }

    private void createForceFields() {
        forceField = new RealisticForceField();
        ForceActor forceActor = new ForceActor(forceField, new GeneratedGraphicMultiForceRenderer(scaleManager, camera.combined));
        addActor(forceActor);
        forceActor.setZIndex(0);
    }

    private void createWanderingCells() {
        RandomCellGenerator generator = new RandomCellGenerator(null, WanderingCell.MakePrototype(), wanderingWorldBounds);
        WanderingCellActor wanderingCellActor = new WanderingCellActor(world, generator, forceField, getBatch(), shapeRenderer, scaleManager);
        addActor(wanderingCellActor);
        wanderingCellActor.setZIndex(2);
        forceTypeSwitch.setWanderingCellActor(wanderingCellActor);

        getActors().sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                return o1.getZIndex() > o2.getZIndex() ? 1 : o1.getZIndex() < o2.getZIndex() ? -1 : 0;
            }
        });
    }

    private void createOrganism() {
        organism = new OrganismActor(world, worldBounds.x, worldBounds.y, worldBounds.width, worldBounds.height, forceField, scaleManager, shapeRenderer);
        organism.addGrowingListener(this);
        organism.setZIndex(1);
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

    public void setPhysicForceSubject(PhysicElement physicForceSubject) {
        this.physicForceSubject = physicForceSubject;
    }

    @Override
    public void draw(){
        shapeRenderer.setProjectionMatrix(camera.combined);
        forceActorShapeRenderer.setProjectionMatrix(camera.combined);
        super.draw();
        //renderer.render(world, camera.combined);
    }

    @Override
    public void act(float delta){
        super.act(delta);

        if(wanderingCellCreationIsScheduled){
            createWanderingCells();
            wanderingCellCreationIsScheduled = false;
        }
        if(physicForceSubject != null)physicForceSubject.apply(forceField);

        accumulator += delta;

        while (accumulator >= delta){
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void dispose(){
        world.dispose();
        forceActorShapeRenderer.dispose();
        forceTypeSwitch.dispose();
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

    @Override
    public void grownBy(OrganismGrownEvent grownEvent) {
        wanderingCellCreationIsScheduled = true;
    }
}
