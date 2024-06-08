package ru.morozovit.util.io;

import java.io.PrintStream;

public class CombinedPrintStreams extends PrintStream {
    private final PrintStream[] outputStreams;

    public CombinedPrintStreams(PrintStream... streams) {
        super(new NullOutputStream());

        this.outputStreams = streams;
    }

    /**
     * Writes the specified byte to this output stream. The general
     * contract for {@code write} is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument {@code b}. The 24
     * high-order bits of {@code b} are ignored.
     *
     * @param b the {@code byte}.
     */
    @Override
    public void write(int b) {
        for (PrintStream stream : outputStreams) {
            stream.write(b);
        }
    }
}

