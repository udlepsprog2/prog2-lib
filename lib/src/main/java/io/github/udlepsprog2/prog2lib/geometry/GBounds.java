package io.github.udlepsprog2.prog2lib.geometry;

import java.util.Objects;

/**
 * Immutable axis-aligned rectangular bounds.
 * <p>
 * A {@code GBounds} is defined by its top-left corner ({@code xTopLeft}, {@code yTopLeft}) and its
 * {@code width} and {@code height}. It can be used to perform simple geometric
 * queries such as checking whether a point lies within the rectangle.
 * </p>
 *
 * <p>
 * Unless stated otherwise, containment checks are inclusive of the rectangle's
 * edges; that is, points on the border are considered inside.
 * </p>
 *
 * @param xTopLeft the xTopLeft-coordinate of the top-left corner
 * @param yTopLeft the yTopLeft-coordinate of the top-left corner
 * @param width the rectangle's width (must be positive)
 * @param height the rectangle's height (must be positive)
 */
public record GBounds(double xTopLeft, double yTopLeft, double width, double height) {
    /**
     * Validates component invariants for this record.
     * Ensures {@code width} and {@code height} are strictly positive.
     *
     * @throws IllegalArgumentException if {@code width <= 0} or {@code height <= 0}
     */
    public GBounds {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive");
        }
    }

    /**
     * Creates bounds from a top-left {@link GPoint} and a {@link GDimension} size.
     * <p>
     * This is a convenience constructor that delegates to the canonical constructor
     * using the point's coordinates and the dimension's width/height.
     * </p>
     *
     * @param topLeft the top-left corner point (must not be {@code null})
     * @param size the dimensions of the bounds (must not be {@code null})
     * @throws NullPointerException if {@code topLeft} or {@code size} is {@code null}
     */
    public GBounds(GPoint topLeft, GDimension size) {
        this(Objects.requireNonNull(topLeft, "topLeft").x(),
             Objects.requireNonNull(topLeft, "topLeft").y(),
             Objects.requireNonNull(size, "size").width(),
             Objects.requireNonNull(size, "size").height());
    }
    
    /**
     * Returns whether the given point lies within these bounds.
     * The check is inclusive on all sides: {@code xTopLeft in [this.xTopLeft, this.xTopLeft + width]}
     * and {@code yTopLeft in [this.yTopLeft, this.yTopLeft + height]}.
     *
     * @param x the xTopLeft-coordinate of the point to test
     * @param y the yTopLeft-coordinate of the point to test
     * @return {@code true} if the point is inside or on the border; {@code false} otherwise
     */
    public boolean contains(double x, double y) {
        return this.xTopLeft <= x && x <= this.xTopLeft + width && this.yTopLeft <= y && y <= this.yTopLeft + height;
    }

    /**
     * Returns whether the given point lies within these bounds.
     * This is equivalent to {@link #contains(double, double)} using the
     * point's coordinates.
     *
     * @param point the point to test (must not be {@code null})
     * @return {@code true} if the point is inside or on the border; {@code false} otherwise
     * @throws NullPointerException if {@code point} is {@code null}
     */
    public boolean contains(GPoint point) {
        Objects.requireNonNull(point, "point");
        return contains(point.x(), point.y());
    }
}
