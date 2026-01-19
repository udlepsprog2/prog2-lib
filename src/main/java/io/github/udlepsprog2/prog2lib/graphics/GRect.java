package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GBounds;
import io.github.udlepsprog2.prog2lib.geometry.GDimension;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * A rectangle shape that can be drawn and optionally filled.
 * <p>
 * The rectangle is positioned by its top-left corner ({@code x}, {@code y}) and defined
 * by its {@code width} and {@code height}. When {@link #isFilled()} is {@code true}, the
 * shape is rendered filled using {@link #getFillColor()}, and its outline is always drawn
 * using {@link #getColor()}.
 * </p>
 */
public class GRect extends GObject implements GFillable, GSizeable {

    private double width;
    private double height;
    private boolean filled = false;
    private Color fillColor;

    /**
     * Creates a rectangle with the given location and size.
     *
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     * @param width the rectangle width
     * @param height the rectangle height
     */
    public GRect(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    /** {@inheritDoc} */
    @Override
    void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Rectangle2D.Double rect = new Rectangle2D.Double(getX(), getY(), width, height);
        if (isFilled()) {
            g2D.setColor(getFillColor());
            g2D.fill(rect);
        }
        g2D.setColor(getColor());
        g2D.draw(rect);
    }

    /** {@inheritDoc} */
    @Override
    GBounds getBounds() {
        return new GBounds(getX(), getY(), width, height);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isFilled() {
        return filled;
    }
    
    /** {@inheritDoc} */
    @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
        repaint();
    }
    
    /** {@inheritDoc} */
    @Override
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
