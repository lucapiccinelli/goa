package com.bnana.goa;

import com.bnana.goa.cell.WanderingCell;

/**
 * Created by luca.piccinelli on 03/09/2015.
 */
public interface PositionChangesNotifier {
    void addPositionListener(PositionListener positionListener);

    void removePositionListener(PositionListener positionListener);

    void notifyPositionChanged();
}
