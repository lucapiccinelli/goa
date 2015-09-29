package com.bnana.goa.tests.unit;

import com.badlogic.gdx.math.Rectangle;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.SwitchableCell;
import com.bnana.goa.cell.factories.CellControllerFactory;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.organism.StartingOrganism;
import com.bnana.goa.organism.events.OrganismGrownEvent;
import com.bnana.goa.organism.listeners.OrganismGrowListener;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 8/25/2015.
 */
public class OrganismTest {

    @Test
    public void AStartingOrganismShouldBeComposedByExactly2Cell(){
        CellControllerFactory controllerFactory = mock(CellControllerFactory.class);
        Organism organism = new StartingOrganism(new Rectangle(-20, -30, 100, 50), controllerFactory);
        Mockito.verify(controllerFactory, Mockito.times(2)).make(any(SwitchableCell.class));
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

    @Test
    public void AnOrganismMustNotifyWhenItsAttractorsAreGrown(){
        Organism organism = new StartingOrganism(new Rectangle(-20, -30, 100, 50), mock(CellControllerFactory.class));
        OrganismGrowListener listener = mock(OrganismGrowListener.class);
        organism.addGrowingListeners(listener);

        AttractorOffCell offCell = mock(AttractorOffCell.class);
        organism.growAttractors(offCell);

        verify(listener).grownBy(any(OrganismGrownEvent.class));
    }


    @Test
    public void AnOrganismMustNotifyWhenItsRepulsorsAreGrown(){
        Organism organism = new StartingOrganism(new Rectangle(-20, -30, 100, 50), mock(CellControllerFactory.class));
        OrganismGrowListener listener = mock(OrganismGrowListener.class);
        organism.addGrowingListeners(listener);

        RepulsorOffCell offCell = mock(RepulsorOffCell.class);
        organism.growRepulsor(offCell);

        verify(listener).grownBy(any(OrganismGrownEvent.class));
    }

    public void AStartingOrganismShouldGenerateAnAttractiveRadialFieldTowardsItsCenterOfMagnitudeEquivalentToItsAttractorsMass(){
        CellControllerFactory controllerFactory = mock(CellControllerFactory.class);
        Organism organism = new StartingOrganism(new Rectangle(-20, -30, 100, 50), controllerFactory);
//        CellGroup group = organism.groupAllCells();
    }
}
