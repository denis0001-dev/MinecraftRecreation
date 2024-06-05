package net.minecraftrecreation.world.block.properties;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;

public class NumberProperty implements Property<Integer>, Serializable {
    @Serial
    private static final long serialVersionUID = 2567605664716788608L;

    private final String id;
    private Integer value;
    private final int maxValue;
    private final int minValue;
    private final int defaultValue;

    public NumberProperty(String name, int defaultVal, int minVal, int maxVal) {
        this.id = name;
        this.value = defaultVal;
        this.maxValue = maxVal;
        this.minValue = minVal;
        this.defaultValue = defaultVal;
    }

    public NumberProperty(String name, int defaultVal) {
        this(name, defaultVal, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public NumberProperty(String name, int defaultVal, int initialValue) {
        this(name, defaultVal, Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.value = initialValue;
    }

    public NumberProperty(String name, int defaultVal, int initialValue, int minValue, int maxValue) {
        this(name, defaultVal, minValue, maxValue);
        this.value = initialValue;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void setValue(@NotNull Integer value) {
        this.value = value;
    }

    @Override
    public @NotNull Integer getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public @Nullable Integer getDefaultValue() {
        return defaultValue;
    }

    @Override
    public @Nullable Integer defaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return "%s: %d".formatted(id, value);
    }
}
