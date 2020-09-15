package soundsynthesizer.synthesis;

import java.util.Arrays;

/**
 * @author Marek Bobrowski
 */
public class CombFilter {
    private final double[] delayBuffer;
    private double feedback;
    private int delayPosition = 0;

    public CombFilter(double feedback, double delayTime) {
        this.feedback = feedback;
        delayBuffer = new double[(int)(delayTime * Converter.SAMPLE_RATE)];
    }

    public double[][] createProcessedBuffer(double[][] buffer) {
        double[][] output = Arrays.stream(buffer).map(double[]::clone).toArray(double[][]::new);
        double lastValue;
        for (int i = 0; i < buffer[0].length; i++) {
            lastValue = output[0][i] + delayBuffer[delayPosition] * feedback;
            delayBuffer[delayPosition] = lastValue;
            output[0][i] = lastValue;
            output[1][i] = lastValue;
            delayPosition = (delayPosition + 1) % delayBuffer.length;
        }
        return output;
    }

    public void setFeedback(double feedback) {
        this.feedback = feedback;
    }

}

