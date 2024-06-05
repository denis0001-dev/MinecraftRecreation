package net.minecraftrecreation.world.block;

import net.minecraftrecreation.world.block.base.Block;

public class Stone extends Block {

    @Override
    public String id() {
        return "stone";
    }

    @Override
    public String texturePath() {
        return "resources/models/stone.png";
    }
}
