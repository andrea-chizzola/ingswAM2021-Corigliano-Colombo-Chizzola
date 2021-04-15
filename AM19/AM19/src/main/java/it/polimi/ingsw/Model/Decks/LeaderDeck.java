package it.polimi.ingsw.Model.Decks;

import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.LinkedList;

/**
 * this class represents a deck of LeaderCards
 * It gives the method to create and extract the cards from the deck.
 */
public class LeaderDeck {
    /**
     * this attribute contains the DevelopmentCards that will be used during the game.
     */
    private Container<LeaderCard> deck;

    /**
     * the constructor of the class creates the LeaderCards that will be used during the game
     * @param file is the name of the file that contains the characteristics of the LeaderCards
     */
    public LeaderDeck(String file){
        deck = new Container<>();
        deck.addAll(ConfigurationParser.parseLeaderCard(file));
        deck.shuffle();
    }

    /**
     * this method extract the top cards of the deck
     * @param num is the number of cards to be extracted
     * @return the list containing the extracted cards
     */
    public LinkedList<LeaderCard> extract (int num) throws IllegalArgumentException{
        return deck.extractTop(num);
    }

}
