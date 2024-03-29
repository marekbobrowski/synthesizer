package synthesizer._interface.gui.panels;

import synthesizer._interface.gui.units.Knob;
import synthesizer._interface.gui.units.Label;
import synthesizer.utils.delegates.DoubleGetter;
import synthesizer.utils.delegates.DoubleSetter;
import synthesizer.dsp.post.Volume;

/**
 * This class works as a panel for controlling the volume (the output loudness) of the synthesizer.
 *
 * @author Marek Bobrowski
 */
public class VolumePanel extends Panel {
    public VolumePanel(Volume volume, double x, double y) {
        DoubleGetter getVolume = volume::getVolume;
        DoubleSetter setVolume = volume::setVolume;
        clickableElements.add(new Knob(x, y, 30, "vol", "Volume [dB]",
                -45, 3, 1, getVolume, setVolume));
        labels.add(new Label((int)x + 5, (int)y - 35, "volume"));

    }
}
