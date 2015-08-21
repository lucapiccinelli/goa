package com.bnana.goa.cell;

/**
 * Created by Luca on 8/21/2015.
 */
public interface CellGroup {
    void add(OffCell offCell);

    void use(CellConsumer consumer);
}
