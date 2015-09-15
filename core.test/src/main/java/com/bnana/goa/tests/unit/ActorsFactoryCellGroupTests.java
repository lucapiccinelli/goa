package com.bnana.goa.tests.unit;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bnana.goa.actors.ActorsFactoryCellGroup;
import com.bnana.goa.actors.CellActor;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.OffCell;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 9/15/2015.
 */
public class ActorsFactoryCellGroupTests {

    @Test
    public void WhenAddingAnOffCellItAdsAnActorToTheStage(){
        Stage stage = mock(Stage.class);
        OffCell offCell = mock(OffCell.class);
        ActorsFactoryCellGroup factoryCellGroup = new ActorsFactoryCellGroup(stage);

        factoryCellGroup.add(offCell);

        verify(stage).addActor(any(CellActor.class));
    }

    @Test
    public void WhenAddingAnOffCellItAdsAnActorToTheStageAndAddTheActorToCellDestroyListeners(){
        Stage stage = mock(Stage.class);
        OffCell offCell = mock(OffCell.class);
        ActorsFactoryCellGroup factoryCellGroup = new ActorsFactoryCellGroup(stage);

        factoryCellGroup.add(offCell);

        verify(offCell).addDestroyListener(any(CellActor.class));
    }
}
