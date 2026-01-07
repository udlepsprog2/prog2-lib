package io.github.udlepsprog2.prog2lib.random;

import java.util.Random;

public class RandomGenerator {

    private static final RandomGenerator generator = new RandomGenerator();
    private final Random random;

    private RandomGenerator() {
        this.random = new Random();
    }

    public static RandomGenerator getInstance() {
        return generator;
    }

    public int nextInt(int min, int maxInclusive) {
        return random.nextInt(min, maxInclusive + 1);
    }

    public int nextInt(int maxExclusive) {
        return random.nextInt(maxExclusive);
    }

    public int nextInt() {
        return random.nextInt();
    }

    public long nextLong() {
        return random.nextLong();
    }

    public float nextFloat() {
        return random.nextFloat();
    }

    public double nextDouble() {
        return random.nextDouble();
    }
}
