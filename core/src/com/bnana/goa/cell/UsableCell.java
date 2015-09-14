package com.bnana.goa.cell;

/**
 * Created by Luca on 9/14/2015.
 */
public interface UsableCell extends Cell{
    void use(CellConsumer consumer);
}
