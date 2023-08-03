package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * GraphicLifeCounter class, extends GameObject- represent graphically the lives in the game.
 */
public class GraphicLifeCounter extends GameObject {
    /**
     * it has a constant field: hearts layers-the layer number the hearts belong to.
     * the private fields: livesCounter- the lives counter, numOfLives- the number of hearts on the
     * screen and gameObjectsCollection.
     */
    private static final int HEARTS_LAYER = 1;
    private final Counter livesCounter;
    private int numOfLives;
    private final GameObjectCollection gameObjectsCollection;

    /**
     * Construct a new GraphicLifeCounter instance.
     *
     * @param widgetTopLeftCorner   Position of the object, in window coordinates (pixels).
     *                              Note that (0,0) is the top-left corner of the window.- the top left
     *                              corner of the left most heart
     * @param widgetDimensions      Width and height in window coordinates.
     * @param livesCounter          the counter which holds current lives count.
     * @param widgetRenderable      The renderable representing the object. Can be null, in which case
     *                              the GameObject will not be rendered.
     * @param gameObjectsCollection the collection of all game objects currently in the game
     * @param numOfLives            number of current lives
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions,
                              Counter livesCounter, Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection, int numOfLives) {
        super(widgetTopLeftCorner, Vector2.ZERO, widgetRenderable);

        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;

        for (int i = 0; i < numOfLives; i++) {
            Vector2 curXWidget = new Vector2(i * widgetDimensions.x(), 0);
            GameObject heart = new GameObject(widgetTopLeftCorner.add(curXWidget),
                    widgetDimensions, widgetRenderable);
            gameObjectsCollection.addGameObject(heart, HEARTS_LAYER);
        }
    }

    /**
     * This method removes hearts from the screen if there are more hearts than there are lives left
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
        if (livesCounter.value() < numOfLives) {
            if (gameObjectsCollection.isLayerEmpty(HEARTS_LAYER)) {
                return;
            }
            //find the last heart
            GameObject lastHeart = null;
            for (GameObject obj : gameObjectsCollection.objectsInLayer(HEARTS_LAYER)) {
                lastHeart = obj;
            }
            gameObjectsCollection.removeGameObject(lastHeart, HEARTS_LAYER);
            numOfLives--;
        }
    }
}

