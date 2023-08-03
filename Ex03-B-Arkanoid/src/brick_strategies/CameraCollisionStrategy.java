package src.brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.Puck;

/**
 * CameraCollisionStrategy represents the strategy of zooming on the ball.
 */
class CameraCollisionStrategy implements CollisionStrategy {
    /**
     * it has the private fields-gameManager, windowController, gameObjects and ball.
     */
    private final GameManager gameManager;
    private final WindowController windowController;
    private final GameObjectCollection gameObjects;
    private final Ball ball;

    /**
     * CameraCollisionStrategy constructor.
     *
     * @param gameManager      the game manager.
     * @param windowController Contains an array of helpful, self-explanatory methods concerning the window.
     * @param gameObjects      An object which holds all game objects of the game running.
     * @param ball             Ball object- to follow.
     */
    public CameraCollisionStrategy(GameManager gameManager, WindowController windowController,
                                   GameObjectCollection gameObjects, Ball ball) {
        this.gameManager = gameManager;
        this.windowController = windowController;
        this.gameObjects = gameObjects;
        this.ball = ball;
    }

    /**
     * When a brick detects a collision, this method is called and activates the current strategy.
     * It decrements the number of active bricks on the screen and removes the current brick from the screen.
     * In addition, this method sets the camera of the game to follow the ball (if it isn't already).
     *
     * @param collidedObj   the collided object
     * @param colliderObj   the collider object.
     * @param bricksCounter the bricks Counter.
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        if (gameObjects.removeGameObject(collidedObj)) {
            bricksCounter.decrement();
        }
        if (colliderObj instanceof Ball && !(colliderObj instanceof Puck)) {
            if (gameManager.getCamera() != null) {
                return;
            }
            ball.resetCollisionCount();
            gameManager.setCamera(new Camera(ball, Vector2.ZERO,
                    windowController.getWindowDimensions().mult(1.2f),
                    windowController.getWindowDimensions()));
        }
    }
}
