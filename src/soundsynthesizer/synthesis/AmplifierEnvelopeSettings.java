package soundsynthesizer.synthesis;

/**
 * This class stores settings of an amplifier envelope:
 * attack time, decay time, sustain level, release time. All are stored in universal values (0 to 1).
 *
 * An amplifier envelope describes how the sound's volume changes over time.
 * Typical segments of such envelope are attack, decay, sustain and release.
 * - Attack segment is responsible for rising the volume of the sound.
 *   The volume goes from 0 to max.
 * - Decay segment is responsible for decaying the volume of the sound to the sustain level.
 *   The volume goes from max to the sustain level.
 * - Sustain is a segment with constant volume level. It lasts until the note is released.
 *   The volume stays at the sustain level.
 * - Release segment is responsible for decaying the volume of the sound after the note has been released.
 *   The volume goes from the sustain level to 0.
 *
 * @author Marek Bobrowski
 */
public class AmplifierEnvelopeSettings {
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
    public AmplifierEnvelopeSettings() {
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

