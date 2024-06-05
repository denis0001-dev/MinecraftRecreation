package net.minecraftrecreation.world.block.properties;

import net.minecraftrecreation.world.face.Direction;

import java.io.Serial;
import java.io.Serializable;

public class DirectionProperty implements Property<Direction>, Serializable {
    @Serial
    private static final long serialVersionUID = -7081584565660654476L;

    private final String id;
    private Direction value;
    private final Direction defaultValue;

    public DirectionProperty(String name, Direction defaultValue) {
        this.id = name;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    public DirectionProperty(String name, Direction defaultValue, Direction initialValue) {
        this(name, defaultValue);
        this.value = initialValue;
    }

    @Deprecated
    public DirectionProperty(String name) {
        //noinspection DataFlowIssue
        this(name, null);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Direction defaultValue() {
        return defaultValue;
    }

    @Override
    public void setValue(Direction value) {
        this.value = value;
    }

    @Override
    public Direction getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "%s: %s".formatted(id, value.toString());
    }
}
