package net.minecraftrecreation.world;

import java.io.Serial;
import java.io.Serializable;

@Deprecated(forRemoval = true)
public class NegativeCapacityException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = -114369375011132495L;

    public NegativeCapacityException(long capacity) {
        super(String.format("Negative capacity: %d", capacity));
    }
}
