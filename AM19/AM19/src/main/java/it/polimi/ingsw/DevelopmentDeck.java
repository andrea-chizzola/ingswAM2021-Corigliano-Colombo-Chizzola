package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.CardParser;

import java.util.LinkedList;

/**
 * this class represents a deck of DevelopmentCards
 * It gives the method to create and extract the cards from the deck.
 */
public class DevelopmentDeck {
    /**
     * this attribute contains the DevelopmentCards that will be used during the game.
     */
    private Container<DevelopmentCard> deck;

    /**
     * the constructor of the class creates the DevelopmentCards that will be used during the game
     * @param file is the name of the file that contains the characteristics of the DevelopmentCards
     */
    public DevelopmentDeck(String file){
        deck = new Container<>();
        CardParser parser = CardParser.instance();
        deck.addAll(parser.parseDevelopmentCard(file));
        deck.shuffle();
    }

    /**
     * this method extract the top cards of the deck
     * @param num is the number of cards to be extracted
     * @return the list containing the extracted cards
     */
    public LinkedList<DevelopmentCard> extract (int num) throws IllegalArgumentException{
        return deck.extractTop(num);
    }

    /**
     * This method reads the top element of the deck.
     * It does not extract that element.
     * @return a copy of the top card of the deck
     */
    public DevelopmentCard readTop() throws IllegalArgumentException{
        return deck.readTop();
    }

    /**
     * Ths method extract the top element of the deck
     * @return the top card of the deck
     */
    public DevelopmentCard getTop() throws IllegalArgumentException{
        return deck.extractTop();
    }
}
