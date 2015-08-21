package com.bnana.goa.cell;

import java.util.ArrayList;

/**
 * Created by Luca on 8/21/2015.
 */
public class SimpleCellGroup implements CellGroup {
    private ArrayList<OnCell> onCells;

    public SimpleCellGroup() {
        onCells = new ArrayList<OnCell>();
    }

    @Override
    public void add(OffCell offCell) {
        onCells.add(offCell.turnOn());
    }

    @Override
    public void use(CellConsumer consumer) {
        for (OnCell cell : onCells) {
            cell.use(consumer);
        }
    }
}
