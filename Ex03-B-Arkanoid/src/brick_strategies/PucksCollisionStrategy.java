package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;

/**
 * PucksCollisionStrategy - represent the strategy of Pucks Balls. When the ball hits the brick it
 * creates a new puck that comes out of the middle brick.
 */
class PucksCollisionStrategy implements CollisionStrategy {
    /**
     * it has some constants fields-the puck speed, puck sound, path.
     */
    private static final float PUCK_SPEED = 200;
    private static final String PUCK_PATH = "assets/mockBall.png";
    private static final String PUCK_SOUND_PATH = "assets/blop_cut_silenced.wav";
    /**
     * private fields-gameObjects,imageReader,soundReader.
     */
    private final GameObjectCollection gameObjects;
    private final ImageReader imageReader;
    private final SoundReader soundReader;

    /**
     * PucksCollisionStrategy constructor.
     *
     * @param gameObjects An object which holds all game objects of the game running.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                    See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     */
    public PucksCollisionStrategy(GameObjectCollection gameObjects, ImageReader imageReader,
                                  SoundReader soundReader) {

        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * When a brick detects a collision, this method is called and activates the current strategy.
     * It decrements the number of active bricks on the screen and removes the current brick from the screen.
     * In addition, this method creates a Puck object.
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
        //create puck, set its position and add to the game.
        Renderable puckImage = imageReader.readImage(PUCK_PATH, true);
        Sound collisionSound = soundReader.readSound(PUCK_SOUND_PATH);
        float PUCK_RADIUS = collidedObj.getDimensions().x() / 3;
        Vector2 curTopLeft = collidedObj.getTopLeftCorner().add(collidedObj.getDimensions().mult(0.5f));
        Puck puck = new Puck(curTopLeft, new Vector2(PUCK_RADIUS, PUCK_RADIUS), puckImage,
                collisionSound);
        puck.setGameObjects(gameObjects);
        setPuckPos(puck);
        gameObjects.addGameObject(puck);
    }

    /**
     * This method sets the puck position- a random diagonal.
     *
     * @param puck-the puck to set its position.
     */
    private void setPuckPos(Puck puck) {
        float puckVelX = PUCK_SPEED;
        float puckVelY = PUCK_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            puckVelX *= -1;
        if (rand.nextBoolean())
            puckVelY *= -1;
        puck.setVelocity(new Vector2(puckVelX, puckVelY));
    }
}
