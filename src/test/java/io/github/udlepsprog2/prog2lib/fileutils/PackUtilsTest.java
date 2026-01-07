package io.github.udlepsprog2.prog2lib.fileutils;

import io.github.udlepsprog2.prog2lib.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import static io.github.udlepsprog2.prog2lib.fileutils.PackUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class PackUtilsTest {

    private static final double DELTA = 1.0E-10;
    private static final int MAX_TEST = 1000;
    private static final int LENGTH = 256;

    private static final RandomGenerator GEN = RandomGenerator.getInstance();
    private static final byte[] buffer = new byte[LENGTH];

    @Test
    void testBooleans() {
        packBoolean(false, buffer, 0);
        packBoolean(true, buffer, 1);
        assertFalse(unpackBoolean(buffer, 0));
        assertTrue(unpackBoolean(buffer, 1));
    }

    @Test
    void testChars() {
        for (int i = 0; i < MAX_TEST; i++) {
            char in = (char) GEN.nextInt('\u0000', '\uffff');
            packChar(in, buffer, 0);
            char out = unpackChar(buffer, 0);
            assertEquals(in, out);
        }
    }

    @Test
    void testStrings() {
        for (int i = 0; i < MAX_TEST; i++) {
            StringBuilder in = new StringBuilder();
            int maxLength = GEN.nextInt(LENGTH / 2);
            for (int j = 0; j < GEN.nextInt(LENGTH); j++) {
                // The zero char in the middle of a string causes problems
                in.append((char) GEN.nextInt('\u0001', '\uffff'));
            }
            packLimitedString(in.toString(), maxLength, buffer, 0);
            String out = unpackLimitedString(maxLength, buffer, 0);
            String savedPrefix = in.substring(0, Math.min(maxLength, in.length()));
            assertEquals(savedPrefix, out);
        }
    }

    @Test
    void testBytes() {
        for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
            byte in = (byte) i;
            packByte(in, buffer, 0);
            byte out = unpackByte(buffer, 0);
            assertEquals(in, out);
        }
    }

    @Test
    void testShorts() {
        for (int i = 0; i < MAX_TEST; i++) {
            short in = (short) GEN.nextInt(0x00, 0xFFFF);
            packShort(in, buffer, 0);
            short out = unpackShort(buffer, 0);
            assertEquals(in, out);
        }
    }

    @Test
    void testInts() {
        for (int i = 0; i < MAX_TEST; i++) {
            int in = GEN.nextInt();
            packInt(in, buffer, 0);
            int out = unpackInt(buffer, 0);
            assertEquals(in, out);
        }
    }

    @Test
    void testLongs() {
        for (int i = 0; i < MAX_TEST; i++) {
            long in = GEN.nextLong();
            packLong(in, buffer, 0);
            long out = unpackLong(buffer, 0);
            assertEquals(in, out);
        }
    }

    @Test
    void testFloats() {
        for (int i = 0; i < MAX_TEST; i++) {
            float in = GEN.nextFloat();
            packFloat(in, buffer, 0);
            float out = unpackFloat(buffer, 0);
            assertEquals(in, out, DELTA);
        }
    }

    @Test
    void testDoubles() {
        for (int i = 0; i < MAX_TEST; i++) {
            double in = GEN.nextDouble();
            packDouble(in, buffer, 0);
            double out = unpackDouble(buffer, 0);
            assertEquals(in, out, DELTA);
        }
    }
}