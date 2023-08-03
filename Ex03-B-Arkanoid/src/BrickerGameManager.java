package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.CollisionStrategy;
import src.brick_strategies.CollisionStrategyFactory;
import src.gameobjects.*;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * the BrickerGameManager, extends GameManager and manages the game.
 */
public class BrickerGameManager extends GameManager {
    /**
     * objects sizes and properties constants:
     * public constant BORDER_WIDTH- the width of the borders.
     * privates: BALL_RADIUS,BALL_SPEED,start position of the bricks, number of bricks in a row, number of
     * bricks in a column.
     */
    public static final float BORDER_WIDTH = 20.0f;
    private static final float BALL_RADIUS = 20;
    private static final float BALL_SPEED = 250;
    private static final Vector2 BRICKS_START = new Vector2(30, 30);
    private static final int NUM_OF_ROWS_BRICKS = 8;
    private static final int NUM_OF_COLS_BRICKS = 7;

    /**
     * private constants fields: dimensions of the window, brick,heart,paddle and number of lives.
     */
    private static final Vector2 WINDOW_DIMENSIONS = new Vector2(700, 500);
    private static final Vector2 BRICK_DIMENSIONS = new Vector2(90, 15);
    private static final Vector2 HEART_DIMENSIONS = new Vector2(15, 15);
    private static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    private static final Vector2 NUM_LIVES_DIMENSIONS = new Vector2(20, 20);

    /**
     * private constants fields: init values of lives and bricks
     */
    private static final int INIT_NUM_OF_LIVES = 3;
    private static final int INIT_NUM_OF_BRICKS = 56;

    /**
     * private constants fields:
     * path's to assets for the background and the gameObjects: bricks, paddle,ball and hearts
     */
    private static final String BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String BRICK_PATH = "assets/brick.png";
    private static final String PADDLE_PATH = "assets/paddle.png";
    private static final String BALL_PATH = "assets/ball.png";
    private static final String BALL_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final String HEART_PATH = "assets/heart.png";

    /**
     * private constants fields: strings and messages.
     */
    private static final String WINDOW_TITLE = "Bricker";
    private static final String WON_PROMPT = "You Won! ";
    private static final String LOST_PROMPT = "You Lost! ";

    /**
     * private fields:
     * the windowController, inputListener, ball, userPaddle.
     * bricksCounter,livesCounter,ballFollower.
     */
    private WindowController windowController;
    private UserInputListener inputListener;
    private Ball ball;
    private Paddle userPaddle;
    private Counter bricksCounter;
    private Counter livesCounter;
    private BallFollower ballFollower;

    /**
     * This is the constructor of a brick game, which calls its super (GameManager)'s constructor
     *
     * @param windowTitle      the title of the window
     * @param windowDimensions a 2d vector representing the height and width of the window
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * The main driver of the program
     *
     * @param args -arguments are unused here.
     */
    public static void main(String[] args) {
        new BrickerGameManager(WINDOW_TITLE, WINDOW_DIMENSIONS).run();
    }

    /**
     * This method initializes a new game. It creates all game objects, sets their values and initial
     * positions and allow the start of a game.
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {

        this.windowController = windowController;

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.inputListener = inputListener;
        //set background
        setBackground(imageReader);
        //create borders
        createBorders();
        //create paddle
        createPaddle(imageReader, inputListener);
        //create Ball
        createBall(imageReader, soundReader);
        //create ballFollower object-that follows the ball
        this.ballFollower = new BallFollower(ball, this);
        //create hearts
        createLives(imageReader);
        //create bricks
        createBricks(imageReader, soundReader);
    }

    /**
     * this method creates the bricks of the game and the collisionStrategy object
     *
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     */
    private void createBricks(ImageReader imageReader, SoundReader soundReader) {
        bricksCounter = new Counter(INIT_NUM_OF_BRICKS);

        for (int i = 0; i < NUM_OF_ROWS_BRICKS; i++) {
            for (int j = 0; j < NUM_OF_COLS_BRICKS; j++) {
                Renderable brickImage = imageReader.readImage(BRICK_PATH, false);
                //the first brick is located at (30,30) ,all bricks have 1 pixel distance between them.
                Vector2 topLeftCornerNewBrick = new Vector2(BRICKS_START.x() + (BRICK_DIMENSIONS.x() + 1) * j,
                        (BRICKS_START.y() + (BRICK_DIMENSIONS.y() + 1) * i));
                //choose strategy for brick
                CollisionStrategyFactory collisionStrategyFactory =
                        new CollisionStrategyFactory(gameObjects(), imageReader, soundReader, inputListener,
                                this, windowController, ball);
                CollisionStrategy collisionStrategy = collisionStrategyFactory.selectStrategy();
                Brick brick = new Brick(topLeftCornerNewBrick, BRICK_DIMENSIONS, brickImage,
                        collisionStrategy, bricksCounter);
                gameObjects().addGameObject(brick);
            }
        }
    }

