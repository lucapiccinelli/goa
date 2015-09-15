package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.organism.StartingOrganism;

import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Luca on 8/25/2015.
 */
public class OrganismTest {

    private CellGroup cellGroup;

    @BeforeClass
    public void setUp(){
        cellGroup = new CellGroup() {
            @Override
            public void add(OffCell offCell) {
                offCell.turnOn();
            }

            @Override
            public void use(CellConsumer consumer) {

            }
        };
    }

    @Test
    public void AStartingOrganismShouldBeComposedByExactly2Cell(){

        Organism organism = new StartingOrganism(new Rectangle2D.Float(-20, -30, 100, 50), cellGroup);

        CellConsumer cellCounter = mock(CellConsumer.class);
        organism.use(cellCounter);

        Mockito.verify(cellCounter, Mockito.times(2)).use(Mockito.any(Cell.class), Mockito.any(Point2D.Float.class), Mockito.any(float.class));
    }

    @Test
    public void AStartingOrganismShouldBeComposedByExactly1AttractorOffCell(){
        Organism organism = new StartingOrganism(new Rectangle2D.Float(-20, -30, 100, 50), cellGroup);

        CellConsumer cellCounter = mock(CellConsumer.class);
        organism.useAttractors(cellCounter);

        Mockito.verify(cellCounter, Mockito.times(1)).use(Mockito.any(Cell.class), Mockito.any(Point2D.Float.class), Mockito.eq(-1f));
    }

    @Test
    public void AStartingOrganismShouldBeComposedByExactly1RepulsorOffCell(){
        Organism organism = new StartingOrganism(new Rectangle2D.Float(-20, -30, 100, 50), cellGroup);

        CellConsumer cellCounter = mock(CellConsumer.class);
        organism.useRepulsors(cellCounter);

        Mockito.verify(cellCounter, Mockito.times(1)).use(Mockito.any(Cell.class), Mockito.any(Point2D.Float.class), Mockito.eq(1f));
    }

    public void AStartingOrganismShouldGenerateAnAttractiveRadialFieldTowardsItsCenterOfMagnitudeEquivalentToItsAttractorsMass(){
        Organism organism = new StartingOrganism(new Rectangle2D.Float(-20, -30, 100, 50), mock(CellGroup.class));
//        CellGroup group = organism.groupAllCells();
    }
}
