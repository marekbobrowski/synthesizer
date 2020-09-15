package soundsynthesizer;

import soundsynthesizer._interface.SynthesizerInterface;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import soundsynthesizer.synthesis.Synthesizer;

/**
 * @author Marek Bobrowski
 */
public class SynthesizerWindow extends JFrame {
    public SynthesizerWindow(Synthesizer synthesizer) {
        super("A simple Java sound synthesizer");
        setSize(800,600);
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
