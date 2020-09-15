package soundsynthesizer.synthesis;

import soundsynthesizer._interface.knobdelegates.IntGetter;

/**
 * @author Marek Bobrowski
 */
public class Oscillator {
    private final IntGetter intGetter;
    private double phase;

    public Oscillator(IntGetter intGetter) {
        this.intGetter = intGetter;
        this.phase = 0;
    }

    public double[][] gatherBuffer(double frequency) {
        int waveTypeNumber = intGetter.get();
        return switch (waveTypeNumber) {
            case 1 -> bufferWithTriangle(frequency);
            case 2 -> bufferWithSawtooth(frequency);
            case 3 -> bufferWithPulse(frequency);
            /* 0 (default) - sine wave */
            default -> bufferWithSine(frequency);
        };
    }

    public double[][] bufferWithSine(double frequency) {
        double[][] buffer = new double[2][Converter.BUFFER_SIZE];
        for (int i = 0; i < Converter.BUFFER_SIZE; i++) {
            buffer[0][i] = Math.sin(this.phase);
            buffer[1][i] = buffer[0][i];
            this.phase += ((2 * Math.PI * frequency) / Converter.SAMPLE_RATE);
            if (this.phase > 2 * Math.PI) {
                this.phase = this.phase - (2 * Math.PI);
            }
        }
        return buffer;
    }

    public double[][] bufferWithTriangle(double frequency) {
        double[][] buffer = new double[2][Converter.BUFFER_SIZE];
        for (int i = 0; i < Converter.BUFFER_SIZE; i++) {
            if (this.phase < Math.PI) {
                buffer[0][i] = -1 + (2/Math.PI)*this.phase;
            } else {
                buffer[0][i] = 3 - (2/Math.PI)*this.phase;
            }
            buffer[1][i] = buffer[0][i];

            this.phase += ((2 * Math.PI*frequency)/Converter.SAMPLE_RATE);

            if (this.phase > 2 * Math.PI) {
                this.phase = this.phase - (2 * Math.PI);
            }
        }
        return buffer;
    }

    public double[][] bufferWithSawtooth(double frequency) {
        double[][] buffer = new double[2][Converter.BUFFER_SIZE];
        for (int i = 0; i < Converter.BUFFER_SIZE; i++) {
            buffer[0][i] = 1 - (1 / Math.PI * this.phase);
            buffer[1][i] = buffer[0][i];
            this.phase += ((2 * Math.PI * frequency) / Converter.SAMPLE_RATE);
            if (this.phase > 2 * Math.PI) {
                this.phase = this.phase - (2 * Math.PI);
            }
        }
        return buffer;
    }

    public double[][] bufferWithPulse(double frequency) {
        double[][] buffer = new double[2][Converter.BUFFER_SIZE];
        for (int i = 0; i < Converter.BUFFER_SIZE; i++) {
            if (this.phase < Math.PI) {
                buffer[0][i] = 1;
            } else {
                buffer[0][i] = -1;
            }
            buffer[1][i] = buffer[0][i];
            this.phase += ((2 * Math.PI * frequency) / Converter.SAMPLE_RATE);
            if (this.phase > 2 * Math.PI) {
                this.phase = this.phase - (2 * Math.PI);
            }
        }
        return buffer;
    }
}

