package com.bnana.goa.cell;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.SwitchableCellOnTouchAction;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.exceptions.InvalidIntegrateRequestException;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.rendering.CellRenderer;
import com.bnana.goa.utils.EuclideanDistance;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Luca on 8/21/2015.
 */
public class RepulsorOffCell implements OffCell {
    public static final float DEFAULT_DENSITY = 1;
    private final EuclideanDistance distanceCalculator;

    private OnCell onCell;
    private Vector2 position;
    private float density;
    private Organism belongingOrganism;
    private List<CellDestroyListener> destroyListeners;
    private List<PositionListener> positionListeners;

    public RepulsorOffCell (Organism belongingOrganism, Vector2 position, float density) {
        this.position = position;
        this.density = Math.abs(density);
        this.belongingOrganism = belongingOrganism;

        distanceCalculator = new EuclideanDistance(position);

        destroyListeners = new ArrayList<CellDestroyListener>();
        positionListeners = new ArrayList<PositionListener>();
    }

    public void setBelongingOrganism(Organism belongingOrganism) {
        this.belongingOrganism = belongingOrganism;
    }

    public RepulsorOffCell (Vector2 position, float density) {
        this(null, position, density);
    }

    public RepulsorOffCell (Organism belongingOrganism, Vector2 position) {
        this(belongingOrganism, position, 1);
    }

    private RepulsorOffCell () {
        this(null, new Vector2(0, 0), 1);
    }

    @Override
    public OnCell turnOn() {
        OnCell tmpOnCell = makeCell();
        return tmpOnCell;
    }

    @Override
    public void growOrganism(Organism organism) {
        organism.growRepulsor(this);
    }

    @Override
    public Cell prototype(Organism belongingOrganism, Vector2 position, float density) {
        return new RepulsorOffCell(belongingOrganism, position, density);
    }


    @Override
    public OffCell opposite(Vector2 position, float density) {
        return new AttractorOffCell(belongingOrganism, position, density);
    }

    @Override
    public OffCell getAnOffCell() {
        return this;
    }

    @Override
    public OnTouchAction createOnTouchAction(PhysicElement element) {
        return new SwitchableCellOnTouchAction(this, element);
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

    private OnCell makeCell() {
        if (onCell == null) onCell = new RepulsorOnCell(this, position, density, belongingOrganism);
        return onCell;
    }

    public static OffCell MakeProtype(){
        return new RepulsorOffCell();
    }

    @Override
    public void usePosition(PositionConsumer positionConsumer) {
        positionConsumer.use(position, density);
    }

    @Override
    public float distance(Cell cell) {
        return distanceCalculator.getDistance(cell);
    }

    @Override
    public void updatePosition(PositionChangedEvent positionChangedEvent) {
        this.position = positionChangedEvent.getPosition();
        if(onCell != null) onCell.setPosition(position);
        for(PositionListener positionListener : positionListeners){
            positionListener.updatePosition(positionChangedEvent);
        }
    }

    @Override
    public SwitchableCell sswitch() {
        return turnOn();
    }

    @Override
    public void integrate(OffCell aNewCell) {
        if(belongingOrganism == null)
            throw new InvalidIntegrateRequestException("The RepulsorOffCell you requested to integrate on doesn't belong to any body");

        aNewCell.growOrganism(belongingOrganism);
    }

    @Override
    public void use(CellConsumer consumer) {
        consumer.useItOff(this, position, density);
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public void render(CellRenderer cellRenderer) {
        cellRenderer.renderRepulsorOffCell(this, position, density);
    }

    @Override
    public void addPositionListener(PositionListener positionListener) {
        positionListeners.add(positionListener);
    }
}
