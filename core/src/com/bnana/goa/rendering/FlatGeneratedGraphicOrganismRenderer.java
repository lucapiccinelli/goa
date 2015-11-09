package com.bnana.goa.rendering;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.utils.ScaleManager;

import java.util.Comparator;

/**
 * Created by Luca on 11/4/2015.
 */
public class FlatGeneratedGraphicOrganismRenderer implements OrganismRenderer {
    private final ShapeRenderer shapeRenderer;
    private final ScaleManager sm;
    private Array<Vector2> positions;
    private PositionInfo[] borderPoints;
    private final int borderQuantizationFactor;
    private int borderQuantizedDimension;

    public FlatGeneratedGraphicOrganismRenderer(ShapeRenderer shapeRenderer, ScaleManager sm) {
        this.shapeRenderer = shapeRenderer;
        this.sm = sm;
        this.positions = new Array<Vector2>();

        borderQuantizationFactor = 20;
        borderQuantizedDimension = 360 / 20;
        borderPoints = new PositionInfo[borderQuantizedDimension];
    }

    @Override
    public void render(Organism organism) {
        positions.clear();
        organism.use(this);

        borderQuantizedDimension = Math.min(borderQuantizedDimension, positions.size);

        Vector2 avg = new Vector2(0, 0);
        for (Vector2 position : positions) {
            avg.add(position);
        }
        avg.scl(1f / positions.size);

        for (Vector2 position : positions) {
            float distance = position.dst(avg);
            float alpha = distance > 0 ? (MathUtils.radiansToDegrees * MathUtils.atan2(position.y - avg.y, position.x - avg.x) + 180) % 360 : 0;
            int alphaToInt = (int)alpha / borderQuantizationFactor;

            PositionInfo currentInfo = borderPoints[alphaToInt];
            if (currentInfo == null){
                borderPoints[alphaToInt] = new PositionInfo(position, alpha, distance);
            }else {
                if(currentInfo.getDistance() < distance){
                    currentInfo.setAlpha(alpha);
                    currentInfo.setDistance(distance);
                    currentInfo.setPosition(position);
                }
            }
        }

        shapeRenderer.setColor(0, 0, 0, 0);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < borderQuantizedDimension; i++){
            Vector2 position = borderPoints[i].getPosition();
            shapeRenderer.circle(position.x, position.y, 0.5f);
        }

        shapeRenderer.end();
    }

    @Override
    public void use(Cell cell, Vector2 position, float density) {
        positions.add(position);
    }

    @Override
    public void useItOff(OffCell cell, Vector2 position, float density) {
        use(cell, position, density);
    }

    private class PositionInfo{
        private Vector2 position;
        private float alpha;
        private float distance;

        public PositionInfo(Vector2 position, float alpha, float distance) {

            this.position = position;
            this.alpha = alpha;
            this.distance = distance;
        }

        public Vector2 getPosition() {
            return position;
        }

        public float getAlpha() {
            return alpha;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public void setPosition(Vector2 position) {
            this.position = position;
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
        }
    }
}
