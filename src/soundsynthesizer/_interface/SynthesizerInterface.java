package soundsynthesizer._interface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import soundsynthesizer.SynthesizerWindow;
import soundsynthesizer.synthesis.Synthesizer;

/**
 * @author Marek Bobrowski
 */
public class SynthesizerInterface extends JPanel {
    private final SynthesizerWindow parentWindow;
    private final ArrayList<Panel> panels = new ArrayList<>();
    private ClickableElement clickedElement;

    public SynthesizerInterface(Synthesizer synthesizer, SynthesizerWindow parentWindow) {
        this.parentWindow = parentWindow;
        this.setFocusable(true);
        this.requestFocusInWindow();

        MouseActionHandler mouseActionHandler = new MouseActionHandler();
        addMouseListener(mouseActionHandler);
        addMouseMotionListener(mouseActionHandler);

        KeyActionHandler keyActionHandler = new KeyActionHandler(synthesizer);
        addKeyListener(keyActionHandler);

        OscillatorPanel oscillatorPanel =
                new OscillatorPanel(synthesizer.getOscillatorSettings(), 30, 15);

        AmplifierEnvelopePanel amplifierEnvelopePanel =
                new AmplifierEnvelopePanel(synthesizer.getAmplifierEnvelope(), 330, 45);

        DelayPanel delayPanel =
                new DelayPanel(synthesizer.getDelay(), 630, 45);

        ReverbPanel reverbPanel =
                new ReverbPanel(synthesizer.getReverb(), 630, 135);

        BufferSizePanel bufferSizePanel =
                new BufferSizePanel(synthesizer.getConverter(), 30, 210);

        DetailPanel detailPanel = new DetailPanel(30, 500, 30, this);

        panels.add(oscillatorPanel);
        panels.add(amplifierEnvelopePanel);
        panels.add(delayPanel);
        panels.add(reverbPanel);
        panels.add(detailPanel);
        panels.add(bufferSizePanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.setColor(new Color(0.2f, 0.2f, 0.2f));
        graphics2d.fillRect(0, 0, parentWindow.getWidth(), parentWindow.getHeight());
        for (Panel m : panels) {
            m.draw(graphics2d);
        }
    }

    public ClickableElement getClickedElement(){
        return clickedElement;
    }

    public SynthesizerWindow getParentWindow() {
        return parentWindow;
    }

    public class MouseActionHandler extends MouseAdapter {
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

        @Override
        public void mouseDragged(MouseEvent event) {
            if (clickedElement != null && clickedElement.isDraggable()) {
                clickedElement.handleDragging(event.getPoint());
                repaint();
            }
        }

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

