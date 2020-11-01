package soundsynthesizer._interface;

import soundsynthesizer.delegates.DoubleGetter;
import soundsynthesizer.delegates.DoubleSetter;
import soundsynthesizer.synthesis.Volume;

/**
 * This class works as a panel for controlling the volume (the output loudness) of the synthesizer.
 *
 * @author Marek Bobrowski
 */
public class VolumePanel extends Panel {
    public VolumePanel(Volume volume, double x, double y) {
        DoubleGetter getVolume = volume::getVolume;
        DoubleSetter setVolume = volume::setVolume;
        clickableElements.add(new Knob(x, y, 30, "vol", "Volume",
                0, 1, 0.01, getVolume, setVolume));
        labels.add(new Label((int)x + 5, (int)y - 35, "volume"));

    }
}
