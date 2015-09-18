package com.bnana.goa.tests.unit;

import com.bnana.goa.actors.CellActorController;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellController;
import com.bnana.goa.cell.SwitchableCell;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Luca on 9/15/2015.
 */
public class CellActorControllerTests {
    @Test
    public void ACellControllerContainsASwitchableCellThatItCanSwitch(){
        SwitchableCell switchableCell = mock(SwitchableCell.class);

        CellController cellController = new CellActorController(switchableCell);
        cellController.sswitch();

        verify(switchableCell).sswitch();
    }

    @Test
    public void ACellControllerIsUsedToWrapCellActions(){
        SwitchableCell switchableCell = mock(SwitchableCell.class);
        CellController cellController = new CellActorController(switchableCell);
        CellConsumer cellConsumer = mock(CellConsumer.class);

        cellController.useCell(cellConsumer);

        verify(switchableCell).use(cellConsumer);
    }
}
