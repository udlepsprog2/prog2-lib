package io.github.udlepsprog2.prog2lib.random;

import java.awt.Color;
import java.util.Random;

/**
 * Convenience singleton wrapper around {@link java.util.Random}.
 * <p>
 * Provides easy access to a shared pseudo-random number generator and
 * a few helper methods with clearer semantics (e.g., inclusive upper bound).
 * This generator is suitable for general-purpose randomness but
 * is <b>not</b> intended for security-sensitive use cases.
 * </p>
 *
 * <p>
 * Concurrency notes: {@link Random} instances are thread-safe for concurrent
 * use; however, using a single shared instance may cause contention under
 * heavy parallel access. For highly concurrent workloads consider creating a
 * dedicated instance per thread/task or using {@link java.util.concurrent.ThreadLocalRandom}.
 * </p>
 */
public class RandomGenerator {

    private static final RandomGenerator generator = new RandomGenerator();
    
    private final Random random;

    /**
     * Creates the underlying random generator.
     * <p>
     * Uses the default-seeded {@link Random}.
     * </p>
     */
    private RandomGenerator() {
        this.random = new Random();
    }

    /**
     * Returns the shared {@code RandomGenerator} instance.
     *
     * @return the singleton instance
     */
    public static RandomGenerator getInstance() {
        return generator;
    }

    /**
     * Returns a random boolean value.
     * <p>
     * This method delegates to {@link Random#nextBoolean()} and yields {@code true}
     * or {@code false} with (approximately) equal probability.
     * </p>
     *
     * @return {@code true} or {@code false} with ~50% probability each
     */
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    /**
     * Returns {@code true} with the supplied probability and {@code false} otherwise.
     * <p>
     * The {@code probability} parameter should be in the range {@code [0.0, 1.0]}.
     * No validation is performed; values outside this range will produce results
     * consistent with the underlying comparison {@code random.nextDouble() < probability}.
     * </p>
     *
     * @param probability the probability of returning {@code true}, expected in {@code [0.0, 1.0]}
     * @return {@code true} with probability {@code probability}; {@code false} otherwise
     */
    public boolean nextBoolean(double probability) {
        return random.nextDouble() < probability;
    }

    /**
     * Returns a random {@code int} value over the full {@code int} range with
     * approximately uniform distribution.
     *
     * @return a random {@code int}
     */
    public int nextInt() {
        return random.nextInt();
    }

    /**
     * Returns a random {@code int} uniformly distributed in the half-open range
     * {@code [0, maxExclusive)}.
     *
     * @param maxExclusive the exclusive upper bound (must be positive)
     * @return a random value in {@code [0, maxExclusive)}
     * @throws IllegalArgumentException if {@code maxExclusive <= 0}
     */
    public int nextInt(int maxExclusive) {
        return random.nextInt(maxExclusive);
    }

    /**
     * Returns a random {@code int} uniformly distributed in the inclusive range
     * {@code [min, maxInclusive]}.
     *
     * @param min the inclusive lower bound
     * @param maxInclusive the inclusive upper bound
     * @return a random value in {@code [min, maxInclusive]}
     * @throws IllegalArgumentException if {@code min > maxInclusive}
     */
    public int nextInt(int min, int maxInclusive) {
        return random.nextInt(min, maxInclusive + 1);
    }

    /**
     * Returns a random {@code long} value over the full {@code long} range with
     * approximately uniform distribution.
     *
     * @return a random {@code long}
     */
    public long nextLong() {
        return random.nextLong();
    }

    /**
     * Returns a random {@code float} value uniformly distributed in the
     * half-open range {@code [0.0f, 1.0f)}.
     *
     * @return a random {@code float} in {@code [0.0f, 1.0f)}
     */
    public float nextFloat() {
        return random.nextFloat();
    }

    /**
     * Returns a random {@code double} value uniformly distributed in the
     * half-open range {@code [0.0, 1.0)}.
     *
     * @return a random {@code double} in {@code [0.0, 1.0)}
     */
    public double nextDouble() {
        return random.nextDouble();
    }

    /**
     * Returns a random {@code double} uniformly distributed in the half-open range
     * {@code [min, max)}.
     * <p>
     * This method does not perform argument validation; callers are expected to
     * provide {@code min < max}. If {@code min == max} the result will always be
     * {@code min}. If {@code min > max}, the distribution will be inverted
     * relative to expectation.
     * </p>
     *
     * @param min the inclusive lower bound
     * @param max the exclusive upper bound
     * @return a random value in {@code [min, max)} when {@code min < max}
     */
    public double nextDouble(double min, double max) {
        return min + (max - min) * nextDouble();
    }

    /**
     * Returns a random opaque RGB color.
     * <p>
     * Each component (red, green, blue) is chosen independently and uniformly
     * from the integer range {@code [0, 255]}.
     * </p>
     *
     * @return a new {@link Color} with random RGB components
     */
    public Color nextColor() {
        return new Color(nextInt(256), nextInt(256), nextInt(256));
    }
    
    /**
     * Reseeds the underlying {@link Random} instance.
     * <p>
     * Changing the seed restarts the pseudo-random sequence and affects all
     * subsequent values produced by this generator. This method is not
     * synchronized; external synchronization is required if multiple threads
     * call it concurrently with other generation methods.
     * </p>
     *
     * @param seed the new seed value
     */
    public void setSeed(long seed) {
        random.setSeed(seed);
    }
}
