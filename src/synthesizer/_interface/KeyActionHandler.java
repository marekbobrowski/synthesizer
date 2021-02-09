package synthesizer._interface;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

import static java.util.Map.entry;

/**
 * This class is responsible for handling key pressing (on a computer keyboard, not MIDI keyboard) by sending
 * MIDI signals into the {@link MidiHandler} object.
 *
 * @author Marek Bobrowski
 */
public class KeyActionHandler extends KeyAdapter {
    /**
     * A list that stores the currently pressed keys.
     */
    private final CopyOnWriteArrayList<Character> pressedKeys = new CopyOnWriteArrayList<>();

    /**
     * A transmitter responsible for sending MIDI signals into the {@link MidiHandler} object.
     */
    private final ComputerKeyboardTransmitter transmitter = new ComputerKeyboardTransmitter();

    /**
     * A dictionary that assigns MIDI note numbers to the keyboard characters.
     */
    private final Map<Character, Integer> charactersAndTheirNumbers = Map.ofEntries(
            entry('z', 48), entry('s', 49), entry('x', 50),
            entry('d', 51), entry('c', 52), entry('v', 53),
            entry('g', 54), entry('b', 55), entry('h', 56),
            entry('n', 57), entry('j', 58), entry('m', 59),
            entry(',', 60), entry('l', 61), entry('.', 62),
            entry(';', 63), entry('/', 64),

            entry('q', 60), entry('2', 61), entry('w', 62),
            entry('3', 63), entry('e', 64), entry('r', 65),
            entry('5', 66), entry('t', 67), entry('6', 68),
            entry('y', 69), entry('7', 70), entry('u', 71),
            entry('i', 72), entry('9', 73), entry('o', 74),
            entry('0', 75), entry('p', 76), entry('[', 77),
            entry('=', 78), entry(']', 79)
            );

    /**
     * Constructor.
     * @param midiHandler The handler that will handle the MIDI signals sent by this KeyActionHandler.
     */
    public KeyActionHandler(MidiHandler midiHandler) {
        transmitter.setReceiver(midiHandler);
    }

    /**
     * Handles the pressing of a key. Sends a MIDI signal with the 144 command to the {@link MidiHandler} object.
     * @param ke Information about the event that occurred.
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        if (pressedKeys.contains(ke.getKeyChar()) || !charactersAndTheirNumbers.containsKey((ke.getKeyChar()))) {
            return;
        }
        pressedKeys.add(ke.getKeyChar());
        ShortMessage shortMessage= new ShortMessage();
        try {
            shortMessage.setMessage(144, 0, charactersAndTheirNumbers.get(ke.getKeyChar()), 0);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        transmitter.getReceiver().send(shortMessage, -1);
    }

    /**
     * Handles the releasing of a key. Sends a MIDI signal with the 128 command to the {@link MidiHandler} object.
     * @param ke Information about the event that occurred.
     */
    @Override
    public void keyReleased(KeyEvent ke) {
        if (!pressedKeys.contains(ke.getKeyChar()) || !charactersAndTheirNumbers.containsKey(ke.getKeyChar())) {
            return;
        }
        ShortMessage shortMessage = new ShortMessage();
        try {
            shortMessage.setMessage(128, 0, charactersAndTheirNumbers.get(ke.getKeyChar()), 0);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        transmitter.getReceiver().send(shortMessage, -1);
        pressedKeys.remove(pressedKeys.indexOf(ke.getKeyChar()));
    }

    /**
     * This nested class is used as a transmitter for sending MIDI signals into the {@link Receiver} object.
     */
    static class ComputerKeyboardTransmitter implements Transmitter {
        /**
         * The MIDI receiver.
         */
        private Receiver receiver;

        /**
         * Sets the MIDI receiver.
         * @param receiver The MIDI receiver.
         */
        @Override
        public void setReceiver(Receiver receiver) {
            this.receiver = receiver;
        }

        /**
         * Returns the MIDI receiver.
         * @return The MIDI receiver.
         */
        @Override
        public Receiver getReceiver() {
            return receiver;
        }

        /**
         * Closes this transmitter.
         */
        @Override
        public void close() {
        }
    }
}
