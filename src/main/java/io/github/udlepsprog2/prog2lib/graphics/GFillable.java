package io.github.udlepsprog2.prog2lib.graphics;

import java.awt.Color;

/**
 * Contract for objects that can be filled with a solid color.
 * <p>
 * Implementations expose a binary state indicating whether the object should
 * be rendered as filled or only with its outline, and a {@link Color} used to
 * fill the shape when filling is enabled.
 * </p>
 */
public interface GFillable {
    /**
     * Indicates whether this object is currently marked as filled.
     *
     * @return {@code true} if the object should be rendered filled; {@code false} for outline only
     */
    boolean isFilled();

    /**
     * Sets whether this object should be rendered as filled or only with its outline.
     *
     * @param filled {@code true} to render filled; {@code false} for outline only
     */
    void setFilled(boolean filled);

    /**
     * Returns the current fill color.
     *
     * @return the non-null {@link Color} used to fill this object
     */
    Color getFillColor();

    /**
     * Sets the fill color to use when {@link #isFilled()} is {@code true}.
     *
     * @param fillColor the new fill {@link Color} (must not be {@code null})
     * @throws NullPointerException if {@code fillColor} is {@code null}
     */
    void setFillColor(Color fillColor);
}
