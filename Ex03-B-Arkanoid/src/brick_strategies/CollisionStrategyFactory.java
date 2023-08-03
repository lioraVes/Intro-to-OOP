package src.brick_strategies;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import src.gameobjects.Ball;

import java.util.Random;

/**
 * A factory that chooses the strategy for the brick.
 */
public class CollisionStrategyFactory {
    /**
     * Constants numbers for each brick collision strategy.
     * UPPER_BOUND_FOR_RAND is 7 so the chance to choose each one of the strategies is 1/6.
     * NUM_STRATEGIES_FOR_DOUBLE is the number of additional strategies each DoubleCollisionStrategy can hold.
     * MAX_STRATEGIES is the max number of CollisionStrategy that DoubleCollisionStrategy can hold.
     */
    private static final int NORMAL_COLLISION_NUM = 1;
    private static final int PADDLE_COLLISION_NUM = 2;
    private static final int LIVES_COLLISION_NUM = 3;
    private static final int PUCKS_COLLISION_NUM = 4;
    private static final int CAMERA_COLLISION_NUM = 5;
    private static final int DOUBLE_COLLISION_NUM = 6;
    private static final int UPPER_BOUND_FOR_RAND = 7;
    private static final int NUM_STRATEGIES_FOR_DOUBLE = 2;
    private static final int MAX_STRATEGIES = 3;

    /**
     * fields needed for the strategies: gameObjects, imageReader, soundReader, inputListener, gameManager,
     * windowController, ball and doubleCollisionCounter- that counts the number of DoubleCollisionStrategy
     * that has been chosen.
     */
    private final GameObjectCollection gameObjects;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final GameManager gameManager;
    private final WindowController windowController;
    private final Ball ball;
    private final Counter doubleCollisionCounter;


    /**
     * CollisionStrategyFactory constructor.
     *
     * @param gameObject       An object which holds all game objects of the game running.
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    The input listener which waits for user inputs and acts on them.
     * @param gameManager      the game manager.
     * @param windowController Contains an array of helpful, self-explanatory methods concerning the window.
     * @param ball             Ball object-the main ball in the game.
     */
    public CollisionStrategyFactory(GameObjectCollection gameObject, ImageReader imageReader,
                                    SoundReader soundReader, UserInputListener inputListener,
                                    GameManager gameManager, WindowController windowController, Ball ball) {
        this.gameObjects = gameObject;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.gameManager = gameManager;
        this.windowController = windowController;
        this.ball = ball;
        this.doubleCollisionCounter = new Counter(0);
    }

    /**
     * this method select the strategy randomly. Each strategy has 1/6 probability to be chosen.
     *
     * @return the chosen strategy.
     */
    public CollisionStrategy selectStrategy() {
        CollisionStrategy collisionStrategy;
        int strategyNum = getRandNum();

        switch (strategyNum) {
            case NORMAL_COLLISION_NUM:
                collisionStrategy = new NormalCollisionStrategy(gameObjects);
                return collisionStrategy;

            case PADDLE_COLLISION_NUM:
                collisionStrategy = new PaddleCollisionStrategy(gameObjects,
                        inputListener, imageReader, windowController);
                return collisionStrategy;

            case LIVES_COLLISION_NUM:
                collisionStrategy = new HeartCollisionStrategy(gameObjects, imageReader);
                return collisionStrategy;

            case PUCKS_COLLISION_NUM:
                collisionStrategy = new PucksCollisionStrategy(gameObjects, imageReader, soundReader);
                return collisionStrategy;

            case CAMERA_COLLISION_NUM:
                collisionStrategy = new CameraCollisionStrategy(gameManager, windowController, gameObjects,
                        ball);
                return collisionStrategy;

            case DOUBLE_COLLISION_NUM:
                if (doubleCollisionCounter.value() >= MAX_STRATEGIES - 1) {
                    return null;
                }
                doubleCollisionCounter.increment();
                CollisionStrategy[] arr = createArrCollisions();
                collisionStrategy = new DoubleCollisionStrategy(arr);
                return collisionStrategy;
            default:
                return null;
        }
    }

    /**
     * This method is called when the factory chose the doubleCollision strategy. It creates an array of
     * NUM_STRATEGIES_FOR_DOUBLE (two) CollisionStrategy and choose randomly NUM_STRATEGIES_FOR_DOUBLE (two)
     * strategies(that are not NormalCollisionStrategy, but if we chose DoubleCollisionStrategy the second
     * time it can choose NormalCollisionStrategy) so each strategy has now 1/5 probability to be
     * chosen. It can only choose DoubleCollision strategy twice which is checked by doubleCollisionCounter.
     *
     * @return array of NUM_STRATEGIES_FOR_DOUBLE(two) CollisionStrategy.
     */
    private CollisionStrategy[] createArrCollisions() {
        CollisionStrategy[] arr = new CollisionStrategy[NUM_STRATEGIES_FOR_DOUBLE];
        if (doubleCollisionCounter.value() >= MAX_STRATEGIES - 1) {
            for (int i = 0; i < NUM_STRATEGIES_FOR_DOUBLE; i++) {
                CollisionStrategy strategy = selectStrategy();
                while (strategy == null) {
                    strategy = selectStrategy();
                }
                arr[i] = strategy;
            }
        } else {
            for (int i = 0; i < NUM_STRATEGIES_FOR_DOUBLE; i++) {
                CollisionStrategy strategy = selectStrategy();
                while (strategy == null || strategy instanceof NormalCollisionStrategy) {
                    strategy = selectStrategy();
                }
                arr[i] = strategy;
            }
        }
        return arr;
    }

    /**
     * This method chooses a random number between 1 to UPPER_BOUND_FOR_RAND (7).
     *
     * @return random number between 1 to UPPER_BOUND_FOR_RAND (7).
     */
    private int getRandNum() {
        Random random = new Random();
        int strategyNum = 0;
        while (strategyNum == 0) {
            strategyNum = random.nextInt(UPPER_BOUND_FOR_RAND);
        }
        return strategyNum;
    }
}
