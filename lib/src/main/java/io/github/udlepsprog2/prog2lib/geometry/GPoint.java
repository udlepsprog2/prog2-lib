package io.github.udlepsprog2.prog2lib.geometry;

/**
 * Immutable 2D point with {@code x} and {@code y} coordinates.
 * <p>
 * Represents a location in a Cartesian plane. Instances are immutable; any
 * transformation produces a new {@code GPoint}.
 * </p>
 *
 * @param x the x-coordinate of the point
 * @param y the y-coordinate of the point
 */
public record GPoint(double x, double y) {

    /**
     * Returns a new point translated by the given delta values.
     *
     * @param dx the horizontal offset to add to {@code x}
     * @param dy the vertical offset to add to {@code y}
     * @return a new {@code GPoint} at {@code (x + dx, y + dy)}
     */
    public GPoint move(double dx, double dy) {
        return new GPoint(x + dx, y + dy);
    }
}
