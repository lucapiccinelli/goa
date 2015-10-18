package com.bnana.goa.rendering;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RadialForceField;
import com.bnana.goa.utils.ScaleManager;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Luca on 9/29/2015.
 */
public class GeneratedGraphicMultiForceRenderer implements CellConsumer{
    private final ScaleManager scaleManager;
    private Matrix4 worldTransform;

    Dictionary<Cell, ForceFieldInfo> renderers;

    public GeneratedGraphicMultiForceRenderer(ScaleManager scaleManager, Matrix4 worldTransform) {
        this.scaleManager = scaleManager;
        this.worldTransform = worldTransform;

        renderers = new Hashtable<Cell, ForceFieldInfo>();
    }

    @Override
    public void use(Cell cell, Vector2 position, float density) {
        ForceFieldInfo info = renderers.get(cell);
        if(info == null){
            info = new ForceFieldInfo();
            renderers.put(cell, info);
        }
        info.render(cell, position, density);
    }

    @Override
    public void useItOff(OffCell cell, Vector2 position, float density) {
        use(cell, position, density);
    }

    private class ForceFieldInfo {
        private final GeneratedGraphicForceRenderer renderer;
        private final ShapeRenderer shapeRenderer;

        public ForceFieldInfo() {
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(worldTransform);
            renderer = new GeneratedGraphicForceRenderer(shapeRenderer, scaleManager);
            renderer.use(null, new Vector2(), 0);
        }

        public void render(Cell cell, Vector2 position, float magnitude){
            renderer.use(cell, position, magnitude);
        }

        public void dispose(){
            shapeRenderer.dispose();
        }
    }
}
