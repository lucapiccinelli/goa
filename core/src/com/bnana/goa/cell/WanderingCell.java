package com.bnana.goa.cell;

import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.actions.WanderingOnTouchAction;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.utils.EuclideanDistance;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by luca.piccinelli on 01/09/2015.
 */
public class WanderingCell implements EvolvableCell {
    private Point2D.Float position;
    private float density;
    private final Random random;
    private final EuclideanDistance distance;
    private OffCell evolved;

    private WanderingCell(){
        this(new Point2D.Float(0, 0), 1f);
    }

    public WanderingCell(Point2D.Float position, float density) {
        this.position = position;
        this.density = density;

        random = new Random();
        distance = new EuclideanDistance(position);

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
    public Cell prototype(Organism belongingOrganism, Point2D.Float position, float density) {
        return new WanderingCell(position, density);
    }

    @Override
    public Cell opposite(Point2D.Float position, float density) {
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
