package soundsynthesizer;

import soundsynthesizer.synthesis.Synthesizer;

/**
 * @author Marek Bobrowski
 */
public class Main {
    public static void main(String[] args) {
        Synthesizer synthesizer = new Synthesizer();
        Thread thread = new Thread(synthesizer);
        thread.setName("Synth");
        thread.start();
        SynthesizerWindow synthesizerWindow = new SynthesizerWindow(synthesizer);
    }
}
