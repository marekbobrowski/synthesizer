package synthesizer.synthesis;

/**
 * This class stores settings of an envelope:
 * attack time, decay time, sustain level, release time. All are stored in universal values (0 to 1).
 *
 * A signal envelope describes how the signal's amplitude changes over time.
 * Typical segments of such envelope are attack, decay, sustain and release.
 * - Attack segment is responsible for rising the amplitude of the signal.
 *   The amplitude goes from 0 to max.
 * - Decay segment is responsible for decaying the amplitude of the signal to the sustain level.
 *   The amplitude goes from max to the sustain level.
 * - Sustain is a segment with constant amplitude level. It lasts until the voice is released.
 *   The amplitude stays at the sustain level.
 * - Release segment is responsible for decaying the amplitude of the signal after the voice has been released.
 *   The amplitude goes from the sustain level to 0.
 *
 * @author Marek Bobrowski
 */
public class EnvelopeSettings {
    /**
     * The attack time (0 to 1).
     */
    private double attack = 0;

    /**
     * The decay time (0 to 1).
     */
    private double decay = 0;

    /**
     * The sustain level (0 to 1).
     */
    private double sustain = 1;

    /**
     * The release time (0 to 1).
     */
    private double release = 0.01;

    /**
     * An empty constructor.
     */
    public EnvelopeSettings() {
    }

    /**
     * Sets the attack time. From 0 to 1.
     * @param value The attack time (0 to 1).
     */
    public void setAttack(double value) {
        attack = value;
    }

    /**
     * Gets the attack time (0 to 1).
     * @return The attack time (0 to 1).
     */
    public double getAttack() {
        return attack;
    }

    /**
     * Sets the decay time (0 to 1).
     * @param value The decay time (0 to 1).
     */
    public void setDecay(double value) {
        decay = value;
    }

    /**
     * Gets the decay time (0 to 1).
     * @return The decay time (0 to 1).
     */
    public double getDecay() {
        return decay;
    }

    /**
     * Sets the sustain level (0 to 1).
     * @param value The sustain level (0 to 1).
     */
    public void setSustain(double value) {
        sustain = value;
    }

    /**
     * Gets the sustain level (0 to 1).
     * @return The sustain level (0 to 1).
     */
    public double getSustain() {
        return sustain;
    }

    /**
     * Sets the release time (0 to 1).
     * @param value The release time (0 to 1).
     */
    public void setRelease(double value) {
        release = value;
    }

    /**
     * Gets the release time (0 to 1).
     * @return The release time (0 to 1).
     */
    public double getRelease() {
        return release;
    }
}

