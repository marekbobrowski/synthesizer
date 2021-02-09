package synthesizer.synthesis;

import synthesizer._interface.MidiHandler;
import synthesizer.delegates.IntGetter;

/**
 * This class works as a single voice of a sound synthesizer.
 * It generates signal using the available oscillators and runs them through an envelope.
 *
 * @author Marek Bobrowski
 */
public class Voice {
    /**
     * The object that will process the signal by applying an envelope to it.
     */
    private final EnvelopeGenerator envelopeGenerator;

    /**
     * The object that describes the parameters of the oscillators.
     */
    private final OscillatorSettings oscillatorSettings;

    /**
     * The synthesizer that created this voice.
     */
    private final Synthesizer synthesizer;

    /**
     * The midi handler that triggered this voice.
     */
    private final MidiHandler midiHandler;

    /**
     * The frequency of this voice.
     */
    private final double frequency;

    /**
     * The first oscillator that generates the signal for this voice.
     */
    private final Oscillator oscillator1;

    /**
     * The second oscillator that generates the signal for this voice.
     */
    private final Oscillator oscillator2;

    /**
     * Tells if this voice has been released on the keyboard.
     */
    private boolean released = false;

    /**
     * Tells if this voice has ended playing (if the release stage of the envelopes has ended).
     */
    private boolean ended = false;

    /**
     * Voice constructor.
     * @param synthesizer The synthesizer that is playing this voice.
     * @param midiHandler The midi handler that triggered this voice.
     * @param frequency The frequency of this voice.
     */
    public Voice(Synthesizer synthesizer, MidiHandler midiHandler, double frequency) {
        this.synthesizer = synthesizer;
        this.midiHandler = midiHandler;
        envelopeGenerator = new EnvelopeGenerator(this, synthesizer.getEnvelopeSettings());
        oscillatorSettings = synthesizer.getOscillatorSettings();
        this.frequency = frequency;
        IntGetter wave1 = oscillatorSettings::getOscillator1Shape;
        IntGetter wave2 = oscillatorSettings::getOscillator2Shape;
        oscillator1 = new Oscillator(wave1);
        oscillator2 = new Oscillator(wave2);
    }

    /**
     * Generates a sound buffer for this voice.
     * It gathers buffers from the available oscillators and applies an envelope to the signal.
     * @param bufferSize The size of the buffer to be generated.
     * @return The generated sound buffer for this voice.
     */
    public double[][] prepareBuffer(int bufferSize) {
        double[][] oscillator1Buffer = oscillator1.generateBuffer(frequency *
                Math.pow(2,(oscillatorSettings.getOscillator1SemitonesShift()*100
                        + oscillatorSettings.getOscillator1CentsShift())/1200.0),
                bufferSize);
        double[][] oscillator2Buffer = oscillator2.generateBuffer(frequency *
                Math.pow(2,(oscillatorSettings.getOscillator2SemitonesShift()*100
                        + oscillatorSettings.getOscillator2CentsShift())/1200.0),
                bufferSize);
        double[][] mixedBuffer = mixOscillators(oscillator1Buffer, oscillator2Buffer);
        envelopeGenerator.processBuffer(mixedBuffer);
        return mixedBuffer;
    }

    /**
     * It mixes the buffers from 2 oscillators.
     * @param buffer1 The buffer from the first oscillator.
     * @param buffer2 The buffer from the second oscillator.
     * @return The mixed buffer.
     */
    public double[][] mixOscillators(double[][] buffer1, double[][] buffer2) {
        double[][] mixedBuffer = new double[buffer1.length][buffer1[0].length];
        for (int i = 0; i < mixedBuffer[0].length; i++) {
            mixedBuffer[0][i] = buffer1[0][i] * (1 - oscillatorSettings.getMixValue())
                    + buffer2[0][i] * oscillatorSettings.getMixValue();
            mixedBuffer[1][i] = mixedBuffer[0][i];
        }
        return mixedBuffer;
    }

    /**
     * Triggers the release of this voice.
     */
    public void triggerRelease() {
        this.released = true;
    }

    /**
     * Handles the end of the envelope.
     */
    public void handleEnvelopeEnd() {
        synthesizer.finishVoice(this);
        midiHandler.endVoice(this);
        ended = true;
    }

    /**
     * Tells if this voice has been released.
     * @return If this voice has been released.
     */
    public boolean isReleased() {
        return this.released;
    }

    /**
     * Tells if this voice has ended (it should after the release stage of the envelope).
     * @return If this voice has ended.
     */
    public boolean hasEnded() {
        return ended;
    }

}

