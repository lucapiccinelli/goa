package com.bnana.goa.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.ConvexHull;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
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
    private FloatArray positions;
    private PositionInfo[] borderPoints;
    private final int borderQuantizationFactor;
    private int borderQuantizedDimension;
    private final ConvexHull convexHull;

    public FlatGeneratedGraphicOrganismRenderer(ShapeRenderer shapeRenderer, ScaleManager sm) {
        this.shapeRenderer = shapeRenderer;
        this.sm = sm;
        this.positions = new FloatArray();

        convexHull = new ConvexHull();

        borderQuantizationFactor = 30;
        borderQuantizedDimension = 360 / borderQuantizationFactor;
        borderPoints = new PositionInfo[borderQuantizedDimension];
    }

    @Override
    public void render(Organism organism) {
        positions.clear();
        organism.use(this);

        Vector2 avg = new Vector2(0, 0);
        for (int i = 0; i < positions.size; i += 2) {
            avg.add(positions.get(i), positions.get(i + 1));
        }
        avg.scl(2f / positions.size);

        FloatArray polygon = convexHull.computePolygon(positions, false);
        float[] newPolygon = new float[polygon.size];

        float x = 0;
        float y = 0;
        for (int i = 0; i < polygon.size; i += 2) {
            x = polygon.get(i);
            y = polygon.get(i + 1);
            Vector2 direction = new Vector2(x - avg.x, y - avg.y).nor();
            newPolygon[i] = x + direction.x;
            newPolygon[i + 1] = y + direction.y;
        }

        shapeRenderer.setColor(0, 0, 0, 0);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        Vector2 p1 = new Vector2();
        Vector2 p2 = new Vector2();
        for (int i = 0; i < polygon.size; i += 2) {
            p1.set(newPolygon[i], newPolygon[i + 1]);
            p2.set(newPolygon[(i + 2) % polygon.size], newPolygon[(i + 3) % polygon.size]);
            drawBeziers(p1, p2);
        }

        shapeRenderer.circle(avg.x, avg.y, 0.5f);

        shapeRenderer.end();
    }

    private void drawBeziers(Vector2 p1, Vector2 p2){
        Vector2 direction = p2.cpy().sub(p1);
        float length = direction.len();
        direction.nor();

        Vector2 c1 = p1.cpy().add(direction.cpy().scl(length * 0.25f));
        Vector2 c2 = p1.cpy().add(direction.cpy().scl(length * 0.75f));

        Vector2 perpendicular = direction.set(-direction.y, direction.x).scl(-0.5f);

        c1.add(perpendicular);
        c2.add(perpendicular);

        /*shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(p1.x, p1.y, 0.2f);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(c1.x, c1.y, 0.2f);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.circle(c2.x, c2.y, 0.2f);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(p2.x, p2.y, 0.2f);*/

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.curve(p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, 5);
    }

    @Override
    public void use(Cell cell, Vector2 position, float density) {
        positions.add(position.x);
        positions.add(position.y);
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
