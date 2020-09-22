package soundsynthesizer._interface;

import soundsynthesizer._interface.knobdelegates.DoubleSetter;
import soundsynthesizer._interface.knobdelegates.DoubleGetter;
import soundsynthesizer.synthesis.Reverb;

/**
 * @author Marek Bobrowski
 */
public class ReverbPanel extends Panel {
    public ReverbPanel(Reverb reverb, double x, double y) {
        DoubleGetter getDecay = reverb::getDecay;
        DoubleSetter setDecay = reverb::setDecay;
        DoubleGetter getMix = reverb::getMix;
        DoubleSetter setMix = reverb::setMix;
        clickableElements.add(new Knob(x,y,30,"dec","Reverb decay",
                0.5, 0.99, 0.01, getDecay, setDecay));
        clickableElements.add(new Knob(x+60, y, 30, "d/w", "Reverb dry/wet",
                0, 1, 0.01, getMix, setMix));
        labels.add(new Label((int)x + 5, (int)y - 35, "reverb fx"));
    }
}
