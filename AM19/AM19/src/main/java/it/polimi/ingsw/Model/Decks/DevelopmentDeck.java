package it.polimi.ingsw.Model.Decks;

import it.polimi.ingsw.Model.Cards.Colors.*;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
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
     * indicates the maximum level of a DevelopmentCard
     */
    private final int maxLevel;

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
        maxLevel = ConfigurationParser.getMaxLevel(file);

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
     * @throws IllegalArgumentException if the target deck does not exist
     */

     //to be modified using the configuration file. The extraction is related to Action Token, that only controls color
     //and num. You need to get "number of decks" from file
    public LinkedList<DevelopmentCard> extract (int num, CardColor color)
            throws IllegalArgumentException{
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

    /**
     * Given a color and a level, this method returns the number of cards corresponding
     * @param color is the color of the cards in the deck
     * @param level is the level of the cards in the deck
     * @return returns the number of cards corresponding to the color and level specified
     */
    public int getNumber(CardColor color, int level){

        ColorLevelKey colorLevelKey = new ColorLevelKey(color, level);

        if(decks.containsKey(colorLevelKey)) {

            return decks.get(colorLevelKey).size();

        }else return 0;
    }

    /**
     * @param cardColor indicates one of the colors of the DevelopmentCards
     * @return returns false if all the DevelopmentCards of the selected color have been eliminated, true otherwise
     */
    public boolean isColorAvailable(CardColor cardColor){
        return getNumber(cardColor, maxLevel) > 0;
    }

    /**
     * @return Map of (Integer,String) which represents the cards on the top of all the decks
     */
    public Map<Integer,String> showDeck(){
        Map<Integer,String> map = new HashMap<>();
        DevelopmentCard card;
        List<CardColor> colors = new LinkedList<>();
        colors.add(new Green());
        colors.add(new Blue());
        colors.add(new Yellow());
        colors.add(new Purple());

        for(int i=1; i<=maxLevel; i++){
            for(int j=0; j<colors.size(); j++){
                try {
                    card = readTop(colors.get(j), i);
                    map.put((i-1)*colors.size()+j, card.getId());
                }catch (IllegalArgumentException e){/*e.printStackTrace();*/}
            }
        }

        return map;
    }
}
