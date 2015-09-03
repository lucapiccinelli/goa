package com.bnana.goa;

/**
 * Created by luca.piccinelli on 03/09/2015.
 */
public interface PositionChangesNotifier {
    void addPositionListener(PositionListener positionListener);

    void notifyPositionChanged();
}
