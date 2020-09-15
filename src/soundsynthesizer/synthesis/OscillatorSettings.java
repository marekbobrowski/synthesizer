package soundsynthesizer.synthesis;

/**
 * @author Marek Bobrowski
 */
public class OscillatorSettings {
    private int oscillator1Type = 0;
    private int oscillator2Type = 0;
    private double oscillator1SemitonesShift = 0;
    private double oscillator1CentsShift = 0;
    private double oscillator2SemitonesShift = 0;
    private double oscillator2CentsShift = 0;
    private double mixValue = 0.5;

    public OscillatorSettings() {
    }

    public int getOscillator1Type() {
        return oscillator1Type;
    }

    public void setOscillator1Type(int oscillator1Type) {
        this.oscillator1Type = oscillator1Type;
    }

    public int getOscillator2Type() {
        return oscillator2Type;
    }

    public void setOscillator2Type(int oscillator2Type) {
        this.oscillator2Type = oscillator2Type;
    }

    public double getOscillator1SemitonesShift() {
        return oscillator1SemitonesShift;
    }

    public void setOscillator1SemitonesShift(double oscillator1SemitonesShift) {
        this.oscillator1SemitonesShift = oscillator1SemitonesShift;
    }

    public double getOscillator1CentsShift() {
        return oscillator1CentsShift;
    }

    public void setOscillator1CentsShift(double oscillator1CentsShift) {
        this.oscillator1CentsShift = oscillator1CentsShift;
    }

    public double getOscillator2SemitonesShift() {
        return oscillator2SemitonesShift;
    }

    public void setOscillator2SemitonesShift(double oscillator2SemitonesShift) {
        this.oscillator2SemitonesShift = oscillator2SemitonesShift;
    }

    public double getOscillator2CentsShift() {
        return oscillator2CentsShift;
    }

    public void setOscillator2CentsShift(double oscillator2CentsShift) {
        this.oscillator2CentsShift = oscillator2CentsShift;
    }

    public double getMixValue() {
        return mixValue;
    }

    public void setMixValue(double mixValue) {
        this.mixValue = mixValue;
    }

}

