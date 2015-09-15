package com.bnana.goa;

import com.bnana.goa.events.CellDestroyEvent;

/**
 * Created by Luca on 9/15/2015.
 */
public interface CellDestroyListener {
    void notifyDestroy(CellDestroyEvent cellDestroyEvent);
}
