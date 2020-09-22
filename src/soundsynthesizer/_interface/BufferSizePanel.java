package soundsynthesizer._interface;

import soundsynthesizer._interface.knobdelegates.IntGetter;
import soundsynthesizer._interface.knobdelegates.IntSetter;
import soundsynthesizer.synthesis.Converter;
public class BufferSizePanel extends Panel {
    public BufferSizePanel(Converter converter, double x, double y) {
        IntSetter setBufferSize = converter::setBufferSize;
        IntGetter getBufferSize = converter::getBufferSize;
        clickableElements.add(new RadioButtons(x, y, 15, 4, 15,
                new String[] {"256","384","512","2048"},
                new int[] {256, 384, 512, 2048}, getBufferSize, setBufferSize));
    }
}
