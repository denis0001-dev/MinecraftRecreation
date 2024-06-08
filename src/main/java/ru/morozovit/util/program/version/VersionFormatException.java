package ru.morozovit.util.program.version;

import java.io.Serial;
import java.io.Serializable;

public class VersionFormatException extends NumberFormatException implements Serializable {
    @Serial
    private static final long serialVersionUID = 8575592825389102214L;

    public VersionFormatException() {
        super();
    }

    public VersionFormatException(String ver) {
        super("[%s] is not a valid version!".formatted(ver));
    }
}
