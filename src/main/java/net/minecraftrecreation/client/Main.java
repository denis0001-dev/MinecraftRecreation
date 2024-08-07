package net.minecraftrecreation.client;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiCond;
import net.minecraftrecreation.client.input.MouseInput;
import net.minecraftrecreation.render.camera.Camera;
import net.minecraftrecreation.render.camera.Renderer;
import net.minecraftrecreation.render.gui.GUIInstance;
import net.minecraftrecreation.render.scene.Scene;
import net.minecraftrecreation.world.Location;
import net.minecraftrecreation.world.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;
import ru.morozovit.logging.Logger;
import ru.morozovit.util.program.Arguments;

import static net.minecraftrecreation.client.AppInfo.*;
import static net.minecraftrecreation.client.CrashHandler.crash;
import static net.minecraftrecreation.world.Worlds.OVERWORLD;
import static net.minecraftrecreation.world.block.Blocks.STONE;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static ru.morozovit.logging.Loglevel.*;

public final class Main implements IAppLogic, GUIInstance {
    public Player player;

    // private final Vector4f displInc = new Vector4f();

    public String[] args = new String[]{};

    // private int counter;

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;
    // private final ArrayList<Entity> entities = new ArrayList<>();

    public static final Logger logger = new Logger("latest.log",true,true);

    public static void postExit(boolean isSuccessful) {
        logger.log(INFO,"Stopping!");
        System.exit(isSuccessful ? 0 : 1);
    }

    public static void main(String[] args) {
        logger.setDefaultStreamLoglevel(ERROR);
        System.setErr(logger.stream);

        Main main = new Main();
        main.args = args;
        try {
            Engine gameEng = new Engine(TITLE+" "+VERSION+" "+VERSION.getType(), new Window.WindowOptions(), main);
            gameEng.start();
        } catch (Exception e) {
            crash(e);
            postExit(false);
        }

        postExit(true);
    }

    @Override
    public void cleanup() {
        // Nothing to be done yet
    }

    @Override
    public void init(@NotNull Window window, @NotNull Scene scene, Renderer renderer) {
        logger.log(INFO,"Starting %s version %s %s".formatted(TITLE, VERSION, VERSION.getType()));
        Arguments args = new Arguments(this.args);
        logger.log(DEBUG,"Arguments: %s".formatted(args.toString()));

        String name = args.getArgument("name");

        logger.log(INFO,"Setting user: "+name);
        player = new Player(name);

        OVERWORLD.load(scene, "world."+WORLD_EXTENSION);

        window.setCloseCallback(() -> {
            OVERWORLD.save("world."+WORLD_EXTENSION);
            return null;
        });

        // the old way to place a block
//        Texture texture = scene.getTextureCache().createTexture("resources/models/cube/texture.png");
//        Material material = new Material();
//        material.setTexturePath(texture.getTexturePath());
//        List<Material> materialList = new ArrayList<>();
//        materialList.add(material);
//
//        Mesh mesh = new Mesh(positions, textCoords, indices);
//        material.getMeshList().add(mesh);
//        Model cubeModel = new Model("cube-model", materialList);
//        scene.addModel(cubeModel);
//
//        entities.add(new Entity("cube-entity", cubeModel.getId()));
//        entities.getFirst().setPosition(0, 0, -2);
//        scene.addEntity(entities.getFirst());

//        Skybox skybox = new Skybox("resources/models/skybox/skybox.obj", scene.getTextureCache());
//        skybox.getEntity().setScale(100000);
//        scene.setSkybox(skybox);

        glClearColor(0.66f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void input(@NotNull Window window, @NotNull Scene scene, long diffTimeMillis, boolean inputConsumed) {
        if (inputConsumed) {
            return;
        }

        int x = (int) scene.getCamera().getPosition().x;
        int y = (int) scene.getCamera().getPosition().y;
        int z = (int) scene.getCamera().getPosition().z;

        // counter++;
        float move = diffTimeMillis * MOVEMENT_SPEED;
        Camera camera = scene.getCamera();
        if (window.isKeyPressed(GLFW_KEY_W)) {
            // logger.log(DEBUG, "position: "+camera.getPosition());
            camera.moveForward(move);
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            camera.moveUp(move);
        } else if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveDown(move);
        }

        if (window.isKeyPressed(GLFW_KEY_R)) {
            if (!player.blockBreakCooldownActive()) {
                OVERWORLD.removeBlock(new Location(x,y,z));
                player.blockBreakCooldown();
            }
        }

        MouseInput mouseInput = window.getMouseInput();
        if (mouseInput.isRightButtonPressed()) {
            // TODO buttonless mouse input
//            glfwSetCursorPosCallback(window.getWindowHandle(), (e,er,o) -> {});
//            glfwSetCursorPos(window.getWindowHandle(), (double) window.getWidth() / 2, (double) window.getHeight() / 2);
//            glfwSetCursorPosCallback(window.getWindowHandle(), mouseInput.cursorMoveCallback);
            Vector2f displVec = mouseInput.getDisplVec();
            camera.addRotation((float) Math.toRadians(-displVec.x * MOUSE_SENSITIVITY),
                    (float) Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }
        if (mouseInput.isLeftButtonPressed()) {
            if (!player.blockPlaceCooldownActive()) {
                OVERWORLD.setBlock(STONE, new Location(x,y,z));
                player.blockPlaceCooldown();
            }
        }
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
//        for (Entity entity : entities) {
//            entity.updateModelMatrix();
//        }

        OVERWORLD.update(scene);
        player.update();
    }

    @Override
    public void drawGUI() {
        ImGui.newFrame();
        ImGui.setNextWindowPos(0,0, ImGuiCond.Always);
        ImGui.showDemoWindow();
        ImGui.endFrame();
        ImGui.render();
    }

    @Override
    public boolean handleGUIInput(Scene scene, @NotNull Window window) {
        ImGuiIO imGuiIO = ImGui.getIO();
        MouseInput mouseInput = window.getMouseInput();
        Vector2f mousePos = mouseInput.getCurrentPos();
        imGuiIO.setMousePos(mousePos.x, mousePos.y);
        imGuiIO.setMouseDown(0, mouseInput.isLeftButtonPressed());
        imGuiIO.setMouseDown(1, mouseInput.isRightButtonPressed());

        return imGuiIO.getWantCaptureMouse() || imGuiIO.getWantCaptureKeyboard();
    }
}
