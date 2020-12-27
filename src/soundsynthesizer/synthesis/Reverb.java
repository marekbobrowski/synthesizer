package soundsynthesizer.synthesis;

/**
 * This class is responsible for creating a reverberation effect on a passed sound buffer.
 * The reverberation algorithm is based on Manfred Schroeder's idea for first reverberators because
 * it consists of a parallel bank of feedback comb filters (8) and a series of all-pass filters (6).
 * Each of the filters have different delay times and the same decay value (feedback/feedforward gain value).
 *
 * @author Marek Bobrowski
 */
public class Reverb {
    /**
     * The feedback and the feedforward gain values for all the filters.
     */
    private double decay = 0.9;

    /**
     * The mix between the dry (0) and the wet (1) signal.
     */
    private double mix = 0;

    // Initialization of all comb filters.
    private final CombFilter cf1 = new CombFilter(decay, 1309);
    private final CombFilter cf2 = new CombFilter(decay, 1636);
    private final CombFilter cf3 = new CombFilter(decay, 1812);
    private final CombFilter cf4 = new CombFilter(decay, 604);
    private final CombFilter cf5 = new CombFilter(decay, 1045);
    private final CombFilter cf6 = new CombFilter(decay, 1653);
    private final CombFilter cf7 = new CombFilter(decay, 1468);
    private final CombFilter cf8 = new CombFilter(decay, 1561);

    // Initialization of all all-pass filters.
    private final AllPassFilter apf1 = new AllPassFilter(decay, 220);
    private final AllPassFilter apf2 = new AllPassFilter(decay, 515);
    private final AllPassFilter apf3 = new AllPassFilter(decay, 956);
    private final AllPassFilter apf4 = new AllPassFilter(decay, 714);
    private final AllPassFilter apf5 = new AllPassFilter(decay, 939);
    private final AllPassFilter apf6 = new AllPassFilter(decay, 696);

    /**
     * Empty constructor.
     */
    public Reverb() {
    }

    /**
     * Creates a processed sound buffer by applying reverberation to the passed sound buffer.
     *
     * @param buffer The buffer that will be used for creating reverberation.
     * @return The processed sound buffer with added reverberation.
     */
    public double[][] createProcessedBuffer(double[][] buffer) {
        double[][] output = new double[2][buffer[0].length];

        double[][] cf1Samples = cf1.createProcessedBuffer(buffer);
        double[][] cf2Samples = cf2.createProcessedBuffer(buffer);
        double[][] cf3Samples = cf3.createProcessedBuffer(buffer);
        double[][] cf4Samples = cf4.createProcessedBuffer(buffer);
        double[][] cf5Samples = cf5.createProcessedBuffer(buffer);
        double[][] cf6Samples = cf6.createProcessedBuffer(buffer);
        double[][] cf7Samples = cf7.createProcessedBuffer(buffer);
        double[][] cf8Samples = cf8.createProcessedBuffer(buffer);

        for (int i = 0; i < buffer[0].length; i++) {
            output[0][i] = cf1Samples[0][i] + cf2Samples[0][i] +
                    cf3Samples[0][i] + cf4Samples[0][i] +
                    cf5Samples[0][i] + cf6Samples[0][i] +
                    cf7Samples[0][i] + cf8Samples[0][i];
            output[0][i] /= 8;
            output[1][i] = output[0][i];
        }

        output = apf1.createProcessedBuffer(output);
        output = apf2.createProcessedBuffer(output);
        output = apf3.createProcessedBuffer(output);
        output = apf4.createProcessedBuffer(output);
        output = apf5.createProcessedBuffer(output);
        output = apf6.createProcessedBuffer(output);

        for (int i = 0; i < buffer[0].length; i++) {
            output[0][i] = (1 - mix) * buffer[0][i] + mix * output[0][i];
            output[1][i] = output[0][i];
        }

        return output;
    }

    /**
     * Returns the mix between the dry (0) and the wet (1) signal.
     * @return The mix between the dry (0) and the wet (1) signal.
     */
    public double getMix() {
        return mix;
    }

    /**
     * Sets the mix between the dry (0) and the wet (1) signal.
     * @param mix The mix between the dry (0) and the wet (1) signal.
     */
    public void setMix(double mix) {
        this.mix = mix;
    }

    /**
     * Sets the feedback and the feedforward gain values for all the filters.
     * @param decay The feedback and the feedforward gain values for all the filters.
     */
    public void setDecay(double decay) {
        if (decay >= 1) {
            this.decay = 1;
        } else if (decay <= 0) {
            this.decay = 0;
        } else {
            this.decay = decay;
        }
        this.decay = decay;
        cf1.setFeedback(this.decay);
        cf2.setFeedback(this.decay);
        cf3.setFeedback(this.decay);
        cf4.setFeedback(this.decay);
        cf5.setFeedback(this.decay);
        cf6.setFeedback(this.decay);
        cf7.setFeedback(this.decay);
        cf8.setFeedback(this.decay);
        apf1.setGain(this.decay);
        apf2.setGain(this.decay);
        apf3.setGain(this.decay);
        apf4.setGain(this.decay);
        apf5.setGain(this.decay);
        apf6.setGain(this.decay);
    }

    /**
     * Returns the feedback and the feedforward gain values for all the filters.
     * @return The feedback and the feedforward gain values for all the filters.
     */
    public double getDecay() {
        return decay;
    }
}
