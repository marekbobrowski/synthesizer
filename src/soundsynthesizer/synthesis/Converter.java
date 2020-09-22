package soundsynthesizer.synthesis;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * @author Marek Bobrowski
 */
public class Converter {
    static final int SAMPLE_RATE = 44100;
    private int bufferSize = 512;
    private SourceDataLine line;

    public Converter() {
        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, 16, 2, true, false);
        try {
            line = AudioSystem.getSourceDataLine(audioFormat);
            line.open(audioFormat, 2048);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
        line.start();
    }

    public void streamBuffer(double[][] buffer) {
        byte[] convertedBuffer = doubleToByte(buffer);
        this.line.write(convertedBuffer, 0, convertedBuffer.length);
    }

    public void stopStreaming() {
        line.close();
    }

    private byte[] doubleToByte(double[][] samplesDouble) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(samplesDouble[0].length * 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < samplesDouble[0].length; i++) {
            byteBuffer.putShort((short)(samplesDouble[0][i] * (double)Short.MAX_VALUE));
            byteBuffer.putShort((short)(samplesDouble[1][i] * (double)Short.MAX_VALUE));
        }
        return byteBuffer.array();
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}
