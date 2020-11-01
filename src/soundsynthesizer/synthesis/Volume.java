package soundsynthesizer.synthesis;

/**
 * This class is responsible for changing the volume (the output loudness) of the signal.
 *
 * @author Marek Bobrowski
 */
public class Volume {
    /**
     * The volume of the signal.
     */
    private double volume = 1;

    public Volume() {
    }

    /**
     * Return the signal volume.
     * @return The signal volume.
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the signal volume.
     * @param volume The signal volume.
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
            buffer[0][i] *= volume;
            buffer[1][i] *= volume;
        }
    }
}
