package soundsynthesizer.synthesis;

/**
 * @author Marek Bobrowski
 */
public class AllPassFilter {
    private final double[] feedbackBuffer;
    private final double[] delayBuffer;
    private int feedbackPosition = 0;
    private int delayPosition = 0;
    private double gain;

    public AllPassFilter(double gain, double delayTime) {
        this.gain = gain;
        feedbackBuffer = new double[(int)(delayTime * Converter.SAMPLE_RATE)];
        delayBuffer = new double[(int)(delayTime * Converter.SAMPLE_RATE)];
    }

    public double[][] createProcessedBuffer(double[][] buffer) {
        double[][] output = new double[2][Converter.BUFFER_SIZE];
        double lastValue;

        for (int i = 0; i < buffer[0].length; i++) {
            lastValue = delayBuffer[delayPosition] + feedbackBuffer[feedbackPosition] * gain
                    - buffer[0][i] * gain;
            feedbackBuffer[feedbackPosition] = lastValue;
            delayBuffer[delayPosition] = buffer[0][i];
            output[0][i] = lastValue;
            output[1][i] = lastValue;

            feedbackPosition = (feedbackPosition + 1) % feedbackBuffer.length;
            delayPosition = (delayPosition + 1) % delayBuffer.length;
        }
        return output;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }
}

