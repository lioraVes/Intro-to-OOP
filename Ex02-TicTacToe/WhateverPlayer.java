import java.util.Random;

/**
 * WhateverPlayer class - a Player that choose the coordinates randomly.
 */
public class WhateverPlayer implements Player {
    /**
     * This method plays a turn for the whatever player on the board. It chooses a random number for the
     * row coordinate and for the col coordinate.
     *
     * @param board -the board
     * @param mark- the mark of whatever player.
     */
    public void playTurn(Board board, Mark mark) {
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