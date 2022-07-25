package synthesizer._interface.gui.panels;

import synthesizer._interface.gui.units.ClickableElement;
import synthesizer._interface.gui.units.Label;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * This class works as a part of the synthesizer GUI used for controlling one of the synthesizer modules.
 * For example reverb settings panel, envelope settings panel.
 * Such panel can contain labels and clickable elements: knobs, radio buttons.
 *
 * @author Marek Bobrowski
 */
public abstract class Panel {
    /**
     * List of clickable elements (knobs, radio buttons) to draw
     * and iterate through when checking if they were clicked.
     */
    final ArrayList<ClickableElement> clickableElements = new ArrayList<>();

    /**
     * List of labels to draw.
     */
    final ArrayList<Label> labels = new ArrayList<>();

    /**
     * Checks if any of the clickable elements contained by this panel was clicked.
     * @param mouseClick The position of the mouse when the user clicked.
     * @return The {@link ClickableElement} that was clicked if any was clicked. If none was clicked,
     * then null is returned.
     */
    public ClickableElement returnClickedElement(Point mouseClick) {
        for (ClickableElement e : clickableElements) {
            if (e.isClicked(mouseClick)){
                return e;
            }
        }
        return null;
    }

    /**
     * Draws all the labels and the clickable elements contained in this panel.
     * @param graphics2d The object that will perform drawing of the labels and the clickable elements.
     */
    public void draw(Graphics2D graphics2d) {
        for (ClickableElement e : clickableElements) {
            e.draw(graphics2d);
        }
        for (Label l : labels) {
            l.draw(graphics2d);
        }
    }
}
