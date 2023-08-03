import java.util.Random;

/**
 * CleverPlayer class - a Player that plays better than WhateverPlayer. Its strategy is to fill as
 * many lines as it can (with the same mark).
 */
public class CleverPlayer implements Player {
    /**
     * This method looks for the wanted mark on the board, then checks if there is Blank by it (in the same
     * line) or a sequence of the mark and tries to put the mark in the first available place.
     *
     * @param board - the board
     * @param mark  -the mark
     * @return true if successfully found new place to continue a sequence, false otherwise.
     */
    private boolean fillLines(Board board, Mark mark) {
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
     * This method plays a turn for CleverPlayer. Tries to fill a line on the board and if that doesn't
     * succeed it chooses a random coordinate on the board.
     *
     * @param board-the board
     * @param mark-the  mark
     */
    public void playTurn(Board board, Mark mark) {
        if (!fillLines(board, mark)) {
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
