package it.polimi.ingsw.Messages;

import java.util.LinkedList;
import java.util.List;

public class UpdateDecks extends UpdateGame {

    /**
     * this attribute represent a set of top cards in the common decks
     */
    List<String> decks;

    /**
     * this is the constructor of the class
     * @param decks represent a set of top cards in the common decks
     */
    public UpdateDecks(List<String> decks) {
        super("Update of the GameBoard decks", UpdateGameType.DECK);
        this.decks = new LinkedList<>(decks);
    }

    /**
     * @return returns a copy of the attribute decks
     */
    public List<String> getDecks(){
        return new LinkedList<>(decks);
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString(){
        StringBuilder cardString = new StringBuilder();
        for(String s : decks){
            cardString.append(s).append('\'');
        }
        return "Message{" +
                "body='" + getBody() + '\'' +
                cardString +
                ", messageType=" + getMessageType() +
                '}';
    }
}
