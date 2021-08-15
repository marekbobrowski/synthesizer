package synthesizer.synthesis;

import synthesizer.delegates.IntGetter;

/**
 * This class is responsible for generating a periodic signal according to the specified parameters.
 *
 * @author Marek Bobrowski
 */
public class Oscillator {
    /**
     * An integer getter delegate for getting the waveform number.
     */
    private final IntGetter waveformGetter;

    /**
     * The phase of this oscillator.
     */
    private double phase;

    /**
     * Oscillator constructor.
     * @param waveformGetter An integer getter delegate for getting the wave type number.
     */
    public Oscillator(IntGetter waveformGetter) {
        this.waveformGetter = waveformGetter;
        phase = 0;
    }

    /**
     * Generates a sound buffer according to the passed method arguments and this oscillator's class fields.
     * @param frequency The frequency of the periodic signal to be generated.
     * @param bufferSize The number of frames of the buffer.
     * @return The buffer generated by this oscillator.
     */
    public double[][] generateBuffer(double frequency, int bufferSize) {
        int waveformNumber = waveformGetter.get();
        return switch (waveformNumber) {
            case 1 -> generateTriangle(frequency, bufferSize);
            case 2 -> generateSawtooth(frequency, bufferSize);
            case 3 -> generateSquare(frequency, bufferSize);
            /* 0 (default) - sine wave */
            default -> generateSine(frequency, bufferSize);
        };
    }

    /**
     * Creates a buffer of a sinusoidal signal.
     * @param frequency The frequency of the sinusoidal signal.
     * @param bufferSize The number of frames of the sound buffer.
     * @return The generated buffer of a sinusoidal signal.
     */
    private double[][] generateSine(double frequency, int bufferSize) {
        double[][] buffer = new double[2][bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            buffer[0][i] = Math.sin(phase);
            buffer[1][i] = buffer[0][i];
            phase += (2 * Math.PI * frequency) / Converter.SAMPLE_RATE;
            if (phase > 2 * Math.PI) {
                phase = phase - (2 * Math.PI);
            }
        }
        return buffer;
    }

    /**
     * Creates a buffer of a triangular signal.
     * @param frequency The frequency of the triangular signal.
     * @param bufferSize The number of frames of the sound buffer.
     * @return The generated buffer of a triangular signal.
     */
    private double[][] generateTriangle(double frequency, int bufferSize) {
        double[][] buffer = new double[2][bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            if (phase < Math.PI) {
                buffer[0][i] = - 1 + (2 / Math.PI) * phase;
            } else {
                buffer[0][i] = 3 - (2 / Math.PI) * phase;
            }
            buffer[1][i] = buffer[0][i];

            phase += (2 * Math.PI * frequency) / Converter.SAMPLE_RATE;

            if (phase > 2 * Math.PI) {
                phase = phase - (2 * Math.PI);
            }
        }
        return buffer;
    }

    /**
     * Creates a buffer of a sawtooth signal.
     * @param frequency The frequency of the sawtooth signal.
     * @param bufferSize The number of frames of the sound buffer.
     * @return The generated buffer of a sawtooth signal.
     */
    private double[][] generateSawtooth(double frequency, int bufferSize) {
        double[][] buffer = new double[2][bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            buffer[0][i] = 1 - (1 / Math.PI * phase);
            buffer[1][i] = buffer[0][i];
            phase += (2 * Math.PI * frequency) / Converter.SAMPLE_RATE;
            if (phase > 2 * Math.PI) {
                phase = phase - (2 * Math.PI);
            }
        }
        return buffer;
    }

    /**
     * Creates a buffer of a square signal.
     * @param frequency The frequency of the square signal.
     * @param bufferSize The number of frames of the sound buffer.
     * @return The generated buffer of a square signal.
     */
    private double[][] generateSquare(double frequency, int bufferSize) {
        double[][] buffer = new double[2][bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            if (phase < Math.PI) {
                buffer[0][i] = 1;
            } else {
                buffer[0][i] = -1;
            }
            buffer[1][i] = buffer[0][i];
            phase += (2 * Math.PI * frequency) / Converter.SAMPLE_RATE;
            if (phase > 2 * Math.PI) {
                phase = phase - (2 * Math.PI);
            }
        }
        return buffer;
    }
}
