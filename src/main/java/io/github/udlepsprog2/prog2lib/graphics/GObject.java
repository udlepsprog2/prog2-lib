package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GBounds;
import io.github.udlepsprog2.prog2lib.geometry.GPoint;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

/**
 * Base class for drawable graphics objects.
 * <p>
 * A {@code GObject} has a 2D location (top-left anchored), an optional stroke
 * {@link Color}, and a visibility flag. It may be attached to a {@link GCanvas}
 * which is notified via {@link #repaint()} when properties change. Subclasses
 * must provide painting logic via {@link #paintComponent(Graphics)} and
 * geometric bounds via {@link #getBounds()}.
 * </p>
 */
public abstract class GObject implements GLocatable {
    private double x;
    private double y;
    private GCanvas canvas;

    private Color color;
    private boolean visible = true;

    /**
     * Creates a new graphics object at the given location.
     *
     * @param x the initial x-coordinate (top-left)
     * @param y the initial y-coordinate (top-left)
     */
    public GObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** {@inheritDoc} */
    @Override
    public GPoint getLocation() {
        return new GPoint(x, y);
    }

    /**
     * {@inheritDoc}
     * <p>Note: This implementation requests a repaint after updating the location.</p>
     */
    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
        repaint();
    }

    /**
     * Returns the current stroke color.
     * <p>
     * If no color was explicitly set, {@link Color#BLACK} is returned.
     * </p>
     *
     * @return a non-null {@link Color}
     */
    public Color getColor() {
        return (color != null) ? color : Color.BLACK;
    }

    /**
     * Sets the stroke color and requests a repaint.
     *
     * @param color the new non-null {@link Color}
     * @throws NullPointerException if {@code color} is {@code null}
     */
    public void setColor(Color color) {
        this.color = Objects.requireNonNull(color, "color");
        repaint();
    }

    /**
     * Requests a repaint of the associated {@link GCanvas}, if any.
     * Subclasses may call this after state changes affecting appearance.
     */
    protected void repaint() {
        if (this.canvas != null)
            this.canvas.repaint();
    }

    /**
     * Associates this object with a {@link GCanvas}.
     *
     * @param canvas the non-null canvas this object belongs to
     * @throws NullPointerException if {@code canvas} is {@code null}
     */
    public void setCanvas(GCanvas canvas) {
        this.canvas = Objects.requireNonNull(canvas, "canvas");
    }

    /**
     * Paints this object onto the provided {@link Graphics} context.
     * Implementations should honor {@link #isVisible()} and current styling.
     *
     * @param g the graphics context to draw on
     */
    abstract void paintComponent(Graphics g);

    /**
     * Sets whether this object should be rendered.
     * Triggers a repaint when the value changes.
     *
     * @param visible {@code true} to render; {@code false} to hide
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        repaint();
    }

    /**
     * Indicates whether this object is marked as visible.
     *
     * @return {@code true} if visible; {@code false} otherwise
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sends this object to the back of the z-order within its canvas.
     * No-op if not attached to a canvas.
     */
    public void sendToBack() {
        if (canvas != null)
            canvas.sendToBack(this);
    }

    /**
     * Brings this object to the front of the z-order within its canvas.
     * No-op if not attached to a canvas.
     */
    public void sendToFront() {
        if (canvas != null)
            canvas.sendToFront(this);
    }

    /**
     * Moves this object one step backward in the z-order within its canvas.
     * No-op if not attached to a canvas.
     */
    public void sendBackward() {
        if (canvas != null)
            canvas.sendBackward(this);
    }

    /**
     * Moves this object one step forward in the z-order within its canvas.
     * No-op if not attached to a canvas.
     */
    public void sendForward() {
        if (canvas != null)
            canvas.sendForward(this);
    }

    /**
     * Returns whether the given point lies within this object's bounds.
     * Delegates to {@link #getBounds()}.
     *
     * @param x the x-coordinate of the point to test
     * @param y the y-coordinate of the point to test
     * @return {@code true} if the point is inside or on the border; {@code false} otherwise
     */
    public boolean contains(double x, double y) {
        return getBounds().contains(x, y);
    }

    /**
     * Returns whether the given point lies within this object's bounds.
     * Delegates to {@link #contains(double, double)}.
     *
     * @param gPoint the point to test (must not be {@code null})
     * @return {@code true} if the point is inside or on the border; {@code false} otherwise
     * @throws NullPointerException if {@code gPoint} is {@code null}
     */
    public boolean contains(GPoint gPoint) {
        return getBounds().contains(Objects.requireNonNull(gPoint, "gPoint"));
    }

    /**
     * Returns the geometric bounds of this object, used for hit testing and
     * layout. Implementations should return tight axis-aligned bounds.
     *
     * @return non-null bounds describing this object
     */
    abstract GBounds getBounds();
    
    /**
     * Returns the {@link GCanvas} this object is currently attached to, if any.
     * <p>
     * The value may be {@code null} when the object has not been added to a
     * canvas yet or after it has been removed. Subclasses can use this to
     * query canvas-specific state (e.g., font metrics) when available.
     * </p>
     *
     * @return the owning {@link GCanvas}, or {@code null} if not attached
     */
    protected GCanvas getGCanvas() {
        return canvas;
    }
}
