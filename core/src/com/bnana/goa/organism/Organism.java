package com.bnana.goa.organism;

import com.bnana.goa.PositionListener;
import com.bnana.goa.cell.CellGroup;

/**
 * Created by luca.piccinelli on 25/08/2015.
 */
public interface Organism extends PositionListener{

    CellGroup groupAllCells();

    CellGroup groupAllAttractors();

    CellGroup groupAllRepulsors();
}
