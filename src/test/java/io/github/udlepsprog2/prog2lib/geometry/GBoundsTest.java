package io.github.udlepsprog2.prog2lib.geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GBoundsTest {

    @Test
    void containsPointInsideAndBorders() {
        GBounds b = new GBounds(0.0, 0.0, 10.0, 5.0);

        // inside
        assertTrue(b.contains(5.0, 2.5));

        // on borders
        assertTrue(b.contains(0.0, 0.0));
        assertTrue(b.contains(10.0, 5.0));
        assertTrue(b.contains(0.0, 5.0));
        assertTrue(b.contains(10.0, 0.0));

        // outside
        assertFalse(b.contains(-0.1, 2.5));
        assertFalse(b.contains(5.0, 5.1));
    }

    @Test
    void constructorWithPointAndSize() {
        GPoint topLeft = new GPoint(2.0, 3.0);
        GDimension size = new GDimension(4.0, 6.0);
        GBounds b = new GBounds(topLeft, size);

        assertEquals(2.0, b.xTopLeft(), 0.0);
        assertEquals(3.0, b.yTopLeft(), 0.0);
        assertEquals(4.0, b.width(), 0.0);
        assertEquals(6.0, b.height(), 0.0);
    }

    @Test
    void invalidDimensionsThrow() {
        assertThrows(IllegalArgumentException.class, () -> new GBounds(0, 0, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> new GBounds(0, 0, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> new GBounds(0, 0, -1, 2));
    }

    @Test
    void containsWithPointOverloadNullThrows() {
        GBounds b = new GBounds(0, 0, 1, 1);
        assertThrows(NullPointerException.class, () -> b.contains(null));
    }
}
