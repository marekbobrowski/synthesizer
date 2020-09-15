package soundsynthesizer._interface;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * @author Marek Bobrowski
 */
interface ClickableElement {
    void handleClicking();
    void handleDragging(Point mouseClick);
    void handleReleasing();
    boolean isDraggable();
    boolean isClicked(Point mouseClick);
    void draw(Graphics2D graphics2d);
}

