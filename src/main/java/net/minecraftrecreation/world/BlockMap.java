package net.minecraftrecreation.world;

import net.minecraftrecreation.world.block.exceptions.BlockOutOfBoundsException;
import net.minecraftrecreation.world.block.base.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.minecraftrecreation.world.block.Blocks.AIR;

public class BlockMap implements Serializable {
    @Serial
    private static final long serialVersionUID = 7062961107903423761L;


    public final float startX;
    public final float startY;
    public final float startZ;

    public final float endX;
    public final float endY;
    public final float endZ;

    // TODO fixed world size
//    public final long capacity;
//
//
//    public int[] getCubeDimensions(@NotNull Location start, @NotNull Location end) {
//        int width = -1;
//        int height = -1;
//        int length = -1;
//
//        if (Math.abs(start.y()) > Math.abs(end.y())) {
//            width = (int) (Math.abs(end.x()) - Math.abs(start.x()));
//            height = (int) (Math.abs(end.y()) - Math.abs(start.y()));
//            length = (int) (Math.abs(end.z()) - Math.abs(start.z()));
//        }
//        else if (Math.abs(start.y()) == Math.abs(end.y())) {
//            width = (int) (Math.abs(start.x()) - Math.abs(end.x()));
//            height = (int) Math.abs(start.y());
//            length = (int) (Math.abs(start.z()) - Math.abs(end.z()));
//        }
//        else if (Math.abs(start.y()) < Math.abs(end.y())) {
//            width = (int) (Math.abs(end.x()) - Math.abs(start.x()));
//            height = (int) (Math.abs(start.y()) - Math.abs(end.y()));
//            length = (int) (Math.abs(end.z()) - Math.abs(start.z()));
//        }
//
//        if (width == -1 || height == -1 || length == -1) {
//            throw new ArithmeticException("Couldn't calculate cube dimensions");
//        }
//
//        return new int[] {width, height, length};
//    }

    public BlockMap(@NotNull Location start, @NotNull Location end) {
        this.startX = start.x();
        this.startY = start.y();
        this.startZ = start.z();
        this.endX = end.x();
        this.endY = end.y();
        this.endZ = end.z();

//        int[] dimensions = getCubeDimensions(start, end);
//
//        this.capacity = (long) dimensions[0] * dimensions[1] * dimensions[2];
//        if (this.capacity < 0) {
//            throw new NegativeCapacityException(capacity);
//        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isInBounds(@NotNull Location loc) {
        // TODO fix
//        return loc.x() >= startX && loc.x() <= endX &&
//                loc.y() >= startY && loc.y() <= endY &&
//                loc.z() >= startZ && loc.z() <= endZ;
        return true;
    }

    public final Map<Location, Block> blocks = new HashMap<>();

    public void addBlock(Location loc, Block b) {
//        if (blocks.size() >= capacity) {
//            throw new FullWorldException(this);
//        }
        if (!isInBounds(loc)) {
            throw new BlockOutOfBoundsException(this, loc);
        }
        blocks.put(loc, b);
    }

    public @Nullable Block getBlock(Location location) {
        return Objects.requireNonNullElse(blocks.get(location), AIR);
    }

    public void removeBlock(Location location) {
        try {
            blocks.remove(location);
        } catch (Exception _) {}
    }

    public void clear() {
        blocks.clear();
    }

    public Map<Location,Block> getBlocks() {
        return blocks;
    }

    public void addAll(@NotNull Map<Location,Block> blocks) {
//        if (blocks.size() + this.blocks.size() >= capacity) {
//            throw new FullWorldException(this);
//        }
        for (Map.Entry<Location,Block> entry : blocks.entrySet()) {
            if (!isInBounds(entry.getKey())) {
                throw new BlockOutOfBoundsException(this, entry.getKey());
            }
            this.blocks.put(entry.getKey(), entry.getValue());
        }
    }
}
