package io.github.udlepsprog2.prog2lib.geometry;

public record GBounds(double x, double y, double width, double height) {
    public boolean contains(double x, double y) {
        return this.x <= x && x <= this.x + width && this.y <= y && y <= this.y + height;
    }

    public boolean contains(GPoint point) {
        return contains(point.x(), point.y());
    }
}
