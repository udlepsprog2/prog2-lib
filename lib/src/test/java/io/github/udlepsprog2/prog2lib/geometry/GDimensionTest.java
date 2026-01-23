package io.github.udlepsprog2.prog2lib.geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GDimensionTest {

    @Test
    void validDimensionCreatesSuccessfully() {
        GDimension d = new GDimension(3.0, 4.0);
        assertEquals(3.0, d.width(), 0.0);
        assertEquals(4.0, d.height(), 0.0);
    }

    @Test
    void zeroOrNegativeValuesThrow() {
        assertThrows(IllegalArgumentException.class, () -> new GDimension(0.0, 1.0));
        assertThrows(IllegalArgumentException.class, () -> new GDimension(1.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new GDimension(-1.0, 2.0));
    }
}
