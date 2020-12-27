package soundsynthesizer.synthesis;

/**
 * This class works as an all-pass filter for processing sound buffers.
 * An all-pass filter passes all frequencies but provides a different phase shift for different frequencies.
 *
 * Implementation of this all-pass filter is based on this circuit:
 *
 *       --------->( -gain )---------
 *      |                            |
 *      |                            v
 * -----*---->(+)--->[ delay ]----->(+)---*---->
 *             ^                          |
 *             |                          |
 *              ---------( gain )<--------
 *
 *
 *
 * @author Marek Bobrowski
 */
public class AllPassFilter {
    /**
     * Stores the delayed samples.
     */
    private final double[] delayBuffer;

    /**
     * Position at which the delayed samples from delayBuffer will be overwritten/accessed.
     */
    private int delayPosition = 0;

    /**
     * The absolute gain value for the feedback and the feedforward signal.
     */
    private double gain;

    /**
     * Assigns the gain parameter and creates an array for the delayed samples.
     * @param gain The absolute gain value for the feedback and the feedforward signal.
     * @param delay Number of samples of delay.
     */
    public AllPassFilter(double gain, int delay) {
        this.gain = gain;
        delayBuffer = new double[delay];
    }

    /**
     * Creates a processed buffer according to this all-pass filter's settings.
     * @param buffer Buffer used to create it's processed version.
     * @return Returns the processed version of the passed buffer.
     */
    public double[][] createProcessedBuffer(double[][] buffer) {
        double[][] output = new double[2][buffer[0].length];
        double outputSample;

        for (int i = 0; i < buffer[0].length; i++) {
            outputSample = delayBuffer[delayPosition] - gain * buffer[0][i];
            delayBuffer[delayPosition] = outputSample * gain + buffer[0][i];
            output[0][i] = outputSample;
            output[1][i] = outputSample;
            delayPosition = (delayPosition + 1) % delayBuffer.length;
        }
        return output;
    }

    /**
     * Sets the gain parameter.
     * @param gain The absolute gain value for the feedback and the feedforward.
     */
    public void setGain(double gain) {
        this.gain = gain;
    }
}

