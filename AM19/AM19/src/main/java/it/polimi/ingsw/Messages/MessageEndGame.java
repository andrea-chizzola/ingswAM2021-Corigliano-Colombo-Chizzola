package it.polimi.ingsw.Messages;

import java.util.*;

public class MessageEndGame extends Message{

    /**
     * contains the final points of each player
     */
    private Map<String, Integer> results;

    /**
     * creates a new message containing the points achieved by each player
     * @param winnerMessage represents the final leaderboard
     * @param results contains the final points of each player
     */
    public MessageEndGame(String winnerMessage, Map<String, Integer> results){

        super(winnerMessage, MessageType.GAME_STATUS);
        this.results = new HashMap<>();
        this.results.putAll(results);

    }

    /**
     * @param nickName represents the nickname of a player
     * @return returns the final points associated to the selected player
     */
    public int getPoints(String nickName) {
        return results.getOrDefault(nickName, 0);
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString() {
        return "MessageEndGame{" +
                "results=" + results +
                "\n" +
                super.toString() +
                '}';
    }
}
