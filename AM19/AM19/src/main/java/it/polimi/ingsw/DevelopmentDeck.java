package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.CardParser;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.*;

/**
 * this class represents a deck of DevelopmentCards. The cards are split by level and color
 * It gives the method to create and extract the cards from the deck.
 */
public class DevelopmentDeck {
    /**
     * this attribute contains the Decks of DevelopmentCards that will be used during the game.
     */
    private Map<ColorLevelKey, Container<DevelopmentCard>> decks;

    /**
     * this private class provides a unique key to map cards into decks
     */
    private class ColorLevelKey{
        CardColor color;
        int level;

        private ColorLevelKey(CardColor color, int level){
            this.color=color;
            this.level=level;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ColorLevelKey that = (ColorLevelKey) o;
            return level == that.level &&
                    Objects.equals(color, that.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(color, level);
        }
    }

    /**
     * the constructor of the class creates the decks that will be used during the game
     * @param file is the name of the file that contains the characteristics of the DevelopmentCards
     */
    public DevelopmentDeck(String file){
        List<DevelopmentCard> cards = ConfigurationParser.parseDevelopmentCard(file);
        Collections.shuffle(cards);

        decks = new HashMap<>();

        for(DevelopmentCard card : cards){
            ColorLevelKey key = new ColorLevelKey(card.getCardColor(), card.getCardLevel());
            if( !decks.containsKey(key)) decks.put(key, new Container<>());
            decks.get(key).add(card);
        }
    }

    /**
     *
     *This method extract the top cards of the a deck
     * @param num is the number of cards to be extracted
     * @param color is the color of the extracted cards
     * @return the extracted cards
     * @throws LorenzoWonException if all the decks of a given color are empty, and therefore Lorenzo won
     * @throws IllegalArgumentException if the target deck does not exist
     */

     //to be modified using the configuration file. The extraction is related to Action Token, that only controls color
     //and num. You need to get "number of decks" from file
    public LinkedList<DevelopmentCard> extract (int num, CardColor color)
            throws LorenzoWonException, IllegalArgumentException{
        LinkedList<DevelopmentCard> extracted = new LinkedList<>();
        int n = num;

        for(int level=1; level<4; level++){
            try{
                extracted.addAll(decks
                        .get(new ColorLevelKey(color, level))
                        .extractTop(n));
                n=num-extracted.size();
            }
            //If a IllegalArgumentException is thrown, the target deck is empty.
            //We have to check the corresponding deck of higher level
            catch(IllegalArgumentException ignored){}
            //If a low level deck does not exist, the cards have not been build properly.
            //In order to have cards of higher level, we should also have cards of lower level
            catch(NullPointerException e){
                throw new IllegalArgumentException("The deck does not exist");
            }
        }
        if(decks.get(new ColorLevelKey(color,3)).size() == 0 ) throw new LorenzoWonException();
        return extracted;
    }

    /**
     * This method reads the top element of a deck of cards.
     * It does not extract that element.
     * @param color is the color of the cards in the deck
     * @param level is the level of the cards in the deck
     * @return a copy of the top card in the deck
     * @throws IllegalArgumentException if the deck is empty or does not exist
     */
    public DevelopmentCard readTop(CardColor color, int level) throws IllegalArgumentException{
        try{
            return decks
                    .get(new ColorLevelKey(color, level))
                    .readTop();
        }
        catch(NullPointerException e){
            throw new IllegalArgumentException("The deck does not exist");
        }
    }

    /**
     * This method reads the top element of a deck of cards.
     * It extracts that element.
     * @param color is the color of the cards in the deck
     * @param level is the level of the cards in the deck
     * @return the top card in the deck
     * @throws IllegalArgumentException if the deck is empty or does not exist
     */
    public DevelopmentCard getTop(CardColor color, int level) throws IllegalArgumentException{
        try{
            return decks
                    .get(new ColorLevelKey(color, level))
                    .extractTop();
        }
        catch(NullPointerException e){
            throw new IllegalArgumentException("The deck does not exist");
        }
    }
}
