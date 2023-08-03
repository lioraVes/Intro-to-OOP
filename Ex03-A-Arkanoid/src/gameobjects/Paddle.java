package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Paddle class, extends GameObject.
 */
public class Paddle extends GameObject {
    /**
     * a Paddle has a public constant field- MOVE_SPEED, the speed of the paddle.
     * the private fields are inputListener, windowDimensions, minDistFromEdge.
     */

    public static final int MOVE_SPEED = 500;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int minDistFromEdge;

    /**
     * Construct a new Paddle instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param inputListener    The input listener which waits for user inputs and acts on them.
     * @param windowDimensions The dimensions of the screen, to know the limits for paddle movements.
     * @param minDistFromEdge  Minimum distance allowed for the paddle from the edge of the walls
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions, int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);

        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;
    }

    /**
     * If right and/or left key is recognised as pressed by the input listener, it moves the paddle,
     * and check that it doesn't move past the borders.
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDir = Vector2.ZERO;

        //get the cur coordinates of the paddle
        Vector2 curTopLeftCorner = getTopLeftCorner();
        Vector2 curTopRightCorner = getDimensions().add(curTopLeftCorner);

        if (curTopLeftCorner.x() >= minDistFromEdge && inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (curTopRightCorner.x() <= windowDimensions.x() - minDistFromEdge &&
                inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVE_SPEED));
    }
}
