package com.bnana.goa.force;

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

    public RadialForceFieldUpdater(ForceField field) {

        this.field = field;
        density = 0;
        positions = new ArrayList<Point2D.Float>();
    }

    @Override
    public void use(Point2D.Float position, float density) {
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

        field.update(new Point2D.Float[]{new Point2D.Float(x, y)}, new float[]{this.density});
    }
}
