package image;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
class ImageIterableProperty<T> implements Iterable<T> {
    private final Image img;
    private final BiFunction<Integer, Integer, T> propertySupplier;
    private final int size;

    public ImageIterableProperty(
            Image img,
            BiFunction<Integer, Integer, T> propertySupplier) {
        this.img = img;
        this.propertySupplier = propertySupplier;
        this.size=1;
    }

    public ImageIterableProperty(
            Image img,
            BiFunction<Integer, Integer, T> propertySupplier,int size) {
        this.img = img;
        this.propertySupplier = propertySupplier;
        this.size=size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int x = 0, y = 0;

            @Override
            public boolean hasNext() {
                return y < img.getHeight()/size;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                var next = propertySupplier.apply(x, y);
                x += 1;
                if (x >= img.getWidth()/size) {
                    x = 0;
                    y += 1;
                }
                return next;
            }
        };
    }
}
