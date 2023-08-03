package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Heart class extends GameObject.
 */
public class Heart extends GameObject {
    /**
     * private field- gameObjects.
     */
    private final GameObjectCollection gameObjects;

    /**
     * Construct a new Heart instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param gameObjects   An object which holds all game objects of the game running.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjects = gameObjects;
    }

    /**
     * it can collide only with the original paddle(not the botGoodPaddle).
     *
     * @param other The other GameObject.
     * @return true if the objects should collide.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Paddle && !(other instanceof BotGoodPaddle));
    }

    /**
     * When the heart collides with the paddle it increments the lives by 1 (max life are 4) and then
     * is removed from the game.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        if (other instanceof Paddle && !(other instanceof BotGoodPaddle)) {
            for (GameObject gameObject : gameObjects) {
                if (gameObject instanceof NumericLifeCounter) {
                    ((NumericLifeCounter) gameObject).addLife();
                }
            }
        }
        gameObjects.removeGameObject(this);
    }

    /**
     * If the heart ran out of the screen it is removed from the game.
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
        float heartHeight = this.getCenter().y();
        if (heartHeight > src.BrickerGameManager.getWindowDimensions().y()) {
            gameObjects.removeGameObject(this);
        }
    }
}

