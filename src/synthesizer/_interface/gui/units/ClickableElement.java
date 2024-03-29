package synthesizer._interface.gui.units;

import synthesizer._interface.gui.panels.Panel;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * This interface is used for any UI element that is intended to do something when clicked (and maybe dragged).
 * Such clickable element has it's use in any class that extends {@link Panel}.
 *
 * @author Marek Bobrowski
 */
public interface ClickableElement {

    /**
     * Handles the event of being clicked.
     */
    void handleClicking();

    /**
     * Handles the event of mouse being dragged.
     * Assumes that the last click was done on this ClickableElement and now the mouse is being dragged.
     * @param mouseClick Current mouse position.
     */
    void handleDragging(Point mouseClick);

    /**
     * Handles the event of mouse being released after clicking this element and after possibly being dragged.
     */
    void handleReleasing();

    /**
     * Tells if this ClickableElement should do something when being dragged after being clicked.
     * For example, a knob should return true and radio buttons should return false.
     *
     * @return If this ClickableElement is draggable.
     */
    boolean isDraggable();

    /**
     * Checks whether the mouse clicked on this ClickableElement.
     * @param mouseClick Current mouse position.
     * @return If the mouse clicked on this ClickableElement.
     */
    boolean isClicked(Point mouseClick);

    /**
     * Draws this ClickableElement.
     * @param graphics2d Object that will perform drawing of this ClickableElement.
     */
    void draw(Graphics2D graphics2d);
}

