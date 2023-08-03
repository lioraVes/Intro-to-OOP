package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * NumericLifeCounter,extends GameObject- represents the number of lives.
 */
public class NumericLifeCounter extends GameObject {
    /**
     * it has a constant field- MAX_LIVES_NUM.
     */
    private final static int MAX_LIVES_NUM = 4;
    /**
     * private fields:
     * livesCounter- the lives counter
     * textRenderable.
     */
    private final Counter livesCounter;
    private final TextRenderable textRenderable;

    /**
     * Construct a new NumericLifeCounter instance.
     *
     * @param livesCounter         The counter of how many lives are left right now.
     * @param topLeftCorner        Position of the object, in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param dimensions           Width and height in window coordinates.
     * @param gameObjectCollection the collection of all game objects currently in the game
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        textRenderable = new TextRenderable(String.valueOf(livesCounter.value()));
        this.renderer().setRenderable(textRenderable);
        textRenderable.setColor(Color.GREEN);
        this.livesCounter = livesCounter;
    }

    /**
     * This method sets the string value of the text object to the number of current lives left.
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        textRenderable.setString(String.valueOf(livesCounter.value()));
        if (livesCounter.value() == 2) {
            textRenderable.setColor(Color.YELLOW);
        }
        if (livesCounter.value() == 1) {
            textRenderable.setColor(Color.red);
        }
        if (livesCounter.value() == 3 || livesCounter.value() == MAX_LIVES_NUM) {
            textRenderable.setColor(Color.GREEN);
        }

    }

    /**
     * add life to livesCounter- not more than MAX_LIVES_NUM(4). This method is called when the paddle
     * collided with a falling heart (from a brick with HeartCollisionStrategy).
     */
    public void addLife() {
        if (livesCounter.value() >= MAX_LIVES_NUM) {
            return;
        }
        livesCounter.increment();
    }
}


