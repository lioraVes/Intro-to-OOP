package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

/**
 * Interface of Brick Strategies
 */
public interface CollisionStrategy {
    /**
     * Each class that implements this interface, has to implement the onCollision method.
     * This method activates the strategy on a collision on the bricks.
     *
     * @param collidedObj   the collided object
     * @param colliderObj   the collider object.
     * @param bricksCounter the bricks Counter.
     */
    void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter);
}
