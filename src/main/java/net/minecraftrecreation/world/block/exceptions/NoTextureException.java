package net.minecraftrecreation.world.block.exceptions;

import net.minecraftrecreation.world.block.base.Block;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class NoTextureException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1891489831069084028L;

    public NoTextureException(@NotNull Block block) {
        super("Block '%s' doesn't have a texture".formatted(block.id()));
    }
}
