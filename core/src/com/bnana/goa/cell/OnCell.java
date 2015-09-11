package com.bnana.goa.cell;

/**
 * Created by Luca on 8/21/2015.
 */
public interface OnCell extends Cell {
    OffCell turnOff();
    void use(CellConsumer consumer);
    void integrate(OffCell aNewCell);
}
