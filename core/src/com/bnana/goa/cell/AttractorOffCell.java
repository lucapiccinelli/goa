package com.bnana.goa.cell;

import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.utils.EuclideanDistance;

import java.awt.Component;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 8/21/2015.
 */
public class AttractorOffCell implements OffCell{

    public static final float DEFAULT_DENSITY = 1;
    private final EuclideanDistance distanceCalculator;

    private OnCell onCell;
    private Point2D.Float position;
    private float density;
    private Organism belongingOrganism;
    private List<CellDestroyListener> destroyListeners;

    public AttractorOffCell(Organism belongingOrganism, Point2D.Float position, float density) {
        this.position = position;
        this.density = Math.abs(density);
        this.belongingOrganism = belongingOrganism;

        distanceCalculator = new EuclideanDistance(position);

        destroyListeners = new ArrayList<CellDestroyListener>();
    }

    public AttractorOffCell(Point2D.Float position, float density) {
        this(null, position, density);
    }

    public AttractorOffCell(Organism belongingOrganism, Point2D.Float position) {
        this(belongingOrganism, position, 1);
    }

    private AttractorOffCell() {
        this(null, new Point2D.Float(0, 0), DEFAULT_DENSITY);
    }

    @Override
    public OnCell turnOn() {
        return makeCell();
    }

    @Override
    public void growOrganism(Organism organism) {
        belongingOrganism = organism;
        organism.growAttractors(this);
    }

    @Override
    public Cell prototype(Organism belongingOrganism, Point2D.Float position, float density) {
        return new AttractorOffCell(belongingOrganism, position, density);
    }

    @Override
    public OffCell opposite(Point2D.Float position, float density) {
        return new RepulsorOffCell(belongingOrganism, position, density);
    }

    @Override
    public OffCell getAnOffCell() {
        return this;
    }

    @Override
    public OnTouchAction createOnTouchAction(PhysicElement element) {
        return null;
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
        if (onCell == null) onCell = new AttractorOnCell(belongingOrganism, this, position, density);
        return onCell;
    }

    public static OffCell MakeProtype(){
        return new AttractorOffCell();
    }

    @Override
    public void usePosition(PositionConsumer positionConsumer) {
        positionConsumer.use(this.position);
    }

    @Override
    public float distance(Cell cell) {
        return distanceCalculator.getDistance(cell);
    }

    @Override
    public void updatePosition(PositionChangedEvent positionChangedEvent) {
        this.position = positionChangedEvent.getPosition();
    }

    @Override
    public SwitchableCell sswitch() {
        return turnOn();
    }

    @Override
    public void use(CellConsumer consumer) {

    }
}
