package soundsynthesizer._interface;

import soundsynthesizer.delegates.IntGetter;
import soundsynthesizer.delegates.IntSetter;
import soundsynthesizer.synthesis.Converter;

/**
 * This class works as a panel for controlling the sound buffer size of assigned {@link Converter} object.
 *
 * @author Marek Bobrowski
 */
public class BufferSizePanel extends Panel {

    /**
     * Creates radio buttons used for selecting the desired buffer size.
     *
     * @param converter this converter's buffer size will be controlled by this panel
     * @param x The X coordinate of this panel.
     * @param y The Y coordinate of this panel.
     */
    public BufferSizePanel(Converter converter, double x, double y) {
        IntSetter setBufferSize = converter::setBufferSize;
        IntGetter getBufferSize = converter::getBufferSize;
        clickableElements.add(new RadioButtons("Buffer size", x, y, 15, 5, 15,
                new String[] {"256","384","512", "1024", "2048"},
                new int[] {256, 384, 512, 1024, 2048}, getBufferSize, setBufferSize));
        labels.add(new Label((int)x, (int)y - 5, "buffer size"));
    }
}
