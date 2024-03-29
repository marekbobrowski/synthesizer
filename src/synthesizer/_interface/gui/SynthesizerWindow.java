package synthesizer._interface.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import synthesizer.dsp.Synthesizer;

/**
 * This class works as a window for interface of a synthesizer.
 *
 * @author Marek Bobrowski
 */
public class SynthesizerWindow extends JFrame {
    /**
     * Window used for settings up the MIDI input.
     */
    private MidiSettingsWindow midiSettingsWindow;

    /**
     * Sets up all basic parameters of the window, like it's dimensions, title or the method called on closing.
     * Creates an object of {@link SynthesizerInterface} and attaches it to itself (so the interface is displayed
     * in this window).
     *
     * @param synthesizer the synthesizer instance that is going to be controlled
     *                    through this window
     */
    public SynthesizerWindow(Synthesizer synthesizer) {
        super("Synthesizer");
        setSize(800,400);

        SynthesizerInterface synthesizerInterface = new SynthesizerInterface(synthesizer, this);

        JMenuBar menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");
        JMenu presetMenu = new JMenu("Preset");
        menuBar.add(settingsMenu);
        JMenuItem midiInput = new JMenuItem("MIDI input");
        midiInput.addActionListener(e -> {
            if (midiSettingsWindow == null) {
                midiSettingsWindow = new MidiSettingsWindow(synthesizerInterface.getMidiHandler());
            } else {
                midiSettingsWindow.updateDropDownMenu();
                midiSettingsWindow.setVisible(true);
            }
        });
        settingsMenu.add(midiInput);
        menuBar.add(presetMenu);
        JMenuItem load = new JMenuItem("Open...");
        JMenuItem save = new JMenuItem("Save as...");
        presetMenu.add(load);
        presetMenu.add(save);
        setJMenuBar(menuBar);


        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        add(synthesizerInterface, BorderLayout.CENTER);
        synthesizerInterface.requestFocus();
        synthesizerInterface.requestFocusInWindow();
        addWindowListener(new WindowAdapter() {
                                   @Override
                                   public void windowClosing(WindowEvent e) {
                                       if (midiSettingsWindow != null) {
                                           if (midiSettingsWindow.getCurrentInputDevice() != null) {
                                               midiSettingsWindow.getCurrentInputDevice().close();
                                           }
                                           midiSettingsWindow.dispose();
                                       }
                                       synthesizer.finishWork();
                                   }
                               });
    }
}
