package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GBounds;

import java.awt.*;
import java.awt.geom.Line2D;

public class GLine extends GObject {
    private double dx;
    private double dy;

    public GLine(double x1, double y1, double x2, double y2) {
        super(x1, y1);
        this.dx = x2 - x1;
        this.dy = y2 - y1;
    }

    @Override
    void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Line2D.Double line = new Line2D.Double(getX(), getY(), getX() + dx, getY() + dy);
        g2D.setColor(getColor());
        g2D.draw(line);
    }

    @Override
    GBounds getBounds() {
        double topLeftX = getX() + Math.min(dx, 0);
        double topLeftY = getY() + Math.min(dy, 0);
        return new GBounds(topLeftX, topLeftY, Math.abs(dx), Math.abs(dy));
    }
}
