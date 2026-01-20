package io.github.udlepsprog2.prog2lib.program;

import io.github.udlepsprog2.prog2lib.geometry.GPoint;
import io.github.udlepsprog2.prog2lib.graphics.GCanvas;
import io.github.udlepsprog2.prog2lib.graphics.GObject;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

/**
 * Convenience abstract base class for simple Swing-based graphics programs.
 * <p>
 * A {@code GraphicsProgram} creates a {@link javax.swing.JFrame} hosting a
 * {@link io.github.udlepsprog2.prog2lib.graphics.GCanvas}. Subclasses can add
 * {@link io.github.udlepsprog2.prog2lib.graphics.GObject GObject}s to the canvas,
 * control the background color, set the window title, and optionally handle mouse
 * events by overriding {@link #mouseClicked(java.awt.event.MouseEvent)} and
 * {@link #mouseMoved(java.awt.event.MouseEvent)}. Basic helpers such as
 * {@link #pause(long)} and {@link #waitForClick()} are provided for simple demos.
 * </p>
 *
 * <p>
 * This class is abstract and intended to be subclassed; create your own subclass
 * and override {@link Program#run()} (or call the provided helpers) to implement
 * program behavior.
 * </p>
 *
 * <p>
 * Threading: the UI is created on the Swing Event Dispatch Thread (EDT). Methods that
 * block the current thread—like {@link #pause(long)} and {@link #waitForClick()}—assert
 * they are not invoked from the EDT.
 * </p>
 */
public abstract class GraphicsProgram extends Program {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    private JFrame window;
    private GCanvas canvas;

    private CountDownLatch clicker = null;

    /**
     * Creates a graphics program window with a default canvas size of
     * {@value #DEFAULT_WIDTH}x{@value #DEFAULT_HEIGHT} pixels.
     */
    public GraphicsProgram() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Creates a graphics program window with the given canvas size.
     * <p>
     * The UI is initialized on the Swing Event Dispatch Thread using
     * {@link SwingUtilities#invokeAndWait(Runnable)}. Any failure is wrapped in a
     * {@link RuntimeException}.
     * </p>
     *
     * @param width the preferred canvas width in pixels
     * @param height the preferred canvas height in pixels
     * @throws RuntimeException if the UI cannot be initialized
     */
    public GraphicsProgram(int width, int height) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                canvas = new GCanvas();
                canvas.setPreferredSize(new Dimension(width, height));
                canvas.setLayout(null);
                window = new JFrame();
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(canvas);
                window.pack();
                window.setVisible(true);
                window.addMouseListener(new ProgramMouseListener());
            });
        } catch (InterruptedException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the underlying {@link GCanvas} attached to the window.
     *
     * @return the non-null canvas used for rendering
     */
    public GCanvas getGCanvas() {
        return canvas;
    }

    /**
     * Sleeps the current thread for the specified number of milliseconds.
     * <p>
     * Intended as a simple timing helper in demonstrations. This method asserts that
     * it is not called from the Swing Event Dispatch Thread (EDT).
     * </p>
     *
     * @param millis the number of milliseconds to sleep (non-negative)
     */
    public static void pause(long millis) {
        assert !SwingUtilities.isEventDispatchThread();
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Returns the current width of the canvas in pixels.
     *
     * @return the canvas width
     */
    public int getWidth() {
        return canvas.getWidth();
    }

    /**
     * Returns the current height of the canvas in pixels.
     *
     * @return the canvas height
     */
    public int getHeight() {
        return canvas.getHeight();
    }

    /**
     * Adds a graphics object to the canvas.
     *
     * @param gObject the object to add (must not be {@code null})
     * @throws NullPointerException if {@code gObject} is {@code null}
     */
    public void add(GObject gObject) {
        canvas.add(gObject);
    }

    /**
     * Adds a graphics object to the canvas at the specified location.
     *
     * @param gObject the object to add (must not be {@code null})
     * @param x the x-coordinate of the object's location
     * @param y the y-coordinate of the object's location
     * @throws NullPointerException if {@code gObject} is {@code null}
     */
    public void add(GObject gObject, int x, int y) {
        canvas.add(gObject, x, y);
    }

    /**
     * Adds a graphics object to the canvas at the specified location.
     *
     * @param gObject the object to add (must not be {@code null})
     * @param position the location of the object's upper-left corner (must not be {@code null})
     * @throws NullPointerException if {@code gObject} or {@code position} is {@code null}
     */
    public void add(GObject gObject, GPoint position) {
        canvas.add(gObject, position);
    }

    /**
     * Sets the canvas background color and requests a repaint.
     *
     * @param color the new background color (must not be {@code null})
     */
    public void setBackground(Color color) {
        canvas.setBackground(color);
    }

    /**
     * Returns the window title.
     *
     * @return the current title (may be empty)
     */
    public String getTitle() {
        return window.getTitle();
    }

    /**
     * Sets the window title.
     *
     * @param title the new title text
     */
    public void setTitle(String title) {
        window.setTitle(title);
    }

    /**
     * Blocks the current thread until a mouse click occurs in the window.
     * <p>
     * This method is intended for simple interactive programs and asserts that it is
     * not called from the Swing Event Dispatch Thread (EDT). It returns after the next
     * mouse click is received anywhere in the window.
     * </p>
     */
    public void waitForClick() {
        assert !SwingUtilities.isEventDispatchThread();
        synchronized (this) {
            if (clicker != null) return;
            clicker = new CountDownLatch(1);
        }
        try {
            clicker.await();
            synchronized (this) {
                clicker = null;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Installs simple mouse listeners on the canvas that forward events to the
     * hook methods {@link #mouseClicked(MouseEvent)} and {@link #mouseMoved(MouseEvent)}.
     * <p>
     * Subclasses can call this during initialization if they prefer the hook-based style
     * over adding listeners directly.
     * </p>
     */
    protected void addMouseListeners() {
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GraphicsProgram.this.mouseClicked(e);
            }
        });
        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                GraphicsProgram.this.mouseMoved(e);
            }
        });
    }

    /**
     * Hook method invoked when the mouse moves over the canvas.
     * Subclasses may override to react to mouse motion.
     *
     * @param e the mouse event
     */
    protected void mouseMoved(MouseEvent e) {
    }

    /**
     * Hook method invoked when the mouse is clicked inside the window.
     * Subclasses may override to react to clicks.
     *
     * @param e the mouse event
     */
    protected void mouseClicked(MouseEvent e) {
    }

    private class ProgramMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            synchronized (GraphicsProgram.this) {
                if (clicker != null)
                    clicker.countDown();
            }
        }
    }
}
