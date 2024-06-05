package net.minecraftrecreation.world;

import java.io.Serial;
import java.io.Serializable;

public final class Worlds implements Serializable {
    @Serial
    private static final long serialVersionUID = -6564465923959850725L;

    public static World OVERWORLD = new World("overworld");
}
