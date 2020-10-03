package soundsynthesizer.synthesis;

import soundsynthesizer.delegates.IntGetter;

/**
 * This class works as a single note of a sound synthesizer.
 * It generates sound using the available oscillators and runs them through an amplifier envelope.
 *
 * @author Marek Bobrowski
 */
public class Note {
    /**
     * The object that will process the sound by applying amplifier envelope to it.
     */
    private final AmplifierEnvelopeProcessor amplifierEnvelopeProcessor;

    /**
     * The object that describes the parameters of the oscillators.
     */
    private final OscillatorSettings oscillatorSettings;

    /**
     * The synthesizer that created this note.
     */
    private final Synthesizer synthesizer;

    /**
     * The frequency of this note.
     */
    private final double frequency;

    /**
     * The first oscillator that generates the sound for this note.
     */
    private final Oscillator oscillator1;

    /**
     * The second oscillator that generates the soudn for this note.
     */
    private final Oscillator oscillator2;

    /**
     * Tells if this note has been released on the keyboard.
     */
    private boolean released = false;

    /**
     * Tells if this note has ended playing (if the release stage of the envelopes have ended).
     */
    private boolean ended = false;

    /**
     * Note constructor.
     * @param synthesizer The synthesizer that is playing this note.
     * @param frequency The frequency of this note.
     */
    public Note(Synthesizer synthesizer, double frequency) {
        this.synthesizer = synthesizer;
        amplifierEnvelopeProcessor = new AmplifierEnvelopeProcessor(this, synthesizer.getAmplifierEnvelope());
        oscillatorSettings = synthesizer.getOscillatorSettings();
        this.frequency = frequency;
        IntGetter wave1 = oscillatorSettings::getOscillator1Type;
        IntGetter wave2 = oscillatorSettings::getOscillator2Type;
        oscillator1 = new Oscillator(wave1);
        oscillator2 = new Oscillator(wave2);
    }

    /**
     * Generates a buffer of this note's sound.
     * It gathers buffers from the available oscillators and applies an amplifier envelope to the sound.
     * @param bufferSize The size of the buffer to be generated.
     * @return The generated sound buffer of this note.
     */
    public double[][] prepareBuffer(int bufferSize) {
        double[][] oscillator1Buffer = oscillator1.gatherBuffer(frequency *
                Math.pow(2,(oscillatorSettings.getOscillator1SemitonesShift()*100
                        + oscillatorSettings.getOscillator1CentsShift())/1200.0),
                bufferSize);
        double[][] oscillator2Buffer = oscillator2.gatherBuffer(frequency *
                Math.pow(2,(oscillatorSettings.getOscillator2SemitonesShift()*100
                        + oscillatorSettings.getOscillator2CentsShift())/1200.0),
                bufferSize);
        double[][] mixedBuffer = mixOscillators(oscillator1Buffer, oscillator2Buffer);
        amplifierEnvelopeProcessor.processBuffer(mixedBuffer);
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
     * Triggers the release of this note.
     */
    public void triggerRelease() {
        this.released = true;
    }

    /**
     * Handles the end of the amplifier envelope.
     */
    public void handleEnvelopeEnd() {
        synthesizer.endNote(this);
        ended = true;
    }

    /**
     * Tells if this note has been released.
     * @return If this note has been released.
     */
    public boolean isReleased() {
        return this.released;
    }

    /**
     * Tells if this note has ended (it should after the release stage of the amp envelope).
     * @return If this note has ended.
     */
    public boolean hasEnded() {
        return ended;
    }

    /**
     * Mixes and normalizes the passed buffers. First index stands for the note number (number of the buffer),
     * second index stands for the channel, third index stands for the frame number.
     * @param arraysOfSamples Arrays of buffers/samples (in the format specified in the method description).
     * @param bufferSize The number of frames of a sound buffer.
     * @return The buffer with the mixed and normalized sound.
     */
    public static double[][] mixAndNormalizeNotes(double[][][] arraysOfSamples, int bufferSize) {
        double[][] mixedSamples = new double[2][bufferSize];

        for (int i = 0; i < bufferSize; i++) {
            double sumLeft = 0;
            double sumRight = 0;

            for (int j = 0; j < arraysOfSamples.length; j++) {
                if(i < arraysOfSamples[j][0].length) {
                    sumLeft += arraysOfSamples[j][0][i];
                    sumRight += arraysOfSamples[j][1][i];
                }
            }

            mixedSamples[0][i] = sumLeft / 16;
            mixedSamples[1][i] = sumRight / 16;
        }
        return mixedSamples;
    }
}

