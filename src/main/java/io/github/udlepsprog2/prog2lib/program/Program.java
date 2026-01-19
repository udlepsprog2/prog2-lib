package io.github.udlepsprog2.prog2lib.program;

import java.lang.reflect.InvocationTargetException;

/**
 * Minimal base class for executable programs in this library.
 * <p>
 * Subclasses are expected to override {@link #run()} with their program logic.
 * The default {@link #start(String[])} implementation simply delegates to
 * {@link #run()} so that subclasses can choose to override either method.
 * </p>
 *
 * <p>
 * This class also provides a reflective {@link #main(String[])} bootstrap that
 * attempts to locate and instantiate the process entry class (the one whose
 * {@code main} was invoked by the JVM), and then calls its {@code start(String[])}
 * method. This allows students/programs to omit a manual {@code public static void main}
 * in their subclasses and focus on overriding {@link #run()} or {@link #start(String[])}.
 * </p>
 */
abstract class Program {

    /**
     * Program entry point for subclasses.
     * <p>
     * Subclasses should override this method to implement their behavior. The
     * default implementation throws an {@link UnsupportedOperationException}
     * to make the requirement explicit.
     * </p>
     *
     * @throws UnsupportedOperationException always, unless overridden
     */
    public void run() {
        throw new UnsupportedOperationException("You must override the run() method in your main class.");
    }

    /**
     * Starts the program with the given command-line arguments.
     * <p>
     * The default implementation delegates to {@link #run()}. Subclasses may
     * override this method if they need access to {@code args}.
     * </p>
     *
     * @param args command-line arguments passed by the JVM (non-null, may be empty)
     */
    public void start(String[] args) {
        run();
    }

    /**
     * Reflective bootstrap used as the application's JVM entry point.
     * <p>Implementation notes:</p>
     * <ul>
     *   <li>Determines the main class name via {@code sun.java.command} system property.</li>
     *   <li>Loads the class, instantiates it using the no-arg constructor, and
     *       invokes a {@code start(String[])} method on that instance.</li>
     *   <li>Wraps common reflection errors into an {@link IllegalStateException} with
     *       a helpful message.</li>
     *   <li>If the invoked method throws an exception, its {@code cause} is rethrown
     *       as a {@link RuntimeException}.</li>
     * </ul>
     *
     * @param args command-line arguments as provided by the JVM
     * @throws IllegalStateException if the main class cannot be determined or started
     * @throws RuntimeException if the target {@code start} method throws a runtime exception
     */
    public static void main(String[] args) {
        // Very, very, very ad-hoc method
        var className = System.getProperty("sun.java.command");
        if (className == null) {
            throw new IllegalStateException("Cannot determine main class, please define a main " +
                    "method in your program.");
        }
        try {
            var classLoader = Thread.currentThread().getContextClassLoader();
            var mainClass = classLoader.loadClass(className);
            var mainConstructor = mainClass.getDeclaredConstructor();
            var mainInstance = mainConstructor.newInstance();
            var startMethod = mainClass.getMethod("start", String[].class);
            startMethod.invoke(mainInstance, new Object[]{args});
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new IllegalStateException("Cannot determine main class, please define a main " +
                    "method in your program.", e);
        } catch (InvocationTargetException e) {
            throw (RuntimeException) e.getCause();
        }
    }
}
