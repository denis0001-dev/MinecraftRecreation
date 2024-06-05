package net.minecraftrecreation.world;

import net.minecraftrecreation.render.scene.Scene;
import net.minecraftrecreation.render.scene.objects.Material;
import net.minecraftrecreation.render.scene.objects.Mesh;
import net.minecraftrecreation.render.scene.objects.Model;
import net.minecraftrecreation.render.scene.objects.Texture;
import net.minecraftrecreation.world.block.base.Block;
import net.minecraftrecreation.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.morozovit.util.ExcParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.minecraftrecreation.client.CrashHandler.crash;
import static net.minecraftrecreation.client.Main.logger;
import static net.minecraftrecreation.client.Main.postExit;
import static net.minecraftrecreation.world.block.base.BlockRegistry.AIR;
import static net.minecraftrecreation.world.block.base.BlockRegistry.GRASS_BLOCK;
import static ru.morozovit.logging.Loglevel.*;

public class World implements Serializable, Cloneable {
    @Serial
    private static final long serialVersionUID = 2931917692691957366L;

    public BlockMap blockMap = null;

    // TODO remove transient keyword
    public transient ArrayList<Entity> entities = new ArrayList<>();
    private transient Scene scene = null;

    private final String name;

    public World(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    public void save(String filename) {
        // TODO save entities

        logger.log(INFO, "Saving world '%s' to '%s'".formatted(this.name, filename));
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(filename))
        ) {
            outputStream.writeObject(this);
        } catch (Exception e) {
            logger.log(ERROR, "Couldn't save %s to %s.\n%s".formatted(
                    this.name,
                    filename,
                    new ExcParser(e)
            ));
        }
    }

    public BlockMap defaultBlockMap() throws CloneNotSupportedException {
        World world = (World) this.clone();
        world.blockMap = new BlockMap(
                new Location(128, -10, 128),
                new Location(-128, 10,-128)
        );

        world.setBlockNoRender(GRASS_BLOCK, new Location(0,0,0));
        world.setBlockNoRender(GRASS_BLOCK, new Location(0,0,1));
        world.setBlockNoRender(GRASS_BLOCK, new Location(1,0,0));
        world.setBlockNoRender(GRASS_BLOCK, new Location(1,0,1));
        return world.blockMap;
    }

    private void loadBlockMap(BlockMap map) {
        for (Map.Entry<Location, Block> entry: map.getBlocks().entrySet()) {
            setBlockNoSave(entry.getValue(), entry.getKey());
        }
    }

    public void load(Scene scene, String filename) {
        try {
            if (this.scene != null) {
                throw new IllegalStateException("World is already loaded");
            }

            this.scene = scene;
//            this.blockMap = new BlockMap(
//                    new Location(128, -10, 128),
//                    new Location(-128, 10,-128)
//            );
//
//            this.setBlock(GRASS_BLOCK,new Location(OVERWORLD,0,0,-4));
//            this.setBlock(GRASS_BLOCK,new Location(OVERWORLD,0,0,-5));
//            this.setBlock(GRASS_BLOCK,new Location(OVERWORLD,1,0,-4));
//            this.setBlock(GRASS_BLOCK,new Location(OVERWORLD,1,0,-5));
//
//            Vector3f pos = scene.getCamera().getPosition();

//            logger.log(INFO, pos.toString());
//
//            Property<Integer> property = new NumberProperty("test",1);
//            logger.log(INFO, property.toString());

            logger.log(INFO, "Loading world '%s' from '%s'".formatted(this.name, filename));
            try (ObjectInputStream inputStream =
                         new ObjectInputStream(new FileInputStream(filename))
            ) {
                World world = (World) inputStream.readObject();
                this.blockMap = world.blockMap;
                // TODO load entities

            } catch (Exception e) {
                logger.log(WARN, "Couldn't load '%s', creating a new one\n%s".formatted(this.name, new ExcParser(e)));
                this.blockMap = defaultBlockMap();
            } finally {
                loadBlockMap(blockMap);
            }
        } catch (Exception e) {
            crash("Couldn't load %s.\n%s".formatted(name, new ExcParser(e)), e);
            postExit(false);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private int lastIndex(@NotNull List<?> list) {
        return list.size() - 1;
    }

    private void setBlockNoSave(Block block, Location location) {
        if (block == AIR) return;

        Texture texture = this.scene.getTextureCache().createTexture(block.texturePath());
        Material material = new Material();
        material.setTexturePath(texture.getTexturePath());
        List<Material> materialList = new ArrayList<>();
        materialList.add(material);

        Mesh mesh = new Mesh(block.positions(), block.textCoords(), block.indices());
        material.getMeshList().add(mesh);

        String modelName = block.id()+"-model"+lastIndex(entities);
        String entityName = block.id()+"-entity"+lastIndex(entities);

        Model blockModel = new Model(modelName, materialList);
        this.scene.addModel(blockModel);

        Entity entity = new Entity(entityName, blockModel.getId());

        entity.setPosition(location.x(), location.y(), location.z());
        this.scene.addEntity(entity);

        entities.add(entity);
    }

    private void setBlockNoRender(Block block, Location location) {
        try {
            blockMap.addBlock(location, block);
        } catch (Exception e) {
            logger.log(WARN, "Unable to save %s at X: %d, Y: %d, Z: %d. Skipping it.\n%s".formatted(
                    block.getClass().getSimpleName(),
                    (int) location.x(),
                    (int) location.y(),
                    (int) location.z(),
                    new ExcParser(e)
            ));
        }
    }

    public void setBlock(@NotNull Block block, @NotNull Location location) {
        setBlockNoSave(block, location);
        setBlockNoRender(block, location);
    }

    @SuppressWarnings("unused")
    public void update(Scene scene) {
        for (Entity entity : entities) {
            entity.updateModelMatrix();
        }
    }
}