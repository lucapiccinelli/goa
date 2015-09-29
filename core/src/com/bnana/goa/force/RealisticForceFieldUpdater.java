package com.bnana.goa.force;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.OffCell;

import java.util.ArrayList;

/**
 * Created by Luca on 9/29/2015.
 */
public class RealisticForceFieldUpdater implements CellConsumer {

    private final Array<Vector2> positions;
    private final Array<Float> densities;
    private ForceField field;

    public RealisticForceFieldUpdater(ForceField field) {
        this.field = field;
        positions = new Array<Vector2>();
        densities = new Array<Float>();
    }

    @Override
    public void use(Cell cell, Vector2 position, float density) {
        positions.add(position);
        densities.add(density);

        field.update(positions, densities);
    }

    @Override
    public void useItOff(OffCell cell, Vector2 position, float density) {

    }

    public void reset(){
        positions.clear();
        densities.clear();

        field.update(positions, densities);
    }
}
