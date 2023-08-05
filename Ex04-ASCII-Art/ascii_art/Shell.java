package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Shell class-interacts with the user.
 */
public class Shell {
    /**
     * sting constants: commands.
     */
    private static final String SHELL_SIGN = ">>> ";
    private static final String EXIT_COMMAND = "exit";
    private static final String CHARS_COMMAND = "chars";
    private static final String ADD_COMMAND = "add ";
    private static final String REMOVE_COMMAND = "remove ";
    private static final String ADD_SPACE_COMMAND = "add space";
    private static final String REMOVE_SPACE_COMMAND = "remove space";
    private static final String ADD_ALL_COMMAND = "add all";
    private static final String REMOVE_ALL_COMMAND = "remove all";
    private static final String RES_UP_COMMAND = "res up";
    private static final String RES_DOWN_COMMAND = "res down";
    private static final String SET_WIDTH_MSG = "Width set to ";
    private static final String CONSOLE_COMMAND = "console";
    private static final String RENDER_COMMAND = "render";

    /**
     * string constants: error messages.
     */
    private static final String EXCEEDING_BOUNDARIES_MSG = "Did not change due to exceeding boundaries";
    private static final String ADD_ERROR_MSG = "Did not add due to incorrect format";
    private static final String REMOVE_ERROR_MSG = "Did not remove due to incorrect format";
    private static final String ERROR_MSG = "Did not executed due to incorrect command";

    /**
     * string constants and magic numbers: ASCII symbols and values.
     */
    private static final char INIT_VALID_ASCII_SPACE = ' ';
    private static final char LAST_VALID_ASCII_TILDE = '~';
    private static final char RANGE_SYMBOL_ASCII = '-';
    private static final char ZERO_ASCII_VALUE = 48;
    private static final char NINE_ASCII_VALUE = 57;
    /**
     * string constants.
     */
    private static final String FONT = "Courier New";
    private static final String OUTPUT_HTML_FILE = "out.html";
    /**
     * magic numbers: commands related.
     */
    private static final int ADD_CHAR_LENGTH = 5;
    private static final int REMOVE_CHAR_LENGTH = 8;
    private static final int ADD_RANGE_LENGTH = 7;
    private static final int ADD_RANGE_SYMBOL_INDEX = 5;
    private static final int REMOVE_RANGE_LENGTH = 10;
    private static final int REMOVE_RANGE_SYMBOL_INDEX = 8;

    /**
     * fields.
     */
    private static final HashSet<Character> ALL_ASCII = new HashSet<>();
    private final HashSet<Character> charSet;
    private final BrightnessImgCharMatcher brightnessImgCharMatcher;
    private boolean printToConsole;
    /**
     * resolution fields.
     */
    private static final int INITIAL_CHARS_IN_ROW = 64;
    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    private final static int MIN_PIXELS_PER_CHAR = 2;


    /**
     * static method- initialize all possible ascii values.
     */
    private static void initialize_all_ascii() {
        for (char i = INIT_VALID_ASCII_SPACE; i < LAST_VALID_ASCII_TILDE + 1; i++) {
            ALL_ASCII.add(i);
        }
    }

    /**
     * constructor for Shell. adds 0-9 by default to charSet and initializes ALL_ASCII map.
     *
     * @param img the image to render.
     */
    public Shell(Image img) {
        charSet = new HashSet<>();

        for (char i = ZERO_ASCII_VALUE; i < NINE_ASCII_VALUE + 1; i++) {
            charSet.add(i);
        }
        initialize_all_ascii();

        printToConsole = false;

        minCharsInRow = Math.max(1, img.getWidth() / img.getHeight());
        maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);

