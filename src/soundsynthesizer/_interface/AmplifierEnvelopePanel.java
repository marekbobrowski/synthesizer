package soundsynthesizer._interface;

import soundsynthesizer._interface.knobdelegates.DoubleGetter;
import soundsynthesizer._interface.knobdelegates.DoubleSetter;
import soundsynthesizer.synthesis.AmplifierEnvelopeSettings;

/**
 * @author Marek Bobrowski
 */
public class AmplifierEnvelopePanel extends Panel {
    public AmplifierEnvelopePanel(AmplifierEnvelopeSettings amplifierEnvelopeSettings, double x, double y) {
        DoubleGetter getAttack = amplifierEnvelopeSettings::getAttack;
        DoubleSetter setAttack = amplifierEnvelopeSettings::setAttack;
        DoubleGetter getDecay = amplifierEnvelopeSettings::getDecay;
        DoubleSetter setDecay = amplifierEnvelopeSettings::setDecay;
        DoubleGetter getSustain = amplifierEnvelopeSettings::getSustain;
        DoubleSetter setSustain = amplifierEnvelopeSettings::setSustain;
        DoubleGetter getRelease = amplifierEnvelopeSettings::getRelease;
        DoubleSetter setRelease = amplifierEnvelopeSettings::setRelease;
        clickableElements.add(new Knob(x,y,30,"att","Amplifier Envelope Attack",
                0,10, 0.01, getAttack, setAttack));
        clickableElements.add(new Knob(x+60,y,30,"dec","Amplifier Envelope Decay",
                0,10, 0.01, getDecay, setDecay));
        clickableElements.add(new Knob(x+120,y,30,"sus","Amplifier Envelope Sustain",
                0,1, 0.01, getSustain, setSustain));
        clickableElements.add(new Knob(x+180,y,30,"rel","Amplifier Envelope Release",
                0,10, 0.01, getRelease, setRelease));
    }

}
