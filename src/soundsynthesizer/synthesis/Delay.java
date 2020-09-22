package soundsynthesizer.synthesis;

/**
 * @author Marek Bobrowski
 */
public class Delay {
    private double feedback = 0.5;
    private double time = 1;
    private double[] delayBuffer;
    private int delayPosition = 0;
    private double mix = 0;

    public Delay() {
        delayBuffer = new double[(int)(time*Converter.SAMPLE_RATE)];
    }

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

    public void setFeedback(double feedback) {
        if (feedback >= 1) {
            this.feedback = 1;
        } else if (feedback <= 0) {
            this.feedback = 0;
        } else {
            this.feedback = feedback;
        }
    }

    public double getFeedback() {
        return this.feedback;
    }

    public double getMix() {
        return mix;
    }

    public void setMix(double mix) {
        this.mix = mix;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

}

