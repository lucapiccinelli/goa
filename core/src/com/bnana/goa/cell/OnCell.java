package com.bnana.goa.cell;

import com.bnana.goa.cell.generator.Cell;

/**
 * Created by Luca on 8/21/2015.
 */
public interface OnCell extends Cell {
    OffCell turnOff();
    void use(CellConsumer consumer);
}
