package soundsynthesizer.synthesis;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is responsible for running and managing the whole process of synthesis:
 * creating notes, mixing them, applying effects and sending to output.
 *
 * @author Marek Bobrowski
 */
public class Synthesizer implements Runnable {
    /**
     * A list of notes that are currently being played.
     */
    private final CopyOnWriteArrayList <Note> notes = new CopyOnWriteArrayList<>();

    /**
     * A list of notes that are going to be removed after the current buffer.
     */
    private final CopyOnWriteArrayList <Note> notesToBeRemoved = new CopyOnWriteArrayList<>();

    /**
     * A list of notes that are going to be played within the next buffer.
     */
    private final CopyOnWriteArrayList <Note> notesToBeAdded = new CopyOnWriteArrayList<>();

    /**
     * The delay effect used to process the sound of this synthesizer.
     */
    private final Delay delay = new Delay();

    /**
     * The reverberation effect used to process the sound of this synthesizer.
     */
    private final Reverb reverb = new Reverb();

    /**
     * The converter that is going to stream the signal from this synthesizer to a sound card.
     */
    private final Converter converter = new Converter();

    /**
     * The amplifier envelope settings of the notes played by this synthesizer.
     */
    private final AmplifierEnvelopeSettings amplifierEnvelopeSettings = new AmplifierEnvelopeSettings();

    /**
     * The settings of the oscillators that generate sound for this synthesizer.
     */
    private final OscillatorSettings oscillatorSettings = new OscillatorSettings();

    /**
     * Tells if this synthesizer should keep buffering new sound.
     */
    private boolean keepBuffering = false;

    /**
     * Empty constructor.
     */
    public Synthesizer() {
    }

    /**
     * Add the note to the "waiting room" - 'notesToBeAdded' list.
     * @param newNote The note to be played.
     */
    public void playNewNote(Note newNote) {
        notesToBeAdded.add(newNote);
    }

    /**
     * Add the note to the 'notesToBeRemoved' list.
     * @param note The note to be removed.
     */
    public void endNote(Note note) {
        notesToBeRemoved.add(note);
    }

    /**
     * Remove the 'notesToBeRemoved' list from the 'notes' list.
     */
    private void endFinishedNotes() {
        for(Note n: notesToBeRemoved) {
            notes.remove(n);
        }
        notesToBeRemoved.clear();
    }

    /**
     * Add all notes from the 'notesToBeAdded' list to the 'notes' list.
     */
    private void acceptNewNotes() {
        notes.addAll(notesToBeAdded);
        notesToBeAdded.clear();
    }

    /**
     * Gathers buffers from all the notes.
     * @param bufferSize The number of frames of a sound buffer.
     * @return The array of sound buffers created by all the notes.
     */
    private double[][][] gatherBuffersFromAllNotes(int bufferSize) {
        double[][][] allNoteBuffers = new double[notes.size()][2][bufferSize];
        int index = 0;
        for(Note n : notes) {
            if (index >= allNoteBuffers.length) {
                break;
            }
            allNoteBuffers[index] = n.prepareBuffer(bufferSize);
            index++;
        }
        return allNoteBuffers;
    }

    /**
     * Returns mixed and processed sound of all notes.
     * @param allNoteBuffers The buffers to be mixed and processed.
     * @param bufferSize The number of frames of a sound buffer.
     */
    private double[][] returnProcessedNotes(double[][][] allNoteBuffers, int bufferSize) {
        double[][] mixedNotes = Note.mixAndNormalizeNotes(allNoteBuffers, bufferSize);
        delay.processBuffer(mixedNotes);
        mixedNotes = reverb.createProcessedBuffer(mixedNotes);
        return mixedNotes;
    }

    /**
     * Stops streaming the sound to the sound card.
     */
    public void finishWork() {
        keepBuffering = false;
        converter.stopStreaming();
    }

    /**
     * Continuous process of buffering the sound:
     * 1. Add notes that have been recently triggered.
     * 2. Remove the notes that ended.
     * 3. Gather the buffers from all the existing notes.
     * 4. Mix and process the gathered buffers.
     * 5. Send the processed sound to the output.
     */
    @Override
    public void run() {
        double[][][] allNoteBuffers;
        this.keepBuffering = true;
        while (keepBuffering) {
            int bufferSize = converter.getBufferSize();
            acceptNewNotes();
            endFinishedNotes();
            allNoteBuffers = gatherBuffersFromAllNotes(bufferSize);
            double[][] processedNotes = returnProcessedNotes(allNoteBuffers, bufferSize);
            converter.streamBuffer(processedNotes);
        }
    }

    /**
     * Get the delay effect used to process the sound of this synthesizer.
     * @return The delay effect used to process the sound of this synthesizer.
     */
    public Delay getDelay() {
        return delay;
    }

    /**
     * Get reverberation effect used to process the sound of this synthesizer.
     * @return The reverberation effect used to process the sound of this synthesizer.
     */
    public Reverb getReverb() {
        return reverb;
    }

    /**
     * Get the amplifier envelope settings of the notes played by this synthesizer.
     * @return The amplifier envelope settings of the notes played by this synthesizer.
     */
    public AmplifierEnvelopeSettings getAmplifierEnvelope() {
        return amplifierEnvelopeSettings;
    }

    /**
     * Get the settings of the oscillators that generate sound for this synthesizer.
     * @return The settings of the oscillators that generate sound for this synthesizer.
     */
    public OscillatorSettings getOscillatorSettings() {
        return oscillatorSettings;
    }

    /**
     * Get the converter that is going to stream the signal from this synthesizer to a sound card.
     * @return The converter that is going to stream the signal from this synthesizer to a sound card.
     */
    public Converter getConverter() {
        return converter;
    }
}
