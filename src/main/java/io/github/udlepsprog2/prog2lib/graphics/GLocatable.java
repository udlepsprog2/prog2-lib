package io.github.udlepsprog2.prog2lib.graphics;


import io.github.udlepsprog2.prog2lib.geometry.GPoint;

public interface GLocatable {
    GPoint getLocation();

    void setLocation(double x, double y);

    default void setLocation(GPoint location) {
        setLocation(location.x(), location.y());
    }

    default void move(double dx, double dy) {
        setLocation(getX() + dx, getY() + dy);
    }

    default double getX() {
        return getLocation().x();
    }

    default double getY() {
        return getLocation().y();
    }

    default void setX(double x) {
        setLocation(x, getY());
    }

    default void setY(double y) {
        setLocation(getX(), y);
    }
}
