package io.github.udlepsprog2.prog2lib.program;

import java.util.Locale;
import java.util.Scanner;

/**
 * Convenience base class for simple command-line programs.
 * <p>
 * Provides minimal I/O helpers built on top of a shared {@link Scanner}
 * reading from {@code System.in}. Subclasses typically override
 * {@link Program#run()} to implement their logic and may use the
 * convenience methods {@link #print(String)}, {@link #println(String)},
 * {@link #readLine(String)}, {@link #readInt(String)}, {@link #readDouble(String)},
 * and {@link #readBoolean(String)} to interact with the user.
 * </p>
 * <p>Notes:</p>
 * <ul>
 *   <li>The input helpers are thin wrappers around {@link Scanner} methods and
 *       will throw a {@link java.util.InputMismatchException} if the next token
 *       does not match the expected type.</li>
 *   <li>When mixing token-based reads (e.g., {@code nextInt()}, {@code nextDouble()})
 *       with line-based reads ({@code nextLine()}), be aware that a pending newline
 *       may cause the next line read to return an empty string. If needed, call
 *       an extra {@code nextLine()} after a token read to consume the end-of-line.</li>
 * </ul>
 */
public abstract class CommandLineProgram extends Program {

    // Scanner is instance level, so tests can replace System.in before
    // instantiating a CommandLineProgram and have the scanner read the test input.
    private final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    /**
     * Creates a new command-line program.
     */
    protected CommandLineProgram() {
        super();
    }

    /**
     * Prints the given message without a trailing newline.
     *
     * @param message the text to print (non-null)
     */
    public void print(String message) {
        System.out.print(message);
    }

    /**
     * Prints the given message followed by a newline.
     *
     * @param message the text to print (non-null)
     */
    public void println(String message) {
        System.out.println(message);
    }

    /**
     * Prints the boolean value without a trailing newline.
     * <p>
     * Convenience wrapper around {@code System.out.print(boolean)}.
     * </p>
     *
     * @param b the boolean value to print
     */
    public void print(boolean b) {
        System.out.print(b);
    }

    /**
     * Prints the boolean value followed by a newline.
     * <p>
     * Convenience wrapper around {@code System.out.println(boolean)}.
     * </p>
     *
     * @param b the boolean value to print
     */
    public void println(boolean b) {
        System.out.println(b);
    }

    /**
     * Prints the integer value without a trailing newline.
     * <p>
     * Convenience wrapper around {@code System.out.print(int)}.
     * </p>
     *
     * @param n the integer value to print
     */
    public void print(int n) {
        System.out.print(n);
    }

    /**
     * Prints the integer value followed by a newline.
     * <p>
     * Convenience wrapper around {@code System.out.println(int)}.
     * </p>
     *
     * @param n the integer value to print
     */
    public void println(int n) {
        System.out.println(n);
    }

    /**
     * Prints the double value without a trailing newline.
     * <p>
     * Convenience wrapper around {@code System.out.print(double)}. Note that
     * number formatting follows the platform's default conventions; use
     * {@link java.lang.String#format} if specific formatting is required.
     * </p>
     *
     * @param d the double value to print
     */
    public void print(double d) {
        System.out.print(d);
    }

    /**
     * Prints the double value followed by a newline.
     * <p>
     * Convenience wrapper around {@code System.out.println(double)}. Note that
     * number formatting follows the platform's default conventions; use
     * {@link java.lang.String#format} if specific formatting is required.
     * </p>
     *
     * @param d the double value to print
     */
    public void println(double d) {
        System.out.println(d);
    }

    /**
     * Prompts the user and reads a boolean value using {@link Scanner#nextBoolean()}.
     *
     * @param prompt the prompt to display before reading
     * @return the boolean value entered by the user
     * @throws java.util.InputMismatchException if the next token is not a boolean
     */
    public boolean readBoolean(String prompt) {
        System.out.print(prompt);
        return scanner.nextBoolean();
    }

    /**
     * Prompts the user and reads an entire line using {@link Scanner#nextLine()}.
     *
     * @param prompt the prompt to display before reading
     * @return the line entered by the user (may be empty)
     */
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Prompts the user and reads an integer using {@link Scanner#nextInt()}.
     *
     * @param prompt the prompt to display before reading
     * @return the integer value entered by the user
     * @throws java.util.InputMismatchException if the next token is not an int
     */
    public int readInt(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    /**
     * Prompts the user and reads a double using {@link Scanner#nextDouble()}.
     *
     * @param prompt the prompt to display before reading
     * @return the double value entered by the user
     * @throws java.util.InputMismatchException if the next token is not a double
     */
    public double readDouble(String prompt) {
        System.out.print(prompt);
        return scanner.nextDouble();
    }
}
