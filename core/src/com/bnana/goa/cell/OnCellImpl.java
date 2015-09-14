package com.bnana.goa.cell;

import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.exceptions.InvalidIntegrateRequestException;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;

import java.awt.geom.Point2D;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Luca on 9/14/2015.
 */
class OnCellImpl implements OnCell {
    private Organism belongingOrganism;
    private final OffCell offCell;
    private Point2D.Float position;
    private final float density;

    OnCellImpl(Organism belongingOrganism, OffCell offCell, Point2D.Float position, float density) {
        this.belongingOrganism = belongingOrganism;
        this.offCell = offCell;
        this.position = position;
        this.density = density;
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
            throw new InvalidIntegrateRequestException("The cell you requested integrate on doesn't belong to any body");

        aNewCell.growOrganism(belongingOrganism);
    }

    @Override
    public void usePosition(PositionConsumer positionConsumer) {
        positionConsumer.use(position);
    }

    @Override
    public float distance(Cell cell) {
        return this.offCell.distance(cell);
    }

    @Override
    public Cell prototype(Organism belongingOrganism, Point2D.Float position, float density) {
        return null;
    }

    @Override
    public Cell opposite(Point2D.Float position, float density) {
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
    public void updatePosition(PositionChangedEvent positionChangedEvent) {
        this.position = positionChangedEvent.getPosition();
        offCell.updatePosition(positionChangedEvent);
    }

    Point2D.Float getPosition() {
        return position;
    }

    float getDensity() {
        return density;
    }
}
