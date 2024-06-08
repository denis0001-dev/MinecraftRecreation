package ru.morozovit.util.io;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.function.Consumer;

import static ru.morozovit.util.io.ByteUtils.bytesToStr;
import static ru.morozovit.util.io.ByteUtils.convertBytesToPrimitiveType;

public class BufferedOutputStream extends OutputStream {
    private final ArrayList<Byte> buffer = new ArrayList<>();
    private final char linebreak;
    private final Consumer<String> onFlush;

    public BufferedOutputStream(char linebreak, Consumer<String> onFlush) {
        this.linebreak = linebreak;
        this.onFlush = onFlush;
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
        buffer.add((byte) b);

        String byteString = bytesToStr(convertBytesToPrimitiveType(buffer.toArray(new Byte[0])));

        if (byteString.contains(String.valueOf(linebreak))) {
            onFlush.accept(byteString.replaceAll(String.valueOf(linebreak), ""));
            buffer.clear();
        }
    }



}