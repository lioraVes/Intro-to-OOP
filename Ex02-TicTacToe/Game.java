/**
 * Game class- represents a single TicTacToe game.
 */
public class Game {
    /**
     * constants and private fields.
     * Default Board size is 4, Default WinStreak is 3.
     * A game has the fields PlayerX, PlayerO,renderer, size and winStreak.
     */
    private static final int DEFAULT_BOARD_SIZE = 4;
    private static final int DEFAULT_WIN_STREAK = 3;

    private final Player playerX;
    private final Player playerO;
    private final Renderer renderer;
    private final int size;
    private final int winStreak;

    /**
     * Constructor for default values(size and winStreak are default)
     *
     * @param playerX- player X
     * @param playerO  - player O
     * @param renderer - the renderer of the game
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.size = DEFAULT_BOARD_SIZE;
        this.winStreak = DEFAULT_WIN_STREAK;
    }

    /**
     * Constructor for game (not default values). If winStreak not valid, it will be size.
     *
     * @param playerX   - player X
     * @param playerO   - player O
     * @param size      - size of the board
     * @param winStreak - number of streaks needed for win
     * @param renderer  - the renderer
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.size = size;
        if (winStreak < 2 || winStreak > this.size) {
            this.winStreak = size;
        } else {
            this.winStreak = winStreak;
        }
    }

    /**
     * A getter for winStreak field.
     *
     * @return winStreak field
     */
    public int getWinStreak() {
        return this.winStreak;
    }


    /**
     * This method checks if some player won over rows.
     * It goes over the rows and for each row looks for a streak with the length of winStreak.
     *
     * @param board- the board.
     * @return the Mark if found a streak, Mark.Blank if no winner was found.
     */
    private Mark checkWinRow(Board board) {
        for (int i = 0; i < this.size; i++) {
            Mark winMark = board.getMark(i, 0);
            int j = 1;
            int count = 1;
            while (j < this.size && count < this.winStreak) {
                if (board.getMark(i, j) != Mark.BLANK && board.getMark(i, j) == winMark) {
                    count++;
                } else {
                    winMark = board.getMark(i, j);
                    count = 1;
                }
                j++;
            }
            if (count == winStreak) {
                return winMark;
            }
        }
        return Mark.BLANK;
    }

    /**
     * This method checks if some player won over cols.
     * It goes over the cols and for each col looks for a streak with the length of winStreak.
     *
     * @param board- the board.
     * @return the Mark if found a streak, Mark.Blank if no winner was found.
     */
    private Mark checkWinCol(Board board) {
        for (int i = 0; i < this.size; i++) {
            Mark winMark = board.getMark(0, i);
            int j = 1;
            int count = 1;
            while (j < this.size && count < this.winStreak) {
                if (board.getMark(j, i) != Mark.BLANK && board.getMark(j, i) == winMark) {
                    count++;
                } else {
                    winMark = board.getMark(j, i);
                    count = 1;
                }
                j++;
            }
            if (count == winStreak) {
                return winMark;
            }
        }
        return Mark.BLANK;
    }

    /**
     * This method checks if the coordinate(row,col) has a diagonal in the length of winStreak
     * from the right side of it.
     *
     * @param board- the board
     * @param row-   the row coordinate
     * @param col-   the col coordinate
     * @return the Mark if found a streak, Mark.Blank if no winner was found.
     */
    private Mark checkRightDiagonal(Board board, int row, int col) {
        Mark curMark = board.getMark(row, col);
        int count = 1;
        for (int k = 1; k < this.winStreak; k++) {
            if (board.getMark(row + k, col + k) != Mark.BLANK &&
                    board.getMark(row + k, col + k) == curMark) {
                count++;
            } else {
                return Mark.BLANK;
            }
        }
        if (count == this.winStreak) {
            return curMark;
        }
        return Mark.BLANK;
    }

    /**
     * This method checks if the coordinate(row,col) has a diagonal in the length of winStreak
     * from the left side of it.
     *
     * @param board- the board
     * @param row-   the row coordinate
     * @param col-   the col coordinate
     * @return the Mark if found a streak, Mark.Blank if no winner was found.
     */
    private Mark checkLeftDiagonal(Board board, int row, int col) {
        Mark curMark = board.getMark(row, col);
        int count = 1;
        for (int k = 1; k < this.winStreak; k++) {
            if (board.getMark(row - k, col - k) != Mark.BLANK &&
                    board.getMark(row - k, col - k) == curMark) {
                count++;
            } else {
                return Mark.BLANK;
            }
        }
        if (count == this.winStreak) {
            return curMark;
        }
        return Mark.BLANK;
    }

    /**
     * This method checks if some player won over diagonals.
     * It goes over the cols and the rows and for each coordinate, it checks if there is a win over right
     * diagonal or left diagonal.
     *
     * @param board- the board
     * @return the Mark if found a streak, Mark.Blank if no winner was found.
     */
    private Mark checkWinDiagonal(Board board) {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Mark rightDiagonal = checkRightDiagonal(board, i, j);
                if (rightDiagonal != Mark.BLANK) {
                    return rightDiagonal;
                }
                Mark leftDiagonal = checkLeftDiagonal(board, i, j);
                if (leftDiagonal != Mark.BLANK) {
                    return leftDiagonal;
                }
            }
        }
        return Mark.BLANK;
    }

    /**
     * This method goes over the board and checks if some player won the game.
     *
     * @param board - the board
     * @return the Mark that won, or Mark.Blank if no one won.
     */
    private Mark checkWin(Board board) {
        Mark rowWinner = checkWinRow(board);
        if (rowWinner != Mark.BLANK) {
            return rowWinner;
        }
        Mark colWinner = checkWinCol(board);
        if (colWinner != Mark.BLANK) {
            return colWinner;
        }
        return checkWinDiagonal(board);
    }

    /**
     * This method runs a single game. It initializes array of two Players- PlayerX, PlayerO, and array of
     * its marks(X and O). PlayerX plays first and PlayerO second. After each move it looks for a winner.
     * and in the end it returns the winner Mark.
     *
     * @return Mark of the winner or Blank for tie.
     */
    public Mark run() {
        Board board = new Board(this.size);

        Player[] players = new Player[2];
        players[0] = this.playerX;
        players[1] = this.playerO;

        Mark[] marks = new Mark[2];
        marks[0] = Mark.X;
        marks[1] = Mark.O;

        for (int i = 0; i < this.size * this.size; i++) {
            this.renderer.renderBoard(board);
            players[i % 2].playTurn(board, marks[i % 2]);
            Mark isWinner = checkWin(board);
            if (isWinner != Mark.BLANK) {
                return isWinner;
            }
        }
        return checkWin(board);
    }
}

