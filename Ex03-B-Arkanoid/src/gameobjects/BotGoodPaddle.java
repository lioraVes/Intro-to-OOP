package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * BotGoodPaddle- extends Paddle. A paddle that disappears from the screen after 3 collisions.
 */
public class BotGoodPaddle extends Paddle {
    /**
     * constant filed MAX_COLLISION_NUM - the max number of collisions allowed-three.
     */
    private static final int MAX_COLLISION_NUM = 3;
    /**
     * private fields- gameObjects,countCollisions- the counter of the paddle collisions.
     */
    private final GameObjectCollection gameObjects;
    private final Counter countCollisions;

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
    public BotGoodPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                         UserInputListener inputListener, Vector2 windowDimensions, int minDistFromEdge,
                         GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistFromEdge);
        this.gameObjects = gameObjects;
        this.countCollisions = new Counter(0);
    }

    /**
     * Doesn't count the collisions with the walls as an actual collision-When colliding with the walls we
     * don't increment the counter of collisions.
     *
     * @param other The other GameObject.
     * @return true if the objects should collide.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return !(other instanceof Wall);
    }

    /**
     * When the paddle collides 3 times with other objects in the game it's removed from the game.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        countCollisions.increment();
        if (countCollisions.value() == MAX_COLLISION_NUM) {
            countCollisions.reset();
            gameObjects.removeGameObject(this);
        }
    }
}