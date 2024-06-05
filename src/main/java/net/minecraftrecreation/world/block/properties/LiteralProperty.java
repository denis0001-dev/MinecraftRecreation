package net.minecraftrecreation.world.block.properties;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

public class LiteralProperty implements Property<String>, Serializable {
    @Serial
    private static final long serialVersionUID = 434200708933833907L;

    private final String id;
    private String value;
    private final String defaultValue;
    private final String[] values;

    public LiteralProperty(String name, String defaultVal, String[] values) {
        this.id = name;
        if (!Arrays.stream(values).toList().contains(defaultVal)) {
            throw new IllegalArgumentException(defaultVal + " is not in values");
        }
        this.value = defaultVal;
        this.defaultValue = defaultVal;
        this.values = values;
    }

    public LiteralProperty(String name, String defaultVal, String[] values, String initialValue) {
        this(name, defaultVal,values);
        if (!Arrays.stream(values).toList().contains(initialValue)) {
            throw new IllegalArgumentException(initialValue + " is not in values");
        }
        this.value = initialValue;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String defaultValue() {
        return defaultValue;
    }

    @Override
    public void setValue(@NotNull String value) {
        if (!Arrays.stream(values).toList().contains(value)) {
            throw new IllegalArgumentException(value + " is not in values");
        }
        this.value = value;
    }

    @Override
    public @NotNull String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "%s: %s".formatted(id, value);
    }
}
