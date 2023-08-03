package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

/**
 * DoubleCollisionStrategy - represent the strategy of multiple collisions Strategy.
 * when the ball hits the brick it can have 2 collisions strategies (when one of them can be
 * also DoubleCollisionStrategy).
 */
class DoubleCollisionStrategy implements CollisionStrategy {
    /**
     * It has the private field- collisionArr- array that holds the collisionStrategies.
     */
    private final CollisionStrategy[] collisionsArr;

    /**
     * DoubleCollisionStrategy constructor. saves the collisions array.
     *
     * @param collisionsArray- an array of collisionStrategies.
     */
    public DoubleCollisionStrategy(CollisionStrategy[] collisionsArray) {
        this.collisionsArr = new CollisionStrategy[collisionsArray.length];
        for (int i = 0; i < collisionsArray.length; i++) {
            this.collisionsArr[i] = collisionsArray[i];
        }
    }

    /**
     * When a brick detects a collision, this method is called and activates the current strategy.
     * This strategy owns multiple strategies and so it activates their strategies.
     *
     * @param collidedObj   the collided object
     * @param colliderObj   the collider object.
     * @param bricksCounter the bricks Counter.
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        for (CollisionStrategy collisionStrategy : collisionsArr) {
            collisionStrategy.onCollision(collidedObj, colliderObj, bricksCounter);
        }
    }
}
