package com.bnana.goa.rendering;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.organism.Organism;

/**
 * Created by Luca on 11/4/2015.
 */
public class FlatGeneratedGraphicOrganismRenderer implements OrganismRenderer {
    private Array<Vector2> positions;

    public FlatGeneratedGraphicOrganismRenderer() {
        this.positions = new Array<Vector2>();
    }

    @Override
    public void render(Organism organism) {
        positions.clear();
        organism.use(this);

        Vector2 avg = new Vector2(0, 0);
        for (Vector2 position : positions){
            avg.add(position);
        }
        avg.scl(1f / avg.len());


    }

    @Override
    public void use(Cell cell, Vector2 position, float density) {
        positions.add(position);
    }

    @Override
    public void useItOff(OffCell cell, Vector2 position, float density) {
        use(cell, position, density);
    }
}
