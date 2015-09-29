package com.bnana.goa.organism.events;

import com.bnana.goa.cell.OffCell;

import java.util.EventObject;

/**
 * Created by Luca on 9/29/2015.
 */
public class OrganismGrownEvent extends EventObject {
    private final OffCell grownByCell;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public OrganismGrownEvent(Object source, OffCell grownByCell) {
        super(source);
        this.grownByCell = grownByCell;
    }
}
