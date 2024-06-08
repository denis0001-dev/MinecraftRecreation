package net.minecraftrecreation.world.block.base;

import java.io.Serial;
import java.io.Serializable;

public abstract class Block implements IBlock, Serializable {
    @Serial
    private static final long serialVersionUID = -7480659516970578007L;

    protected Block() {

    }

    public abstract String id();
}
