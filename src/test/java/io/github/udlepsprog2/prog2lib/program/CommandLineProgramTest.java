package io.github.udlepsprog2.prog2lib.program;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineProgramTest {

    private static class TestProgram extends CommandLineProgram {
        @Override
        public void run() {
            // no-op for testing
        }
    }

    @Test
    void printWritesWithoutNewline() {
        TestProgram p = new TestProgram();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            p.print("hello");
            assertEquals("hello", bout.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void printlnWritesWithNewline() {
        TestProgram p = new TestProgram();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            p.println("line");
            String out = bout.toString();
            assertTrue(out.equals("line\n") || out.equals("line\r\n"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void noSetOutputMethodPresent() {
        // Verify there is no protected setOutput(PrintStream) method on TestProgram by name.
        boolean has = Arrays.stream(TestProgram.class.getDeclaredMethods()).anyMatch(m -> m.getName().equals("setOutput"));
        assertFalse(has, "setOutput method should not be present");
    }

    @Test
    void readLineReadsFullLineAndPrintsPrompt() {
        String input = "a full line" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram(); // scanner created after System.in replaced
            String result = p.readLine("prompt>");

            assertEquals("a full line", result);
            assertEquals("prompt>", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    @Test
    void readIntReadsIntegerAndPrintsPrompt() {
        String input = "42" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram();
            int v = p.readInt("enter int:");

            assertEquals(42, v);
            assertEquals("enter int:", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    @Test
    void readDoubleReadsDoubleAndPrintsPrompt() {
        String input = "3.14" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram();
            double d = p.readDouble("enter dbl:");

            assertEquals(3.14, d, 1e-9);
            assertEquals("enter dbl:", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    @Test
    void readBooleanReadsBooleanAndPrintsPrompt() {
        String input = "true" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram();
            boolean b = p.readBoolean("enter bool:");

            assertTrue(b);
            assertEquals("enter bool:", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    // New tests for primitive overloads of print/println
    @Test
    void printBooleanWritesWithoutNewline() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            new TestProgram().print(true);
            assertEquals("true", bout.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void printlnBooleanWritesWithNewline() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            new TestProgram().println(false);
            String out = bout.toString();
            assertTrue(out.equals("false\n") || out.equals("false\r\n"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void printIntWritesWithoutNewline() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            new TestProgram().print(123);
            assertEquals("123", bout.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void printlnIntWritesWithNewline() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            new TestProgram().println(456);
            String out = bout.toString();
            assertTrue(out.equals("456\n") || out.equals("456\r\n"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void printDoubleWritesWithoutNewline() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            new TestProgram().print(2.718);
            assertEquals("2.718", bout.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void printlnDoubleWritesWithNewline() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            new TestProgram().println(1.618);
            String out = bout.toString();
            assertTrue(out.equals("1.618\n") || out.equals("1.618\r\n"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void readIntInvalidThrows() {
        String input = "not-an-int" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram();
            assertThrows(java.util.InputMismatchException.class, () -> p.readInt("enter int:"));
            // prompt should have been printed even if parsing failed
            assertEquals("enter int:", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    @Test
    void readDoubleInvalidThrows() {
        String input = "not-a-double" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram();
            assertThrows(java.util.InputMismatchException.class, () -> p.readDouble("enter dbl:"));
            assertEquals("enter dbl:", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    @Test
    void readBooleanInvalidThrows() {
        String input = "not-a-bool" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram();
            assertThrows(java.util.InputMismatchException.class, () -> p.readBoolean("enter bool:"));
            assertEquals("enter bool:", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    @Test
    void printfFormatsAndWrites() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            new TestProgram().printf("hello %s %d", "world", 42);
            assertEquals("hello world 42", bout.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void printfAppendsFormattedOutput() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            TestProgram p = new TestProgram();
            p.printf("%s", "first");
            p.printf("-%s", "second");
            assertEquals("first-second", bout.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void printObjectWritesWithoutNewline() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            record Person(String name, int age) {
            }
            Person person = new Person("Alice", 30);
            new TestProgram().print(person);
            assertEquals("Person[name=Alice, age=30]", bout.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void printObjectWritesWithNewline() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            record Person(String name, int age) {
            }
            Person person = new Person("Alice", 30);
            new TestProgram().println(person);
            assertEquals("Person[name=Alice, age=30]\n", bout.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void printlnNoArgWritesNewline() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            new TestProgram().println();
            String out = bout.toString();
            assertTrue(out.equals("\n") || out.equals("\r\n"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void readLineWithoutPromptReadsFullLine() {
        String input = "no prompt line" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram();
            String result = p.readLine();

            assertEquals("no prompt line", result);
            assertEquals("", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    @Test
    void readIntWithoutPromptReadsInteger() {
        String input = "7" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram();
            int v = p.readInt();

            assertEquals(7, v);
            assertEquals("", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    @Test
    void readDoubleWithoutPromptReadsDouble() {
        String input = "0.5" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram();
            double d = p.readDouble();

            assertEquals(0.5, d, 1e-9);
            assertEquals("", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    @Test
    void readBooleanWithoutPromptReadsBoolean() {
        String input = "false" + System.lineSeparator();
        ByteArrayInputStream bin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setIn(bin);
            System.setOut(new PrintStream(bout));

            TestProgram p = new TestProgram();
            boolean b = p.readBoolean();

            assertFalse(b);
            assertEquals("", bout.toString());
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }
}

