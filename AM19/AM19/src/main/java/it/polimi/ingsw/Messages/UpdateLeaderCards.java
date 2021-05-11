package it.polimi.ingsw.Messages;

import java.util.HashMap;
import java.util.Map;

public class UpdateLeaderCards extends UpdateBoard {

    /**
     * this attribute represent the LeaderCards of a player, with their Status
     */
    private Map<String, ItemStatus> cards;

    /**
     * this method is the constructor of the class
     * @param cards is a map containing a set of leader cards and their status
     */
    public UpdateLeaderCards(Map<String, ItemStatus> cards) {
        super("Update of the current player's leader cards", UpdateBoardType.LEADER);
        this.cards = new HashMap<>();
        this.cards.putAll(cards);
    }

    /**
     * @return a copy of the attribute cards, which is a set of LeaderCards IDs and their status.
     */
    public Map<String, ItemStatus> getLeaderCards(){
        return new HashMap<>(cards);
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString(){
        StringBuilder cardString = new StringBuilder();
        for(String s : cards.keySet()){
            cardString.append(s).append('\'');
        }
        return "Message{" +
                "body='" + getBody() + '\'' +
                cardString +
                ", messageType=" + getMessageType() +
                '}';
    }

}
