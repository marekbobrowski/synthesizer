package soundsynthesizer._interface;

import soundsynthesizer.delegates.IntSetter;
import soundsynthesizer.delegates.IntGetter;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author Marek Bobrowski
 */
public class RadioButtons implements ClickableElement {
    private static final Color ACTIVE_COLOR = new Color(0.7f, 0.7f, 0.7f);
    private static final Color INACTIVE_COLOR = new Color(0.1f, 0.1f, 0.1f);
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 12);
    private final String name;
    private final Option[] options;
    private final IntGetter parameterGetter;
    private final IntSetter parameterSetter;
    private int lastClicked;

    public RadioButtons(String name, double x, double y, double diameter,
                        int numberOfStates, double distance, String[] names, int[] values,
                        IntGetter parameterGetter, IntSetter parameterSetter) {
        this.name = name;
        options = new Option[numberOfStates];
        this.parameterGetter = parameterGetter;
        this.parameterSetter = parameterSetter;

        for (int i = 0; i < this.options.length; i++) {
            if (i == 0) {
                options[i] = new Option(x, y, diameter,
                        diameter, names[i], values[i]);
            } else {
                options[i] = new Option(x, y + distance * i,
                        diameter, diameter, names[i], values[i]);
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < options.length; i++) {
            graphics2D.setColor(ACTIVE_COLOR);
            graphics2D.setFont(font);
            graphics2D.drawString(options[i].name, (int)(options[i].x + options[i].getWidth() + 3),
                    (int)(options[i].y + options[i].getHeight() - 3));
            if (options[i].value == parameterGetter.get()) {
                graphics2D.setColor(ACTIVE_COLOR);
            } else {
                graphics2D.setColor(INACTIVE_COLOR);
            }
            graphics2D.fill(options[i]);
        }
    }



    @Override
    public void handleClicking() {
        parameterSetter.set(options[lastClicked].value);
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
        for (int i = 0; i < options.length; i++) {
            if (options[i].contains(mouseClick)) {
                lastClicked = i;
                return true;
            }
        }
        return false;
    }

    private static class Option extends Ellipse2D.Double {
        private final String name;
        private final int value;
        public Option(double x, double y,
                      double w, double h, String name,
                      int value) {
            super(x, y, w, h);
            this.name = name;
            this.value = value;
        }
    }

    public String getClickedOptionName() {
        return options[lastClicked].name;
    }

    public String getName() {
        return name;
    }
}
