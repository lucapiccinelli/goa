package com.bnana.goa.organism;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.SimpleCellGroup;
import com.bnana.goa.cell.generator.InverseProximityCellGenerator;
import com.bnana.goa.cell.generator.RandomCellGenerator;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luca.piccinelli on 25/08/2015.
 */
public class StartingOrganism implements Organism {
    List<OffCell> cells;
    List<OffCell> attractors;
    List<OffCell> repulsors;

    public StartingOrganism(Rectangle2D.Float viewBounds) {
        cells = new ArrayList<OffCell>();
        attractors = new ArrayList<OffCell>();
        repulsors = new ArrayList<OffCell>();

        RandomCellGenerator randomCellGenerator = new RandomCellGenerator(AttractorOffCell.MakeProtype(), viewBounds);
        OffCell attractor = randomCellGenerator.generate();
        cells.add(attractor);
        attractors.add(attractor);

        InverseProximityCellGenerator proximityCellGenerator = new InverseProximityCellGenerator(attractor);
        OffCell repulsor = proximityCellGenerator.generate();
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

    private CellGroup groupCellsFrom(List<OffCell> cellList){
        CellGroup cellGroup = new SimpleCellGroup();
        for (OffCell cell : cellList){
            cellGroup.add(cell);
        }
        return cellGroup;
    }
}
