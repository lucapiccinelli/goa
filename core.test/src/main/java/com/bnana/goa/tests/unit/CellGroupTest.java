package com.bnana.goa.tests.unit;

import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.CellGroup;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.OnCell;
import com.bnana.goa.cell.SimpleCellGroup;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Luca on 8/21/2015.
 */
public class CellGroupTest {
    @Test
    public void AnOffCellAddedToAGroupShouldBeTurnedOn(){
        OffCell offCell = mock(OffCell.class);
        CellGroup group = new SimpleCellGroup();
        group.add(offCell);

        verify(offCell).turnOn();
    }

    @Test
    public void WhenUsingACellGroupAllTheCellAddedShouldBeConsumed(){
        OffCell offCell1 = mock(OffCell.class);
        OffCell offCell2 = mock(OffCell.class);

        OnCell onCell1 = mock(OnCell.class);
        OnCell onCell2 = mock(OnCell.class);

        when(offCell1.turnOn()).thenReturn(onCell1);
        when(offCell2.turnOn()).thenReturn(onCell2);

        CellGroup group = new SimpleCellGroup();
        group.add(offCell1);
        group.add(offCell2);

        CellConsumer consumer = mock(CellConsumer.class);
        group.use(consumer);

        verify(onCell1).use(consumer);
        verify(onCell2).use(consumer);
    }
}
