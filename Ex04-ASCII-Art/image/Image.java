package image;

import java.awt.*;
import java.io.IOException;

/**
 * Facade for the image module and an interface representing an image.
 *
 * @author Dan Nirel
 */
public interface Image {
    /**
     * returns the pixel Color object in the given coordinate.
     *
     * @param x col coordinate.
     * @param y row coordinate.
     * @return the pixel Color object in (y,x) in the img.
     */
    Color getPixel(int x, int y);

    /**
     * @return the width of the image
     */
    int getWidth();

    /**
     * @return the height of the image
     */
    int getHeight();

    /**
     * Open an image from file. Each dimensions of the returned image is guaranteed
     * to be a power of 2, but the dimensions may be different.
     *
     * @param filename a path to an image file on disk
     * @return an object implementing Image if the operation was successful,
     * null otherwise
     */
    static Image fromFile(String filename) {
        try {
            return new FileImage(filename);
        } catch (IOException ioe) {
            return null;
        }
    }

    /**
     * Allows iterating the pixels' colors by order (first row, second row and so on).
     *
     * @return an Iterable<Color> that can be traversed with a foreach loop
     */
    default Iterable<Color> pixels() {
        return new ImageIterableProperty<>(
                this, this::getPixel);
    }

    /**
     * returns iterator that iterates on the sub-images (of the given size).
     *
     * @param size the size of each subImg (subImg is a square).
     * @return an iterator that iterates on the sub-images (of the given size).
     */
    default Iterable<Image> getSubImages(int size) {
        return new ImageIterableProperty<>(this,
                (col, row) -> new SubImage(this, size, row, col), size);
    }

}
