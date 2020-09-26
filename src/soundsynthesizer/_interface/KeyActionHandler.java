package soundsynthesizer._interface;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ConcurrentHashMap;
import soundsynthesizer.synthesis.Note;
import soundsynthesizer.synthesis.Synthesizer;

/**
 * This class is responsible for handling key pressing (starting and ending the notes).
 *
 * @author Marek Bobrowski
 */
public class KeyActionHandler extends KeyAdapter {
    /**
     * The synthesizer that will play the notes.
     */
    private final Synthesizer synthesizer;

    /**
     * A dictionary that stores currently pressed keys (only from those specified in the keyPressed function).
     */
    private final ConcurrentHashMap<Character, Note> pressedKeysAndTheirNotes = new ConcurrentHashMap<>();

    public KeyActionHandler(Synthesizer synthesizer) {
        this.synthesizer = synthesizer;
    }

    /**
     * Assigns 37 keys to different frequencies. When the user presses a certain key,
     * then the note of the assigned frequency will be played by the synthesizer.
     * @param ke Information about the event that occurred.
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyChar()) {
            case 'z':
                handleKeyPressed('z', calculateFrequencyByInterval(-9));
                break;
            case 's':
                handleKeyPressed('s', calculateFrequencyByInterval(-8));
                break;
            case 'x':
                handleKeyPressed('x', calculateFrequencyByInterval(-7));
                break;
            case 'd':
                handleKeyPressed('d', calculateFrequencyByInterval(-6));
                break;
            case 'c':
                handleKeyPressed('c', calculateFrequencyByInterval(-5));
                break;
            case 'v':
                handleKeyPressed('v', calculateFrequencyByInterval(-4));
                break;
            case 'g':
                handleKeyPressed('g', calculateFrequencyByInterval(-3));
                break;
            case 'b':
                handleKeyPressed('b', calculateFrequencyByInterval(-2));
                break;
            case 'h':
                handleKeyPressed('h', calculateFrequencyByInterval(-1));
                break;
            case 'n':
                handleKeyPressed('n', calculateFrequencyByInterval(0));
                break;
            case 'j':
                handleKeyPressed('j', calculateFrequencyByInterval(1));
                break;
            case 'm':
                handleKeyPressed('m', calculateFrequencyByInterval(2));
                break;
            case ',':
                handleKeyPressed(',', calculateFrequencyByInterval(3));
                break;
            case 'l':
                handleKeyPressed('l', calculateFrequencyByInterval(4));
                break;
            case '.':
                handleKeyPressed('.', calculateFrequencyByInterval(5));
                break;
            case ';':
                handleKeyPressed(';', calculateFrequencyByInterval(6));
                break;
            case '/':
                handleKeyPressed('/', calculateFrequencyByInterval(7));
                break;
            case 'q':
                handleKeyPressed('q', calculateFrequencyByInterval(3));
                break;
            case '2':
                handleKeyPressed('2', calculateFrequencyByInterval(4));
                break;
            case 'w':
                handleKeyPressed('w', calculateFrequencyByInterval(5));
                break;
            case '3':
                handleKeyPressed('3', calculateFrequencyByInterval(6));
                break;
            case 'e':
                handleKeyPressed('e', calculateFrequencyByInterval(7));
                break;
            case 'r':
                handleKeyPressed('r', calculateFrequencyByInterval(8));
                break;
            case '5':
                handleKeyPressed('5', calculateFrequencyByInterval(9));
                break;
            case 't':
                handleKeyPressed('t', calculateFrequencyByInterval(10));
                break;
            case '6':
                handleKeyPressed('6', calculateFrequencyByInterval(11));
                break;
            case 'y':
                handleKeyPressed('y', calculateFrequencyByInterval(12));
                break;
            case '7':
                handleKeyPressed('7', calculateFrequencyByInterval(13));
                break;
            case 'u':
                handleKeyPressed('u', calculateFrequencyByInterval(14));
                break;
            case 'i':
                handleKeyPressed('i', calculateFrequencyByInterval(15));
                break;
            case '9':
                handleKeyPressed('9', calculateFrequencyByInterval(16));
                break;
            case 'o':
                handleKeyPressed('o', calculateFrequencyByInterval(17));
                break;
            case '0':
                handleKeyPressed('0', calculateFrequencyByInterval(18));
                break;
            case 'p':
                handleKeyPressed('p', calculateFrequencyByInterval(19));
                break;
            case '[':
                handleKeyPressed('[', calculateFrequencyByInterval(20));
                break;
            case '=':
                handleKeyPressed('=', calculateFrequencyByInterval(21));
                break;
            case ']':
                handleKeyPressed(']', calculateFrequencyByInterval(22));
                break;
            default:
                break;
        }
    }

    /**
     * Calculates the frequency of a note that differs from 440 Hz by the specified amount of semitones.
     * @param semitones Number of semitones of difference.
     * @return Frequency of a note that differs from 440 Hz by the specified amount of semitones.
     */
    private double calculateFrequencyByInterval(int semitones) {
        return 440 * Math.pow(2, semitones/12.0);
    }

    /**
     * Handles the releasing of a key. Shortly, triggers the release phase of the note that was being played
     * by the released key.
     * @param ke Information about the event that occurred.
     */
    @Override
    public void keyReleased(KeyEvent ke) {
        Character c = ke.getKeyChar();
        if (this.pressedKeysAndTheirNotes.containsKey(c)) {
            if (this.pressedKeysAndTheirNotes.get(c) != null) {
                this.pressedKeysAndTheirNotes.get(c).triggerRelease();
            }
            this.pressedKeysAndTheirNotes.remove(c);
        }
    }


    /**
     * Handles the pressing of a key - tells the synthesizer to play a note.
     * @param c The character of the pressed key.
     * @param frequency The frequency to be played by the synthesizer.
     */
    private void handleKeyPressed(Character c, double frequency) {
        if (!this.pressedKeysAndTheirNotes.containsKey(c)) {
            Note newNote = new Note(synthesizer, frequency);
            this.pressedKeysAndTheirNotes.put(c, newNote);
            synthesizer.playNewNote(newNote);
        }
    }
}
