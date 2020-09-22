package soundsynthesizer._interface;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Marek Bobrowski
 */
public class DetailPanel extends Panel {
    private static final Color BAR_COLOR = new Color(0.1f, 0.1f, 0.1f);
    private static final Color TEXT_COLOR = new Color(0.7f, 0.7f, 0.7f);
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 14);
    private static final int borderWidth = 1;
    private final SynthesizerInterface synthesizerInterface;
    private final double x;
    private final double y;
    private final int height;
    private final double barLengthFactor;
    private Rectangle2D.Double levelBar;
    private Rectangle2D.Double borderBar;

    public DetailPanel(double x, double y, int height, SynthesizerInterface synthesizerInterface) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.synthesizerInterface = synthesizerInterface;
        levelBar = new Rectangle2D.Double(x, y, height, 0);
        borderBar = new Rectangle2D.Double(x, y, height, 0);
        barLengthFactor = (synthesizerInterface.getParentWindow().getWidth() - x * 2) / 300;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        ClickableElement clicked = synthesizerInterface.getClickedElement();
        if (clicked == null) {
            return;
        }

        if (clicked instanceof Knob) {
            Knob clickedKnob = (Knob)clicked;
            String name = clickedKnob.getFullName();
            levelBar = new Rectangle2D.Double(x, y, clickedKnob.getAngle() * barLengthFactor, height);
            borderBar = new Rectangle2D.Double(x - borderWidth, y - borderWidth,
                    clickedKnob.getAngle() * barLengthFactor + 2 * borderWidth, height + 2 * borderWidth);
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

