package soundsynthesizer;

import soundsynthesizer._interface.SynthesizerInterface;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import soundsynthesizer.synthesis.Synthesizer;

/**
 * This class works as a window for interface of a synthesizer.
 *
 * @author Marek Bobrowski
 */
public class SynthesizerWindow extends JFrame {

    /**
     * Sets up all basic parameters of the window, like it's dimensions, title or method called on closing.
     * Creates an object of {@link SynthesizerInterface} and attaches it to itself (so the interface is displayed
     * in this window).
     *
     * @param synthesizer the synthesizer instance that is going to be controlled
     *                    through this window
     */
    public SynthesizerWindow(Synthesizer synthesizer) {
        super("A simple Java sound synthesizer");
        setSize(800,400);
        SynthesizerInterface synthesizerInterface = new SynthesizerInterface(synthesizer, this);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        this.setLayout(new BorderLayout());
        this.add(synthesizerInterface, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
                                   @Override
                                   public void windowClosing(WindowEvent e) {
                                       synthesizer.finishWork();
                                   }
                               });
    }
}
