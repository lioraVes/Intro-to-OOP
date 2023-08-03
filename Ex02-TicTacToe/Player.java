/**
 * Player interface.
 */
interface Player {
    /**
     * Each player has the playTurn method which responsible to make a move on the board with
     * the given mark.
     *
     * @param board -the board
     * @param mark  -the mark
     */
    void playTurn(Board board, Mark mark);
}
