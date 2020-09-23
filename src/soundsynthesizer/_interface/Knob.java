package soundsynthesizer._interface;

import soundsynthesizer.delegates.DoubleSetter;
import soundsynthesizer.delegates.DoubleGetter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * @author Marek Bobrowski
 */
public class Knob extends Ellipse2D.Double implements ClickableElement {
    private static final Color ELLIPSE_COLOR = new Color(0.1f, 0.1f, 0.1f);
    private static final Color INDICATOR_COLOR = new Color(0.7f, 0.7f, 0.7f);
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 12);

    private final double diameter;
    private final Line2D.Double line;
    private final String shortName;
    private final String fullName;
    private final double min;
    private final double max;
    private final DoubleSetter parameterSetter;
    private final double accuracy;

    private double value;
    private double clickedY;
    private int angle;
    private int lastAngle;
    private AffineTransform affineTransform;

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

    @Override
    public void handleReleasing() {
        lastAngle = angle;
    }

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

    private void updateRotation() {
        this.affineTransform = AffineTransform.getRotateInstance(Math.toRadians(this.angle + 30),
                this.line.getX1(), this.line.getY1());
    }

    private void updateAngle() {
        angle = (int)(300 * (value - min) / (max - min));
    }

    public int getAngle() {
        return angle;
    }

    public double getValue() {
        return value;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public void handleClicking() {
    }

    @Override
    public boolean isDraggable() {
        return true;
    }

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

    @Override
    public boolean isClicked(Point mouseClick) {
        if (contains(mouseClick)) {
            clickedY = mouseClick.getY();
            return true;
        }
        return false;
    }
}
