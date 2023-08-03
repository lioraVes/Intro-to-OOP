import java.util.Scanner;

/**
 * HumanPlayer class - a Player that asks from the user the coordinates to make the move on the board.
 */
public class HumanPlayer implements Player {
    /**
     * constant string for invalid coordinates.
     */
    private static final String INVALID_COORDINATES_ERR_MSG = "Invalid coordinates, type again: ";

    /**
     * Empty constructor for HumanPlayer
     */
    public HumanPlayer() {
    }

    /**
     * This method plays a turn for the player. Asks the user for coordinates to put mark on the board.
     *
     * @param board- board
     * @param mark-  the mark of the human player
     */
    public void playTurn(Board board, Mark mark) {
        System.out.println("Player " + mark + ", type coordinates: ");
        boolean playedTurn = false;
        Scanner scanner = new Scanner(System.in);
        while (!playedTurn) {
            String coordinates = scanner.next();
            int row = Integer.parseInt(coordinates.substring(0, 1));
            int col = Integer.parseInt(coordinates.substring(1, 2));
            if (board.putMark(mark, row, col)) {
                playedTurn = true;
            } else {
                System.out.println(INVALID_COORDINATES_ERR_MSG);
            }
        }
    }
}
