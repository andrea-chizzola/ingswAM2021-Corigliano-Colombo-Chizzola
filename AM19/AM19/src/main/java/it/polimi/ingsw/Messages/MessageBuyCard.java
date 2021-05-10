package it.polimi.ingsw.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.Resources.ResQuantity;

import java.util.LinkedList;
import java.util.List;

/**
 * message to be used to buy a card
 */
public class MessageBuyCard extends Message{

    /**
     * the color of the card to buy
     */
    @JsonProperty("color")
    private String color;

    /**
     * the level of the card to buy
     */
    @JsonProperty("level")
    private int level;

    /**
     * the slot where the card is put
     */
    @JsonProperty("slot")
    private int slot;

    /**
     * the id of the card
     */
    @JsonProperty("ID")
    private String ID;

    /**
     * It contains all the information about the shelves and the quantities in this way: numberOfShelf:numberOfResources...
     */
    //1:1:2:1:3:3
    @JsonProperty("warehouse")
    private String warehouse;


    /**
     * It represents all the resources selected from the strongBox in this way: nameOfResource:numberOfResources...
     */
    //coins:1:servants:5:shields:3
    @JsonProperty("strongBox")
    private String strongBox;

    /**
     * it sets MessageType as BUY_CARD
     * @param color the color of the card to buy
     * @param level the level of the card to buy
     * @param slot the slot where the card is put
     * @param ID the id of the card
     * @param warehouse a String which contains all the information about the shelves and the quantities in this way: numberOfShelf:numberOfResources...
     * @param strongBox a String which represents all the resources selected from the strongBox in this way: nameOfResource:numberOfResources...
     */
    @JsonCreator
    public MessageBuyCard(@JsonProperty("color") String color,@JsonProperty("level") int level, @JsonProperty("slot")int slot,
                          @JsonProperty("ID") String ID,@JsonProperty("warehouse") String warehouse, @JsonProperty("strongBox") String strongBox) {
        super("Buy card managing.", MessageType.BUY_CARD);
        this.color = color;
        this.level = level;
        this.slot = slot;
        this.ID = ID;
        this.warehouse = warehouse;
        this.strongBox = strongBox;
    }


    /**
     * @return the color of the card to buy
     */
    @JsonIgnore
    public CardColor getColor() throws MalformedMessageException {
        return MessageUtilities.instance().getCardColor(color);
    }

    /**
     * @return the slot where the card is put
     */
    @JsonIgnore
    public int getSlot() {
        return slot;
    }

    /**
     * @return the level of the card to buy
     */
    @JsonIgnore
    public int getLevel() {
        return level;
    }

    /**
     * @return the id of the card
     */
    @JsonIgnore
    public String getID() {
        return ID;
    }

    /**
     * @return ArrayList of Integer which represents all the shelves selected
     */
    @JsonIgnore
    public List<Integer> getShelves() throws MalformedMessageException {
        return MessageUtilities.instance().getShelves(warehouse);
    }

    /**
     * @return ArrayList of Integer which represents the amount of resources selected for each shelf
     */
    @JsonIgnore
    public List<Integer> getQuantity() throws MalformedMessageException {
        return MessageUtilities.instance().getQuantity(warehouse);
    }

    /**
     * @return ArrayList of ResQuantity which represents all the resources selected from the strongBox
     */@JsonIgnore
    public List<ResQuantity> getStrongBox() throws MalformedMessageException {
        return MessageUtilities.instance().getResQuantityList(strongBox);
    }
}
