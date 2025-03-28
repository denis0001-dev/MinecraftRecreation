package net.minecraftrecreation.client.input;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.*;

@SuppressWarnings("resource")
public class MouseInput {
    public final GLFWCursorPosCallbackI cursorMoveCallback;

    private final Vector2f currentPos;
    private final Vector2f displVec;
    private boolean inWindow;
    private boolean leftButtonPressed;
    private final Vector2f previousPos;
    private boolean rightButtonPressed;

    public MouseInput(long windowHandle) {
        previousPos = new Vector2f(-1, -1);
        currentPos = new Vector2f();
        displVec = new Vector2f();
        leftButtonPressed = false;
        rightButtonPressed = false;
        inWindow = false;

        cursorMoveCallback = (_, xpos, ypos) -> {
            currentPos.x = (float) xpos;
            currentPos.y = (float) ypos;
        };

        glfwSetCursorPosCallback(windowHandle, cursorMoveCallback);
        glfwSetCursorEnterCallback(windowHandle, (_, entered) -> inWindow = entered);
        glfwSetMouseButtonCallback(windowHandle, (_, button, action, _) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }

    public Vector2f getCurrentPos() {
        return currentPos;
    }

    public Vector2f getDisplVec() {
        return displVec;
    }

    public void input() {
        displVec.x = 0;
        displVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
}
