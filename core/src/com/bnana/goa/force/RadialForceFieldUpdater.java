package com.bnana.goa.force;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.OffCell;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 8/21/2015.
 */
public class RadialForceFieldUpdater implements CellConsumer {
    private ForceField field;
    private float density;
    private List<Vector2> positions;
    private Vector2[] points;
    private float[] densities;

    public RadialForceFieldUpdater(ForceField field) {

        this.field = field;
        density = 0;
        positions = new ArrayList<Vector2>();
        points = new Vector2[]{new Vector2()};
        densities = new float[]{0};
    }

    @Override
    public void use(Cell cell, Vector2 position, float density) {
        positions.add(position);

        this.density += density;
        float x = 0;
        float y = 0;
        for (Vector2 p : positions) {
            x += p.x;
            y += p.y;
        }
        x /= positions.size();
        y /= positions.size();

        points[0].set(x, y);
        densities[0] = this.density;
        field.update(points, densities);
    }

    @Override
    public void useItOff(OffCell cell, Vector2 position, float density) {
    }

    public void reset(){
        positions.clear();
        density = 0;
        points[0].set(0, 0);
        densities[0] = 0;

        field.update(points, densities);
    }
}
