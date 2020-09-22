package soundsynthesizer._interface;

import soundsynthesizer._interface.knobdelegates.IntGetter;
import soundsynthesizer._interface.knobdelegates.DoubleSetter;
import soundsynthesizer._interface.knobdelegates.IntSetter;
import soundsynthesizer._interface.knobdelegates.DoubleGetter;
import soundsynthesizer.synthesis.OscillatorSettings;

/**
 * @author Marek Bobrowski
 */
public class OscillatorPanel extends Panel {
    public OscillatorPanel(OscillatorSettings oscillatorSettings, double x, double y) {
        IntGetter getOscillator1Type = oscillatorSettings::getOscillator1Type;
        IntSetter setOscillator1Type = oscillatorSettings::setOscillator1Type;
        IntGetter getOscillator2Type = oscillatorSettings::getOscillator2Type;
        IntSetter setOscillator2Type = oscillatorSettings::setOscillator2Type;
        DoubleGetter getOscillator1SemitonesShift = oscillatorSettings::getOscillator1SemitonesShift;
        DoubleSetter setOscillator1SemitonesShift = oscillatorSettings::setOscillator1SemitonesShift;
        DoubleGetter getOscillator1CentsShift = oscillatorSettings::getOscillator1CentsShift;
        DoubleSetter setOscillator1CentsShift = oscillatorSettings::setOscillator1CentsShift;
        DoubleGetter getOscillator2SemitonesShift = oscillatorSettings::getOscillator2SemitonesShift;
        DoubleSetter setOscillator2SemitonesShift = oscillatorSettings::setOscillator2SemitonesShift;
        DoubleGetter getOscillator2CentsShift = oscillatorSettings::getOscillator2CentsShift;
        DoubleSetter setOscillator2CentsShift = oscillatorSettings::setOscillator2CentsShift;
        DoubleGetter getMixValue = oscillatorSettings::getMixValue;
        DoubleSetter setMixValue = oscillatorSettings::setMixValue;
        clickableElements.add(new RadioButtons(x, y, 15, 4, 15,
                new String[] {"sine","triangle","saw","square"}, new int[] {0,1,2,3},
                getOscillator1Type, setOscillator1Type));
        clickableElements.add(new Knob(x + 90, y + 30,30,"semi","Oscillator 1 Semitone Shift",
                -24,24, 1, getOscillator1SemitonesShift, setOscillator1SemitonesShift));
        clickableElements.add(new Knob(x + 150, y + 30,30,"cent","Oscillator 1 Cent Shift",
                -50,50, 1, getOscillator1CentsShift, setOscillator1CentsShift));
        clickableElements.add(new RadioButtons(x, y + 90, 15, 4, 15,
                new String[] {"sine","triangle","saw","square"}, new int[] {0,1,2,3},
                getOscillator2Type, setOscillator2Type));
        clickableElements.add(new Knob(x + 90, y + 120,30,"semi","Oscillator 2 Semitone Shift",
                -24,24, 1, getOscillator2SemitonesShift, setOscillator2SemitonesShift));
        clickableElements.add(new Knob(x + 150, y + 120,30,"cent","Oscillator 2 Cent Shift",
                -50,50, 1, getOscillator2CentsShift, setOscillator2CentsShift));
        clickableElements.add(new Knob(x + 210, y + 75,30,"mix","Oscillators mix",
                0,1, 0.01, getMixValue, setMixValue));


    }
}
