package soundsynthesizer._interface;

import soundsynthesizer._interface.knobdelegates.DoubleGetter;
import soundsynthesizer._interface.knobdelegates.DoubleSetter;
import soundsynthesizer.synthesis.Delay;

/**
 * @author Marek Bobrowski
 */
public class DelayPanel extends Panel {
    public DelayPanel(Delay delay, double x, double y) {
        DoubleGetter getFeedback = delay::getFeedback;
        DoubleSetter setFeedback = delay::setFeedback;
        DoubleGetter getMix = delay::getMix;
        DoubleSetter setMix = delay::setMix;
        clickableElements.add(new Knob(x,y,30,"fdb","Delay feedback",
                0,0.99, 0.01, getFeedback, setFeedback));
        clickableElements.add(new Knob(x+60,y,30,"d/w","Delay dry/wet",
                0,1, 0.01, getMix, setMix));

    }
}

