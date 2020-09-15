package soundsynthesizer._interface;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Marek Bobrowski
 */
abstract class Panel {
    final ArrayList<ClickableElement> clickableElements = new ArrayList<>();
    ClickableElement returnClickedElement(Point mouseClick) {
        for (ClickableElement e : clickableElements) {
            if (e.isClicked(mouseClick)){
                return e;
            }
        }
        return null;
    }

    void draw(Graphics2D graphics2d) {
        for(ClickableElement e : clickableElements) {
            e.draw(graphics2d);
        }
    }
}
