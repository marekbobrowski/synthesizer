package synthesizer.signalprocessing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * This class is responsible for converting sound buffers and streaming them into the sound card.
 * It accepts buffers in the format explained below:
 *
 * The initialization of buffer in such format should look like:
 * double[2][bufferSize] buffer = new double[2][bufferSize];
 *        ^
 *        because 2 channels
 *
 *
 * How to access desired samples from such buffer:
 * buffer[0] - an array of samples in the first (left) channel
 * buffer[1] - an array of samples in the second (right) channel
 * buffer[0][5] - 6th sample in the first (left) channel
 * buffer[1][12] - 13th sample in the second (right) channel
 *
 * The samples for each channel should be within [0-1] range.
 *
 * @author Marek Bobrowski
 */
public class Converter {
    /**
     * The sample rate of this converter.
     * It describes how many samples are there in 1 one second of sound.
     */
    public static final int SAMPLE_RATE = 44100;

    /**
     * The buffer size of this converter.
     * It describes how many frames (samples per channel) are there in one sound buffer.
     */
    private int bufferSize = 512;

    /**
     * The object responsible for streaming ready buffers into the sound card.
     */
    private SourceDataLine line;

    /**
     * Creates a {@link AudioFormat} and a {@link SourceDataLine} objects for streaming the sound buffers
     * into the sound card.
     */
    public Converter() {
        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, 16, 2, true, false);
        try {
            line = AudioSystem.getSourceDataLine(audioFormat);
            line.open(audioFormat, 4096);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
        line.start();
    }

    /**
     * Streams a buffer of sound into the sound card.
     * @param buffer The sound buffer to be streamed into the sound card.
     */
    public void streamBuffer(double[][] buffer) {
        byte[] convertedBuffer = doubleToByte(buffer);
        this.line.write(convertedBuffer, 0, convertedBuffer.length);
    }

    /**
     * Closes the {@link SourceDataLine} that is used for streaming the sound
     * buffers.
     */
    public void stopStreaming() {
        line.close();
    }

    /**
     * Converts the sound buffer into the format accepted by the {@link SourceDataLine}.
     * It converts a 2 - dimensional double array into a 1 - dimensional byte array.
     * The sample depth is changed from 64-bit to 16-bit.
     * @param samplesDouble The sound buffer to be converted.
     * @return The converted buffer in byte format.
     */
    private byte[] doubleToByte(double[][] samplesDouble) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(samplesDouble[0].length * 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < samplesDouble[0].length; i++) {
            byteBuffer.putShort((short)(samplesDouble[0][i] * (double)Short.MAX_VALUE));
            byteBuffer.putShort((short)(samplesDouble[1][i] * (double)Short.MAX_VALUE));
        }
        return byteBuffer.array();
    }

    /**
     * Returns the buffer size of this converter.
     * Buffer size describes how many frames (samples per channel) are there in one sound buffer.
     * @return The buffer size of this converter.
     */
    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * Sets the buffer size of this converter.
     * Buffer size describes how many frames (samples per channel) are there in one sound buffer.
     * @param bufferSize The buffer size of this converter.
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}
