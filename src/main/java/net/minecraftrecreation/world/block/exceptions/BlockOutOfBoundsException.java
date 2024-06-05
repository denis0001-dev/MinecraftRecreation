package net.minecraftrecreation.world.block.exceptions;

import net.minecraftrecreation.world.BlockMap;
import net.minecraftrecreation.world.Location;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;

public class BlockOutOfBoundsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3455274353367249199L;

    public BlockOutOfBoundsException(@NotNull BlockMap world, @NotNull Location loc) {
        super(String.format("Block out of bounds in block map %s at %s", world, loc));
    }

}
