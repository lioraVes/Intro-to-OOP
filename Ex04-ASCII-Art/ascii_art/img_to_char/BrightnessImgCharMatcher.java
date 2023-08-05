package ascii_art.img_to_char;

import image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * BrightnessImgCharMatcher class, converts an image to an ASCII char.
 */

public class BrightnessImgCharMatcher {
    /**
     * private final statics for magic numbers:
     * DEFAULT_RESOLUTION, RED_COORDINATE_MULT, GREEN_COORDINATE_MULT, BLUE_COORDINATE_MULT, MAX_RGB_VAL.
     */
    private final static int DEFAULT_RESOLUTION = 16;
    private final static double RED_COORDINATE_MULT = 0.2126;
    private final static double GREEN_COORDINATE_MULT = 0.7152;
    private final static double BLUE_COORDINATE_MULT = 0.0722;
    private final static int MAX_RGB_VAL = 255;
    /**
     * private final fields. font,img, curCharBrightnessMap, ASCIICharBrightnessMap, subImgBrightnessMap.
     */
    private final String font;
    private final Image img;
    private final HashMap<Character, Double> curCharBrightnessMap;
    private final HashMap<Character, Double> ASCIICharBrightnessMap;
    private final HashMap<Image, Double> subImgBrightnessMap;


    /**
     * constructor for BrightnessImgCharMatcher.
     *
     * @param img  image to convert to AsciiChars.
     * @param font the font of the chars.
     */
    public BrightnessImgCharMatcher(Image img, String font) {
        this.img = img;
        this.font = font;
        this.curCharBrightnessMap = new HashMap<>();
        this.ASCIICharBrightnessMap = new HashMap<>();
        this.subImgBrightnessMap = new HashMap<>();
    }

    /**
     * this method converts the img to chars.
     *
     * @param numCharsInRow number of chars in a row.
     * @param charSet       the chars to calculate their brightness (we might not use all of them).
     * @return 2-dimensional array of chars that represent the img in ascii chars.
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        curCharBrightnessMap.clear();
        calcBrightnessOfSet(charSet);
        return convertToAsciiChars(numCharsInRow);
    }

    /**
     * fills the charBrightnessMap with brightness.
     *
     * @param charSet array of chars to calculate their brightness.
     */
    private void calcBrightnessOfSet(Character[] charSet) {
        for (Character c : charSet) {
            if (ASCIICharBrightnessMap.containsKey(c)) {
                curCharBrightnessMap.put(c, ASCIICharBrightnessMap.get(c));
            } else if (!curCharBrightnessMap.containsKey(c)) {
                double charBrightness = calcInitCharBrightness(c);
                curCharBrightnessMap.put(c, charBrightness);
                ASCIICharBrightnessMap.put(c, charBrightness);
            }
        }
        normalBrightness();
    }

    /**
     * this method calculates the initial brightness of a char.
     * It gets a boolean arr from getImg, counts all the True it got, divides the sum by the default size
     * (DEFAULT_RESOLUTION*DEFAULT_RESOLUTION) and returns the result.
     *
     * @param c the char, we calculate the brightness of.
     * @return the initial brightness of char.
     */
    private double calcInitCharBrightness(char c) {
        boolean[][] booleanArr = CharRenderer.getImg(c, DEFAULT_RESOLUTION, font);
        int countTrue = 0;
        for (int i = 0; i < DEFAULT_RESOLUTION; i++) {
            for (int j = 0; j < DEFAULT_RESOLUTION; j++) {
                if (booleanArr[i][j]) {
                    countTrue++;
                }
            }
        }
        return (double) countTrue / (DEFAULT_RESOLUTION * DEFAULT_RESOLUTION);
    }

    /**
     * Normalizes the brightness of each char in the charBrightnessMap.
     */
    private void normalBrightness() {
        double minBrightness = getMinBrightness();
        double maxBrightness = getMaxBrightness();
        double maxSubMin = maxBrightness - minBrightness;

        for (Map.Entry<Character, Double> entry : curCharBrightnessMap.entrySet()) {
            Double newCharBrightness = (entry.getValue() - minBrightness) / maxSubMin;
            curCharBrightnessMap.put(entry.getKey(), newCharBrightness);
        }
    }

