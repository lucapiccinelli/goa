package com.bnana.goa.cell.factories;

import com.bnana.goa.cell.CellController;
import com.bnana.goa.cell.SwitchableCell;

/**
 * Created by Luca on 9/18/2015.
 */
public interface CellControllerFactory {
    CellController make(SwitchableCell switchableCell);
}
