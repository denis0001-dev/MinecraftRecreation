package net.minecraftrecreation.render.scene;

import net.minecraftrecreation.render.camera.Camera;
import net.minecraftrecreation.render.camera.Projection;
import net.minecraftrecreation.render.gui.GUIInstance;
import net.minecraftrecreation.render.scene.objects.Model;
import net.minecraftrecreation.render.scene.objects.TextureCache;
import net.minecraftrecreation.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Scene {
    private final Map<String, Model> modelMap;
    private final Projection projection;
    private final TextureCache textureCache;
    private final Camera camera;
    private Skybox skybox;

    private GUIInstance guiInstance;

    public Scene(int width, int height) {
        modelMap = new HashMap<>();
        projection = new Projection(width, height);
        textureCache = new TextureCache();
        camera = new Camera();
    }

    public void addEntity(@NotNull Entity entity) {
        String modelId = entity.getModelId();
        Model model = modelMap.get(modelId);
        if (model == null) {
            throw new RuntimeException("Could not find model [" + modelId + "]");
        }
        model.getEntitiesList().add(entity);
    }

    public void addModel(Model model) {
        modelMap.put(model.getId(), model);
    }

    public void cleanup() {
        modelMap.values().forEach(Model::cleanup);
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    public Projection getProjection() {
        return projection;
    }

    public TextureCache getTextureCache() {
        return textureCache;
    }

    public void resize(int width, int height) {
        projection.updateProjMatrix(width, height);
    }

    public Camera getCamera() {
        return camera;
    }

    public void removeEntity(@Nullable Entity entity) {
        if (entity != null) {
            modelMap.remove(entity.getModelId());
        }
    }

    public GUIInstance getGuiInstance() {
        return guiInstance;
    }

    public void setGuiInstance(GUIInstance guiInstance) {
        this.guiInstance = guiInstance;
    }

    public Skybox getSkybox() {
        return skybox;
    }

    public void setSkybox(Skybox skybox) {
        this.skybox = skybox;
    }
}
