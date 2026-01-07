package io.github.udlepsprog2.prog2lib.program;

import java.lang.reflect.InvocationTargetException;

abstract class Program {

    public void run() {
        throw new UnsupportedOperationException("You must override the run() method in your main class.");
    }

    public void start(String[] args) {
        run();
    }

    public static void main(String[] args) {
        // Very-very add-hoc method
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
