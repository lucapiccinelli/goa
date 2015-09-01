package com.bnana.goa.cell;

import com.bnana.goa.cell.generator.RandomCellGenerator;
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

    public WanderingCell(Point2D.Float position, float density) {
        this.position = position;
        this.density = density;

        random = new Random();

        distance = new EuclideanDistance(position);
    }

    @Override
    public OffCell evolve() {
        float r = random.nextFloat();
        return r >= 0.5 ? new AttractorOffCell(position, density) : new RepulsorOffCell(position, density);
        //return  new AttractorOffCell(position, density);
    }

    @Override
    public void usePosition(PositionConsumer positionConsumer) {
        positionConsumer.use(position);
    }

    @Override
    public float distance(Cell cell) {
        return distance.getDistance(cell);
    }
}
