package com.bnana.goa.organism.listeners;

import com.bnana.goa.organism.events.OrganismGrownEvent;

/**
 * Created by Luca on 9/29/2015.
 */
public interface OrganismGrowListener {
    void grownBy(OrganismGrownEvent grownEvent);
}
