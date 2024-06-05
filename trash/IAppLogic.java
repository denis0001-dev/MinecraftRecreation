package net.minecraftrecreation;

import net.minecraftrecreation.client.Window;
import net.minecraftrecreation.render.Scene;
import net.minecraftrecreation.render.Renderer;

public interface IAppLogic {
    void cleanup();

    void init(Window window, Scene scene, Renderer render);

    void input(Window window, Scene scene, long diffTimeMills);

    void update(Window window, Scene scene, long diffTimeMills);
}
