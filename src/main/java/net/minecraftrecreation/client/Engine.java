package net.minecraftrecreation.client;

import net.minecraftrecreation.render.camera.Renderer;
import net.minecraftrecreation.render.scene.Scene;
import org.jetbrains.annotations.NotNull;
import ru.morozovit.util.ExcParser;

import static net.minecraftrecreation.client.Main.logger;
import static ru.morozovit.logging.Loglevel.WARN;

public class Engine {

    public static final int TARGET_UPS = 30;
    private final IAppLogic appLogic;
    private final Window window;
    private final Renderer renderer;
    private boolean running;
    private final Scene scene;
    private final int targetFps;
    private final int targetUps;

    public Engine(String windowTitle, Window.WindowOptions opts, @NotNull IAppLogic appLogic) {
        window = new Window(windowTitle, opts, () -> {
            resize();
            return null;
        });
        targetFps = opts.fps;
        targetUps = opts.ups;
        this.appLogic = appLogic;
        renderer = new Renderer();
        scene = new Scene(window.getWidth(), window.getHeight());
        appLogic.init(window, scene, renderer);
        running = true;
    }

    private void cleanup() {
        appLogic.cleanup();
        renderer.cleanup();
        scene.cleanup();
        window.cleanup();
    }

    private void resize() {
        scene.resize(window.getWidth(), window.getHeight());
    }

    private void run() {
        long initialTime = System.currentTimeMillis();
        float timeU = 1000.0f / targetUps;
        float timeR = targetFps > 0 ? 1000.0f / targetFps : 0;
        float deltaUpdate = 0;
        float deltaFps = 0;

        long updateTime = initialTime;
        while (running && !window.windowShouldClose()) {
            window.pollEvents();

            long now = System.currentTimeMillis();
            deltaUpdate += (now - initialTime) / timeU;
            deltaFps += (now - initialTime) / timeR;

            if (targetFps <= 0 || deltaFps >= 1) {
                window.getMouseInput().input();
                appLogic.input(window, scene, now - initialTime);
            }

            if (deltaUpdate >= 1) {
                long diffTimeMillis = now - updateTime;
                appLogic.update(window, scene, diffTimeMillis);
                updateTime = now;
                deltaUpdate--;
            }

            if (targetFps <= 0 || deltaFps >= 1) {
                renderer.render(window, scene);
                deltaFps--;
                window.update();
            }
            initialTime = now;
        }

        try {
            window.getCloseCallback().call();
        } catch (Exception e) {
            logger.log(WARN, "There was an error while calling the close callback.\n"+new ExcParser(e));
        }

        cleanup();
    }

    public void start() {
        running = true;
        run();
    }

    public void stop() {
        running = false;
    }

}
