package io.github.udlepsprog2.prog2lib.graphics;


import io.github.udlepsprog2.prog2lib.geometry.GPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class GCanvas extends JComponent {

    private static final Color DEFAULT_BACKGROUND = Color.WHITE;

    private final LinkedList<GObject> gObjects = new LinkedList<>();
    private Color backgroundColor;

    public GCanvas() {
        this.backgroundColor = DEFAULT_BACKGROUND;
    }

    @Override
    public void paintComponent(Graphics g) {
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

    public void add(GObject gObj) {
        synchronized (gObjects) {
            if (!gObjects.contains(gObj)) {
                gObjects.add(gObj);
                gObj.setCanvas(this);
            }
        }
        this.repaint();
    }

    public void remove(GObject gObj) {
        synchronized (gObjects) {
            if (gObjects.remove(gObj)) {
                gObj.setCanvas(null);
            }
        }
        this.repaint();
    }

    @Override
    public Color getBackground() {
        return backgroundColor;
    }

    @Override
    public void setBackground(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.repaint();
    }

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

    public GObject getElementAt(GPoint gPoint) {
        return getElementAt(gPoint.x(), gPoint.y());
    }

    public GObject[] getElementsAt(double x, double y) {
        var gObjs = new ArrayList<GObject>();
        synchronized (gObjects) {
            for (var gObj : gObjects) {
                if (gObj.isVisible() && gObj.contains(x, y)) {
                    gObjs.addFirst(gObj);
                }
            }
        }
        return gObjs.toArray(new GObject[0]);
    }

    public GObject[] getElementsAt(GPoint gPoint) {
        return getElementsAt(gPoint.x(), gPoint.y());
    }

    public void sendToBack(GObject gObj) {
        synchronized (gObjects) {
            if (gObjects.remove(gObj)) {
                gObjects.addFirst(gObj);
                this.repaint();
            }
        }
    }

    public void sendToFront(GObject gObject) {
        synchronized (gObjects) {
            if (gObjects.remove(gObject)) {
                gObjects.addLast(gObject);
                this.repaint();
            }
        }
    }

    public void sendBackward(GObject gObject) {
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

    public void sendForward(GObject gObject) {
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
