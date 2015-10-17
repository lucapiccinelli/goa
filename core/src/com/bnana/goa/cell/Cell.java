package com.bnana.goa.cell;

import com.badlogic.gdx.math.Vector2;
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

    Cell prototype(Organism belongingOrganism, Vector2 position, float density);

    Cell opposite(Vector2 position, float density);

    OffCell getAnOffCell();

    OnTouchAction createOnTouchAction(PhysicElement element);

    void addDestroyListener(CellDestroyListener destroyListener);

    void destroy();

    void setPosition(Vector2 position);

    void render(CellRenderer cellRenderer);

    void addPositionListener(PositionListener positionListener);
}
