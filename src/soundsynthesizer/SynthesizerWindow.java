package soundsynthesizer;

import soundsynthesizer._interface.MidiSettingsWindow;
import soundsynthesizer._interface.SynthesizerInterface;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;

import soundsynthesizer.synthesis.Synthesizer;

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

        JMenuBar menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");
        JMenu presetMenu = new JMenu("Preset");
        menuBar.add(settingsMenu);
        JMenuItem midiInput = new JMenuItem("MIDI input");
        midiInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (midiSettingsWindow == null) {
                    midiSettingsWindow = new MidiSettingsWindow(synthesizerInterface.getMidiHandler());
                } else {
                    midiSettingsWindow.updateDropDownMenu();
                    midiSettingsWindow.setVisible(true);
                }
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
                                       midiSettingsWindow.getCurrentInputDevice().close();
                                       midiSettingsWindow.dispose();
                                       synthesizer.finishWork();
                                   }
                               });
    }
}
