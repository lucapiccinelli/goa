package com.bnana.goa.cell;

import com.bnana.goa.PositionListener;

/**
 * Created by Luca on 9/15/2015.
 */
public interface CellController extends PositionListener{
    void sswitch();

    void useCell(CellConsumer cellConsumer);
}
