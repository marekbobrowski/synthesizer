package synthesizer;

import synthesizer._interface.gui.SynthesizerWindow;
import synthesizer.signalprocessing.Synthesizer;

/**
 * Class used for starting the synthesizer application.
 *
 * @author Marek Bobrowski
 */
public class Main {

    /**
     * Runs the synthesizer and creates a window for it.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Synthesizer synthesizer = new Synthesizer();
        Thread thread = new Thread(synthesizer);
        thread.setName("Synth");
        thread.start();
        SynthesizerWindow synthesizerWindow = new SynthesizerWindow(synthesizer);
    }
}
