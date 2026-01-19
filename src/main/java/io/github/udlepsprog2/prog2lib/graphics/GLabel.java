package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GBounds;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * A drawable text label.
 * <p>
 * A {@code GLabel} renders a text string at the object's location using the current
 * stroke color as the text color and the configured {@link Font}. Positioning follows
 * typical text rendering semantics in AWT/Swing: the label's {@code y} coordinate is
 * used as the text baseline when drawing. Methods like {@link #getAscent()} and
 * {@link #getDescent()} expose font metrics to assist with alignment.
 * </p>
 */
public class GLabel extends GObject {

    private String text;
    private Font font = new Font("Default", Font.PLAIN, 12);

    private static final Component EMPTY_COMPONENT = new Component() {};

    private FontMetrics getFontMetrics() {
        var canvas = getGCanvas();
        Component component = canvas != null ? canvas : EMPTY_COMPONENT;
        return component.getFontMetrics(font);
    }

    /**
     * Creates a label with the given text at the origin ({@code 0,0}).
     *
     * @param text the text to display (must not be {@code null})
     */
    public GLabel(String text) {
        this(text, 0.0, 0.0);
    }

    /**
     * Creates a label with the given text and position.
     *
     * @param text the text to display (must not be {@code null})
     * @param x the x-coordinate of the text baseline start
     * @param y the y-coordinate of the text baseline
     */
    public GLabel(String text, double x, double y) {
        super(x, y);
        this.text = text;
    }

    /**
     * Returns the current label text.
     *
     * @return the text content (may be {@code null} if set so)
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the label text and requests a repaint.
     *
     * @param text the new text (should not be {@code null})
     */
    public void setText(String text) {
        this.text = text;
        repaint();
    }

    /**
     * Returns the font used to render this label.
     *
     * @return the non-null {@link Font}
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the font used to render this label and requests a repaint.
     *
     * @param font the new {@link Font} (must not be {@code null})
     */
    public void setFont(Font font) {
        this.font = font;
        repaint();
    }

    /**
     * Returns the pixel width of the current text using the current font metrics.
     *
     * @return the text width in pixels
     */
    public double getWidth() {
        return getFontMetrics().stringWidth(text);
    }

    /**
     * Returns the total line height for the current font.
     *
     * @return the font height in pixels
     */
    public double getHeight() {
        return getFontMetrics().getHeight();
    }

    /**
     * Returns the ascent of the current font, i.e., the distance from baseline to the
     * top of most characters.
     *
     * @return the ascent in pixels
     */
    public double getAscent() {
        return getFontMetrics().getAscent();
    }

    /**
     * Returns the descent of the current font, i.e., the distance from baseline to the
     * bottom of characters that descend below the baseline.
     *
     * @return the descent in pixels
     */
    public double getDescent() {
        return getFontMetrics().getDescent();
    }

    /** {@inheritDoc} */
    @Override
    void paintComponent(Graphics g) {
        g.setColor(getColor());
        g.setFont(font);
        g.drawString(text, (int) Math.round(getX()), (int) Math.round(getY()));
    }

    /** {@inheritDoc} */
    @Override
    GBounds getBounds() {
        return new GBounds(getX(), getY(), getWidth(), getHeight());
    }
}
