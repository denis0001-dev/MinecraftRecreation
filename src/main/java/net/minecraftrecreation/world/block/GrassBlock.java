package net.minecraftrecreation.world.block;

import net.minecraftrecreation.world.block.base.Block;

public class GrassBlock extends Block {
    public static final String texturePath = "resources/models/cube/cube.png";
    public static final String id = "grass_block";

    @Override
    public String id() {
        return id;
    }

    @Override
    public String texturePath() {
        return texturePath;
    }
}
