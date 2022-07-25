package synthesizer._interface.gui.units;

import synthesizer._interface.gui.panels.Panel;
import synthesizer.utils.delegates.IntSetter;
import synthesizer.utils.delegates.IntGetter;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Represents a group of radio buttons.
 * Radio buttons are another way for controlling a synthesizer parameter.
 * They allow user to choose one from few options.
 * For example, choosing a buffer size between: 256, 384, 512, 1024, 2048.
 * They're useful inside a {@link Panel}.
 *
 * @author Marek Bobrowski
 */
public class RadioButtons implements ClickableElement {
    /**
     * Color of a chosen radio button.
     */
    private static final Color ACTIVE_COLOR = new Color(0.7f, 0.7f, 0.7f);

    /**
     * Color of a not chosen radio button.
     */
    private static final Color INACTIVE_COLOR = new Color(0.1f, 0.1f, 0.1f);

    /**
     * Font of an option (displayed next to it's radio button).
     */
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 12);

    /**
     * Name of the parameter controlled by this radio button.
     */
    private final String name;

    /**
     * List of available options.
     */
    private final Option[] options;

    /**
     * A delegate for getting the value of the assigned synthesizer parameter.
     * For example, if we want these radio buttons to display the current settings
     * of the buffer size, then we have to assign reverb.getBufferSize to this field.
     */
    private final IntGetter parameterGetter;

    /**
     * A delegate for setting the value of the assigned synthesizer parameter.
     * For example, if we want these radio buttons to control the buffer size,
     * then we have to assign converter.setBufferSize to this field.
     */
    private final IntSetter parameterSetter;

    /**
     * The number of the last clicked option.
     */
    private int lastClicked;

    /**
     * Sets up the basic parameters of these radio buttons.
     * @param name The name of the parameter controlled by these radio buttons group.
     * @param x The X coordinate of this radio button group.
     * @param y The Y coordinate of this radio button group.
     * @param diameter The diameter of each radio button from this group.
     * @param numberOfOptions The number of radio buttons available in this group.
     * @param distance The distance between each radio button from this group.
     * @param names An array of names for each option. For example: {"Name 1" , "Name 2", "Name 3"}.
     *              These names are just for displaying - radio buttons won't set these values to the parameter.
     * @param values An array of values for each option. For example: {1, 2, 3}. Radio buttons will assign
     *               these values to the parameters.
     * @param parameterGetter A delegate for getting the value of the assigned synthesizer parameter.
     *                        For example, if we want these radio buttons to display the current settings
     *                        of the buffer size, then we have to assign reverb.getBufferSize to this field.
     * @param parameterSetter A delegate for setting the value of the assigned synthesizer parameter.
     *                        For example, if we want these radio buttons to control the buffer size,
     *                        then we have to assign converter.setBufferSize to this field.
     */
    public RadioButtons(String name, double x, double y, double diameter,
                        int numberOfOptions, double distance, String[] names, int[] values,
                        IntGetter parameterGetter, IntSetter parameterSetter) {
        this.name = name;
        options = new Option[numberOfOptions];
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

    /**
     * Draws this group of radio buttons.
     * @param graphics2D The object that will perform drawing of these radio buttons.
     */
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


    /**
     * Handle the event of clicking on one of the radio buttons from this group.
     */
    @Override
    public void handleClicking() {
        parameterSetter.set(options[lastClicked].value);
    }

    /**
     * Should always return false, because radio buttons are just for clicking.
     * @return False, because radio buttons are only for clicking.
     */
    @Override
    public boolean isDraggable() {
        return false;
    }

    /**
     * Handle the event of mouse-releasing one of the radio buttons from this group.
     */
    @Override
    public void handleReleasing() {
    }

    /**
     * Handle the event of dragging.
     * @param mouseClick Current mouse position.
     */
    @Override
    public void handleDragging(Point mouseClick) {
    }

    /**
     * Tells if the specified mouse position lays on one of the radio buttons from this group.
     * @param mouseClick Current mouse position.
     * @return If the specified mouse position lays on one of the radio buttons from this group.
     */
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

    /**
     * Nested class that represents an option within radio buttons group.
     */
    private static class Option extends Ellipse2D.Double {
        /**
         * The name of this option.
         */
        private final String name;

        /**
         * The value of this option.
         */
        private final int value;

        /**
         * Assigns passed arguments to the class fields.
         * @param x The X coordinate of this option's radio button.
         * @param y The Y coordinate of this option's radio button.
         * @param w The width of this option's radio button.
         * @param h The height of this option's radio button.
         * @param name The name of this option.
         * @param value The value of this option.
         */
        public Option(double x, double y,
                      double w, double h, String name,
                      int value) {
            super(x, y, w, h);
            this.name = name;
            this.value = value;
        }
    }

    /**
     * Returns the name of the last clicked option.
     * @return The name of the last clicked option.
     */
    public String getClickedOptionName() {
        return options[lastClicked].name;
    }

    /**
     * Returns the name of the parameter controlled by this radio button group.
     * @return The name of the parameter controlled by this radio button group.
     */
    public String getName() {
        return name;
    }
}
