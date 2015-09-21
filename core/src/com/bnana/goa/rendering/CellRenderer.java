package com.bnana.goa.rendering;

import com.bnana.goa.cell.AttractorOffCell;
import com.bnana.goa.cell.CellConsumer;
import com.bnana.goa.cell.OffCell;

import java.awt.geom.Point2D;

/**
 * Created by Luca on 9/21/2015.
 */
public interface CellRenderer extends CellConsumer{
    void renderAttractorOffCell(AttractorOffCell attractorOffCell, Point2D.Float position, float density);
}
