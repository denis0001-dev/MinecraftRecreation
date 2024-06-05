package net.minecraftrecreation.world;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;

public class Location implements Serializable {
    @Serial
    private static final long serialVersionUID = -6306796589830172677L;

    public static final Location DEFAULT = new Location(0,0,0);

    public static final float DEFAULT_X = 0;
    public static final float DEFAULT_Y = 0;
    public static final float DEFAULT_Z = 0;

    private final @Nullable World world;
    private final float x;
    private final float y;
    private final float z;

    public Location() {
        this(DEFAULT_X, DEFAULT_Y, DEFAULT_Z);
    }

    public Location(@Nullable World world) {
        this(world, DEFAULT_X, DEFAULT_Y, DEFAULT_Z);
    }

    public Location(@Nullable World world, @NotNull Location location) {
        this(world, location.x(), location.y(), location.z());
    }

    public Location(@Nullable World world, float x, float y, float z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(float x, float y, float z) {
        this.world = null;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public @Nullable World world() {
        return world;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    @Override
    public String toString() {
        return "%f %f %f".formatted(x, y, z);
    }
}