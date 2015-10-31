package com.bnana.goa.force.functions;

/**
 * Created by Luca on 10/18/2015.
 */
public class LinearValueAtDistanceFunction implements ValueAtDistanceFunction {
    @Override
    public float calculate(float distance, float magnitude) {
        if((distance < 0.1 && distance > -0.1) || (distance <= 2.1 && magnitude < 0)) return 0;
        return (float)(magnitude / (1 + Math.log1p(Math.abs(distance)))) * 0.05f;
    }
}
