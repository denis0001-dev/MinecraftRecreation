package ru.morozovit.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExcParser {
    /**
     * The exception.
     */
    private final Throwable exc;

    /**
     * Constructs a new {@link Throwable} parser.
     * The exception cannot be null.
     * @param e The exception.
     * @throws NullPointerException if the exception is null
     */
    public ExcParser(@NotNull Throwable e) {
        this.exc = e;
    }

    /**
     * Makes a {@link String} out of the exception like in the console.
     * @return A {@link String} with the exception name, message, and stack trace.
     */
    @Override
    public String toString() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        this.exc.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * @return The exception.
     */
    public Throwable getException() {
        return exc;
    }

    /**
     * Returns the exception message.
     * @return The exception message got through {@code Exception.getMessage()}.
     */
    public @Nullable String getMessage() {
        return this.exc.getMessage();
    }

    /**
     * Returns the stack trace of the exception.
     * @return An array of {@link StackTraceElement}s.
     */
    public StackTraceElement[] getStackTraceList() {
        return exc.getStackTrace();
    }

    /**
     * Return the stack trace of the exception converted to strings.
     * @return An array of {@link String}s.
     */
    public String[] getStackTraceAsStrings() {
        String[] result = new String[exc.getStackTrace().length];

        for (int i = 0; i<exc.getStackTrace().length; i++) {
            result[i] = exc.getStackTrace()[i].toString();
        }
        return result;
    }

    /**
     * Throws the exception.
     */
    public void _throw() throws Throwable {
        throw exc;
    }
}
