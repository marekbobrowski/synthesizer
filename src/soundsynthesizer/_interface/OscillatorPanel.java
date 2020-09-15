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
        clickableElements.add(new Knob(x,y,30,"semi","Oscillator 1 Semitone Shift",
                -24,24, getOscillator1SemitonesShift, setOscillator1SemitonesShift));
        clickableElements.add(new Knob(x+60,y,30,"cent","Oscillator 1 Cent Shift",
                -100,100, getOscillator1CentsShift, setOscillator1CentsShift));
        clickableElements.add(new Knob(x+120,y,30,"semi","Oscillator 2 Semitone Shift",
                -24,24, getOscillator2SemitonesShift, setOscillator2SemitonesShift));
        clickableElements.add(new Knob(x+180,y,30,"cent","Oscillator 2 Cent Shift",
                -100,100, getOscillator2CentsShift, setOscillator2CentsShift));
        clickableElements.add(new Knob(x+240,y,30,"mix","Oscillators mix",
                0,1, getMixValue, setMixValue));
        clickableElements.add(new Switch(x+300, y, 15, 4, 15,
                new String[]{"sine","triangle","saw","square"}, getOscillator1Type, setOscillator1Type));
        clickableElements.add(new Switch(x+360, y, 15, 4, 15,
                new String[]{"sine","triangle","saw","square"}, getOscillator2Type, setOscillator2Type));
    }
}
