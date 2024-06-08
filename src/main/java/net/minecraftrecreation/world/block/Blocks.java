package net.minecraftrecreation.world.block;

import net.minecraftrecreation.world.block.base.Block;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public final class Blocks {
    public static final Block GRASS_BLOCK = new GrassBlock();
    public static final Block AIR = new Air();
    public static final Block STONE = new Stone();

    public static Block @NotNull [] getBlocks() {
        Field[] blocks = Blocks.class.getDeclaredFields();

        Block[] blockArray = new Block[blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            try {
                blockArray[i] = (Block) blocks[i].get(null);
            } catch (IllegalAccessException _) {}
        }

        return blockArray;
    }
}