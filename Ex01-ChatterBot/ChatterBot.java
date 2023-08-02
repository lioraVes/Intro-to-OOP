import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 *
 * @author Dan Nirel
 */
class ChatterBot {
    static final String REQUEST_PREFIX = "say ";
    static final String REQUESTED_PHRASE_PLACEHOLDER = "<phrase>";
    static final String ILLEGAL_REQUEST_PLACEHOLDER = "<request>";
    Random rand = new Random();
    String[] repliesToIllegalRequest;
    String[] repliesToLegalRequest;
    String name;

    ChatterBot(String name, String[] repliesToLegalRequest, String[] repliesToIllegalRequest) {
        this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
        for (int i = 0; i < repliesToIllegalRequest.length; i = i + 1) {
            this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
        }
        this.repliesToLegalRequest = repliesToLegalRequest;
        for (int i = 0; i < repliesToLegalRequest.length; i++) {
            this.repliesToLegalRequest[i] = repliesToLegalRequest[i];
        }
        this.name = name;
    }

    /**
     * This function gets a string and returns a reply to it.
     *
     * @param statement - a string to reply to.
     * @return - the reply to the string.
     */
    String replyTo(String statement) {
        if (statement.startsWith(REQUEST_PREFIX)) {
            String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
            return replacePlaceholderInARandomPattern(repliesToLegalRequest,
                    REQUESTED_PHRASE_PLACEHOLDER,
                    phrase);
        }
        return replacePlaceholderInARandomPattern(repliesToIllegalRequest,
                ILLEGAL_REQUEST_PLACEHOLDER,
                statement);
    }

    /**
     * This function gets an array of possible replies, a substring to replace and a string to replace it
     * with.
     *
     * @param repliesToRequest - an array of possible replies
     * @param subString        - substring that is in the replies.
     * @param replaceSubString - a string to replace the substring with.
     * @return a new string.
     */
    String replacePlaceholderInARandomPattern(String[] repliesToRequest, String subString, String
            replaceSubString) {
        int randomIndex = rand.nextInt(repliesToRequest.length);
        String reply = repliesToRequest[randomIndex];
        return reply.replaceAll(subString, replaceSubString);
    }

    /**
     * @return the name of the bots.
     */
    String getName() {
        return this.name;
    }
}