    /**
     * @return minimum brightness in charBrightnessMap
     */
    private double getMinBrightness() {
        double min = -1; //initial minimum
        for (Map.Entry<Character, Double> entry : curCharBrightnessMap.entrySet()) {
            if (min == -1 || entry.getValue() < min) {
                min = entry.getValue();
            }
        }
        return min;
    }

    /**
     * @return maximum brightness in charBrightnessMap
     */
    private double getMaxBrightness() {
        double max = -1; //initial maximum
        for (Map.Entry<Character, Double> entry : curCharBrightnessMap.entrySet()) {
            if (max == -1 || entry.getValue() > max) {
                max = entry.getValue();
            }
        }
        return max;
    }

    /**
     * converts an img to char and save the brightness of it to the map(if it's not already there).
     *
     * @param numCharsInRow num of chars in row .
     * @return 2-dimensional array of chars. Each char representing a subImage.
     */
    private char[][] convertToAsciiChars(int numCharsInRow) {
        int sizeOfEachSubImg = img.getWidth() / numCharsInRow;
        int numCharsInCol = img.getHeight() / sizeOfEachSubImg;
        char[][] AsciiChars = new char[numCharsInCol][numCharsInRow];

        int subImageCounter = 0;
        for (Image subImg : img.getSubImages(img.getWidth() / numCharsInRow)) {
            double val;
            //check if already calculated the brightness
            if (subImgBrightnessMap.containsKey(subImg)) {
                val = subImgBrightnessMap.get(subImg);
            } else {
                val = convertSingleImgToDouble(subImg, img.getWidth() / numCharsInRow);
                subImgBrightnessMap.put(subImg, val);
            }
            char finalChar = findBestChar(val);

            AsciiChars[(subImageCounter / numCharsInRow)][subImageCounter % numCharsInRow] = finalChar;
            subImageCounter++;
        }
        return AsciiChars;
    }

    /**
     * converts a single sub-img to a char. It first converts each pixel of the subImg to a grey pixel,
     * then sum all the values and divides the result by the size of the sub image and MAX_RGB_VAL.
     *
     * @param subImg     sub Image
     * @param sizeSubImg the size of the sub Image (height or width, it's a square)
     * @return char that represent the img.
     */
    private double convertSingleImgToDouble(Image subImg, int sizeSubImg) {
        double[][] greyPixelArr = new double[sizeSubImg][sizeSubImg];
        for (int i = 0; i < sizeSubImg; i++) {
            for (int j = 0; j < sizeSubImg; j++) {
                greyPixelArr[i][j] =
                        subImg.getPixel(j, i).getRed() * RED_COORDINATE_MULT +
                                subImg.getPixel(j, i).getGreen() * GREEN_COORDINATE_MULT +
                                subImg.getPixel(j, i).getBlue() * BLUE_COORDINATE_MULT;
            }
        }
        double sumGray = 0;
        for (double[] doubles : greyPixelArr) {
            for (int j = 0; j < greyPixelArr.length; j++) {
                sumGray += doubles[j];
            }
        }
        return sumGray / (subImg.getWidth() * subImg.getHeight() * MAX_RGB_VAL);
    }

    /**
     * finds the closest char to val from charBrightnessMap.
     *
     * @param val the value.
     * @return the char with the closest Brightness to val.
     */
    private char findBestChar(double val) {
        //if the val is in the map return its key.
        if (curCharBrightnessMap.containsValue(val)) {
            for (Map.Entry<Character, Double> entry : curCharBrightnessMap.entrySet()) {
                if (entry.getValue().equals(val)) {
                    return entry.getKey();
                }
            }
        }
        char closestChar = ' ';
        double sub = 0;
        boolean start = true;
        for (Map.Entry<Character, Double> entry : curCharBrightnessMap.entrySet()) {
            if (start || Math.abs(val - entry.getValue()) < sub) {
                sub = Math.abs(val - entry.getValue());
                closestChar = entry.getKey();
                start = false;
            }
        }
        return closestChar;
    }
}
