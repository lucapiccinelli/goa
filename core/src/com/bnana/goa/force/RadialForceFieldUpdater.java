package com.bnana.goa.force;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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
    private Array<Vector2> points;
    private Array<Float> densities;

    public RadialForceFieldUpdater(ForceField field) {

        this.field = field;
        density = 0;
        positions = new ArrayList<Vector2>();
        points = new Array<Vector2>();
        points.add(new Vector2());
        densities = new Array<Float>();
        densities.add(0f);
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

        points.get(0).set(x, y);
        densities.set(0, this.density);
        field.update(points, densities);
    }

    @Override
    public void useItOff(OffCell cell, Vector2 position, float density) {
    }

    public void reset(){
        positions.clear();
        density = 0;
        points.get(0).set(0, 0);
        densities.set(0, 0f);

        field.update(points, densities);
    }
}
