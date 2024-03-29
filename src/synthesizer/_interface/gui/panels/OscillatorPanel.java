package synthesizer._interface.gui.panels;

import synthesizer._interface.gui.units.Knob;
import synthesizer._interface.gui.units.Label;
import synthesizer._interface.gui.units.RadioButtons;
import synthesizer.utils.delegates.IntGetter;
import synthesizer.utils.delegates.DoubleSetter;
import synthesizer.utils.delegates.IntSetter;
import synthesizer.utils.delegates.DoubleGetter;
import synthesizer.models.OscillatorSettings;

/**
 * This class works as a panel for controlling the waveform of the synthesizer oscillators.
 *
 * @author Marek Bobrowski
 */
public class OscillatorPanel extends Panel {

    /**
     * Creates radio buttons used for selecting the oscillator waveforms. Four waveforms for each of 2 oscillators.
     *
     * @param oscillatorSettings These settings will be controlled by this panel.
     * @param x The X coordinate of this panel.
     * @param y The Y coordinate of this panel.
     */
    public OscillatorPanel(OscillatorSettings oscillatorSettings, double x, double y) {
        IntGetter getOscillator1Type = oscillatorSettings::getOscillator1Shape;
        IntSetter setOscillator1Type = oscillatorSettings::setOscillator1Shape;
        IntGetter getOscillator2Type = oscillatorSettings::getOscillator2Shape;
        IntSetter setOscillator2Type = oscillatorSettings::setOscillator2Shape;
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
        clickableElements.add(new RadioButtons("Oscillator 1 waveform", x, y, 15, 4,
                15, new String[] {"sine","triangle","saw","square"}, new int[] {0,1,2,3},
                getOscillator1Type, setOscillator1Type));
        clickableElements.add(new Knob(x + 90, y + 30,30,"semi",
                "Oscillator 1 semitone Shift", -24,24, 1,
                getOscillator1SemitonesShift, setOscillator1SemitonesShift));
        clickableElements.add(new Knob(x + 150, y + 30,30,"cent",
                "Oscillator 1 cent Shift", -50,50, 1, getOscillator1CentsShift,
                setOscillator1CentsShift));
        clickableElements.add(new RadioButtons("Oscillator 2 waveform", x, y + 90, 15,
                4, 15, new String[] {"sine","triangle","saw","square"}, new int[] {0,1,2,3},
                getOscillator2Type, setOscillator2Type));
        clickableElements.add(new Knob(x + 90, y + 120,30,"semi",
                "Oscillator 2 semitone shift", -24,24, 1, getOscillator2SemitonesShift,
                setOscillator2SemitonesShift));
        clickableElements.add(new Knob(x + 150, y + 120,30,"cent",
                "Oscillator 2 cent shift", -50,50, 1,
                getOscillator2CentsShift, setOscillator2CentsShift));
        clickableElements.add(new Knob(x + 210, y + 75,30,"mix","Oscillators mix",
                0,1, 0.01, getMixValue, setMixValue));
        labels.add(new Label((int)x, (int)y - 5, "osc1"));
        labels.add(new Label((int)x, (int)y + 85, "osc2"));



    }
}