        brightnessImgCharMatcher = new BrightnessImgCharMatcher(img, FONT);
    }

    /**
     * a method that runs the shell. gets input from the user.
     */
    public void run() {
        boolean typedExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!typedExit) {
            System.out.print(SHELL_SIGN);
            String userStr = scanner.nextLine();

            if (userStr.equals(EXIT_COMMAND)) {
                typedExit = true;
            } else if (userStr.equals(CHARS_COMMAND)) {
                for (Character c : charSet) {
                    System.out.print(c + " ");
                }
                System.out.println();
            } else if (userStr.equals(ADD_COMMAND.trim()) || userStr.startsWith(ADD_COMMAND)) {
                if (!add_remove(userStr)) {
                    System.out.println(ADD_ERROR_MSG);
                }
            } else if (userStr.equals(REMOVE_COMMAND.trim()) || userStr.startsWith(REMOVE_COMMAND)) {
                if (!add_remove(userStr)) {
                    System.out.println(REMOVE_ERROR_MSG);
                }
            } else if (userStr.equals(RES_UP_COMMAND) || userStr.equals(RES_DOWN_COMMAND)) {
                res_up_down(userStr);
            } else if (userStr.equals(CONSOLE_COMMAND)) {
                printToConsole = true;
            } else if (userStr.equals(RENDER_COMMAND)) {
                renderOutput();
            } else {
                System.out.println(ERROR_MSG);
            }
        }
    }

    /**
     * Renders the output. Is called when the user typed in the RENDER_COMMAND. If console was typed
     * before, renders to the console, else renders to html file.
     */
    private void renderOutput() {
        if (charSet.isEmpty()) {
            System.out.println(ERROR_MSG);
            return;
        }

        Character[] charArr = convertToArr(charSet);

        char[][] charAsciiArr = brightnessImgCharMatcher.chooseChars(charsInRow, charArr);

        if (printToConsole) {
            ConsoleAsciiOutput console = new ConsoleAsciiOutput();
            console.output(charAsciiArr);
            return;
        }
        HtmlAsciiOutput html = new HtmlAsciiOutput(OUTPUT_HTML_FILE, FONT);
        html.output(charAsciiArr);
    }

    /**
     * converts the HashSet of characters to regular array of Characters.
     *
     * @param charArr the HashSet to convert.
     * @return array of Characters.
     */
    private Character[] convertToArr(HashSet<Character> charArr) {
        Character[] newArr = new Character[charArr.size()];
        int ind = 0;
        for (Character c : charArr) {
            newArr[ind] = c;
            ind++;
        }
        return newArr;
    }

    /**
     * increase or decrease the current resolution according to the boundaries- minCharsInRow and
     * maxCharsInRow.
     *
     * @param userInput the user input.
     */
    private void res_up_down(String userInput) {
        int charsInRowBefore = charsInRow;
        if (userInput.equals(RES_UP_COMMAND)) {
            charsInRow *= 2;
        }
        if (userInput.equals(RES_DOWN_COMMAND)) {
            charsInRow /= 2;
        }
        if (minCharsInRow <= charsInRow && charsInRow <= maxCharsInRow) {
            System.out.println(SET_WIDTH_MSG + charsInRow);
        } else {
            charsInRow = charsInRowBefore;
            System.out.println(EXCEEDING_BOUNDARIES_MSG);
        }
    }

    /**
     * this method manages all the possible add, remove commands.
     *
     * @param userInput - the user input.
     * @return true if the command is valid and done, false if the command is not valid.
     */
    private boolean add_remove(String userInput) {
        return add_remove_single_char(userInput) || add_remove_all(userInput) ||
                add_remove_space(userInput) || add_remove_range(userInput);
    }

    /**
     * adds or removes a single char to the charSet.
     *
     * @param userInput - the user input indicates if we're adding or removing.
     * @return true if successfully added or removed one char, false otherwise.
     */
    private boolean add_remove_single_char(String userInput) {
        if (userInput.startsWith(ADD_COMMAND) && userInput.length() == ADD_CHAR_LENGTH &&
                ALL_ASCII.contains(userInput.charAt(ADD_CHAR_LENGTH - 1)) &&
                userInput.charAt(ADD_CHAR_LENGTH - 1) != INIT_VALID_ASCII_SPACE) {
            charSet.add(userInput.charAt(ADD_CHAR_LENGTH - 1));
            return true;
        }

        if (userInput.startsWith(REMOVE_COMMAND) && userInput.length() == REMOVE_CHAR_LENGTH &&
                ALL_ASCII.contains(userInput.charAt(REMOVE_CHAR_LENGTH - 1)) &&
                userInput.charAt(REMOVE_CHAR_LENGTH - 1) != INIT_VALID_ASCII_SPACE) {
            charSet.remove(userInput.charAt(ADD_CHAR_LENGTH - 1));
            return true;
        }
        return false;
    }

    /**
     * adds or removes all the chars from charSet.
     *
     * @param userInput- the user input indicates if we're adding or removing.
     * @return true if successfully added or removed all chars, false otherwise.
     */
    private boolean add_remove_all(String userInput) {
        if (userInput.equals(ADD_ALL_COMMAND)) {
            charSet.addAll(ALL_ASCII);
            return true;
        }
        if (userInput.equals(REMOVE_ALL_COMMAND)) {
            charSet.clear();
            return true;
        }
        return false;
    }

    /**
     * this method adds or removes the space char from charSet.
     *
     * @param userInput- the user input indicates if we're adding or removing.
     * @return true if successfully added or removed the space char, false otherwise.
     */
    private boolean add_remove_space(String userInput) {
        if (userInput.equals(ADD_SPACE_COMMAND)) {
            charSet.add(INIT_VALID_ASCII_SPACE);
            return true;
        }
        if (userInput.equals(REMOVE_SPACE_COMMAND)) {
            charSet.remove(INIT_VALID_ASCII_SPACE);
            return true;
        }
        return false;
    }

    /**
     * adds or removes a range of chars from charSet.
     *
     * @param userInput the user input indicates if we're adding or removing.
     * @return true if successfully added or removed the range, false otherwise.
     */
    private boolean add_remove_range(String userInput) {
        if (checkValidRangeInput(userInput)) {
            if (userInput.startsWith(ADD_COMMAND)) {
                return helper_range(userInput, ADD_CHAR_LENGTH - 1, ADD_CHAR_LENGTH + 1);
            }
            return helper_range(userInput, REMOVE_CHAR_LENGTH - 1, REMOVE_CHAR_LENGTH + 1);
        }
        return false;
    }

    /**
     * a helper method that checks if the input is valid. called from the add_remove_range method.
     * checks if the input is in the form: add *-* or remove *-* (when * is any valid ASCII val).
     *
     * @param userInput the user input indicates if we're adding or removing.
     * @return true if the input is valid false otherwise.
     */
    private boolean checkValidRangeInput(String userInput) {
        return (userInput.length() == ADD_RANGE_LENGTH &&
                (userInput.charAt(ADD_RANGE_SYMBOL_INDEX) == RANGE_SYMBOL_ASCII) &&
                ALL_ASCII.contains(userInput.charAt(ADD_RANGE_SYMBOL_INDEX - 1)) &&
                userInput.charAt(ADD_RANGE_SYMBOL_INDEX - 1) != INIT_VALID_ASCII_SPACE &&
                ALL_ASCII.contains(userInput.charAt(ADD_CHAR_LENGTH + 1)) &&
                userInput.charAt(ADD_CHAR_LENGTH + 1) != INIT_VALID_ASCII_SPACE)
                ||
                (userInput.length() == REMOVE_RANGE_LENGTH &&
                        (userInput.charAt(REMOVE_RANGE_SYMBOL_INDEX) == RANGE_SYMBOL_ASCII) &&
                        ALL_ASCII.contains(userInput.charAt(REMOVE_CHAR_LENGTH - 1)) &&
                        userInput.charAt(REMOVE_CHAR_LENGTH - 1) != INIT_VALID_ASCII_SPACE &&
                        ALL_ASCII.contains(userInput.charAt(REMOVE_CHAR_LENGTH + 1)) &&
                        userInput.charAt(REMOVE_CHAR_LENGTH + 1) != INIT_VALID_ASCII_SPACE);
    }

    /**
     * helper method to add_remove_range that adds or removes range of chars to the charSet.
     *
     * @param userInput    the user input indicates if we're adding or removing.
     * @param leftCharInd  the left char index.(changes between add and remove commands).
     * @param RightCharInd the right char index.(changes between add and remove commands).
     * @return true after successfully adding all the chars in the range to charSet.
     */
    private boolean helper_range(String userInput, int leftCharInd, int RightCharInd) {
        char leftChar = userInput.charAt(leftCharInd);
        char rightChar = userInput.charAt(RightCharInd);

        if (leftChar > rightChar) {
            char temp = leftChar;
            leftChar = rightChar;
            rightChar = temp;
        }
        for (char i = leftChar; i < rightChar + 1; i++) {
            if (userInput.startsWith(ADD_COMMAND)) {
                add_remove_single_char(ADD_COMMAND + i);
            }
            if (userInput.startsWith(REMOVE_COMMAND)) {

                add_remove_single_char(REMOVE_COMMAND + i);
            }
        }
        return true;
    }
}
