package synthesizer._interface.gui.units;

import synthesizer._interface.gui.panels.DetailPanel;
import synthesizer.utils.delegates.DoubleSetter;
import synthesizer.utils.delegates.DoubleGetter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * An interface element that is used for controlling the value of the assigned parameter.
 * User can click and drag it in order to control the value.
 *
 * @author Marek Bobrowski
 */
public class Knob extends Ellipse2D.Double implements ClickableElement {
    /**
     * The color of an ellipse of a knob.
     */
    private static final Color ELLIPSE_COLOR = new Color(0.1f, 0.1f, 0.1f);

    /**
     * The color of an indicator of a knob.
     */
    private static final Color INDICATOR_COLOR = new Color(0.7f, 0.7f, 0.7f);

    /**
     * The font of the knob text.
     * The knob text represents a short name of the assigned parameter.
     */
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 12);

    /**
     * The diameter of this knob.
     */
    private final double diameter;

    /**
     * The indicator line of this knob.
     */
    private final Line2D.Double line;

    /**
     * The short name of this knob. It's displayed right next to this knob.
     */
    private final String shortName;

    /**
     * The full name of this knob. It's displayed only by the {@link DetailPanel}.
     */
    private final String fullName;

    /**
     * The minimal value to which this knob can be set.
     */
    private final double min;

    /**
     * The maximal value to which this knob can be set.
     */
    private final double max;

    /**
     * A delegate for setting the value of the assigned synthesizer parameter.
     * For example, if we want the knob the control the reverb decay, then we
     * have to assign reverb.setDecay to this field.
     */
    private final DoubleSetter parameterSetter;

    /**
     * The accuracy of the values set by this knob. For example if we want only integer values,
     * then we have to set the accuracy to 1. If we want only numbers divisible by 5, then we have
     * to set the accuracy to 5.
     */
    private final double accuracy;

    /**
     * The current value of this knob.
     */
    private double value;

    /**
     * Stores the Y coordinate of the mouse when the knob was clicked.
     * It's useful for handling the dragging.
     */
    private double clickedY;

    /**
     * The current angle of this knob.
     */
    private int angle;

    /**
     * The angle of this knob from the last handleDragging() call.
     */
    private int lastAngle;

    /**
     * An object responsible for rotating the indicator of this knob.
     */
    private AffineTransform affineTransform;

    /**
     * Sets up basic knob parameters according to the passed arguments.
     * @param x The X coordinate of this knob.
     * @param y The Y coordinate of this knob.
     * @param diameter The diameter of this knob.
     * @param shortName The short name of this knob. It's displayed right next to this knob.
     * @param fullName The full name of this knob. It's displayed only by the {@link DetailPanel}.
     * @param min The minimal value to which this knob can be set.
     * @param max The maximal value to which this knob can be set.
     * @param accuracy The accuracy of the values set by this knob. For example if we want only integer values,
     *                 then we have to set the accuracy to 1. If we want only numbers divisible by 5, then we have
     *                 to set the accuracy to 5.
     * @param parameterGetter A delegate for setting the value of the assigned synthesizer parameter.
     *                        For example, if we want the knob the control the reverb decay, then we
     *                        have to assign reverb.setDecay to this field.
     * @param parameterSetter A delegate for getting the value of the assigned synthesizer parameter.
     *                        For example, if we want the knob to display the value of reverb decay,
     *                        then we have to assign reverb.getDecay to this field.
     *                        It is only useful on the initialization of the knob (so it displays the initial value).
     */
    public Knob(double x, double y, double diameter, String shortName,
                String fullName, double min, double max, double accuracy,
                DoubleGetter parameterGetter, DoubleSetter parameterSetter) {
        super(x - diameter / 2,y - diameter / 2, diameter, diameter);
        this.diameter = diameter;
        this.line = new Line2D.Double(x, y, x, y + diameter / 2);
        this.shortName = shortName;
        this.fullName = fullName;
        this.min = min;
        this.max = max;
        this.accuracy = accuracy;
        this.value = parameterGetter.get();
        affineTransform = new AffineTransform();
        affineTransform = AffineTransform.getRotateInstance(Math.toRadians(this.angle + 30), this.line.getX1(),
                this.line.getY1());
        this.parameterSetter = parameterSetter;
        angle = (int)(300 * (value - min) / (max - min));
        lastAngle = this.angle;
        updateRotation();
    }

    /**
     * Handle the releasing of this knob.
     */
    @Override
    public void handleReleasing() {
        lastAngle = angle;
    }


    /**
     * Handle the dragging of this knob.
     * @param mouseClick Current mouse position.
     */
    @Override
    public void handleDragging(Point mouseClick) {
        int change = (int) (-mouseClick.getY() + clickedY);
        angle = change + lastAngle;
        if (angle <= 0) {
            angle = 0;
        } else if (angle >= 300) {
            angle = 300;
        }
        value = (((double)angle / 300) * (max - min) + min);
        double remainder = value % accuracy;
        value = value - remainder;
        if (remainder != 0) {
            updateAngle();
        }
        parameterSetter.set(value);
        updateRotation();
    }

    /**
     * Visually update the rotation of this knob's indicator according to the current angle.
     */
    private void updateRotation() {
        this.affineTransform = AffineTransform.getRotateInstance(Math.toRadians(this.angle + 30),
                this.line.getX1(), this.line.getY1());
    }

    /**
     * Update the angle of this knob's indicator according to the current value.
     */
    private void updateAngle() {
        angle = (int)(300 * (value - min) / (max - min));
    }

    /**
     * @return The current angle of this knob.
     */
    public int getAngle() {
        return angle;
    }

    /**
     * @return The current value of this knob.
     */
    public double getValue() {
        return value;
    }

    /**
     * @return The full name of this knob.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Handle the clicking of this knob.
     */
    @Override
    public void handleClicking() {
    }

    /**
     * @return Will always return true, because a knob is supposed to be draggable.
     */
    @Override
    public boolean isDraggable() {
        return true;
    }

    /**
     * Draws this knob: the ellipse, it's border, the indicator and the short name right next to the ellipse.
     * @param graphics2D The object that will perform drawing of this knob.
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(ELLIPSE_COLOR);
        graphics2D.fill(this);
        graphics2D.setColor(INDICATOR_COLOR);
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.drawOval((int)x, (int)y, (int)diameter, (int)diameter);
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.draw(affineTransform.createTransformedShape(line));
        graphics2D.setColor(INDICATOR_COLOR);
        graphics2D.setFont(font);
        graphics2D.drawString(shortName, (int)x, (int)y);
    }

    /**
     * Tells if this knob is clicked when the mouse clicks on the specified position.
     * @param mouseClick Mouse position.
     * @return If this knob is clicked when the mouse clicks on the specified position.
     */
    @Override
    public boolean isClicked(Point mouseClick) {
        if (contains(mouseClick)) {
            clickedY = mouseClick.getY();
            return true;
        }
        return false;
    }
}
