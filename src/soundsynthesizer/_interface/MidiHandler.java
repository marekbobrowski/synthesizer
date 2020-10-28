package soundsynthesizer._interface;

import soundsynthesizer.synthesis.Note;
import soundsynthesizer.synthesis.Synthesizer;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.concurrent.ConcurrentHashMap;

public class MidiHandler implements Receiver {
    /**
     * The synthesizer that will play the notes.
     */
    private final Synthesizer synthesizer;

    /**
     * A dictionary that stores currently played notes by their MIDI number.
     */
    private final ConcurrentHashMap<Integer, Note> activeNotes = new ConcurrentHashMap<>();

    /**
     * Constructor.
     * @param synthesizer The synthesizer that will play the notes according to the received MIDI signals.
     */
    public MidiHandler(Synthesizer synthesizer) {
        this.synthesizer = synthesizer;
    }

    /**
     * Accepts the MIDI signal sent by a {@link Receiver} object.
     * @param message The MIDI message.
     * @param timeStamp This argument is unimportant in this application.
     */
    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage) {
            int command = ((ShortMessage) message).getCommand();
            int noteNumber = ((ShortMessage) message).getData1();
            if (command == 144) {
                if (activeNotes.get(noteNumber) != null) {
                    return;
                }
                int interval = noteNumber - 69;
                Note newNote = new Note(this.synthesizer, calculateFrequencyByInterval(interval));
                activeNotes.put(noteNumber, newNote);
                this.synthesizer.playNewNote(newNote);
            }
            else if (command == 128) {
                if (activeNotes.get(noteNumber) == null) {
                    return;
                }
                activeNotes.get(noteNumber).triggerRelease();
                activeNotes.remove(noteNumber);
            }

        }
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
