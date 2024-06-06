package net.minecraftrecreation.render.camera;

import net.minecraftrecreation.render.scene.Scene;
import net.minecraftrecreation.render.scene.SceneRender;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL;
import net.minecraftrecreation.client.Window;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private final SceneRender sceneRender;

    public Renderer() {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        sceneRender = new SceneRender();
    }

    public void cleanup() {
        sceneRender.cleanup();
    }

    public void render(@NotNull Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getWidth(), window.getHeight());

        sceneRender.render(scene);
    }
}