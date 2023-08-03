package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Heart;

/**
 * HeartCollisionStrategy - represent the strategy of live returning. When the ball hits the brick, a heart
 * falls from the brick down. If the paddle catches it a live is returned to the player (as long ad his life
 * is under 4).
 */
class HeartCollisionStrategy implements CollisionStrategy {
    /**
     * it has the constants-HEART_PATH,HEART_SPEED.
     */
    private static final String HEART_PATH = "assets/heart.png";
    private static final int HEART_SPEED = 100;
    /**
     * private fields- gameObjects, imageReader.
     */
    private final GameObjectCollection gameObjects;
    private final ImageReader imageReader;

    /**
     * HeartCollisionStrategy constructor.
     *
     * @param gameObjects An object which holds all game objects of the game running.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                    See its documentation for help.
     */
    public HeartCollisionStrategy(GameObjectCollection gameObjects, ImageReader imageReader) {
        this.imageReader = imageReader;
        this.gameObjects = gameObjects;
    }

    /**
     * When a brick detects a collision, this method is called and activates the current strategy.
     * It decrements the number of active bricks on the screen and removes the current brick from the screen.
     * In addition, this method creates a Heart object that falls frm the middle of the brick.
     *
     * @param collidedObj   the collided object.
     * @param colliderObj   the collider object.
     * @param bricksCounter the bricks Counter.
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        if (gameObjects.removeGameObject(collidedObj)) {
            bricksCounter.decrement();
        }
        //create heart that falls from the brick and adds it to the game.
        Renderable heartImage = imageReader.readImage(HEART_PATH, true);
        Vector2 heartDimensions = src.BrickerGameManager.getHeartDimensions();
        Heart heart = new Heart(
                collidedObj.getTopLeftCorner().add(new Vector2(collidedObj.getDimensions().x() / 2, 0)),
                heartDimensions, heartImage, gameObjects);

        heart.setVelocity(Vector2.DOWN.mult(HEART_SPEED));
        gameObjects.addGameObject(heart);
    }
}

