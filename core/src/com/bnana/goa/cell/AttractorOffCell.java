package com.bnana.goa.cell;

import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.actions.SwitchableCellOnTouchAction;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.exceptions.InvalidIntegrateRequestException;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.utils.EuclideanDistance;

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
    private CellController cellController;

    public AttractorOffCell(Organism belongingOrganism, Point2D.Float position, float density) {
        this.position = position;
        this.density = Math.abs(density);
        this.belongingOrganism = belongingOrganism;

        distanceCalculator = new EuclideanDistance(position);

        destroyListeners = new ArrayList<CellDestroyListener>();
    }

    public void setBelongingOrganism(Organism belongingOrganism) {
        this.belongingOrganism = belongingOrganism;
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
        OnCell tmpOnCell = makeCell();
        if(cellController != null) cellController.changeSwitchable(tmpOnCell);
        return tmpOnCell;
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
        if(onCell != null) onCell.setPosition(position);
    }

    @Override
    public SwitchableCell sswitch() {
        return turnOn();
    }

    @Override
    public void setController(CellController cellController) {
        this.cellController = cellController;
    }

    @Override
    public void integrate(OffCell aNewCell) {
        if(belongingOrganism == null)
            throw new InvalidIntegrateRequestException("The AttractorOffCell you requested to integrate on doesn't belong to any body");

        aNewCell.growOrganism(belongingOrganism);
    }

    @Override
    public void use(CellConsumer consumer) {
    }

    @Override
    public void setPosition(Point2D.Float position) {
        this.position = position;
    }
}
