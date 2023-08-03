package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * GraphicLifeCounter class, extends GameObject- represent graphically the lives in the game.
 */
public class GraphicLifeCounter extends GameObject {
    /**
     * it has a constant field: hearts layers-the layer number the hearts belong to.
     */
    private static final int HEARTS_LAYER = 1;
    /**
     * private fields:
     * livesCounter- the lives counter,
     * numOfLives- the number of hearts on the screen,
     * widgetRenderable- the renderable of the heart.
     * leftWidgetTopLeftCorner-the top left corner of the left heart.
     * gameObjectsCollection- the game-objects collection.
     * heartDimensions- the dimensions of the heart.
     */
    private final Counter livesCounter;
    private int numOfLives;
    private final Renderable widgetRenderable;
    private final Vector2 leftWidgetTopLeftCorner;
    private final GameObjectCollection gameObjectsCollection;
    private final Vector2 heartDimensions;


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
        this.widgetRenderable = widgetRenderable;
        this.leftWidgetTopLeftCorner = widgetTopLeftCorner;
        this.heartDimensions = src.BrickerGameManager.getHeartDimensions();
        //creates numOfLives hearts
        for (int i = 0; i < numOfLives; i++) {
            Vector2 curXWidget = new Vector2(i * widgetDimensions.x(), 0);
            addHeart(curXWidget);
        }
    }

    /**
     * This method removes hearts from the screen if there are more hearts than there are lives left.
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
        //if we need to add a life
        else if (livesCounter.value() > numOfLives) {
            Vector2 curXWidget = new Vector2(numOfLives * heartDimensions.x(), 0);
            addHeart(curXWidget);
            numOfLives++;
        }
    }

    /**
     * this method creates a Heart object and adds it ot the game.
     *
     * @param curXWidget the Left Top corner of the new heart (in x)
     */
    private void addHeart(Vector2 curXWidget) {
        Heart heart = new Heart(leftWidgetTopLeftCorner.add(curXWidget),
                heartDimensions, widgetRenderable, gameObjectsCollection);
        heart.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjectsCollection.addGameObject(heart, HEARTS_LAYER);

    }
}
