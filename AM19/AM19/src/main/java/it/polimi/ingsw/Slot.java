package it.polimi.ingsw;

import java.util.LinkedList;

/**
 * This class represents a slot which contains the purchased development cards
 */
public class Slot {

    /**
     * cards represents the LinkedList which contains all the cards present in the slot.
     */
    private LinkedList<DevelopmentCard> cards;

    /**
     * This is the constructor of this class.
     * No parameters are required
     */
    public Slot() {
        cards = new LinkedList<>();
    }

    /**
     * This method checks if a card can be inserted in the slot
     * It also checks (indirectly) that it is not possible to insert more than three cards
     * @param card the card you want to insert
     * @return true if the card can be inserted, false otherwise
     */
    private boolean isInsertable(DevelopmentCard card){
        if(card.getCardLevel() == 1 && cards.size() == 0)
            return true;
        if(cards.size() == 0)//useful to avoid calling getLast().getCardLevel if there is not any card
            return false;
        if(card.getCardLevel() == 2 && cards.getLast().getCardLevel() == 1)
            return true;
        if(card.getCardLevel() == 3 && cards.getLast().getCardLevel() == 2)
            return true;
        return false;
    }

    /**
     * This method inserts a card in the slot.
     * An IllegalSlotException is thrown if the card can't be inserted.
     * @param card the card you want to insert
     * @throws IllegalSlotException
     */
    public void insertCard(DevelopmentCard card) throws IllegalSlotException{
        if(!isInsertable(card))
            throw new IllegalSlotException("This card can't be inserted!");
        cards.add(card);
    }

    /**
     * This method gets the last card added to the slot (the card on the top).
     * An IllegalSlotException is thrown if the slot is empty.
     * @return the last card added to the slot
     * @throws IllegalSlotException
     */
    public DevelopmentCard getTop() throws IllegalSlotException{

        if(cards.size() == 0)
            throw new IllegalSlotException("Empty slot!");
        return cards.getLast();
    }

    /**
     * This method gets the card present at the position 'position'
     * An IllegalSlotException is thrown if there is not a card at tha position 'position'
     * @param position of the card you want to get
     * @return card you want to get
     * @throws IllegalSlotException
     */
    public DevelopmentCard getCard(int position) throws IllegalSlotException{
        if(position < 0 || position >= cards.size())
            throw new IllegalSlotException("This slot does not exist!");
        return cards.get(position);
    }

    /**
     * This method returns the sum of all the victory points of the cards present in the slot
     * @return sum of victory points
     */
    public int countPoints(){
        if(cards.size() == 0)
            return 0;
        return cards.stream().mapToInt(developmentCard -> developmentCard.getVictoryPoint()).sum();
    }

    /**
     * This method returns the number of cards present in the slot
     * @return number of cards present in the slot
     */
    public int getNumberOfCards(){
        return cards.size();
    }
}
