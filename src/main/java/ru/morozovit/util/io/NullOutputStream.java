package ru.morozovit.util.io;

import java.io.OutputStream;

final class NullOutputStream extends OutputStream {
    @Override
    public void write(int b) {
    }
}
