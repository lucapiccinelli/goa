package com.bnana.goa.organism;

import com.badlogic.gdx.math.Rectangle;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellController;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.factories.CellControllerFactory;
import com.bnana.goa.cell.generator.InverseProximityCellGenerator;
import com.bnana.goa.cell.generator.RandomCellGenerator;
import com.bnana.goa.events.PositionChangedEvent;
import com.bnana.goa.organism.events.OrganismGrownEvent;
import com.bnana.goa.organism.listeners.OrganismGrowListener;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luca.piccinelli on 25/08/2015.
 */
public class StartingOrganism implements Organism {
    private final Rectangle viewBounds;
    private CellControllerFactory controllerFactory;
    List<CellController> cells;
    List<CellController> attractors;
    List<CellController> repulsors;
    private List<OrganismGrowListener> growingListeners;

    public StartingOrganism(Rectangle viewBounds, CellControllerFactory cellControllerFactory) {
        this.viewBounds = viewBounds;
        this.controllerFactory = cellControllerFactory;
        cells = new ArrayList<CellController>();
        attractors = new ArrayList<CellController>();
        repulsors = new ArrayList<CellController>();

        growingListeners = new ArrayList<OrganismGrowListener>();

        RandomCellGenerator randomCellGenerator = new RandomCellGenerator(this, AttractorOffCell.MakeProtype(), viewBounds);
        OffCell attractor = randomCellGenerator.generate().getAnOffCell();
        growAttractors((AttractorOffCell) attractor);

        InverseProximityCellGenerator proximityCellGenerator = new InverseProximityCellGenerator(attractor);
        OffCell repulsor = proximityCellGenerator.generate().getAnOffCell();
        growRepulsor((RepulsorOffCell) repulsor);
    }

    @Override
    public void growAttractors(AttractorOffCell aNewAttractor) {
        aNewAttractor.setBelongingOrganism(this);

        CellController controller = controllerFactory.make(aNewAttractor);
        attractors.add(controller);
        cells.add(controller);

        notifyGrowingListeners(aNewAttractor);
        aNewAttractor.addPositionListener(controller);
    }

    @Override
    public void growRepulsor(RepulsorOffCell aNewRepulsor) {
        aNewRepulsor.setBelongingOrganism(this);

        CellController controller = controllerFactory.make(aNewRepulsor);
        repulsors.add(controller);
        cells.add(controller);

        notifyGrowingListeners(aNewRepulsor);
        aNewRepulsor.addPositionListener(controller);
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
    public void addGrowingListeners(OrganismGrowListener listener) {
        growingListeners.add(listener);
    }

    @Override
    public void updatePosition(PositionChangedEvent position) {

    }

    private void notifyGrowingListeners(OffCell aNewCell) {
        OrganismGrownEvent event = new OrganismGrownEvent(this, aNewCell);
        for (OrganismGrowListener listener:
             growingListeners) {
            listener.grownBy(event);
        }
    }
}
