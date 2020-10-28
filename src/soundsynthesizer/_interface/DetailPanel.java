package soundsynthesizer._interface;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This class works as a panel for displaying detailed information about
 * currently clicked {@link ClickableElement} contained in one of the panels
 * of assigned {@link SynthesizerInterface}. The detailed information includes
 * the value of the parameter and the parameter's full name.
 *
 * @author Marek Bobrowski
 */
public class DetailPanel extends Panel {
    /**
     * Color of the bar that visually represents the value of the currently clicked {@link ClickableElement}.
     */
    private static final Color BAR_COLOR = new Color(0.1f, 0.1f, 0.1f);

    /**
     * Color of the text that gives detailed information about the currently clicked {@link ClickableElement}.
     */
    private static final Color TEXT_COLOR = new Color(0.7f, 0.7f, 0.7f);

    /**
     * The font of any text drawn by this panel.
     */
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 14);

    /**
     * The width of the border of the detail bar.
     */
    private static final int borderWidth = 1;

    /**
     * The height of the detail bar.
     */
    private final int barHeight = 15;

    /**
     * The assigned parent interface. The detailed information of the currently clicked {@link ClickableElement}
     * from this parent interface will be displayed by this panel.
     */
    private final SynthesizerInterface synthesizerInterface;

    /**
     * The X coordinate of this panel.
     */
    private final double x;

    /**
     * The Y coordinate of this panel.
     */
    private final double y;

    /**
     * This factor is needed for drawing of the detail bar. The length of the detail bar is determined
     * by the size of the synthesizer window.
     */
    private final double barLengthFactor;

    /**
     * Object of the rectangle that represents the detail bar.
     */
    private Rectangle2D.Double levelBar;

    /**
     * Object of the rectangle that represent the border of the detail bar.
     * It's slightly larger than the actual detail bar. This bar is drawn underneath the detail bar.
     */
    private Rectangle2D.Double borderBar;

    /**
     * Sets up basic parameters of this panel according to the passed arguments.
     * @param x The X coordinate of this panel.
     * @param y The Y coordinate of this panel.
     * @param synthesizerInterface Parent interface object. Information about currently
     *                             clicked {@link ClickableElement} from this parent interface will
     *                             be displayed in this panel.
     */
    public DetailPanel(double x, double y, SynthesizerInterface synthesizerInterface) {
        this.x = x;
        this.y = y;
        this.synthesizerInterface = synthesizerInterface;
        levelBar = new Rectangle2D.Double(x, y, barHeight, 0);
        borderBar = new Rectangle2D.Double(x, y, barHeight, 0);
        barLengthFactor = (synthesizerInterface.getParentWindow().getWidth() - x * 2) / 300;
    }

    /**
     * Draws detailed information about currently clicked {@link ClickableElement}
     * from the assigned parent {@link SynthesizerInterface}.
     * If the currently clicked element is a knob, then the name of the parameter
     * and it's value (in form of a number and a horizontal bar) will be displayed.
     * If the currently clicked element is a radio button, the the name of the parameter
     * and the name of the chosen option will be displayed.
     * @param graphics2D The object that will perform drawing of this panel.
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        ClickableElement clicked = synthesizerInterface.getClickedElement();
        if (clicked == null) {
            return;
        }

        if (clicked instanceof Knob) {
            Knob clickedKnob = (Knob)clicked;
            String name = clickedKnob.getFullName();
            levelBar = new Rectangle2D.Double(x, y, clickedKnob.getAngle() * barLengthFactor, barHeight);
            borderBar = new Rectangle2D.Double(x - borderWidth, y - borderWidth,
                    clickedKnob.getAngle() * barLengthFactor + 2 * borderWidth, barHeight + 2 * borderWidth);
            graphics2D.setColor(TEXT_COLOR);
            graphics2D.fill(borderBar);
            graphics2D.setFont(font);
            graphics2D.drawString(name,
                    synthesizerInterface.getParentWindow().getWidth() / 2 - name.length() * 5 / 2, (int)y - 40);
            graphics2D.drawString(String.valueOf((double)(Math.round(clickedKnob.getValue() * 100)) / 100),
                    synthesizerInterface.getParentWindow().getWidth() / 2, (int)y - 20);
            graphics2D.setColor(BAR_COLOR);
            graphics2D.fill(levelBar);
        }
        if (clicked instanceof RadioButtons) {
            RadioButtons clickedRadioButtons = (RadioButtons) clicked;
            String name = clickedRadioButtons.getName();
            String optionName = clickedRadioButtons.getClickedOptionName();
            graphics2D.setColor(TEXT_COLOR);
            graphics2D.setFont(font);
            graphics2D.drawString(name + ": " + optionName,
                    synthesizerInterface.getParentWindow().getWidth() / 2 - name.length() * 5 / 2, (int)y + 20);
        }
    }
}

