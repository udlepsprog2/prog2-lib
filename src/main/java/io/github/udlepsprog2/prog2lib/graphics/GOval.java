package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GBounds;
import io.github.udlepsprog2.prog2lib.geometry.GDimension;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

/**
 * An oval (ellipse) shape that can be drawn and optionally filled.
 * <p>
 * Positioned by its top-left corner ({@code x}, {@code y}) and defined by
 * {@code width} and {@code height}. When {@link #isFilled()} is {@code true},
 * the interior is painted using {@link #getFillColor()}, and the outline is
 * always drawn using {@link #getColor()}.
 * </p>
 */
public class GOval extends GObject implements GFillable, GSizeable {

    private double width;
    private double height;
    private boolean filled = false;
    private Color fillColor;

    /**
     * Creates an oval with the given location and size.
     *
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     * @param width the oval width
     * @param height the oval height
     */
    public GOval(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    /** {@inheritDoc} */
    @Override
    void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Ellipse2D.Double oval = new Ellipse2D.Double(getX(), getY(), width, height);
        if (isFilled()) {
            g2D.setColor(getFillColor());
            g2D.fill(oval);
        }
        g2D.setColor(getColor());
        g2D.draw(oval);
    }

    /** {@inheritDoc} */
    @Override
    GBounds getBounds() {
        return new GBounds(getX(), getY(), width, height);
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
