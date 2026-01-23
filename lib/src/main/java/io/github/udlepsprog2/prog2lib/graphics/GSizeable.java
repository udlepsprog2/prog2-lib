package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GDimension;

import java.util.Objects;

/**
 * Contract for objects that have a two-dimensional size.
 * <p>
 * Implementations expose and modify their size through a {@link GDimension}
 * value consisting of a {@code width} and a {@code height}. Unless otherwise specified,
 * sizes are expected to be strictly positive.
 * </p>
 */
public interface GSizeable {
    /**
     * Returns the current size of this object.
     *
     * @return a non-null {@link GDimension} representing the current size
     */
    GDimension getSize();

    /**
     * Sets the size using raw width and height values.
     * <p>
      * Implementations are expected to enforce that {@code width > 0} and {@code height > 0}.
     * </p>
     *
     * @param width the new width (must be positive)
     * @param height the new height (must be positive)
     * @throws IllegalArgumentException if {@code width <= 0} or {@code height <= 0}
     */
    void setSize(double width, double height);

    /**
     * Sets the size using a {@link GDimension} instance.
     * <p>
     * This default implementation validates the argument and delegates to
     * {@link #setSize(double, double)}.
     * </p>
     *
     * @param size the new size (must not be {@code null} and must have positive components)
     * @throws NullPointerException if {@code size} is {@code null}
     */
    default void setSize(GDimension size) {
        Objects.requireNonNull(size, "size");
        setSize(size.width(), size.height());
    }

    /**
     * Returns the current width.
     *
     * @return the width (strictly positive)
     */
    default double getWidth() {
        return getSize().width();
    }

    /**
     * Updates only the width, keeping the current height.
     *
     * @param width the new width (must be positive)
     * @throws IllegalArgumentException if {@code width <= 0}
     */
    default void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("width must be positive");
        }
        setSize(width, getHeight());
    }

    /**
     * Returns the current height.
     *
     * @return the height (strictly positive)
     */
    default double getHeight() {
        return getSize().height();
    }

    /**
     * Updates only the height, keeping the current width.
     *
     * @param height the new height (must be positive)
     * @throws IllegalArgumentException if {@code height <= 0}
     */
    default void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("height must be positive");
        }
        setSize(getWidth(), height);
    }
}
