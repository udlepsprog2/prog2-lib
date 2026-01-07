package io.github.udlepsprog2.prog2lib.graphics;


import io.github.udlepsprog2.prog2lib.geometry.GDimension;

public interface GSizeable {
    GDimension getSize();

    void setSize(double width, double height);

    default void setSize(GDimension size) {
        setSize(size.width(), size.height());
    }

    default double getWidth() {
        return getSize().width();
    }

    default void setWidth(double width) {
        setSize(width, getHeight());
    }

    default double getHeight() {
        return getSize().height();
    }

    default void setHeight(double height) {
        setSize(getWidth(), height);
    }
}
