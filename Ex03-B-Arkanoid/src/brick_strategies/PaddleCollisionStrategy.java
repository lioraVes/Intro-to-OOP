package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.BotGoodPaddle;

import static src.BrickerGameManager.BORDER_WIDTH;

/**
 * PaddleCollisionStrategy - represent the strategy of additional paddle. When the ball hits the brick it
 * creates a new paddle in the middle of the screen that disappears after 3 collisions.
 */
class PaddleCollisionStrategy implements CollisionStrategy {
    /**
     * it has the constant field-BOT_GOOD_PADDLE_PATH the path for the paddle image.
     */
    private static final String BOT_GOOD_PADDLE_PATH = "assets/botGood.png";
    /**
     * it has the private fields- gameObjects, inputListener, imageReader and windowController.
     */
    private final GameObjectCollection gameObjects;
    private final UserInputListener inputListener;
    private final ImageReader imageReader;
    private final WindowController windowController;

    /**
     * constructor for PaddleCollisionStrategy.
     *
     * @param gameObjects      An object which holds all game objects of the game running.
     * @param inputListener    The input listener which waits for user inputs and acts on them.
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param windowController Contains an array of helpful, self-explanatory methods concerning the window.
     */
    public PaddleCollisionStrategy(GameObjectCollection gameObjects,
                                   UserInputListener inputListener, ImageReader imageReader,
                                   WindowController windowController) {
        this.gameObjects = gameObjects;
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.windowController = windowController;
    }

    /**
     * When a brick detects a collision, this method is called and activates the current strategy.
     * It decrements the number of active bricks on the screen and removes the current brick from the screen.
     * In addition, this method creates another paddle-a botGoodPaddle in the middle of the screen (if it is
     * not on the screen already).
     *
     * @param collidedObj   the collided object
     * @param colliderObj   the collider object.
     * @param bricksCounter bricks counter.
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        if (gameObjects.removeGameObject(collidedObj)) {
            bricksCounter.decrement();
        }
        //check if botGoodPaddle in game:
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof BotGoodPaddle) {
                return;
            }
        }
        // create botGoodPaddle object,set its position and add to the game
        Renderable botGoodPaddleImage = imageReader.readImage(BOT_GOOD_PADDLE_PATH, true);
        Vector2 paddleDimensions = src.BrickerGameManager.getPaddleDimensions();
        BotGoodPaddle botGoodPaddle = new BotGoodPaddle(Vector2.ZERO, paddleDimensions,
                botGoodPaddleImage, inputListener, windowController.getWindowDimensions(),
                (int) BORDER_WIDTH, gameObjects);
        botGoodPaddle.setCenter(windowController.getWindowDimensions().mult(0.5f));
        gameObjects.addGameObject(botGoodPaddle);
    }
}
