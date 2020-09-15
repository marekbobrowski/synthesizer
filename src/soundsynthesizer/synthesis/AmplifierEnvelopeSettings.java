package soundsynthesizer.synthesis;

/**
 * @author Marek Bobrowski
 */
public class AmplifierEnvelopeSettings {
    private double attack = 0;
    private double decay = 0;
    private double sustain = 1;
    private double release = 0.01;

    public AmplifierEnvelopeSettings() {
    }

    public void setAttack(double value) {
        attack = value;
    }

    public double getAttack() {
        return attack;
    }

    public void setDecay(double value) {
        decay = value;
    }

    public double getDecay() {
        return decay;
    }

    public void setSustain(double value) {
        sustain = value;
    }

    public double getSustain() {
        return sustain;
    }

    public void setRelease(double value) {
        release = value;
    }

    public double getRelease() {
        return release;
    }
}

