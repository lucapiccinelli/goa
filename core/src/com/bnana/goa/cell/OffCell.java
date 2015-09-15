package com.bnana.goa.cell;

import com.bnana.goa.organism.Organism;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public interface OffCell extends SwitchableCell{
    OnCell turnOn();

    void growOrganism(Organism same);
}
