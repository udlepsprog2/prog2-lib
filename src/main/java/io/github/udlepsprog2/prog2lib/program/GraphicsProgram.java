package io.github.udlepsprog2.prog2lib.program;

import io.github.udlepsprog2.prog2lib.graphics.GCanvas;
import io.github.udlepsprog2.prog2lib.graphics.GObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

public class GraphicsProgram extends Program {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    private JFrame window;
    private GCanvas canvas;

    private CountDownLatch clicker = null;

    public GraphicsProgram() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

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

    public GCanvas getGCanvas() {
        return canvas;
    }

    public static void pause(long millis) {
        assert !SwingUtilities.isEventDispatchThread();
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getWidth() {
        return canvas.getWidth();
    }

    public int getHeight() {
        return canvas.getHeight();
    }

    public void add(GObject gobj) {
        canvas.add(gobj);
    }

    public void setBackground(Color color) {
        canvas.setBackground(color);
    }

    public String getTitle() {
        return window.getTitle();
    }

    public void setTitle(String title) {
        window.setTitle(title);
    }

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

    protected void mouseMoved(MouseEvent e) {
    }

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
