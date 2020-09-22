package soundsynthesizer._interface;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Marek Bobrowski
 */
public class DetailPanel extends Panel {
    private static final Color BAR_COLOR = new Color(0.1f, 0.1f, 0.1f);
    private static final Color TEXT_COLOR = new Color(0.7f, 0.7f, 0.7f);
    private final SynthesizerInterface synthesizerInterface;
    private final double x;
    private final double y;
    private final int height;
    private final double barLengthFactor;
    private Rectangle2D.Double levelBar;

    public DetailPanel(double x, double y, int height, SynthesizerInterface synthesizerInterface) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.synthesizerInterface = synthesizerInterface;
        levelBar = new Rectangle2D.Double(x, y, height, 0);
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
            graphics2D.setColor(BAR_COLOR);
            graphics2D.fill(levelBar);
            graphics2D.setColor(TEXT_COLOR);
            graphics2D.drawString(name, synthesizerInterface.getParentWindow().getWidth() / 2 - name.length() * 5 / 2,
                    (int)y - 40);
            graphics2D.drawString(String.valueOf((double)(Math.round(clickedKnob.getValue() * 100)) / 100),
                    synthesizerInterface.getParentWindow().getWidth() / 2, (int)y - 20);
        }
        if (clicked instanceof RadioButtons) {
            String name = ((RadioButtons)clicked).getClickedName();
            graphics2D.drawString(name, synthesizerInterface.getParentWindow().getWidth() / 2 - name.length() * 5 / 2,
                    (int)y + 20);
        }
    }
}

