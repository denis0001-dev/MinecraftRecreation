package net.minecraftrecreation.render.scene;

import net.minecraftrecreation.render.scene.objects.Model;
import net.minecraftrecreation.render.scene.objects.ModelLoader;
import net.minecraftrecreation.render.scene.objects.TextureCache;
import net.minecraftrecreation.world.entity.Entity;

public class Skybox {
    private Entity entity;
    private Model model;

    public Skybox(String skyBoxModelPath, TextureCache textureCache) {
        model = ModelLoader.loadModel("skybox-model", skyBoxModelPath, textureCache);
        entity = new Entity("skybox-entity", model.getId());
    }

    public Entity getEntity() {
        return entity;
    }

    public Model getModel() {
        return model;
    }
}
