package com.bnana.goa.rendering;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.force.RadialForceField;
import com.bnana.goa.utils.ScaleManager;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Luca on 9/29/2015.
 */
public class GeneratedGraphicMultiForceRenderer implements ForceRenderer{
    private final ScaleManager scaleManager;
    private Matrix4 worldTransform;

    Dictionary<Vector2, ForceFieldInfo> renderers;

    public GeneratedGraphicMultiForceRenderer(ScaleManager scaleManager, Matrix4 worldTransform) {
        this.scaleManager = scaleManager;
        this.worldTransform = worldTransform;

        renderers = new Hashtable<Vector2, ForceFieldInfo>();
    }

    @Override
    public void renderForce(ForceField forceField, Vector2 position, float magnitude) {
        ForceFieldInfo info = renderers.get(position);
        if(info == null){
            info = new ForceFieldInfo();
            renderers.put(position, info);
        }
        info.render(forceField, position, magnitude);
    }

    private class ForceFieldInfo {
        private final GeneratedGraphicForceRenderer renderer;
        private final ShapeRenderer shapeRenderer;
        public int lastUsed;

        public ForceFieldInfo() {
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(worldTransform);
            renderer = new GeneratedGraphicForceRenderer(shapeRenderer, scaleManager);
            renderer.renderForce(new RadialForceField(), new Vector2(), 0);

            lastUsed = 0;
        }

        public void render(ForceField field, Vector2 position, float magnitude){
            renderer.renderForce(field, position, magnitude);
            use();
        }

        public void dispose(){
            shapeRenderer.dispose();
        }

        public void use(){
            lastUsed++;
        }

        public void unuse(){
            lastUsed--;
        }
    }
}
