import java.util.Random;

/**
 * the GeniusPlayer class- a player that plays better than CleverPlayer. Its strategy is to fill as
 * many lines, columns and diagonals as it can (with the same mark).
 */
public class GeniusPlayer implements Player {
    /**
     * This method looks for the wanted mark on the board, then checks if there is Blank by it (in the same
     * left diagonal the starts in the coordinate (i,j) ) or a sequence of the mark and tries to put the mark
     * in the first available place.
     *
     * @param board - the board
     * @param mark  -the mark
     * @return true if successfully found new place to continue a sequence, false otherwise.
     */
    private boolean fillDiagonalsLeft(Board board, Mark mark) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                int k = 0;
                while (board.getMark(i - k, j - k) == mark) {
                    k++;
                    if (i - k < board.getSize() && j - k < board.getSize() &&
                            board.getMark(i - k, j - k) == Mark.BLANK) {
                        return board.putMark(mark, i - k, j - k);
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method looks for the wanted mark on the board, then checks if there is Blank by it (in the same
     * right diagonal the starts in the coordinate (i,j) ) or a sequence of the mark and tries to put the mark
     * in the first available place.
     *
     * @param board - the board
     * @param mark  -the mark
     * @return true if successfully found new place to continue a sequence, false otherwise.
     */
    private boolean fillDiagonalsRight(Board board, Mark mark) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                int k = 0;
                while (board.getMark(i + k, j + k) == mark) {
                    k++;
                    if (i + k < board.getSize() && j + k < board.getSize() &&
                            board.getMark(i + k, j + k) == Mark.BLANK) {
                        return board.putMark(mark, i + k, j + k);
                    }
                }
            }
        }
        return false;
    }


    /**
     * This method looks for the wanted mark on the board, then checks if there is Blank by it (in the same
     * column) or a sequence of the mark and tries to put the mark in the first available place.
     *
     * @param board - the board
     * @param mark  -the mark
     * @return true if successfully found new place to continue a sequence, false otherwise.
     */
    private boolean fillCols(Board board, Mark mark) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                while (board.getMark(j, i) == mark) {
                    j++;
                    if (j < board.getSize() && board.getMark(j, i) == Mark.BLANK) {
                        return board.putMark(mark, j, i);
                    }
                }
            }
        }
        return false;

    }

    /**
     * This method looks for the wanted mark on the board, then checks if there is Blank by it (in the same
     * line) or a sequence of the mark and tries to put the mark in the first available place.
     *
     * @param board - the board
     * @param mark  -the mark
     * @return true if successfully found new place to continue a sequence, false otherwise.
     */
    private boolean fillLines(Board board, Mark mark) {
        //first-find the first occurrence of mark, then check if there is place near its sequence
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                while (board.getMark(i, j) == mark) {
                    j++;
                    if (j < board.getSize() && board.getMark(i, j) == Mark.BLANK) {
                        return board.putMark(mark, i, j);
                    }
                }
            }
        }
        return false;
    }

    /**
     * this method plays a turn for the GeniusPlayer. Tries to fill a line or a column or a diagonal on the
     * board and if that doesn't succeed it chooses a random coordinate on the board.
     *
     * @param board - the board
     * @param mark  - the mark
     */
    public void playTurn(Board board, Mark mark) {
        if (!fillLines(board, mark) && !fillCols(board, mark) && !fillDiagonalsRight(board, mark) &&
                !fillDiagonalsLeft(board, mark)) {
            boolean playedTurn = false;
            while (!playedTurn) {
                Random random = new Random();
                int randRow = random.nextInt(board.getSize());
                int randCol = random.nextInt(board.getSize());
                if (board.putMark(mark, randRow, randCol)) {
                    playedTurn = true;
                }
            }
        }
    }
}
