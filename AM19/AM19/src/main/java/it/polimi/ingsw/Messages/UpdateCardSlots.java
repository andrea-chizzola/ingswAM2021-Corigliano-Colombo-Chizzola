package it.polimi.ingsw.Messages;

import java.util.HashMap;
import java.util.Map;

public class UpdateCardSlots extends UpdateBoard {

    /**
     * this attribute represents a set of slots with their top card
     */
    Map<Integer, String> slots;
    public UpdateCardSlots(Map<Integer, String> slots) {
        super("Update of a player's card slots", UpdateBoardType.CARD_UPDATE);
        this.slots = new HashMap<>(slots);
    }

    /**
     * @return a copy of the attribute slots
     */
    public Map<Integer, String> getSlots(){
        return new HashMap<>(slots);
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString(){
        StringBuilder cardString = new StringBuilder();
        for(int i : slots.keySet()){
            cardString.append("slot").append(i).append("card").append(slots.get(i)).append('\'');
        }
        return "Message{" +
                "body='" + getBody() + '\'' +
                cardString +
                ", messageType=" + getMessageType() +
                '}';
    }
}
