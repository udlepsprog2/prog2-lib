package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GBounds;

import java.awt.*;

public class GLabel extends GObject {

    private String text;
    private Font font = new Font("Default", Font.PLAIN, 12);

    public GLabel(String text) {
        this(text, 0.0, 0.0);
    }

    public GLabel(String text, double x, double y) {
        super(x, y);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
        repaint();
    }

    public double getWidth() {
        return getFontMetrics().stringWidth(text);
    }

    public double getHeight() {
        return getFontMetrics().getHeight();
    }

    public double getAscent() {
        return getFontMetrics().getAscent();
    }

    public double getDescent() {
        return getFontMetrics().getDescent();
    }

    @Override
    void paintComponent(Graphics g) {
        g.setColor(getColor());
        g.setFont(font);
        g.drawString(text, (int) Math.round(x), (int) Math.round(y));
    }

    private static final Component EMPTY_COMPONENT = new Component() {};

    private FontMetrics getFontMetrics() {
        Component component = canvas != null ? canvas : EMPTY_COMPONENT;
        return component.getFontMetrics(font);
    }

    @Override
    GBounds getBounds() {
        return new GBounds(x, y, getWidth(), getHeight());
    }
}
