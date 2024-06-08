package net.minecraftrecreation.client;

import net.minecraftrecreation.render.scene.Scene;
import net.minecraftrecreation.render.camera.Renderer;

public sealed interface IAppLogic permits Main {
    void cleanup();

    void init(Window window, Scene scene, Renderer renderer);

    void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed);

    void update(Window window, Scene scene, long diffTimeMillis);
}