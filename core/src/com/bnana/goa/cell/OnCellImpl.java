package com.bnana.goa.cell;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.events.CellDestroyEvent;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.exceptions.InvalidIntegrateRequestException;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.rendering.CellRenderer;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Luca on 9/14/2015.
 */
class OnCellImpl implements OnCell {
    private Organism belongingOrganism;
    private final OffCell offCell;
    private Vector2 position;
    private final float density;
    private List<CellDestroyListener> destroyListeners;
    private List<PositionListener> positionListeners;

    OnCellImpl(OffCell offCell, Vector2 position, float density, Organism belongingOrganism){
        this.belongingOrganism = belongingOrganism;
        this.offCell = offCell;
        this.position = position;
        this.density = density;

        destroyListeners = new ArrayList<CellDestroyListener>();
        positionListeners = new ArrayList<PositionListener>();
    }

    @Override
    public OffCell turnOff() {
        return offCell;
    }

    @Override
    public void use(CellConsumer consumer) {
        throw new NotImplementedException();
    }

    @Override
    public void integrate(OffCell aNewCell) {
        if(belongingOrganism == null)
            throw new InvalidIntegrateRequestException("The OnCell you requested to integrate on doesn't belong to any body");

        aNewCell.growOrganism(belongingOrganism);
    }

    @Override
    public void usePosition(PositionConsumer positionConsumer) {
        positionConsumer.use(position, density);
    }

    @Override
    public float distance(Cell cell) {
        return this.offCell.distance(cell);
    }

    @Override
    public Cell prototype(Organism belongingOrganism, Vector2 position, float density) {
        return null;
    }

    @Override
    public Cell opposite(Vector2 position, float density) {
        return null;
    }

    @Override
    public OffCell getAnOffCell() {
        return offCell;
    }

    @Override
    public OnTouchAction createOnTouchAction(PhysicElement element) {
        throw new NotImplementedException();
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
    public void updatePosition(PositionChangedEvent positionChangedEvent) {
        this.position = positionChangedEvent.getPosition();
        offCell.setPosition(position);
        for(PositionListener positionListener : positionListeners){
            positionListener.updatePosition(positionChangedEvent);
        }
    }

    Vector2 getPosition() {
        return position;
    }

    float getDensity() {
        return density;
    }

    @Override
    public SwitchableCell sswitch() {
        return turnOff();
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public void render(CellRenderer cellRenderer) {

    }

    @Override
    public void addPositionListener(PositionListener positionListener) {
        positionListeners.add(positionListener);
    }
}
