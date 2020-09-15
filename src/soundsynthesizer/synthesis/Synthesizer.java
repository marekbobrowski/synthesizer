package soundsynthesizer.synthesis;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Marek Bobrowski
 */
public class Synthesizer implements Runnable {
    private final CopyOnWriteArrayList <Note> notes = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList <Note> notesToBeRemoved = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList <Note> notesToBeAdded = new CopyOnWriteArrayList<>();
    private final Delay delay = new Delay();
    private final Reverb reverb = new Reverb();
    private final Converter converter = new Converter();
    private final NoteMixer noteMixer = new NoteMixer();
    private final AmplifierEnvelopeSettings amplifierEnvelopeSettings = new AmplifierEnvelopeSettings();
    private final OscillatorSettings oscillatorSettings = new OscillatorSettings();

    private boolean keepBuffering = false;

    public Synthesizer() {
    }

    public void playNewNote(Note newNote) {
        notesToBeAdded.add(newNote);
    }

    public void endNote(Note note) {
        notesToBeRemoved.add(note);
    }

    private void endFinishedNotes() {
        for(Note n: notesToBeRemoved) {
            notes.remove(n);
        }
        notesToBeRemoved.clear();
    }

    private void acceptNewNotes() {
        notes.addAll(notesToBeAdded);
        notesToBeAdded.clear();
    }

    private double[][][] gatherBuffersFromAllNotes() {
        double[][][] allNoteBuffers = new double[notes.size()][2][Converter.BUFFER_SIZE];
        int index = 0;
        for(Note n : notes) {
            if(index >= allNoteBuffers.length) break;
            allNoteBuffers[index] = n.prepareBuffer();
            index++;
        }
        return allNoteBuffers;
    }

    private void processGatheredNotes(double[][][] allNoteBuffers) {
        double[][] mixedNotes = noteMixer.mixAndNormalizeAllNotes(allNoteBuffers);
        delay.processBuffer(mixedNotes);
        mixedNotes = reverb.createProcessedBuffer(mixedNotes);
        converter.streamBuffer(mixedNotes);
    }

    public void finishWork() {
        keepBuffering = false;
        converter.stopStreaming();
    }

    @Override
    public void run() {
        double[][][] allNoteBuffers;
        this.keepBuffering = true;
        while (keepBuffering) {
            acceptNewNotes();
            endFinishedNotes();
            allNoteBuffers = gatherBuffersFromAllNotes();
            processGatheredNotes(allNoteBuffers);
        }
    }

    public Delay getDelay() {
        return delay;
    }

    public Reverb getReverb() {
        return reverb;
    }

    public AmplifierEnvelopeSettings getAmplifierEnvelope() {
        return amplifierEnvelopeSettings;
    }

    public OscillatorSettings getOscillatorSettings() {
        return oscillatorSettings;
    }
}
