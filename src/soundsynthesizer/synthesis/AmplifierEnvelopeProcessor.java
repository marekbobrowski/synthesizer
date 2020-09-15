package soundsynthesizer.synthesis;

/**
 * @author Marek Bobrowski
 */
public class AmplifierEnvelopeProcessor {
    private final AmplifierEnvelopeSettings amplifierEnvelopeSettings;
    private int attackSampleCount = 0;
    private int decaySampleCount = 0;
    private int releaseSampleCount = 0;
    private int beforeReleaseSampleCount = 0;
    private final Note note;
    private double lastValue = 0;

    public AmplifierEnvelopeProcessor(Note note, AmplifierEnvelopeSettings amplifierEnvelopeSettings) {
        this.note = note;
        this.amplifierEnvelopeSettings = amplifierEnvelopeSettings;
    }

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

    //time in seconds
    private int timeToSamples(double time) {
        return (int) (time * Converter.SAMPLE_RATE);
    }
}
