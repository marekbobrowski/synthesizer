package soundsynthesizer._interface;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ConcurrentHashMap;
import soundsynthesizer.synthesis.Note;
import soundsynthesizer.synthesis.Synthesizer;

/**
 * @author Marek Bobrowski
 */
public class KeyActionHandler extends KeyAdapter {
    private final Synthesizer synthesizer;
    private final ConcurrentHashMap<Character, Note> pressedKeysAndTheirNotes = new ConcurrentHashMap<>();

    public KeyActionHandler(Synthesizer synthesizer) {
        this.synthesizer = synthesizer;
    }

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

    private double calculateFrequencyByInterval(int semitones) {
        return 440 * Math.pow(2, semitones/12.0);
    }

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

    private void handleKeyPressed(Character c, double frequency) {
        if (!this.pressedKeysAndTheirNotes.containsKey(c)) {
            Note newNote = new Note(synthesizer, frequency);
            this.pressedKeysAndTheirNotes.put(c, newNote);
            synthesizer.playNewNote(newNote);
        }
    }
}
