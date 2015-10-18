package com.bnana.goa.force.functions;

/**
 * Created by Luca on 10/18/2015.
 */
public class LinearValueAtDistanceFunction implements ValueAtDistanceFunction {
    @Override
    public float calculate(float distance, float magnitude) {
        if(distance <= 2 && magnitude < 0) return 0;
        return magnitude / 100f;
    }
}
