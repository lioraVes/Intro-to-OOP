/**
 * PlayerFactory- Factory that generates Players.
 */
public class PlayerFactory {
    /**
     * Constants for the switch case.
     */
    private static final String HUMAN_PLAYER = "human";
    private static final String GENIUS_PLAYER = "genius";
    private static final String WHATEVER_PLAYER = "whatever";
    private static final String CLEVER_PLAYER = "clever";

    /**
     * Empty constructor for PlayerFactory.
     */
    public PlayerFactory() {
    }

    /**
     * This method gets a string and generates the wanted Player.
     *
     * @param type - the string name of the wanted Player.
     * @return the wanted Player object.
     */
    public Player buildPlayer(String type) {
        String lowerCaseType=type.toLowerCase();
        Player player;
        switch (lowerCaseType) {
            case HUMAN_PLAYER:
                player = new HumanPlayer();
                return player;
            case GENIUS_PLAYER:
                player = new GeniusPlayer();
                return player;
            case WHATEVER_PLAYER:
                player = new WhateverPlayer();
                return player;
            case CLEVER_PLAYER:
                player = new CleverPlayer();
                return player;
            default:
                return null;
        }
    }
}
