package soundsynthesizer.synthesis;

/**
 * @author Marek Bobrowski
 */
public class Reverb {
    private double decay = 0.8;
    private double mix = 0.5;

    private final CombFilter cf1 = new CombFilter(0.965, 0.0297);
    private final CombFilter cf2 = new CombFilter(0.951, 0.0371);
    private final CombFilter cf3 = new CombFilter(0.950, 0.0411);
    private final CombFilter cf4 = new CombFilter(0.973, 0.0137);
    private final CombFilter cf5 = new CombFilter(0.983, 0.0237);
    private final CombFilter cf6 = new CombFilter(0.971, 0.0375);
    private final CombFilter cf7 = new CombFilter(0.978, 0.0333);
    private final CombFilter cf8 = new CombFilter(0.969, 0.0354);

    private final AllPassFilter apf1 = new AllPassFilter(0.975, 0.005);
    private final AllPassFilter apf2 = new AllPassFilter(0.961, 0.0117);
    private final AllPassFilter apf3 = new AllPassFilter(0.951, 0.0217);
    private final AllPassFilter apf4 = new AllPassFilter(0.914, 0.0162);
    private final AllPassFilter apf5 = new AllPassFilter(0.971, 0.0213);
    private final AllPassFilter apf6 = new AllPassFilter(0.918, 0.0158);

    public Reverb() {
    }

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
                    cf7Samples[0][i] + cf8Samples[0][i] +
                    buffer[0][i];
            output[0][i] /= 9;
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

    public double getMix() {
        return mix;
    }

    public void setMix(double mix) {
        this.mix = mix;
    }

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

    public double getDecay() {
        return decay;
    }
}
