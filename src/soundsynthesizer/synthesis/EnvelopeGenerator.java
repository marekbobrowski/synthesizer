package soundsynthesizer.synthesis;

/**
 * This class processes the sound by applying an amplitude envelope to sound buffers.
 * The processing is applied according to the settings of an {@link EnvelopeSettings} object.
 * An instance of such processor should be created for every new voice.
 *
 * @author Marek Bobrowski
 * @see EnvelopeSettings
 */
public class EnvelopeGenerator {
    /**
     * The envelope settings that describe how the sound should be processed by this envelope generator.
     */
    private final EnvelopeSettings envelopeSettings;

    /**
     * The number of the samples that have gone through the attack segment.
     */
    private int attackSampleCount = 0;

    /**
     * The number of the samples that have gone through the decay segment.
     */
    private int decaySampleCount = 0;

    /**
     * The number of the samples that have gone through the release segment.
     */
    private int releaseSampleCount = 0;

    /**
     * The number of the samples that have gone through the before-release segments.
     */
    private int beforeReleaseSampleCount = 0;

    /**
     * The instance of the voice that is being processed.
     */
    private final Voice voice;

    /**
     * Last value of the envelope. Envelope's value is a value that describes how
     * the volume of the passing sound is being changed. With every envelope "tick", this value is calculated anew.
     * For example, for a typical attack segment this value will change incrementally like that: 0 -> 0.01 -> 0.02 etc.
     * Then, for a release segment it might look like that: 1 -> 0.95 -> 0.9 etc.
     */
    private double lastValue = 0;


    /**
     * Assigns the passed arguments to the class fields.
     * @param voice This voice's signal amplitude will be controlled by this envelope generator.
     * @param envelopeSettings The envelope settings that describe how the sound
     *                                  should be processed by this envelope generator.
     */
    public EnvelopeGenerator(Voice voice, EnvelopeSettings envelopeSettings) {
        this.voice = voice;
        this.envelopeSettings = envelopeSettings;
    }

    /**
     * Processes the buffer according to the passed {@link EnvelopeSettings} instance.
     * @param buffer The buffer to be processed.
     */
    public void processBuffer(double[][] buffer) {
        for (int i = 0; i < buffer[0].length; i++) {
            int numberOfAttackSamples = timeToSamples(envelopeSettings.getAttack());
            int numberOfDecaySamples = timeToSamples(envelopeSettings.getDecay());
            double sustainLevel = envelopeSettings.getSustain();
            int numberOfReleaseSamples = timeToSamples(envelopeSettings.getRelease());
            if (!voice.isReleased()) {
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
                    if (!voice.hasEnded()) {
                        voice.handleEnvelopeEnd();
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
