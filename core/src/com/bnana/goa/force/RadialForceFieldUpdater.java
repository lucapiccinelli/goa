package com.bnana.goa.force;

import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 8/21/2015.
 */
public class RadialForceFieldUpdater implements CellConsumer {
    private ForceField field;
    private float density;
    private List<Point2D.Float> positions;
    private Point2D.Float[] points;
    private float[] densities;

    public RadialForceFieldUpdater(ForceField field) {

        this.field = field;
        density = 0;
        positions = new ArrayList<Point2D.Float>();
        points = new Point2D.Float[]{new Point2D.Float()};
        densities = new float[]{0};
    }

    @Override
    public void use(Cell cell, Point2D.Float position, float density) {
        positions.add(position);

        this.density += density;
        float x = 0;
        float y = 0;
        for (Point2D.Float p : positions) {
            x += p.getX();
            y += p.getY();
        }
        x /= positions.size();
        y /= positions.size();

        points[0].setLocation(x, y);
        densities[0] = this.density;
        field.update(points, densities);
    }

    public void reset(){
        positions.clear();
        density = 0;
    }
}
