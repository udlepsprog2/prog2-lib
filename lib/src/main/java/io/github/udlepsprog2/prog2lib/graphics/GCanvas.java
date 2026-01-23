package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GPoint;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Lightweight Swing canvas that manages and renders {@link GObject} instances.
 * <p>
 * The canvas maintains an internal z-ordered list of {@code GObject}s. It handles
 * painting by clearing the background to the current {@link #getBackground()} color
 * and then delegating to each object's {@link GObject#paintComponent(Graphics)} in
 * list order. Higher-indexed objects are rendered on top.
 * </p>
 *
 * <p>
 * Methods are provided to add/remove objects, query which object(s) are at a given
 * point, and change the z-order (send to back/front or move one step). The canvas
 * is thread-confined to the Swing Event Dispatch Thread (EDT) for painting; casual
 * usage in this library does not synchronize with the EDT explicitly beyond the
 * internal synchronization on the list for simple safety.
 * </p>
 */
public class GCanvas extends JComponent {

    private static final Color DEFAULT_BACKGROUND = Color.WHITE;

    /** Z-ordered list of objects on this canvas; index 0 is backmost. */
    private final LinkedList<GObject> gObjects = new LinkedList<>();

    /** Background color of this canvas. */
    private Color backgroundColor;

    /**
     * Creates a new canvas with a default white background.
     */
    public GCanvas() {
        this.backgroundColor = DEFAULT_BACKGROUND;
    }

    /** {@inheritDoc} */
    @Override
    public void paintComponent(Graphics g) {
        Objects.requireNonNull(g, "g");
        synchronized (gObjects) {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, getWidth(), getHeight());
            for (var gObj : gObjects) {
                if (!gObj.isVisible()) continue;
                Graphics newG = g.create();
                gObj.paintComponent(newG);
                newG.dispose();
            }
        }
    }

    /**
     * Adds a {@link GObject} to this canvas if it is not already present.
     * <p>
     * When added, the object is appended to the end of the z-order (topmost)
     * and its canvas reference is set via {@link GObject#setCanvas(GCanvas)}.
     * A repaint is requested after the change.
     * </p>
     *
     * @param gObj the object to add (must not be {@code null})
     * @throws NullPointerException if {@code gObj} is {@code null}
     */
    public void add(GObject gObj) {
        Objects.requireNonNull(gObj, "gObj");
        synchronized (gObjects) {
            if (!gObjects.contains(gObj)) {
                gObjects.add(gObj);
                gObj.setCanvas(this);
            }
        }
        this.repaint();
    }

    /**
     * Adds a {@link GObject} to this canvas at the specified location
     * (x, y) if it is not already present.
     * <p>
     * When added, the object is appended to the end of the z-order (topmost)
     * and its canvas reference is set via {@link GObject#setCanvas(GCanvas)}.
     * A repaint is requested after the change.
     * </p>
     *
     * @param gObj the object to add (must not be {@code null})
     * @param x the x-coordinate where the object should be placed
     * @param y the y-coordinate where the object should be placed
     * @throws NullPointerException if {@code gObj} is {@code null}
     */
    public void add(GObject gObj, double x, double y) {
        Objects.requireNonNull(gObj, "gObj");
        gObj.setLocation(x, y);
        add(gObj);
    }

    /**
     * Adds a {@link GObject} to this canvas at the specified location
     * if it is not already present.
     * <p>
     * When added, the object is appended to the end of the z-order (topmost)
     * and its canvas reference is set via {@link GObject#setCanvas(GCanvas)}.
     * A repaint is requested after the change.
     * </p>
     *
     * @param gObj the object to add (must not be {@code null})
     * @param gPoint the point where the object should be placed (must not be {@code null})
     * @throws NullPointerException if {@code gObj} or {@code gPoint} is {@code null}
     */
    public void add(GObject gObj, GPoint gPoint) {
        Objects.requireNonNull(gObj, "gObj");
        Objects.requireNonNull(gPoint, "gPoint");
        gObj.setLocation(gPoint);
        add(gObj);
    }

    /**
     * Removes a {@link GObject} from this canvas if it is present.
     * <p>
     * When removed, the object is no longer part of this canvas's z-order. A repaint
     * is requested after the change.
     * </p>
     *
     * @param gObj the object to remove (must not be {@code null})
     * @throws NullPointerException if {@code gObj} is {@code null}
     */
    public void remove(GObject gObj) {
        Objects.requireNonNull(gObj, "gObj");
        synchronized (gObjects) {
            gObjects.remove(gObj);
        }
        this.repaint();
    }

    /** {@inheritDoc} */
    @Override
    public Color getBackground() {
        return backgroundColor;
    }

    /** {@inheritDoc} */
    @Override
    public void setBackground(Color backgroundColor) {
        Objects.requireNonNull(backgroundColor, "backgroundColor");
        this.backgroundColor = backgroundColor;
        this.repaint();
    }

    /**
     * Returns the topmost object at the given coordinate, or {@code null} if none.
     * <p>
     * This method searches the internal list from front to back (topmost first)
     * and returns the first visible object whose bounds contain the point.
     * </p>
     *
     * @param x the x-coordinate to test
     * @param y the y-coordinate to test
     * @return the topmost {@link GObject} under the point, or {@code null} if no hit
     */
    public GObject getElementAt(double x, double y) {
        synchronized (gObjects) {
            for (int i = gObjects.size() - 1; i >= 0; i--) {
                GObject gObj = gObjects.get(i);
                if (gObj.isVisible() && gObj.contains(x, y)) {
                    return gObj;
                }
            }
        }
        return null;
    }

    /**
     * Returns the topmost object at the given point, or {@code null} if none.
     * <p>
     * This is a convenience overload delegating to
     * {@link #getElementAt(double, double)}.
     * </p>
     *
     * @param gPoint the point to test (must not be {@code null})
     * @return the topmost {@link GObject} under the point, or {@code null} if no hit
     * @throws NullPointerException if {@code gPoint} is {@code null}
     */
    public GObject getElementAt(GPoint gPoint) {
        Objects.requireNonNull(gPoint, "gPoint");
        return getElementAt(gPoint.x(), gPoint.y());
    }

    /**
     * Returns all objects under the given coordinate, ordered from topmost to backmost.
     *
     * @param x the x-coordinate to test
     * @param y the y-coordinate to test
     * @return an array of matching {@link GObject}s (possibly empty, never {@code null})
     */
    public GObject[] getElementsAt(double x, double y) {
        var gObjs = new LinkedList<GObject>();
        synchronized (gObjects) {
            for (var gObj : gObjects) {
                if (gObj.isVisible() && gObj.contains(x, y)) {
                    gObjs.addFirst(gObj);
                }
            }
        }
        return gObjs.toArray(new GObject[0]);
    }

    /**
     * Returns all objects under the given point, ordered from topmost to backmost.
     * <p>
     * Convenience overload delegating to {@link #getElementsAt(double, double)}.
     * </p>
     *
     * @param gPoint the point to test (must not be {@code null})
     * @return an array of matching {@link GObject}s (possibly empty, never {@code null})
     * @throws NullPointerException if {@code gPoint} is {@code null}
     */
    public GObject[] getElementsAt(GPoint gPoint) {
        Objects.requireNonNull(gPoint, "gPoint");
        return getElementsAt(gPoint.x(), gPoint.y());
    }

    /**
     * Moves the given object to the back of the z-order (rendered first).
     * No effect if the object is not currently on this canvas.
     *
     * @param gObj the object to move (must not be {@code null})
     * @throws NullPointerException if {@code gObj} is {@code null}
     */
    public void sendToBack(GObject gObj) {
        Objects.requireNonNull(gObj, "gObj");
        synchronized (gObjects) {
            if (gObjects.remove(gObj)) {
                gObjects.addFirst(gObj);
                this.repaint();
            }
        }
    }

    /**
     * Moves the given object to the front of the z-order (rendered last/topmost).
     * No effect if the object is not currently on this canvas.
     *
     * @param gObject the object to move (must not be {@code null})
     * @throws NullPointerException if {@code gObject} is {@code null}
     */
    public void sendToFront(GObject gObject) {
        Objects.requireNonNull(gObject, "gObject");
        synchronized (gObjects) {
            if (gObjects.remove(gObject)) {
                gObjects.addLast(gObject);
                this.repaint();
            }
        }
    }

    /**
     * Moves the given object one step backward in the z-order.
     * No effect if the object is not present or already at the back.
     *
     * @param gObject the object to move backward (must not be {@code null})
     * @throws NullPointerException if {@code gObject} is {@code null}
     */
    public void sendBackward(GObject gObject) {
        Objects.requireNonNull(gObject, "gObject");
        synchronized (gObjects) {
            ListIterator<GObject> iterator = gObjects.listIterator(gObjects.size());
            while (iterator.hasPrevious()) {
                if (iterator.previous() == gObject && iterator.hasPrevious()) {
                    iterator.remove();
                    iterator.previous();
                    iterator.add(gObject);
                    this.repaint();
                    break;
                }
            }
        }
    }

    /**
     * Moves the given object one step forward in the z-order.
     * No effect if the object is not present or already at the front.
     *
     * @param gObject the object to move forward (must not be {@code null})
     * @throws NullPointerException if {@code gObject} is {@code null}
     */
    public void sendForward(GObject gObject) {
        Objects.requireNonNull(gObject, "gObject");
        synchronized (gObjects) {
            ListIterator<GObject> iterator = gObjects.listIterator();
            while (iterator.hasNext()) {
                if (iterator.next() == gObject && iterator.hasNext()) {
                    iterator.remove();
                    iterator.next();
                    iterator.add(gObject);
                    this.repaint();
                    break;
                }
            }
        }
    }
}
