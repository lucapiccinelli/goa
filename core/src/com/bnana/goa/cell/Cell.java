package com.bnana.goa.cell;

import com.bnana.goa.CellDestroyListener;
import com.bnana.goa.PositionListener;
import com.bnana.goa.actions.OnTouchAction;
import com.bnana.goa.organism.Organism;
import com.bnana.goa.physics.PhysicElement;
import com.bnana.goa.rendering.CellRenderer;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 8/21/2015.
 */
public interface Cell extends PositionListener {
    void usePosition(PositionConsumer positionConsumer);

    float distance(Cell cell);

    Cell prototype(Organism belongingOrganism, Point2D.Float position, float density);

    Cell opposite(Point2D.Float position, float density);

    OffCell getAnOffCell();

    OnTouchAction createOnTouchAction(PhysicElement element);

    void addDestroyListener(CellDestroyListener destroyListener);

    void destroy();

    void setPosition(Point2D.Float position);

    void render(CellRenderer cellRenderer);
}
