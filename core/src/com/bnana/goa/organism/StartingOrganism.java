package com.bnana.goa.organism;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellController;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.RepulsorOffCell;
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
    private final Rectangle2D.Float viewBounds;
    private final CellGroup cellGroup;
    List<CellController> cells;
    List<CellController> attractors;
    List<CellController> repulsors;

    public StartingOrganism(Rectangle2D.Float viewBounds, CellGroup cellGroup) {
        this.viewBounds = viewBounds;
        this.cellGroup = cellGroup;
        cells = new ArrayList<CellController>();
        attractors = new ArrayList<CellController>();
        repulsors = new ArrayList<CellController>();

        RandomCellGenerator randomCellGenerator = new RandomCellGenerator(this, AttractorOffCell.MakeProtype(), viewBounds);
        OffCell attractor = randomCellGenerator.generate().getAnOffCell();
        CellController attractorController = new CellController(attractor);
        attractor.setController(attractorController);

        cells.add(attractorController);
        attractors.add(attractorController);

        InverseProximityCellGenerator proximityCellGenerator = new InverseProximityCellGenerator(attractor);
        OffCell repulsor = proximityCellGenerator.generate().getAnOffCell();
        CellController repulsorController = new CellController(repulsor);
        repulsor.setController(repulsorController);

        cells.add(repulsorController);
        repulsors.add(repulsorController);

        this.cellGroup.add(attractor);
        this.cellGroup.add(repulsor);
    }

    @Override
    public void growAttractors(AttractorOffCell aNewAttractor) {
        this.cellGroup.add(aNewAttractor);

        CellController controller = new CellController(aNewAttractor);
        attractors.add(controller);
        cells.add(controller);
    }

    @Override
    public void growRepulsor(RepulsorOffCell aNewRepulsor) {
        this.cellGroup.add(aNewRepulsor);

        CellController controller = new CellController(aNewRepulsor);
        repulsors.add(controller);
        cells.add(controller);
    }

    @Override
    public void use(CellConsumer cellConsumer) {
        for (CellController cell :
                cells) {
            cell.useCell(cellConsumer);
        }
    }

    @Override
    public void useAttractors(CellConsumer cellConsumer) {
        for (CellController cell :
                attractors) {
            cell.useCell(cellConsumer);
        }
    }

    @Override
    public void useRepulsors(CellConsumer cellConsumer) {
        for (CellController cell :
                repulsors) {
            cell.useCell(cellConsumer);
        }
    }

    @Override
    public void updatePosition(PositionChangedEvent position) {

    }
}
