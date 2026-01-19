package io.github.udlepsprog2.prog2lib.graphics;

import io.github.udlepsprog2.prog2lib.geometry.GPoint;

import java.util.Objects;

/**
 * Contract for objects that have a 2D location.
 * <p>
 * Implementations expose and modify their position through a {@link GPoint}
 * consisting of {@code x} and {@code y} coordinates.
 * </p>
 */
public interface GLocatable {
    /**
     * Returns the current location of this object.
     *
     * @return a non-null {@link GPoint} representing the current location
     */
    GPoint getLocation();

    /**
     * Sets the location using raw coordinate values.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    void setLocation(double x, double y);

    /**
     * Sets the location using a {@link GPoint}.
     * <p>
     * This default implementation validates the argument and delegates to
     * {@link #setLocation(double, double)}.
     * </p>
     *
     * @param location the new location (must not be {@code null})
     * @throws NullPointerException if {@code location} is {@code null}
     */
    default void setLocation(GPoint location) {
        Objects.requireNonNull(location, "location");
        setLocation(location.x(), location.y());
    }

    /**
     * Moves this object by the specified deltas.
     *
     * @param dx the horizontal offset to add to {@code x}
     * @param dy the vertical offset to add to {@code y}
     */
    default void move(double dx, double dy) {
        setLocation(getX() + dx, getY() + dy);
    }

    /**
     * Returns the current x-coordinate.
     *
     * @return the x-coordinate
     */
    default double getX() {
        return getLocation().x();
    }

    /**
     * Returns the current y-coordinate.
     *
     * @return the y-coordinate
     */
    default double getY() {
        return getLocation().y();
    }

    /**
     * Updates only the x-coordinate, keeping the current y-coordinate.
     *
     * @param x the new x-coordinate
     */
    default void setX(double x) {
        setLocation(x, getY());
    }

    /**
     * Updates only the y-coordinate, keeping the current x-coordinate.
     *
     * @param y the new y-coordinate
     */
    default void setY(double y) {
        setLocation(getX(), y);
    }
}
