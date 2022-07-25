package synthesizer._interface.gui;

import synthesizer._interface.input.MidiHandler;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class works as a window for setting up the MIDI input device.
 */
public class MidiSettingsWindow extends JFrame {
    /**
     * The device that is sending the MIDI signal into the synthesizer.
     */
    private MidiDevice currentInputDevice;

    /**
     * Drop down menu for choosing an input device.
     */
    private final JComboBox<String> dropDownMenu;

    /**
     * Constructor.
     * @param midiHandler The object responsible for handling the MIDI signals from the input.
     */
    public MidiSettingsWindow(MidiHandler midiHandler) {
        super("MIDI input");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0.2f, 0.2f, 0.2f));
        add(panel);

        JLabel instruction = new JLabel("Choose MIDI input");
        instruction.setForeground(new Color(0.7f, 0.7f, 0.7f));
        panel.add(instruction);

        JButton acceptButton = new JButton("OK");

        // Add a handler to the accept-button.
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String) dropDownMenu.getSelectedItem();
                MidiDevice device = findMidiDeviceByName(name);
                if (device != null) {
                    try {
                        if (currentInputDevice != null) {
                            currentInputDevice.close();
                        }
                        currentInputDevice = device;
                        currentInputDevice.getTransmitter().setReceiver(midiHandler);


                        currentInputDevice.open();
                        dispose();
                    } catch (MidiUnavailableException midiUnavailableException) {
                        midiUnavailableException.printStackTrace();
                    }
                }
            }
        });

        dropDownMenu = new JComboBox<>();
        updateDropDownMenu();

        panel.add(dropDownMenu);
        panel.add(acceptButton);

        setSize(300,100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Scans all the available devices for ones that can send input into the synthesizer and creates
     * a drop-down menu with their names.
     */
    public void updateDropDownMenu() {
        MidiDevice.Info[] midiInfo = MidiSystem.getMidiDeviceInfo();
        ArrayList<String> names = new ArrayList<>();

        // Scan for MIDI devices that can send input.
        for (int i = 0; i < midiInfo.length; i++) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(midiInfo[i]);
                if (device.getMaxReceivers() >= 0)
                {
                    names.add(device.getDeviceInfo().toString());
                }

            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }

        // Update the list in the drop-down menu.
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.addAll(names);
        Object selectedItem = dropDownMenu.getSelectedItem();
        dropDownMenu.setModel(comboBoxModel);
        if (selectedItem != null) {
            dropDownMenu.setSelectedItem(selectedItem);
        }
    }

    /**
     * Finds a MIDI device with a given name. Returns null if none was found.
     * @param name The name of the device.
     * @return The MIDI device with given name.
     */
    private MidiDevice findMidiDeviceByName(String name) {
        MidiDevice.Info[] midiInfo = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < midiInfo.length; i++) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(midiInfo[i]);
                if (device.getDeviceInfo().toString() == name) {
                    return device;
                }

            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Return the device that is sending the MIDI signal into the synthesizer.
     * @return The device that is sending the MIDI signal into the synthesizer.
     */
    public MidiDevice getCurrentInputDevice() {
        return currentInputDevice;
    }
}
