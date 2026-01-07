package io.github.udlepsprog2.prog2lib.graphics;


import io.github.udlepsprog2.prog2lib.geometry.GBounds;
import io.github.udlepsprog2.prog2lib.geometry.GPoint;

import java.awt.*;

public abstract class GObject implements GLocatable {
    protected double x;
    protected double y;
    private Color color;
    private boolean visible = true;

    protected GCanvas canvas;

    public GObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public GPoint getLocation() {
        return new GPoint(x, y);
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
        repaint();
    }

    public Color getColor() {
        return (color != null) ? color : Color.BLACK;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    protected void repaint() {
        if (this.canvas != null)
            this.canvas.repaint();
    }

    public void setCanvas(GCanvas canvas) {
        this.canvas = canvas;
    }

    abstract void paintComponent(Graphics g);

    public void setVisible(boolean visible) {
        this.visible = visible;
        repaint();
    }

    public boolean isVisible() {
        return visible;
    }

    public void sendToBack() {
        if (canvas != null)
            canvas.sendToBack(this);
    }

    public void sendToFront() {
        if (canvas != null)
            canvas.sendToFront(this);
    }

    public void sendBackward() {
        if (canvas != null)
            canvas.sendBackward(this);
    }

    public void sendForward() {
        if (canvas != null)
            canvas.sendForward(this);
    }

    public boolean contains(double x, double y) {
        return getBounds().contains(x, y);
    }

    public boolean contains(GPoint gPoint) {
        return getBounds().contains(gPoint);
    }

    abstract GBounds getBounds();
}
