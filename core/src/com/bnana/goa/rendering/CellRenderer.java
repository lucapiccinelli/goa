package com.bnana.goa.rendering;

import com.badlogic.gdx.math.Vector2;
import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.AttractorOnCell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.OffCell;
import com.bnana.goa.cell.RepulsorOffCell;
import com.bnana.goa.cell.RepulsorOnCell;
import com.bnana.goa.cell.WanderingCell;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 9/21/2015.
 */
public interface CellRenderer extends CellConsumer{
    void renderAttractorOffCell(AttractorOffCell attractorOffCell, Vector2 position, float density);

    void renderRepulsorOffCell(RepulsorOffCell repulsorOffCell, Vector2 position, float density);

    void renderAttractorOnCell(AttractorOnCell attractorOnCell, Vector2 position, float density);

    void renderRepulsorOnCell(RepulsorOnCell repulsorOnCell, Vector2 position, float density);

    void renderWanderingCell(WanderingCell wanderingCell, Vector2 position, float density);
}