    /**
     * this method creates the lives-graphic and numeric of the game.
     *
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     */
    private void createLives(ImageReader imageReader) {
        Renderable heartImage = imageReader.readImage(HEART_PATH, true);
        livesCounter = new Counter(INIT_NUM_OF_LIVES);

        Vector2 leftHeartTopLeftCorner = new Vector2(5, WINDOW_DIMENSIONS.y() - 20);
        GraphicLifeCounter lives = new GraphicLifeCounter(leftHeartTopLeftCorner,
                HEART_DIMENSIONS, livesCounter, heartImage, gameObjects(), livesCounter.value());
        lives.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(lives, Layer.UI);

        Vector2 numLivesTopLeftCorner = new Vector2(75, WINDOW_DIMENSIONS.y() - 25);
        NumericLifeCounter numLives = new NumericLifeCounter(livesCounter,
                numLivesTopLeftCorner, NUM_LIVES_DIMENSIONS, gameObjects());
        numLives.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(numLives, Layer.UI);
    }

    /**
     * this method creates the ball and adds it to the game.
     *
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     * @param soundReader Contains a single method: readSound, which reads a wav file from disk.
     */
    private void createBall(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage(BALL_PATH, true);
        Sound collisionSound = soundReader.readSound(BALL_SOUND_PATH);
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        gameObjects().addGameObject(ball);
        setBallPos();
    }

    /**
     * this method sets the ball beginning position.
     */
    private void setBallPos() {
        ball.setCenter(WINDOW_DIMENSIONS.mult(0.5f));
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * this method creates the user paddle and adds it to the game.
     *
     * @param imageReader   Contains a single method: readImage, which reads an image from disk.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not.
     */
    private void createPaddle(ImageReader imageReader, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage(PADDLE_PATH, false);
        userPaddle = new Paddle(Vector2.ZERO, PADDLE_DIMENSIONS, paddleImage,
                inputListener, WINDOW_DIMENSIONS, (int) BORDER_WIDTH);
        setPaddle();
        gameObjects().addGameObject(userPaddle);
    }

    /**
     * this method sets the paddle position to its initial position.
     */
    private void setPaddle() {
        userPaddle.setCenter(new Vector2(WINDOW_DIMENSIONS.x() / 2, (int) WINDOW_DIMENSIONS.y() - 50));
    }

    /**
     * this method creates the borders of the board and adds them to the game.
     */
    private void createBorders() {

        Wall leftBorder = new Wall(Vector2.ZERO, new Vector2(BORDER_WIDTH, WINDOW_DIMENSIONS.y())
                , null);
        gameObjects().addGameObject(leftBorder, Layer.STATIC_OBJECTS);

        Wall rightBorder = new Wall(new Vector2(WINDOW_DIMENSIONS.x() - BORDER_WIDTH, 0)
                , new Vector2(BORDER_WIDTH, WINDOW_DIMENSIONS.y()), null);
        gameObjects().addGameObject(rightBorder, Layer.STATIC_OBJECTS);

        Wall ceilingBorder = new Wall(Vector2.ZERO, new Vector2(WINDOW_DIMENSIONS.x(),
                BORDER_WIDTH), null);
        gameObjects().addGameObject(ceilingBorder, Layer.STATIC_OBJECTS);
    }

    /**
     * This method sets the background of the game.
     *
     * @param imageReader- Contains a single method: readImage, which reads an image from disk.
     */
    private void setBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, WINDOW_DIMENSIONS, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * This method checks for game status, and triggers a new game popup.
     *
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        ballFollower.checkCollisions();
        checkEndGame();
    }

    /**
     * this method checks if the game ended.
     */
    private void checkEndGame() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";

        if (ballHeight < 0 || ballHeight > WINDOW_DIMENSIONS.y()) {
            //ball ran out of the screen-decrement life,set ball and paddle to its init pos.
            livesCounter.decrement();
            setBallPos();
            setPaddle();
        }
        if (bricksCounter.value() <= 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            //all bricks are cleared OR W was pressed-player won the game
            prompt = WON_PROMPT;
        }
        if (livesCounter.value() <= 0) {
            //player lost the game
            prompt = LOST_PROMPT;
        }
        if (!prompt.isEmpty()) {
            prompt += " Play again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * A getter method for PADDLE_DIMENSIONS.
     *
     * @return PADDLE_DIMENSIONS
     */
    public static Vector2 getPaddleDimensions() {
        return PADDLE_DIMENSIONS;
    }

    /**
     * A getter method for HEART_DIMENSIONS.
     *
     * @return HEART_DIMENSIONS
     */
    public static Vector2 getHeartDimensions() {
        return HEART_DIMENSIONS;
    }

    /**
     * A getter method for WINDOW_DIMENSIONS.
     *
     * @return WINDOW_DIMENSIONS
     */
    public static Vector2 getWindowDimensions() {
        return WINDOW_DIMENSIONS;
    }
}
