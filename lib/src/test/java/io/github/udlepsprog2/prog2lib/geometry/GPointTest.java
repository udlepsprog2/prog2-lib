package io.github.udlepsprog2.prog2lib.geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPointTest {

    @Test
    void moveTranslatesAndIsImmutable() {
        GPoint p = new GPoint(1.0, 2.0);
        GPoint moved = p.move(3.0, -1.0);

        // original should remain unchanged
        assertEquals(1.0, p.x(), 0.0);
        assertEquals(2.0, p.y(), 0.0);

        // moved should be the sum of coordinates
        assertEquals(new GPoint(4.0, 1.0), moved);
    }

    @Test
    void moveWithNegativeDeltas() {
        GPoint p = new GPoint(-5.5, 10.0);
        GPoint moved = p.move(5.5, -10.0);
        assertEquals(new GPoint(0.0, 0.0), moved);
    }
}
