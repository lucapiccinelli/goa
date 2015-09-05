package com.bnana.goa.organism;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.SimpleCellGroup;
import com.bnana.goa.cell.generator.InverseProximityCellGenerator;
import com.bnana.goa.cell.generator.RandomCellGenerator;
import com.bnana.goa.events.PositionChangedEvent;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luca.piccinelli on 25/08/2015.
 */
public class StartingOrganism implements Organism {
    List<Cell> cells;
    List<Cell> attractors;
    List<Cell> repulsors;

    public StartingOrganism(Rectangle2D.Float viewBounds) {
        cells = new ArrayList<Cell>();
        attractors = new ArrayList<Cell>();
        repulsors = new ArrayList<Cell>();

        RandomCellGenerator randomCellGenerator = new RandomCellGenerator(AttractorOffCell.MakeProtype(), viewBounds);
        Cell attractor = randomCellGenerator.generate();
        cells.add(attractor);
        attractors.add(attractor);

        InverseProximityCellGenerator proximityCellGenerator = new InverseProximityCellGenerator(attractor);
        Cell repulsor = proximityCellGenerator.generate();
        cells.add(repulsor);
        repulsors.add(repulsor);
    }

    @Override
    public CellGroup groupAllCells() {
        return groupCellsFrom(cells);
    }

    @Override
    public CellGroup groupAllAttractors() {
        return groupCellsFrom(attractors);
    }

    @Override
    public CellGroup groupAllRepulsors() {
        return groupCellsFrom(repulsors);
    }

    private CellGroup groupCellsFrom(List<Cell> cellList){
        CellGroup cellGroup = new SimpleCellGroup();
        for (Cell cell : cellList){
            cellGroup.add(cell.getAnOffCell());
        }
        return cellGroup;
    }

    @Override
    public void updatePosition(PositionChangedEvent position) {

    }
}
