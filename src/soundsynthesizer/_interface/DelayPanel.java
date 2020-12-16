package soundsynthesizer._interface;

import soundsynthesizer.delegates.DoubleGetter;
import soundsynthesizer.delegates.DoubleSetter;
import soundsynthesizer.synthesis.Delay;

/**
 * This class works as a panel for controlling the settings of the assigned delay effect.
 *
 * @author Marek Bobrowski
 * @see Delay
 */
public class DelayPanel extends Panel {

    /**
     * Creates knobs used for controlling the settings of the assigned delay effect.
     *
     * @param delay It's parameters will be controlled by this panel.
     * @param x The X coordinate of this panel.
     * @param y The Y coordinate of this panel.
     */
    public DelayPanel(Delay delay, double x, double y) {
        DoubleGetter getFeedback = delay::getFeedback;
        DoubleSetter setFeedback = delay::setFeedback;
        DoubleGetter getMix = delay::getMix;
        DoubleSetter setMix = delay::setMix;
        clickableElements.add(new Knob(x,y,30,"fdb","Delay feedback",
                0,0.99, 0.01, getFeedback, setFeedback));
        clickableElements.add(new Knob(x+60,y,30,"d/w","Delay dry/wet",
                0,1, 0.01, getMix, setMix));
        labels.add(new Label((int)x + 5, (int)y - 35, "delay fx"));

    }
}

