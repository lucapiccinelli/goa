package com.bnana.goa.cell;

/**
 * Created by Luca on 9/15/2015.
 */
public interface SwitchableCell extends UsableCell {
    SwitchableCell sswitch();

    void integrate(OffCell evolvedCell);
}
