package net.minecraftrecreation.world.block.base;

import net.minecraftrecreation.world.block.exceptions.NoModelException;

import java.io.Serial;
import java.io.Serializable;

public abstract class TexturelessBlock extends Block implements Serializable {
    @Serial
    private static final long serialVersionUID = -1428295384720515800L;

    public abstract String id();

    public final String modelPath() {
        throw new NoModelException(this);
    }
}
