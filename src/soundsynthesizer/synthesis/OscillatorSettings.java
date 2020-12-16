package soundsynthesizer.synthesis;

/**
 * This class is responsible for storing oscillator settings of a synthesizer:
 * Waveforms, frequency shifts, mix between the oscillators.
 *
 * @author Marek Bobrowski
 */
public class OscillatorSettings {
    /**
     * The waveform of the first oscillator.
     * 0 - sine wave
     * 1 - triangle wave
     * 2 - sawtooth wave
     * 3 - pulse wave
     */
    private int oscillator1Type = 0;

    /**
     * The waveform of the second oscillator.
     * 0 - sine wave
     * 1 - triangle wave
     * 2 - sawtooth wave
     * 3 - pulse wave
     */
    private int oscillator2Type = 0;

    /**
     * The semitone shift of the first oscillator.
     */
    private double oscillator1SemitonesShift = 0;

    /**
     * The cent shift of the first oscillator.
     * 100 cents = 1 semitone.
     */
    private double oscillator1CentsShift = 0;

    /**
     * The semitone shift of the second oscillator.
     */
    private double oscillator2SemitonesShift = 0;

    /**
     * The cent shift of the second oscillator.
     * 100 cents = 1 semitone.
     */
    private double oscillator2CentsShift = 0;

    /**
     * The mix balance between the two oscillators.
     */
    private double mixValue = 0.5;

    /**
     * Empty constructor.
     */
    public OscillatorSettings() {
    }


    /**
     * Get the waveform of the first oscillator.
     * 0 - sine wave
     * 1 - triangle wave
     * 2 - sawtooth wave
     * 3 - pulse wave
     * @return The waveform of the first oscillator.
     */
    public int getOscillator1Type() {
        return oscillator1Type;
    }

    /**
     * Set the waveform of the first oscillator.
     * 0 - sine wave
     * 1 - triangle wave
     * 2 - sawtooth wave
     * 3 - pulse wave
     * @param oscillator1Type The waveform of the first oscillator.
     */
    public void setOscillator1Type(int oscillator1Type) {
        this.oscillator1Type = oscillator1Type;
    }

    /**
     * Get the waveform of the second oscillator.
     * 0 - sine wave
     * 1 - triangle wave
     * 2 - sawtooth wave
     * 3 - pulse wave
     * @return The waveform of the second oscillator.
     */
    public int getOscillator2Type() {
        return oscillator2Type;
    }

    /**
     * Set the waveform of the second oscillator.
     * 0 - sine wave
     * 1 - triangle wave
     * 2 - sawtooth wave
     * 3 - pulse wave
     * @param oscillator2Type The waveform of the second oscillator.
     */
    public void setOscillator2Type(int oscillator2Type) {
        this.oscillator2Type = oscillator2Type;
    }

    /**
     * Get the semitone shift of the first oscillator.
     * @return The semitone shift of the first oscillator.
     */
    public double getOscillator1SemitonesShift() {
        return oscillator1SemitonesShift;
    }

    /**
     * Set the semitone shift of the first oscillator.
     * @param oscillator1SemitonesShift The semitone shift of the first oscillator.
     */
    public void setOscillator1SemitonesShift(double oscillator1SemitonesShift) {
        this.oscillator1SemitonesShift = oscillator1SemitonesShift;
    }

    /**
     * Get the cent shift of the first oscillator.
     * 100 cents = 1 semitone.
     * @return The cent shift of the first oscillator.
     */
    public double getOscillator1CentsShift() {
        return oscillator1CentsShift;
    }

    /**
     * Set the cent shift of the first oscillator.
     * 100 cents = 1 semitone.
     * @param oscillator1CentsShift The cent shift of the first oscillator.
     */
    public void setOscillator1CentsShift(double oscillator1CentsShift) {
        this.oscillator1CentsShift = oscillator1CentsShift;
    }

    /**
     * Get the semitone shift of the  second oscillator.
     * @return The semitone shift of the second oscillator.
     */
    public double getOscillator2SemitonesShift() {
        return oscillator2SemitonesShift;
    }

    /**
     * Set the semitone shift of the second oscillator.
     * @param oscillator2SemitonesShift The semitone shift of the second oscillator.
     */
    public void setOscillator2SemitonesShift(double oscillator2SemitonesShift) {
        this.oscillator2SemitonesShift = oscillator2SemitonesShift;
    }

    /**
     * Get the cent shift of the second oscillator.
     * 100 cents = 1 semitone.
     * @return The cent shift of the second oscillator.
     */
    public double getOscillator2CentsShift() {
        return oscillator2CentsShift;
    }

    /**
     * Set the cent shift of the second oscillator.
     * 100 cents = 1 semitone.
     * @param oscillator2CentsShift The cent shift of the second oscillator.
     */
    public void setOscillator2CentsShift(double oscillator2CentsShift) {
        this.oscillator2CentsShift = oscillator2CentsShift;
    }

    /**
     * Get the mix balance between the two oscillators.
     * @return The mix balance between the two oscillators.
     */
    public double getMixValue() {
        return mixValue;
    }

    /**
     * Set the mix balance between the two oscillators.
     * @param mixValue The mix balance between the two oscillators.
     */
    public void setMixValue(double mixValue) {
        this.mixValue = mixValue;
    }

}

