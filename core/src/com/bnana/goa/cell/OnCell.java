package com.bnana.goa.cell;

/**
 * Created by Luca on 8/21/2015.
 */
public interface OnCell {
    OffCell turnOff();
    void use(CellConsumer consumer);
}
