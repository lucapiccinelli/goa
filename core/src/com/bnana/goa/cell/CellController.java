package com.bnana.goa.cell;

/**
 * Created by Luca on 9/15/2015.
 */
public class CellController {
    private SwitchableCell switchableCell;

    public CellController(SwitchableCell switchableCell) {
        this.switchableCell = switchableCell;
    }

    public void sswitch() {
        this.switchableCell = this.switchableCell.sswitch();
    }

    public void useCell(CellConsumer cellConsumer) {
        switchableCell.use(cellConsumer);
    }
}
