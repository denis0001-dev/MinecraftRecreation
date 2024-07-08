package net.minecraftrecreation.render.camera;

import net.minecraftrecreation.render.gui.GUIRender;
import net.minecraftrecreation.render.scene.Scene;
import net.minecraftrecreation.render.scene.SceneRender;
import net.minecraftrecreation.render.scene.SkyboxRender;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL;
import net.minecraftrecreation.client.Window;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private final SceneRender sceneRender;
    private final GUIRender guiRender;
    private final SkyboxRender skyboxRender;

    public Renderer(Window window) {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        sceneRender = new SceneRender();
        guiRender = new GUIRender(window);
        skyboxRender = new SkyboxRender();
    }

    public void cleanup() {
        sceneRender.cleanup();
        guiRender.cleanup();
        skyboxRender.cleanup();
    }

    public void render(@NotNull Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getWidth(), window.getHeight());

        skyboxRender.render(scene);
        sceneRender.render(scene);
        guiRender.render(scene);
    }

    public void resize(int width, int height) {
        guiRender.resize(width, height);
    }
}