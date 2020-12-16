package soundsynthesizer._interface;

import soundsynthesizer.delegates.DoubleSetter;
import soundsynthesizer.delegates.DoubleGetter;
import soundsynthesizer.synthesis.Reverb;

/**
 * This class works as a panel for controlling the parameters of the assigned {@link Reverb}.
 *
 * @author Marek Bobrowski
 * @see Reverb
 */
public class ReverbPanel extends Panel {
    /**
     * Creates knobs used for controlling the settings of the assigned reverb:
     * a knob for reverb decay and a knob for dry/wet mix.
     *
     * @param reverb The settings of this reverb will be controlled by this panel.
     * @param x The X coordinate of this panel.
     * @param y The Y coordinate of this panel.
     */
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
