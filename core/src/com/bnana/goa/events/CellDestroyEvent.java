package com.bnana.goa.events;

import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.WanderingCell;

import java.util.EventObject;

/**
 * Created by Luca on 9/15/2015.
 */
public class CellDestroyEvent extends EventObject {

    public CellDestroyEvent(Cell sourceCell) {
        super(sourceCell);
    }
}
