package com.bnana.goa.cell;

import com.bnana.goa.exceptions.InvalidIntegrateRequestException;

/**
 * Created by Luca on 8/21/2015.
 */
public interface OnCell extends UsableCell {
    OffCell turnOff();
    void integrate(OffCell aNewCell);
}
