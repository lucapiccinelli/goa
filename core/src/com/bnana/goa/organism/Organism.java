package com.bnana.goa.organism;

import com.bnana.goa.PositionListener;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.organism.listeners.OrganismGrowListener;

/**
 * Created by luca.piccinelli on 25/08/2015.
 */
public interface Organism extends PositionListener{

    void growAttractors(AttractorOffCell aNewAttractor);

    void growRepulsor(RepulsorOffCell repulsorOffCell);

    void use(CellConsumer cellConsumer);

    void useAttractors(CellConsumer cellConsumer);

    void useRepulsors(CellConsumer cellConsumer);

    void addGrowingListeners(OrganismGrowListener listener);
}
