/**
 * Tournament class-plays a tournament of games.
 */
public class Tournament {
    /**
     * constants and private fields.
     * INVALID_NAMES_ERR_MSG, HUMAN_PLAYER, GENIUS_PLAYER, WHATEVER_PLAYER, CLEVER_PLAYER are constants
     * strings.
     * 0,1,2,3,4,5 are the indexes of numRounds, boardSize, winStreak, renderer, playerOne, pLayerTwo
     * arguments.
     * A Tournament has the fields rounds, renderer, and players.
     */
    private static final String INVALID_NAMES_ERR_MSG = "Choose a player, and start again\n" +
            "The players: [human, clever, whatever, genius]";

    private static final String HUMAN_PLAYER = "human";
    private static final String GENIUS_PLAYER = "genius";
    private static final String WHATEVER_PLAYER = "whatever";
    private static final String CLEVER_PLAYER = "clever";

    private static final int NUM_ROUNDS_IND = 0;
    private static final int BOARD_SIZE_IND = 1;
    private static final int WIN_STREAK_IND = 2;
    private static final int RENDER_NAME_IND = 3;
    private static final int PLAYER_ONE_IND = 4;
    private static final int PLAYER_TWO_IND = 5;

    private final int rounds;
    private final Renderer renderer;
    private final Player[] players;

    /**
     * this method checks the validity of the players names.
     *
     * @param args- the arguments the function got.
     * @return true if valid, false otherwise.
     */
    private static boolean checkValidNames(String[] args) {

        String[] players = new String[2];
        players[0] = args[PLAYER_ONE_IND].toLowerCase();
        players[1] = args[PLAYER_TWO_IND].toLowerCase();
        int countValid = 0;
        for (int i = 0; i < 2; i++) {
            switch (players[i]) {
                case HUMAN_PLAYER:

                case GENIUS_PLAYER:

                case WHATEVER_PLAYER:

                case CLEVER_PLAYER:
                    countValid++;
                default:
                    continue;
            }
        }
        return countValid == 2;
    }

    /**
     * the main function. It first checks the validity of the names of the players, if they are not valid
     * it stops the program and prints a message.
     * It calls playerFactory and RendererFactory that creates players and a renderer and starts the
     * tournament.
     *
     * @param args- the arguments the function got.
     */
    public static void main(String[] args) {
        if (!checkValidNames(args)) {
            System.out.println(INVALID_NAMES_ERR_MSG);
            return;
        }
        //ARGUMENTS
        int numRounds = Integer.parseInt(args[NUM_ROUNDS_IND]);
        int boardSize = Integer.parseInt(args[BOARD_SIZE_IND]);
        int winStreak = Integer.parseInt(args[WIN_STREAK_IND]);
        String renderName = args[RENDER_NAME_IND];
        String playerOne = args[PLAYER_ONE_IND].toLowerCase();
        String playerTwo = args[PLAYER_TWO_IND].toLowerCase();

        String[] playersNames = new String[2];
        playersNames[0] = args[PLAYER_ONE_IND];
        playersNames[1] = args[PLAYER_TWO_IND];

        Player[] players = new Player[2];
        PlayerFactory playerFactory = new PlayerFactory();
        players[0] = playerFactory.buildPlayer(playerOne);
        players[1] = playerFactory.buildPlayer(playerTwo);

        RendererFactory rendererFactory = new RendererFactory();
        Renderer rend = rendererFactory.buildRenderer(renderName, boardSize);

        Tournament tournament = new Tournament(numRounds, rend, players);
        tournament.playTournament(boardSize, winStreak, playersNames);
    }

    /**
     * Constructor for a tournament.Initialize the tournament.
     *
     * @param rounds   - number of games to play.
     * @param renderer - the renderer of the game.
     * @param players  - array of players that plays the game.
     */
    public Tournament(int rounds, Renderer renderer, Player[] players) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.players = players;
    }

    /**
     * This method plays a tournament. It plays this.rounds games. on the even number of games the first
     * player is X and on the odd the first player is O. In the end it prints the number of winning of each
     * player and the number of ties.
     *
     * @param size        - the size of the board
     * @param winStreak   - the number of winStreak.
     * @param playerNames - the playerNames.
     */
    public void playTournament(int size, int winStreak, String[] playerNames) {
        int firstPlayerWins = 0;
        int secondPlayerWins = 0;
        int numTies = 0;
        Mark curWinner = Mark.BLANK;

        for (int i = 0; i < this.rounds; i++) {
            Game game = new Game(this.players[i % 2 == 0 ? 0 : 1], this.players[i % 2 == 1 ? 0 : 1], size, winStreak,
                    this.renderer);
            curWinner = game.run();

            switch (curWinner) {
                case X:
                    if (i % 2 == 0) {
                        firstPlayerWins++;
                        continue;
                    }
                    secondPlayerWins++;
                    break;
                case O:
                    if (i % 2 == 0) {
                        secondPlayerWins++;
                        continue;
                    }
                    firstPlayerWins++;
                    break;
                default:
                    numTies++;
            }
        }

        System.out.println("######### Results #########");
        System.out.println("Player 1, " + playerNames[0] + " won: " + firstPlayerWins + " rounds");
        System.out.println("Player 2, " + playerNames[1] + " won: " + secondPlayerWins + " rounds");
        System.out.println("Ties: " + numTies);
    }
}
