package io.github.udlepsprog2.prog2lib.geometry;

/**
 * Immutable two-dimensional size represented by a {@code width} and a {@code height}.
 * <p>
 * Instances of this record are validated to ensure both {@code width} and {@code height}
 * are strictly positive values.
 * </p>
 *
 * @param width  the width (must be positive)
 * @param height the height (must be positive)
 */
public record GDimension(double width, double height) {
    /**
     * Validates component invariants for this record.
     * Ensures {@code width} and {@code height} are strictly positive.
     *
     * @throws IllegalArgumentException if {@code width <= 0} or {@code height <= 0}
     */
    public GDimension {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive");
        }
    }
}
