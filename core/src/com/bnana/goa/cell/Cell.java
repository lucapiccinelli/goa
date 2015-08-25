package com.bnana.goa.cell;

/**
 * Created by Luca on 8/21/2015.
 */
public interface Cell {
    void usePosition(PositionConsumer positionConsumer);
    float distance(Cell cell);
}
