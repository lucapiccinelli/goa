package com.bnana.goa.actors;

import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.OffCell;

import java.awt.Component;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 9/15/2015.
 */
public class ActorsFactoryCellGroup implements CellGroup{
    private List<OffCell> offCells;

    public ActorsFactoryCellGroup() {
        this.offCells = new ArrayList<OffCell>();
    }

    @Override
    public void add(OffCell offCell) {
        offCells.add(offCell);
    }

    @Override
    public void use(CellConsumer consumer) {
        for (OffCell cell :
                offCells) {
            cell.use(consumer);
        }
    }

    public List<CellActor> createActors() {
        List<CellActor> actors = new ArrayList<CellActor>();
        for (OffCell cell :
                offCells) {
            actors.add(new CellActor(cell));
        }
        return actors;
    }

    public void turnOn(){
        for (OffCell cell :
                offCells) {
            cell.turnOn();
        }
    }

    public void turnOff(){
        for (OffCell cell :
                offCells) {
            cell.turnOn().turnOff();
        }
    }
}
