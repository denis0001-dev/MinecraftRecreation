package net.minecraftrecreation.world;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;

@Deprecated(forRemoval = true)
public class FullWorldException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1596892836160935595L;


    public FullWorldException(@NotNull BlockMap blockMap) {
        super("Block map is full. Capacity: unknown");
    }
}
