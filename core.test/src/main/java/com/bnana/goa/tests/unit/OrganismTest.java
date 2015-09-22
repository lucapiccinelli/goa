package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Rectangle;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellController;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.SwitchableCell;
import com.bnana.goa.cell.factories.CellControllerFactory;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.organism.StartingOrganism;

import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Luca on 8/25/2015.
 */
public class OrganismTest {

    @Test
    public void AStartingOrganismShouldBeComposedByExactly2Cell(){
        CellControllerFactory controllerFactory = mock(CellControllerFactory.class);
        Organism organism = new StartingOrganism(new Rectangle(-20, -30, 100, 50), controllerFactory);
        Mockito.verify(controllerFactory, Mockito.times(2)).make(Mockito.any(SwitchableCell.class));
    }

    @Test
    public void AStartingOrganismShouldBeComposedByExactly1AttractorOffCell(){
        CellControllerFactory controllerFactory = mock(CellControllerFactory.class);
        Organism organism = new StartingOrganism(new Rectangle(-20, -30, 100, 50), controllerFactory);
        Mockito.verify(controllerFactory, Mockito.times(1)).make(Mockito.isA(AttractorOffCell.class));
    }

    @Test
    public void AStartingOrganismShouldBeComposedByExactly1RepulsorOffCell(){
        CellControllerFactory controllerFactory = mock(CellControllerFactory.class);
        Organism organism = new StartingOrganism(new Rectangle(-20, -30, 100, 50), controllerFactory);
        Mockito.verify(controllerFactory, Mockito.times(1)).make(Mockito.isA(RepulsorOffCell.class));
    }

    public void AStartingOrganismShouldGenerateAnAttractiveRadialFieldTowardsItsCenterOfMagnitudeEquivalentToItsAttractorsMass(){
        CellControllerFactory controllerFactory = mock(CellControllerFactory.class);
        Organism organism = new StartingOrganism(new Rectangle(-20, -30, 100, 50), controllerFactory);
//        CellGroup group = organism.groupAllCells();
    }
}
