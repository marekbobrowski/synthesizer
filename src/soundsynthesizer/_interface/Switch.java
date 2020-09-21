package soundsynthesizer._interface;

import soundsynthesizer._interface.knobdelegates.IntSetter;
import soundsynthesizer._interface.knobdelegates.IntGetter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

/**
 * @author Marek Bobrowski
 */
public class Switch implements ClickableElement {
    private static final Color ACTIVE_COLOR = new Color(0.7f, 0.7f, 0.7f);
    private static final Color INACTIVE_COLOR = new Color(0.1f, 0.1f, 0.1f);
    private final SwitchPoint[] switchPoints;
    private final IntGetter parameterGetter;
    private final IntSetter parameterSetter;
    private int lastClicked;

    public Switch(double x, double y, double diameter,
                  int numberOfStates, double distance, String[] names, int[] values,
                  IntGetter parameterGetter, IntSetter parameterSetter) {
        switchPoints = new SwitchPoint[numberOfStates];
        this.parameterGetter = parameterGetter;
        this.parameterSetter = parameterSetter;

        for (int i = 0; i < this.switchPoints.length; i++) {
            if (i == 0) {
                switchPoints[i] = new SwitchPoint(x, y, diameter,
                        diameter, names[i], values[i]);
            } else {
                switchPoints[i] = new SwitchPoint(x, y+distance*i,
                        diameter, diameter, names[i], values[i]);
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < switchPoints.length; i++) {
            if (switchPoints[i].value == parameterGetter.get()) {
                graphics2D.setColor(ACTIVE_COLOR);
            } else {
                graphics2D.setColor(INACTIVE_COLOR);
            }
            graphics2D.fill(switchPoints[i]);
        }
    }



    @Override
    public void handleClicking() {
        parameterSetter.set(switchPoints[lastClicked].value);
    }

    @Override
    public boolean isDraggable() {
        return false;
    }

    @Override
    public void handleReleasing() {
    }

    @Override
    public void handleDragging(Point mouseClick) {
    }

    @Override
    public boolean isClicked(Point mouseClick) {
        for (int i = 0; i < switchPoints.length; i++) {
            if (switchPoints[i].contains(mouseClick)) {
                lastClicked = i;
                return true;
            }
        }
        return false;
    }

    private static class SwitchPoint extends Ellipse2D.Double {
        private final String name;
        private final int value;
        public SwitchPoint(double x, double y,
                           double w, double h, String name,
                           int value) {
            super(x, y, w, h);
            this.name = name;
            this.value = value;
        }
    }

    public String getClickedName() {
        return switchPoints[lastClicked].name;
    }

}
