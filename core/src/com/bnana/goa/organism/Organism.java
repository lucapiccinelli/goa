package com.bnana.goa.organism;

import com.bnana.goa.cell.CellGroup;

/**
 * Created by luca.piccinelli on 25/08/2015.
 */
public interface Organism {

    CellGroup groupAllCells();

    CellGroup groupAllAttractors();

    CellGroup groupAllRepulsors();
}
