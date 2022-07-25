package synthesizer._interface.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

import synthesizer._interface.gui.panels.*;
import synthesizer._interface.gui.units.ClickableElement;
import synthesizer._interface.input.KeyActionHandler;
import synthesizer._interface.input.MidiHandler;
import synthesizer.signalprocessing.Synthesizer;

/**
 * This class works as an interface for the assigned synthesizer.
 * It stores all the sub-interfaces (panels) for each of the synthesizer modules.
 * For example: a panel for reverb, a panel for oscillators etc.
 * It's responsible for:
 * - handling the mouse actions on the interface.
 * - drawing all the sub-interfaces (panels).
 *
 * @author Marek Bobrowski
 */
public class SynthesizerInterface extends JPanel {
    /**
     * The parent window of this interface.
     */
    private final SynthesizerWindow parentWindow;

    /**
     * List of the panels attached to this synthesizer interface.
     */
    private final ArrayList<Panel> panels = new ArrayList<>();

    /**
     * The currently clicked element (chosen from all the {@link ClickableElement}s from all the panels attached
     * to this synthesizer interface). If none is clicked, then this value is null.
     */
    private ClickableElement clickedElement;

    /**
     * The object responsible for handling the MIDI data.
     */
    private final MidiHandler midiHandler;


    /**
     * Sets up all the basic parameters of the interface and creates a panel for each of the synthesizer modules.
     * @param synthesizer The synthesizer that will be controlled by this interface.
     * @param parentWindow The parent window of this interface.
     */
    public SynthesizerInterface(Synthesizer synthesizer, SynthesizerWindow parentWindow) {
        this.parentWindow = parentWindow;
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.midiHandler = new MidiHandler(synthesizer);

        MouseActionHandler mouseActionHandler = new MouseActionHandler();
        addMouseListener(mouseActionHandler);
        addMouseMotionListener(mouseActionHandler);

        KeyActionHandler keyActionHandler = new KeyActionHandler(midiHandler);
        addKeyListener(keyActionHandler);

        OscillatorPanel oscillatorPanel =
                new OscillatorPanel(synthesizer.getOscillatorSettings(), 30, 30);

        EnvelopePanel envelopePanel =
                new EnvelopePanel(synthesizer.getEnvelopeSettings(), 330, 60);

        DelayPanel delayPanel =
                new DelayPanel(synthesizer.getDelay(), 660, 60);

        ReverbPanel reverbPanel =
                new ReverbPanel(synthesizer.getReverb(), 660, 150);

        BufferSizePanel bufferSizePanel =
                new BufferSizePanel(synthesizer.getConverter(), 30, 210);

        VolumePanel volumePanel =
                new VolumePanel(synthesizer.getVolume(), 660, 240);

        DetailPanel detailPanel = new DetailPanel(30, 300, this);

        panels.add(oscillatorPanel);
        panels.add(envelopePanel);
        panels.add(delayPanel);
        panels.add(reverbPanel);
        panels.add(detailPanel);
        panels.add(bufferSizePanel);
        panels.add(volumePanel);
    }

    /**
     * Draws all the panels attached to this interface.
     * @param g The object that will perform the drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.setColor(new Color(0.2f, 0.2f, 0.2f));
        graphics2d.fillRect(0, 0, parentWindow.getWidth(), parentWindow.getHeight());
        for (Panel m : panels) {
            m.draw(graphics2d);
        }
    }

    /**
     * Returns the currently clicked element. If none is clicked, then it will return null.
     * @return The currently clicked element.
     */
    public ClickableElement getClickedElement(){
        return clickedElement;
    }

    /**
     * Returns the parent window object.
     * @return The parent window object.
     */
    public SynthesizerWindow getParentWindow() {
        return parentWindow;
    }

    /**
     * Returns the object responsible for handling the MIDI data.
     * @return The object responsible for handling the MIDI data.
     */
    public MidiHandler getMidiHandler() {
        return midiHandler;
    }

    /**
     * Nested class used for handling the mouse actions on the interface.
     */
    public class MouseActionHandler extends MouseAdapter {

        /**
         * Handles the mouse pressing.
         * @param event The information about the mouse event that occurred.
         */
        @Override
        public void mousePressed(MouseEvent event) {
            for(Panel m : panels) {
                ClickableElement e = m.returnClickedElement(event.getPoint());
                if(e != null) {
                    clickedElement = e;
                    clickedElement.handleClicking();
                    repaint();
                    return;
                }
            }
        }

        /**
         * Handles the mouse dragging.
         * @param event The information about the mouse event that occurred.
         */
        @Override
        public void mouseDragged(MouseEvent event) {
            if (clickedElement != null && clickedElement.isDraggable()) {
                clickedElement.handleDragging(event.getPoint());
                repaint();
            }
        }

        /**
         * Handles the mouse releasing.
         * @param event The information about the mouse event that occurred.
         */
        @Override
        public void mouseReleased(MouseEvent event) {
            if (clickedElement !=null) {
                clickedElement.handleReleasing();
                clickedElement = null;
                repaint();
            }
        }
    }
}

