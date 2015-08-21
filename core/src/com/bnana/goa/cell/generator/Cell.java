package com.bnana.goa.cell.generator;

import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.PositionConsumer;

/**
 * Created by Luca on 8/21/2015.
 */
public interface Cell {
    void usePosition(PositionConsumer positionConsumer);
    float distance(Cell cell);
}
