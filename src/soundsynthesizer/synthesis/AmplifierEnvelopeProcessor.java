package soundsynthesizer.synthesis;

/**
 * This class processes the sound by applying an amplifier envelope to sound buffers.
 * The processing is applied according to the settings of an {@link AmplifierEnvelopeSettings} object.
 * An instance of such processor should be created for every new note.
 *
 * @author Marek Bobrowski
 * @see AmplifierEnvelopeSettings
 */
public class AmplifierEnvelopeProcessor {
    /**
     * The amplifier envelope settings that describe how the sound should be processed by this processor.
     */
    private final AmplifierEnvelopeSettings amplifierEnvelopeSettings;

    /**
     * The number of the samples that have gone through the attack phase.
     */
    private int attackSampleCount = 0;

    /**
     * The number of the samples that have gone through the decay phase.
     */
    private int decaySampleCount = 0;

    /**
     * The number of the samples that have gone through the release phase.
     */
    private int releaseSampleCount = 0;

    /**
     * The number of the samples that have gone through the before-release phases.
     */
    private int beforeReleaseSampleCount = 0;

    /**
     * The instance of the note that is being processed.
     */
    private final Note note;

    /**
     * Last value of the amplifier envelope. Envelope's value is a value that describes how
     * the volume of the passing sound is being changed. With every envelope "tick", this value is calculated anew.
     * For example, for a typical attack phase this value will change incrementally like that: 0 -> 0.01 -> 0.02 etc.
     * Then, for a release phase it might look like that: 1 -> 0.95 -> 0.9 etc.
     */
    private double lastValue = 0;


    /**
     * Assigns the passed arguments to the class fields.
     * @param note This note's volume will be controlled by this processor.
     * @param amplifierEnvelopeSettings The amplifier envelope settings that describe how the sound
     *                                  should be processed by this processor.
     */
    public AmplifierEnvelopeProcessor(Note note, AmplifierEnvelopeSettings amplifierEnvelopeSettings) {
        this.note = note;
        this.amplifierEnvelopeSettings = amplifierEnvelopeSettings;
    }

    /**
     * Processes the buffer according to the passed {@link AmplifierEnvelopeSettings} instance.
     * @param buffer The buffer to be processed.
     */
    public void processBuffer(double[][] buffer) {
        for (int i = 0; i < buffer[0].length; i++) {
            int numberOfAttackSamples = timeToSamples(amplifierEnvelopeSettings.getAttack());
            int numberOfDecaySamples = timeToSamples(amplifierEnvelopeSettings.getDecay());
            double sustainLevel = amplifierEnvelopeSettings.getSustain();
            int numberOfReleaseSamples = timeToSamples(amplifierEnvelopeSettings.getRelease());
            if (!note.isReleased()) {
                if (beforeReleaseSampleCount < numberOfAttackSamples) {
                    lastValue = (1 - lastValue) / (numberOfAttackSamples - attackSampleCount)
                            + lastValue;
                    attackSampleCount++;
                } else if(beforeReleaseSampleCount < numberOfDecaySamples + numberOfAttackSamples) {
                    if (decaySampleCount == 0) {
                        lastValue = 1;
                    }
                    lastValue  = (sustainLevel - lastValue) /
                            (numberOfDecaySamples - decaySampleCount)
                            + lastValue;
                    decaySampleCount++;
                } else if (beforeReleaseSampleCount >= numberOfAttackSamples + numberOfDecaySamples) {
                    lastValue = sustainLevel;
                }
                beforeReleaseSampleCount++;
            } else {
                if (releaseSampleCount < numberOfReleaseSamples) {
                    lastValue = lastValue / (releaseSampleCount
                            - numberOfReleaseSamples)
                            + lastValue;
                } else {
                    if (!note.hasEnded()) {
                        note.handleEnvelopeEnd();
                    }
                    lastValue = 0;
                }
                releaseSampleCount++;
            }

            if (lastValue < 0) {
                lastValue *= -1;
            }

            buffer[0][i] *= lastValue;
            buffer[1][i] = buffer[0][i];
        }
    }

    /**
     * Calculates the number of samples contained in the specified number of seconds.
     * @param time Time in seconds.
     * @return The number of samples contained in the specified number of seconds.
     */
    private int timeToSamples(double time) {
        return (int) (time * Converter.SAMPLE_RATE);
    }
}
