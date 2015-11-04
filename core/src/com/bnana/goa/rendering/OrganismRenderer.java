package com.bnana.goa.rendering;

import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.organism.Organism;

/**
 * Created by Luca on 11/4/2015.
 */
public interface OrganismRenderer extends CellConsumer{
    void render(Organism organism);
}
