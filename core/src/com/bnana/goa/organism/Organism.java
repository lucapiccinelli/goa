package com.bnana.goa.organism;

import com.bnana.goa.PositionListener;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.RepulsorOffCell;

/**
 * Created by luca.piccinelli on 25/08/2015.
 */
public interface Organism extends PositionListener{

    CellGroup groupAllCells();

    CellGroup groupAllAttractors();

    CellGroup groupAllRepulsors();

    void growAttractors(AttractorOffCell aNewAttractor);

    void growRepulsor(RepulsorOffCell repulsorOffCell);
}
