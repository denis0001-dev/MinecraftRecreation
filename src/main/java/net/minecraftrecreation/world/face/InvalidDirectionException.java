package net.minecraftrecreation.world.face;

import java.io.Serial;
import java.io.Serializable;

public class InvalidDirectionException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = -4449629255504286645L;

    public InvalidDirectionException(Direction direction) {
        super(String.format("Invalid direction: %s", direction));
    }
}
