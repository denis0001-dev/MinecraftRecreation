package net.minecraftrecreation.world.block.properties;

import java.io.Serializable;

public interface Property<T> extends Serializable {
    String id();

    T defaultValue();

    void setValue(T value);

    T getValue();

    default void unset() {
        setValue(defaultValue());
    }

    String toString();
}