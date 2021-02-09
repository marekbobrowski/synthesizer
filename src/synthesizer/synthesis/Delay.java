package synthesizer.synthesis;

/**
 * This class is responsible for creating a delay (echo) effect on a passed sound buffer.
 * It is basically a {@link CombFilter} with much longer delay times and ability to adjust
 * the mix between the dry and wet signal.
 *
 * @author Marek Bobrowski
 * @see CombFilter
 */
public class Delay {
    /**
     * The feedback gain of the comb filter.
     * It describes how quickly will the echoes fade. It also describes the relative amplitude
     * of the first echo (relative to the original signal amplitude).
     */
    private double feedback = 0.5;

    /**
     * The delay time in seconds.
     * It describes the time between the echoes.
     */
    private double time = 1;

    /**
     * Stores the delayed samples.
     */
    private final double[] delayBuffer;

    /**
     * Position at which the delayed samples from delayBuffer will be overwritten/accessed.
     */
    private int delayPosition = 0;

    /**
     * Mix between the dry and the wet signal of this effect.
     */
    private double mix = 0;

    /**
     * Creates an array for storing the delayed samples.
     */
    public Delay() {
        delayBuffer = new double[(int)(time*Converter.SAMPLE_RATE)];
    }


    /**
     * Processes the passed buffer by adding echo effect to it.
     *
     * @param buffer The buffer to be processed.
     */
    public void processBuffer(double[][] buffer) {
        double lastValue;
        for (int i = 0; i < buffer[0].length; i++) {
            lastValue = buffer[0][i] + delayBuffer[delayPosition] * feedback;
            buffer[0][i] = (1 - mix) * buffer[0][i] + mix * delayBuffer[delayPosition] * feedback;
            buffer[1][i] = buffer[0][i];
            delayBuffer[delayPosition] = lastValue;
            delayPosition = (delayPosition + 1) % delayBuffer.length;
        }
    }

    /**
     * Sets the feedback gain of the comb filter.
     * Feedback describes how quickly will the echoes fade. It also describes the relative amplitude
     * of the first echo (relative to the original signal amplitude).
     *
     * @param feedback The feedback gain of the comb filter.
     */
    public void setFeedback(double feedback) {
        if (feedback >= 1) {
            this.feedback = 1;
        } else if (feedback <= 0) {
            this.feedback = 0;
        } else {
            this.feedback = feedback;
        }
    }

    /**
     * Gets the feedback gain of the comb filter.
     * Feedback describes how quickly will the echoes fade. It also describes the relative amplitude
     * of the first echo (relative to the original signal amplitude).
     *
     * @return The feedback gain of the comb filter.
     */
    public double getFeedback() {
        return this.feedback;
    }

    /**
     * Returns the mix between the dry and the wet signal of this effect.
     * @return The mix between the dry and the wet signal of this effect.
     */
    public double getMix() {
        return mix;
    }

    /**
     * Sets the mix between the dry and the wet signal of this effect.
     * @param mix The mix between the dry and the wet signal of this effect.
     */
    public void setMix(double mix) {
        this.mix = mix;
    }

    /**
     * Returns the delay time (in seconds).
     * The delay time describes the time between the echoes.
     * @return The delay time (in seconds).
     */
    public double getTime() {
        return time;
    }

    /**
     * Sets the delay time (in seconds).
     * The delay time describes the time between the echoes.
     * @param time The delay time (in seconds).
     */
    public void setTime(double time) {
        this.time = time;
    }

}

