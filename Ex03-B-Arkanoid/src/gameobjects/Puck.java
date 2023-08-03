package src.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Puck class extends Ball.
 */
public class Puck extends Ball {
    /**
     * private field- gameObjects.
     */
    private GameObjectCollection gameObjects;

    /**
     * Construct a new Puck instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param sound         The sound file object of the ball's collision.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound sound) {
        super(topLeftCorner, dimensions, renderable, sound);
        this.gameObjects = null;
    }

    /**
     * this method sets the gameObjects for the puck. is called from PucksCollisionStrategy.
     *
     * @param gameObjects An object which holds all game objects of the game running.
     */
    public void setGameObjects(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * If the Puck ran out of the screen it is removed from the game.
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
        if (gameObjects != null) {
            float puckHeight = this.getCenter().y();
            if (puckHeight > src.BrickerGameManager.getWindowDimensions().y()) {
                gameObjects.removeGameObject(this);
            }
        }
    }
}
