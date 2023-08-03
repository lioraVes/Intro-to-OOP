/**
 * Board class - the board of the TicTacToe game.
 */
public class Board {
    /**
     * constants and private fields.
     * Default Board size is 4.
     * A board has the fields boardArr and boardSize.
     */
    private static final int DEFAULT_BOARD_SIZE = 4;
    private final Mark[][] boardArr;
    private final int boardSize;

    /**
     * This method initializes all the cells in the boardArr to Mark.Blank
     *
     * @param arr  - the board array.
     * @param size -the size of the board
     */
    private void initializeBoard(Mark[][] arr, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = Mark.BLANK;
            }
        }
    }

    /**
     * Default constructor for Board class. Initializes the board to be of size 4X4.
     */
    public Board() {
        this.boardArr = new Mark[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
        this.boardSize = DEFAULT_BOARD_SIZE;
        initializeBoard(this.boardArr, this.boardSize);
    }

    /**
     * Constructor for Board class. Initializes the Board to be of size:size x size.
     *
     * @param size- the size of the rows and the cols of the board.
     */
    public Board(int size) {
        this.boardArr = new Mark[size][size];
        this.boardSize = size;
        initializeBoard(this.boardArr, this.boardSize);
    }

    /**
     * A getter for the size of the board.
     *
     * @return the size of the board. (size of rows(= cols)).
     */
    public int getSize() {
        return this.boardSize;
    }

    /**
     * A getter for the board array.
     *
     * @return the board array itself.
     */
    public Mark[][] getBoard() {
        return this.boardArr;
    }

    /**
     * This method puts marks on the board.
     *
     * @param mark- the mark to put on the board
     * @param row-  the row to put the mark in.
     * @param col-  the col to put the mark in.
     * @return true - if successfully marked the (row,col) cell, false otherwise.
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
            return false;
        }
        if (boardArr[row][col] == Mark.BLANK) {
            boardArr[row][col] = mark;
            return true;
        }
        return false;
    }

    /**
     * This method gets row and col and returns the mark that is in it.
     *
     * @param row - the wanted row.
     * @param col -the wanted col.
     * @return if the parameters are not valid returns Mark.Blank, otherwise the Mark.
     */
    public Mark getMark(int row, int col) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
            return Mark.BLANK;
        }
        return boardArr[row][col];
    }
}
