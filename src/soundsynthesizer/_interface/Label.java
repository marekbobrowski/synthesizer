package soundsynthesizer._interface;

import java.awt.*;

public class Label {
    private static final Color color = new Color(0.7f, 0.7f, 0.7f);
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 14);
    private final int x;
    private final int y;
    private final String text;

    public Label(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);
        graphics2D.setFont(font);
        graphics2D.drawString(text, x, y);
    }
}
