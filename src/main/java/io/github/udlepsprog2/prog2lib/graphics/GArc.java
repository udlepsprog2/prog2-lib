package io.github.udlepsprog2.prog2lib.graphics;


import io.github.udlepsprog2.prog2lib.geometry.GBounds;
import io.github.udlepsprog2.prog2lib.geometry.GDimension;

import java.awt.*;
import java.awt.geom.Arc2D;

public class GArc extends GObject implements GFillable, GSizeable {

    private double width;
    private double height;
    private double startAngle;
    private double sweepAngle;
    private boolean filled = false;
    private Color fillColor;

    public GArc(double x, double y, double width, double height, double start, double sweep) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.startAngle = start;
        this.sweepAngle = sweep;
    }

    @Override
    void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Arc2D.Double arc = new Arc2D.Double(x, y, width, height, startAngle, sweepAngle, Arc2D.PIE);
        if (isFilled()) {
            g2D.setColor(getFillColor());
            g2D.fill(arc);
        }
        g2D.setColor(getColor());
        g2D.draw(arc);
    }

    @Override
    GBounds getBounds() {
        return new GBounds(x, y, width, height);
    }

    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double newAngle) {
        startAngle = newAngle;
        repaint();
    }

    public double getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(double newAngle) {
        sweepAngle = newAngle;
        repaint();
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
