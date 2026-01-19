package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GBounds;
import io.github.udlepsprog2.prog2lib.geometry.GDimension;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.Objects;

/**
 * An arc (pie) shape that can be drawn and optionally filled.
 * <p>
 * The arc is defined by a bounding rectangle ({@code x}, {@code y}, {@code width},
 * {@code height}), a {@code startAngle}, and a {@code sweepAngle}. When {@link #isFilled()}
 * is {@code true}, the interior (pie) is painted using {@link #getFillColor()}, and the
 * outline is always drawn using {@link #getColor()}.
 * </p>
 */
public class GArc extends GObject implements GFillable, GSizeable {

    private double width;
    private double height;
    private double startAngle;
    private double sweepAngle;
    private boolean filled = false;
    private Color fillColor;

    /**
     * Creates an arc with the given bounding box and angles.
     *
     * @param x the x-coordinate of the top-left corner of the bounding box
     * @param y the y-coordinate of the top-left corner of the bounding box
     * @param width the width of the bounding box
     * @param height the height of the bounding box
     * @param start the starting angle in degrees
      * @param sweep the angular extent of the arc in degrees
     */
    public GArc(double x, double y, double width, double height, double start, double sweep) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.startAngle = start;
        this.sweepAngle = sweep;
    }

    /** {@inheritDoc} */
    @Override
    void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Arc2D.Double arc = new Arc2D.Double(getX(), getY(), width, height, startAngle, sweepAngle, Arc2D.PIE);
        if (isFilled()) {
            g2D.setColor(getFillColor());
            g2D.fill(arc);
        }
        g2D.setColor(getColor());
        g2D.draw(arc);
    }

    /** {@inheritDoc} */
    @Override
    GBounds getBounds() {
        return new GBounds(getX(), getY(), width, height);
    }

    /**
     * Returns the starting angle of this arc, in degrees.
     *
     * @return the starting angle in degrees
     */
    public double getStartAngle() {
        return startAngle;
    }

    /**
     * Sets the starting angle of this arc, in degrees, and requests a repaint.
     *
     * @param newAngle the new starting angle in degrees
     */
    public void setStartAngle(double newAngle) {
        startAngle = newAngle;
        repaint();
    }

    /**
     * Returns the angular extent of this arc, in degrees.
     *
     * @return the angular extent in degrees
     */
    public double getSweepAngle() {
        return sweepAngle;
    }

    /**
     * Sets the angular extent of this arc, in degrees, and requests a repaint.
     *
     * @param newAngle the new angular extent in degrees
     */
    public void setSweepAngle(double newAngle) {
        sweepAngle = newAngle;
        repaint();
    }

    /** {@inheritDoc} */
    public boolean isFilled() {
        return filled;
    }

    /** {@inheritDoc} */
    public void setFilled(boolean filled) {
        this.filled = filled;
        repaint();
    }

    /** {@inheritDoc} */
    public Color getFillColor() {
        return (fillColor != null) ? fillColor : getColor();
    }

    /** {@inheritDoc} */
    @Override
    public void setFillColor(Color fillColor) {
        this.fillColor = Objects.requireNonNull(fillColor, "fillColor");
        repaint();
    }

    /** {@inheritDoc} */
    @Override
    public GDimension getSize() {
        return new GDimension(width, height);
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
        repaint();
    }
}
