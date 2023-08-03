/**
 * Renderer Interface- visualise the board of the game.
 */
interface Renderer {
    /**
     * Each renderer has the renderBoard method which responsible to visualise the board.
     *
     * @param board-the board
     */
    void renderBoard(Board board);
}