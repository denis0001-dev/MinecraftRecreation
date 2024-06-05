package net.minecraftrecreation.world.block.properties;

import java.io.Serial;
import java.io.Serializable;

public class BooleanProperty implements Property<Boolean>, Serializable {
    @Serial
    private static final long serialVersionUID = -7081584565660654476L;

    private final String id;
    private boolean value;
    private final boolean defaultValue;

    public BooleanProperty(String name, boolean defaultVal) {
        this.id = name;
        this.value = defaultVal;
        this.defaultValue = defaultVal;
    }

    public BooleanProperty(String name, boolean defaultVal, boolean initialValue) {
        this(name, defaultVal);
        this.value = initialValue;
    }

    public BooleanProperty(String name) {
        this(name, false);
    }


    @Override
    public String id() {
        return id;
    }

    @Override
    public Boolean defaultValue() {
        return defaultValue;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "%s: %s".formatted(id, value);
    }
}
