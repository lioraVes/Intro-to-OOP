package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 *
 * @author Dan Nirel
 */
class FileImage implements Image {
    /**
     * a private static final field- DEFAULT_COLOR, which is white.
     */
    private static final Color DEFAULT_COLOR = Color.WHITE;
    /**
     * private final fields-width, height, pixelArray.
     */
    private final int width;
    private final int height;
    private final Color[][] pixelArray;

    /**
     * constructor which initializes the width,height fields, pixelArray arr.
     * adds white pixels if needed.
     *
     * @param filename the name of the file containing the image.
     * @throws IOException throws an exception, if it couldn't open the file.
     */
    public FileImage(String filename) throws IOException {
        java.awt.image.BufferedImage im = ImageIO.read(new File(filename));
        int origWidth = im.getWidth(), origHeight = im.getHeight();
        //im.getRGB(x, y)); getter for access to a specific RGB rates

        int newWidth = getNextExp(origWidth);
        int newHeight = getNextExp(origHeight);

        width = newWidth;
        height = newHeight;
        pixelArray = new Color[newHeight][newWidth];

        int rowsToAdd = newHeight - origHeight;
        int colsToAdd = newWidth - origWidth;

        //first fill all white
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                pixelArray[i][j] = DEFAULT_COLOR;
            }
        }
        //copy the original image
        for (int i = 0; i < origHeight; i++) {
            for (int j = 0; j < origWidth; j++) {
                pixelArray[i + rowsToAdd / 2][j + colsToAdd / 2] = new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * helper method that calculates the next power of 2 (bigger than number).
     *
     * @param number- the number
     * @return the next power of two bigger then the number(or the number itself if it is a power of 2)
     */
    private int getNextExp(int number) {
        //check if log n of base 2 is int, if it is, the number is already a power of 2, this is because
        // Math.log is in base e.
        double logNumber = Math.log(number) / Math.log(2);
        if (Math.floor(logNumber) == logNumber) {// it is integer
            return number;
        }
        //if not return the closest power of two to number
        return (int) Math.pow(2, Math.ceil(logNumber));
    }

    /**
     * @return the width of the image.
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * @return the height of the image.
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * returns the Color value in the coordinate(y,x) of the image.
     *
     * @param x col coordinate.
     * @param y row coordinate.
     * @return the Color value in the coordinate(y,x) in the image.
     */
    @Override
    public Color getPixel(int x, int y) {
        return pixelArray[y][x];
    }
}
