package synthesizer._interface;

import synthesizer.delegates.DoubleGetter;
import synthesizer.delegates.DoubleSetter;
import synthesizer.synthesis.EnvelopeSettings;

/**
 * This class works as a panel for controlling the parameters of the assigned envelope.
 *
 * @author Marek Bobrowski
 * @see EnvelopeSettings
 */
public class EnvelopePanel extends Panel {

    /**
     * Creates knobs used for controlling the settings of the assigned envelope:
     * an attack knob, a decay knob, a sustain knob and a release knob.
     *
     * @param envelopeSettings These settings will be modified by this panel.
     * @param x The X coordinate of this panel.
     * @param y The Y coordinate of this panel.
     */
    public EnvelopePanel(EnvelopeSettings envelopeSettings, double x, double y) {
        DoubleGetter getAttack = envelopeSettings::getAttack;
        DoubleSetter setAttack = envelopeSettings::setAttack;
        DoubleGetter getDecay = envelopeSettings::getDecay;
        DoubleSetter setDecay = envelopeSettings::setDecay;
        DoubleGetter getSustain = envelopeSettings::getSustain;
        DoubleSetter setSustain = envelopeSettings::setSustain;
        DoubleGetter getRelease = envelopeSettings::getRelease;
        DoubleSetter setRelease = envelopeSettings::setRelease;
        clickableElements.add(new Knob(x,y,30,"att","Envelope attack",
                0,10, 0.01, getAttack, setAttack));
        clickableElements.add(new Knob(x + 60,y,30,"dec","Envelope decay",
                0,10, 0.01, getDecay, setDecay));
        clickableElements.add(new Knob(x + 120,y,30,"sus","Envelope sustain",
                0,1, 0.01, getSustain, setSustain));
        clickableElements.add(new Knob(x + 180,y,30,"rel","Envelope release",
                0,10, 0.01, getRelease, setRelease));
        labels.add(new Label((int)x + 60, (int)y - 35, "envelope"));
    }

}
