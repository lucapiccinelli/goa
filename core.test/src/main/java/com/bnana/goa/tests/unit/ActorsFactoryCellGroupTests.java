package com.bnana.goa.tests.unit;

import com.bnana.goa.actors.ActorsFactoryCellGroup;
import com.bnana.goa.actors.CellActor;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.OffCell;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by Luca on 9/15/2015.
 */
public class ActorsFactoryCellGroupTests {

    @Test
    public void AnActorsFactoryCellGroupCreatesAnActorForAnyAddedCell(){
        ActorsFactoryCellGroup factoryCellGroup = new ActorsFactoryCellGroup();
        factoryCellGroup.add(AttractorOffCell.MakeProtype());

        List<CellActor> cellActors = factoryCellGroup.createActors();
        Assert.assertEquals(cellActors.size(), 1);
    }
}
