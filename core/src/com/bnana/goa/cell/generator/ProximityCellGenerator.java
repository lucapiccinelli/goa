package com.bnana.goa.cell.generator;

import com.bnana.goa.cell.OffCell;

/**
 * Created by Luca on 8/21/2015.
 */
public class ProximityCellGenerator implements CellGenerator {
    private Cell sourceCell;

    public ProximityCellGenerator(Cell sourceCell) {
        this.sourceCell = sourceCell;
    }

    @Override
    public OffCell generate() {
        return null;
    }
}
