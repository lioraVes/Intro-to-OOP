package src.gameobjects;

import danogl.GameManager;

/**
 * a class that checks the ball collisions and resets the camera of the game if needed.
 */
public class BallFollower {
    /**
     * constant field- MAX_NUM_COLLISIONS allowed for the ball.
     */
    private static final int MAX_NUM_COLLISIONS = 4;
    /**
     * private fields-gameManager, ball- the ball to follow.
     */
    private final GameManager gameManager;
    private final Ball ball;

    /**
     * BallFollower constructor.
     *
     * @param ball        the ball to follow.
     * @param gameManager the game manager.
     */
    public BallFollower(Ball ball, GameManager gameManager) {
        this.ball = ball;
        this.gameManager = gameManager;
    }

    /**
     * This method checks the ball collision counter and if it is more than 4 it resets the camera of the
     * game.
     */
    public void checkCollisions() {
        if (ball.getCollisionCount().value() >= MAX_NUM_COLLISIONS) {
            ball.resetCollisionCount();
            gameManager.setCamera(null);
        }
    }
}
