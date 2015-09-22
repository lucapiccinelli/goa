package com.bnana.goa.cell;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.actions.WanderingOnTouchAction;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.rendering.CellRenderer;
import com.bnana.goa.utils.EuclideanDistance;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by luca.piccinelli on 01/09/2015.
 */
public class WanderingCell implements EvolvableCell {
    private Vector2 position;
    private float density;
    private final Random random;
    private final EuclideanDistance distance;
    private OffCell evolved;
    private List<CellDestroyListener> destroyListeners;

    private WanderingCell(){
        this(new Vector2(0, 0), 1f);
    }

    public WanderingCell(Vector2 position, float density) {
        this.position = position;
        this.density = density;

        random = new Random();
        distance = new EuclideanDistance(position);

        destroyListeners = new ArrayList<CellDestroyListener>();

        evolved = null;
    }

    @Override
    public OffCell evolve() {
        if(evolved != null) return evolved;

        float r = random.nextFloat();
        evolved =  r >= 0.5 ? new AttractorOffCell(position, density) : new RepulsorOffCell(position, density);
        return evolved;
    }

    @Override
    public void usePosition(PositionConsumer positionConsumer) {
        positionConsumer.use(position);
    }

    @Override
    public float distance(Cell cell) {
        return distance.getDistance(cell);
    }

    @Override
    public Cell prototype(Organism belongingOrganism, Vector2 position, float density) {
        return new WanderingCell(position, density);
    }

    @Override
    public Cell opposite(Vector2 position, float density) {
        return null;
    }

    @Override
    public OffCell getAnOffCell() {
        return evolve();
    }

    @Override
    public OnTouchAction createOnTouchAction(PhysicElement element) {
        return new WanderingOnTouchAction(this, element);
    }

    @Override
    public void addDestroyListener(CellDestroyListener destroyListener) {
        destroyListeners.add(destroyListener);
    }

    @Override
    public void destroy() {
        CellDestroyEvent event = new CellDestroyEvent(this);
        for (CellDestroyListener destroyListener : destroyListeners) {
            destroyListener.notifyDestroy(event);
        }
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public void render(CellRenderer cellRenderer) {
        cellRenderer.renderWanderingCell(this, position, density);
    }

    @Override
    public void updatePosition(PositionChangedEvent positionChangedEvent) {
        position = positionChangedEvent.getPosition();
    }

    public static WanderingCell MakePrototype(){
        return new WanderingCell();
    }

    @Override
    public void use(CellConsumer consumer) {
        consumer.use(this, position, density);
    }
}
