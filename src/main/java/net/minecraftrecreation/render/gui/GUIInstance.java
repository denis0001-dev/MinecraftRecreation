package net.minecraftrecreation.render.gui;

import net.minecraftrecreation.client.Window;
import net.minecraftrecreation.render.scene.Scene;

public interface GUIInstance {
    void drawGUI();

    boolean handleGUIInput(Scene scene, Window window);
}
