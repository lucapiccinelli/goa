package com.bnana.goa.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.Cell;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.RepulsorOnCell;
import com.bnana.goa.cell.WanderingCell;
import com.bnana.goa.force.ForceField;
import com.bnana.goa.utils.Const;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 9/22/2015.
 */
public class CellForceFieldRenderer implements CellRenderer {
    private final TextureRegion textureRegion;
    private ForceField forceField;
    private Batch batch;

    public CellForceFieldRenderer(ForceField forceField, Batch batch) {
        this.forceField = forceField;
        this.batch = batch;

        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(Const.FORCE_ARROW_IMAGE_PATH)));
    }

    @Override
    public void renderAttractorOffCell(AttractorOffCell attractorOffCell, Vector2 position, float density) {
        drawForce(position);
    }

    @Override
    public void renderRepulsorOffCell(RepulsorOffCell repulsorOffCell, Vector2 position, float density) {
        drawForce(position);
    }

    @Override
    public void renderAttractorOnCell(AttractorOnCell attractorOnCell, Vector2 position, float density) {
        drawForce(position);
    }

    @Override
    public void renderRepulsorOnCell(RepulsorOnCell repulsorOnCell, Vector2 position, float density) {
        drawForce(position);
    }

    @Override
    public void renderWanderingCell(WanderingCell wanderingCell, Vector2 position, float density) {
        drawForce(position);
    }

    private void drawForce(Vector2 position){
        float magnitude = forceField.valueAtPoint(position);
        if(magnitude == 0) return;

        Vector2 direction = forceField.direction(position).scl(Math.signum(magnitude));
        magnitude = MathUtils.log(10, (float) (1.2 + Math.abs(magnitude))) * 2;

        Affine2 transform = new Affine2();
        float skew = direction.angle();
        Vector2 perpendicularDirection = new Vector2(-direction.y, direction.x).scl(magnitude * 0.5f);
        transform.preTranslate(position.x + direction.x - perpendicularDirection.x, position.y + direction.y - perpendicularDirection.y).rotate(skew);
        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, magnitude * 0.3f);
        batch.draw(textureRegion, magnitude, magnitude, transform);
    }

    @Override
    public void use(Cell cell, Vector2 position, float density) {
        cell.render(this);
    }

    @Override
    public void useItOff(OffCell cell, Vector2 position, float density) {
        use(cell, position, density);
    }

    public void setForceField(ForceField forceField) {
        this.forceField = forceField;
    }
}
