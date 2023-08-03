package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.CollisionStrategy;

/**
 * Brick class, extends GameObject.
 */
public class Brick extends GameObject {
    /**
     * it has the private fields- collisionStrategy, counter- the bricks counter.
     */
    private final CollisionStrategy collisionStrategy;
    private final Counter counter;

    /**
     * Construct a new Brick instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels),
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param strategy      The strategy that will be used when the brick breaks.
     * @param counter       the number of bricks
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy strategy,
                 Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = strategy;
        this.counter = counter;
    }

    /**
     * When the game detects a collision between the two objects, it activates the strategy of the brick.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, counter);
    }
}
