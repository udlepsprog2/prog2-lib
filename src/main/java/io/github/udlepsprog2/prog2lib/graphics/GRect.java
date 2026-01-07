package io.github.udlepsprog2.prog2lib.graphics;


import io.github.udlepsprog2.prog2lib.geometry.GBounds;
import io.github.udlepsprog2.prog2lib.geometry.GDimension;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GRect extends GObject implements GFillable, GSizeable {

    private double width;
    private double height;
    private boolean filled = false;
    private Color fillColor;

    public GRect(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Rectangle2D.Double rect = new Rectangle2D.Double(x, y, width, height);
        if (isFilled()) {
            g2D.setColor(getFillColor());
            g2D.fill(rect);
        }
        g2D.setColor(getColor());
        g2D.draw(rect);
    }

    @Override
    GBounds getBounds() {
        return new GBounds(x, y, width, height);
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
        repaint();
    }

    public Color getFillColor() {
        return (fillColor != null) ? fillColor : getColor();
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        repaint();
    }

    @Override
    public GDimension getSize() {
        return new GDimension(width, height);
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
        repaint();
    }
}
