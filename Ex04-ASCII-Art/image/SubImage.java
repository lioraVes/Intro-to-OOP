package image;

import java.awt.*;
import java.util.Objects;

/**
 * a class that represent a square sub-Image
 */
class SubImage implements Image {
    /**
     * private final fields- size, img, numRow, numCol.
     */
    private final Image img;
    private final int size;
    private final int numRow;
    private final int numCol;

    /**
     * returns the sub image in the given size and that its "index" is(numRow,numCol).
     *
     * @param img    the Image containing the sub Image
     * @param size   the size of the sub Image. it is a square so this is its width and height.
     * @param numRow the row index of the sub Image.
     * @param numCol the col index of the sub Image.
     */
    public SubImage(Image img, int size, int numRow, int numCol) {
        this.size = size;
        this.img = img;
        this.numRow = numRow;
        this.numCol = numCol;
    }

    /**
     * returns the Color value in the coordinate(y,x) of the image.
     *
     * @param x - col coordinate
     * @param y - row coordinate.
     * @return the pixel Color object in (y,x) in the img.
     */
    @Override
    public Color getPixel(int x, int y) {
        return img.getPixel(numCol * size + x, numRow * size + y);
    }

    /**
     * @return the width of the image
     */
    @Override
    public int getWidth() {
        return size;
    }

    /**
     * @return the height of the image.
     */
    @Override
    public int getHeight() {
        return size;
    }

    /**
     * checks if obj is equal to this subImage obj, based on img, size,numRow, numCol fields.
     *
     * @param obj the other object
     * @return true if it does, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SubImage) {
            return img.equals(((SubImage) obj).img) && size == ((SubImage) obj).size &&
                    numRow == ((SubImage) obj).numRow && numCol == ((SubImage) obj).numCol;
        }
        return false;
    }

    /**
     * @return a hashcode value for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(img, size, numRow, numCol);
    }

}
