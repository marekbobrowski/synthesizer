package synthesizer._interface.input;

import synthesizer.dsp.voice.Voice;
import synthesizer.dsp.Synthesizer;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.concurrent.ConcurrentHashMap;

public class MidiHandler implements Receiver {
    /**
     * The synthesizer that will generate the voices.
     */
    private final Synthesizer synthesizer;

    /**
     * A dictionary that stores currently played voices by their MIDI note number.
     */
    private final ConcurrentHashMap<Integer, Voice> activeVoices = new ConcurrentHashMap<>();

    /**
     * Constructor.
     * @param synthesizer The synthesizer that will generate voices according to the received MIDI signals.
     */
    public MidiHandler(Synthesizer synthesizer) {
        this.synthesizer = synthesizer;
    }

    /**
     * Accepts the MIDI signal sent by a {@link javax.sound.midi.Transmitter} object.
     * @param message The MIDI message.
     * @param timeStamp This argument is unimportant in this application.
     */
    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage) {
            int command = ((ShortMessage) message).getCommand();
            int noteNumber = ((ShortMessage) message).getData1();
            if (command == 144) {
                if (activeVoices.size() >= 16) {
                    return;
                }
                int interval = noteNumber - 69;
                Voice newVoice = new Voice(this.synthesizer, this, calculateFrequencyByInterval(interval));
                activeVoices.put(noteNumber, newVoice);
                this.synthesizer.addNewVoice(newVoice);
            }
            else if (command == 128) {
                if (activeVoices.get(noteNumber) == null) {
                    return;
                }
                activeVoices.get(noteNumber).triggerRelease();
            }

        }
    }

    /**
     * End the voice by removing the voice from the activeVoices list.
     * @param voice The voice to be ended.
     */
    public void endVoice(Voice voice) {
        activeVoices.values().remove(voice);
    }

    /**
     * Closes this MIDI receiver.
     */
    @Override
    public void close() {
    }

    /**
     * Calculates the frequency of a note that differs from 440 Hz (A4) by the specified amount of semitones.
     * @param interval The number of semitones of difference.
     * @return Frequency of a note that differs from A4 by the specified interval.
     */
    private double calculateFrequencyByInterval(int interval) {
        return 440 * Math.pow(2, interval/12.0);
    }
}
