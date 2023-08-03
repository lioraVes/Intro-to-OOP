package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Ball class, extends GameObject.
 */
public class Ball extends GameObject {
    /**
     * private fields -the collision sound, and collisionCount that counts the number of collisions the
     * ball had.
     */
    private final Sound sound;
    private final Counter collisionCount;

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param sound         The sound file object of the ball's collision.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound sound) {
        super(topLeftCorner, dimensions, renderable);
        this.sound = sound;
        this.collisionCount = new Counter(0);
    }

    /**
     * When the ball collides with another object (brick or border), it flips its direction and plays the
     * collision sound.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        sound.play();
        this.collisionCount.increment();
    }

    /**
     * This method returns the collisionCount field.
     *
     * @return collisionCount-the number of collisions the ball had.
     */
    public Counter getCollisionCount() {
        return this.collisionCount;
    }

    /**
     * This method resets the collisionCount Counter to zero.
     */
    public void resetCollisionCount() {
        this.collisionCount.reset();
    }
}
