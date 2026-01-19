package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GBounds;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * A straight line segment between two points.
 * <p>
 * The line is defined by its starting point ({@code x1}, {@code y1}) and its
 * ending point ({@code x2}, {@code y2}). Internally, the start is stored via the
 * inherited location from {@link GObject} and the end is represented as a delta
 * vector {@code (dx, dy)} from that location. The line is rendered using the
 * current stroke {@link #getColor()}.
 * </p>
 */
public class GLine extends GObject {
    private double dx;
    private double dy;

    /**
     * Creates a line segment from ({@code x1}, {@code y1}) to ({@code x2}, {@code y2}).
     *
     * @param x1 the x-coordinate of the starting point
     * @param y1 the y-coordinate of the starting point
     * @param x2 the x-coordinate of the ending point
     * @param y2 the y-coordinate of the ending point
     */
    public GLine(double x1, double y1, double x2, double y2) {
        super(x1, y1);
        this.dx = x2 - x1;
        this.dy = y2 - y1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Line2D.Double line = new Line2D.Double(getX(), getY(), getX() + dx, getY() + dy);
        g2D.setColor(getColor());
        g2D.draw(line);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    GBounds getBounds() {
        double topLeftX = getX() + Math.min(dx, 0);
        double topLeftY = getY() + Math.min(dy, 0);
        return new GBounds(topLeftX, topLeftY, Math.abs(dx), Math.abs(dy));
    }
}
