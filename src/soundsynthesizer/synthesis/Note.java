package soundsynthesizer.synthesis;

import soundsynthesizer.delegates.IntGetter;

/**
 * @author Marek Bobrowski
 */
public class Note {
    private final AmplifierEnvelopeProcessor amplifierEnvelopeProcessor;
    private final OscillatorSettings oscillatorSettings;
    private final Synthesizer synthesizer;
    private final double frequency;
    private final Oscillator oscillator1;
    private final Oscillator oscillator2;
    private boolean released = false;
    private boolean ended = false;

    public Note(Synthesizer synthesizer, double frequency) {
        this.synthesizer = synthesizer;
        amplifierEnvelopeProcessor = new AmplifierEnvelopeProcessor(this, synthesizer.getAmplifierEnvelope());
        oscillatorSettings = synthesizer.getOscillatorSettings();
        this.frequency = frequency;
        IntGetter wave1 = oscillatorSettings::getOscillator1Type;
        IntGetter wave2 = oscillatorSettings::getOscillator2Type;
        oscillator1 = new Oscillator(wave1);
        oscillator2 = new Oscillator(wave2);
    }

    public double[][] prepareBuffer(int bufferSize) {
        double[][] oscillator1Buffer = oscillator1.gatherBuffer(frequency *
                Math.pow(2,(oscillatorSettings.getOscillator1SemitonesShift()*100
                        + oscillatorSettings.getOscillator1CentsShift())/1200.0),
                bufferSize);
        double[][] oscillator2Buffer = oscillator2.gatherBuffer(frequency *
                Math.pow(2,(oscillatorSettings.getOscillator2SemitonesShift()*100
                        + oscillatorSettings.getOscillator2CentsShift())/1200.0),
                bufferSize);
        double[][] mixedBuffer = mixOscillators(oscillator1Buffer, oscillator2Buffer);
        amplifierEnvelopeProcessor.processBuffer(mixedBuffer);
        return mixedBuffer;
    }

    public double[][] mixOscillators(double[][] buffer1, double[][] buffer2) {
        double[][] mixedBuffer = new double[buffer1.length][buffer1[0].length];
        for (int i = 0; i < mixedBuffer[0].length; i++) {
            mixedBuffer[0][i] = buffer1[0][i] * (1 - oscillatorSettings.getMixValue())
                    + buffer2[0][i] * oscillatorSettings.getMixValue();
            mixedBuffer[1][i] = mixedBuffer[0][i];
        }
        return mixedBuffer;
    }

    public void triggerRelease() {
        this.released = true;
    }

    public void handleEnvelopeEnd() {
        synthesizer.endNote(this);
        ended = true;
    }

    public boolean isReleased() {
        return this.released;
    }

    public boolean hasEnded() {
        return ended;
    }

}

