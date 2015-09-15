package com.bnana.goa.cell;

import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.actions.SwitchableCellOnTouchAction;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public class RepulsorOnCell implements OnCell {
    private final OnCellImpl onCellImpl;
    private float density;

    public RepulsorOnCell(Organism belongingOrganism, OffCell offCell, Point2D.Float position, float density) {
        this(offCell, position, density, belongingOrganism, null);
    }

    public RepulsorOnCell(OffCell offCell, Point2D.Float position, float density, Organism belongingOrganism, CellController cellController){
        this.onCellImpl = new OnCellImpl(offCell, position, density, belongingOrganism, cellController);
    }

    @Override
    public OffCell turnOff() {
        return onCellImpl.turnOff();
    }

    @Override
    public void use(CellConsumer consumer) {
        consumer.use(this, onCellImpl.getPosition(), onCellImpl.getDensity());
    }

    @Override
    public void integrate(OffCell aNewCell){
        onCellImpl.integrate(aNewCell);
    }

    @Override
    public void usePosition(PositionConsumer positionConsumer) {
        onCellImpl.usePosition(positionConsumer);
    }

    @Override
    public float distance(Cell cell) {
        return onCellImpl.distance(cell);
    }

    @Override
    public Cell prototype(Organism belongingOrganism, Point2D.Float position, float density) {
        return onCellImpl.prototype(belongingOrganism, position, density);
    }

    @Override
    public Cell opposite(Point2D.Float position, float density) {
        return onCellImpl.opposite(position, density);
    }

    @Override
    public OffCell getAnOffCell() {
        return onCellImpl.getAnOffCell();
    }

    @Override
    public OnTouchAction createOnTouchAction(PhysicElement element) {
        return new SwitchableCellOnTouchAction(this, element);
    }

    @Override
    public void addDestroyListener(CellDestroyListener destroyListener) {
        onCellImpl.addDestroyListener(destroyListener);
    }

    @Override
    public void destroy() {
        onCellImpl.destroy();
    }

    @Override
    public void setPosition(Point2D.Float position) {
        onCellImpl.setPosition(position);
    }

    @Override
    public void updatePosition(PositionChangedEvent positionChangedEvent) {
        onCellImpl.updatePosition(positionChangedEvent);
    }

    @Override
    public SwitchableCell sswitch() {
        return onCellImpl.sswitch();
    }

    @Override
    public void setController(CellController cellController) {
        onCellImpl.setController(cellController);
    }
}
