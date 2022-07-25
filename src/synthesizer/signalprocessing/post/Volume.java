package synthesizer.signalprocessing.post;

/**
 * This class is responsible for changing the volume (the output loudness) of the signal.
 *
 * @author Marek Bobrowski
 */
public class Volume {
    /**
     * The volume of the signal (in dB).
     */
    private double volume = 0;

    public Volume() {
    }

    /**
     * Return the signal volume (in dB).
     * @return The signal volume (in dB).
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the signal volume specified in dB.
     * @param volume The signal volume (in dB).
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Processes the passed buffer by changing the volume of the signal.
     * @param buffer The buffer to be processed.
     */
    public void processBuffer(double[][] buffer) {
        for (int i = 0; i < buffer[0].length; i++) {
            buffer[0][i] *= Math.pow(10, volume/20);
            buffer[1][i] *= Math.pow(10, volume/20);
        }
    }
}
