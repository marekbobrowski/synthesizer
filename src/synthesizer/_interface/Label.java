package synthesizer._interface;

import java.awt.*;

/**
 * A label that can be drawn in any {@link Panel} or {@link javax.swing.JPanel} that uses
 * {@link Graphics2D} for drawing.
 */
public class Label {
    /**
     * Color of a label.
     */
    private static final Color color = new Color(0.7f, 0.7f, 0.7f);

    /**
     * Font of a label.
     */
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 14);

    /**
     * The X coordinate of this label.
     */
    private final int x;

    /**
     * The Y coordinate of this label.
     */
    private final int y;

    /**
     * The text of this label.
     */
    private final String text;

    /**
     * Assigns passed arguments to the fields.
     * @param x The X coordinate of this label.
     * @param y The Y coordinate of this label.
     * @param text The text of this label.
     */
    public Label(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    /**
     * Draws a label according to it's position and text using the passed {@link Graphics2D} object.
     * @param graphics2D The object that will perform drawing of the label.
     */
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);
        graphics2D.setFont(font);
        graphics2D.drawString(text, x, y);
    }
}
