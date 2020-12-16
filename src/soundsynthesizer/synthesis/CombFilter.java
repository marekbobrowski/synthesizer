package soundsynthesizer.synthesis;

import java.util.Arrays;

/**
 * This class works as a feedback comb filter for processing sound buffers.
 * A comb filter adds a delayed version of a signal to itself, causing peaks
 * and troughs in frequency response.
 * The internal loop of a feedback comb filter causes the delayed signal to be repeatably
 * fed back into the delay. Short delay times are useful for reverberation effect and
 * long delay times create an echoing effect.
 *
 * Implementation of this feedback comb filter is based on this circuit:
 *
 *                ---------------------->
 *               |
 *               |
 * ----->(+)-----*------>[ delay ]------
 *        ^                             |
 *        |                             |
 *        ----------( gain )<-----------
 *

 * @author Marek Bobrowski
 */
public class CombFilter {
    /**
     * Stores the delayed samples.
     */
    private final double[] delayBuffer;

    /**
     * The gain value for the feedback signal.
     */
    private double feedback;

    /**
     * Position at which the delayed samples from delayBuffer will be overwritten/accessed.
     */
    private int delayPosition = 0;

    /**
     * Assigns the feedback gain parameter and creates an array for the delayed samples.
     * @param feedback The feedback signal gain value.
     * @param delayTime Delay time in seconds.
     */
    public CombFilter(double feedback, double delayTime) {
        this.feedback = feedback;
        delayBuffer = new double[(int)(delayTime * Converter.SAMPLE_RATE)];
    }

    /**
     * Creates a processed buffer according to this comb filter's settings.
     * @param buffer Buffer used to create it's processed version.
     * @return Returns the processed version of the passed buffer.
     */
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

    /**
     * Sets the feedback signal gain parameter.
     * @param feedback The gain value for the feedback signal.
     */
    public void setFeedback(double feedback) {
        this.feedback = feedback;
    }

}

