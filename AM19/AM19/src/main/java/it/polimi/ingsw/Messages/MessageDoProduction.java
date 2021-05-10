package it.polimi.ingsw.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Model.Resources.ResQuantity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * message to be used to activate the production
 */
public class MessageDoProduction extends Message {

    /**
     * true if the personal board Production is active
     */
    @JsonProperty("personalProduction")
    private boolean personalProduction;

    /**
     * A String which contains information about the slot and the ID of the development cards in this way: numberOfSlot:IDOfTheCard....
     */
    //1:id1:2:id2
    @JsonProperty("developmentCards")
    private String developmentCards;

    /**
     * A String which contains information about the position and the ID of the leader cards in this way: position:IDOfTheCard....
     */
    //1:id1:2:id2
    @JsonProperty("leaderCards")
    private String leaderCards;

    /**
     * It represents all the resources (products) selected by the player in this way: nameOfResource:numberOfResources...
     */
    @JsonProperty("chosenProducts")
    private String chosenProducts;

    /**
     * It represents all the resources (materials)  selected by the player in this way: nameOfResource:numberOfResources...
     */
    @JsonProperty("chosenMaterials")
    private String chosenMaterials;

    /**
     * It contains all the information about the shelves and the quantities in this way: numberOfShelf:numberOfResources...
     */
    @JsonProperty("warehouse")
    private String warehouse;


    /**
     * It represents all the resources selected from the strongBox in this way: nameOfResource:numberOfResources...
     */
    @JsonProperty("strongBox")
    private String strongBox;

    /**
     * it sets MessageType as DO_PRODUCTION
     * @param personalProduction true if the personal board Production is selected
     * @param developmentCards A String which contains information about the slot and the ID of the development cards in this way: numberOfSlot:IDOfTheCard....
     * @param leaderCards A String which contains information about the position and the ID of the leader cards in this way: position:IDOfTheCard....
     * @param chosenProducts It represents all the resources (products) selected by the player in this way: nameOfResource:numberOfResources...
     * @param chosenMaterials It represents all the resources (materials)  selected by the player in this way: nameOfResource:numberOfResources...
     * @param warehouse It contains all the information about the shelves and the quantities in this way: numberOfShelf:numberOfResources...
     * @param strongBox It represents all the resources selected from the strongBox in this way: nameOfResource:numberOfResources...
     */
    @JsonCreator
    public MessageDoProduction (@JsonProperty("personalProduction") boolean personalProduction, @JsonProperty("developmentCards") String developmentCards,
                                @JsonProperty("leaderCards") String leaderCards, @JsonProperty("chosenProducts") String chosenProducts, @JsonProperty("chosenMaterials")String chosenMaterials,
                                @JsonProperty("warehouse")String warehouse, @JsonProperty("strongBox") String strongBox) {
        super("Production managing.", MessageType.DO_PRODUCTION);
        this.personalProduction = personalProduction;
        this.developmentCards = developmentCards;
        this.leaderCards = leaderCards;
        this.chosenProducts = chosenProducts;
        this.chosenMaterials = chosenMaterials;
        this.warehouse = warehouse;
        this.strongBox = strongBox;
    }

    /**
     * @return true if the personal board Production is selected
     */
    @JsonIgnore
    public boolean isPersonalProduction() {
        return personalProduction;
    }

    /**
     * @return a map which associates an Integer to a String
     *         the integer represents the position of the leader card
     *         the string represents the ID of the card itself
     */
    @JsonIgnore
    public Map<Integer, String> getDevelopmentCards() throws MalformedMessageException{
        return MessageUtilities.instance().getMapIntegerString(developmentCards);
    }

    /**
     * @return a map which associates an Integer to a String
     *        the integer represents the position of the leader card
     *        the string represents the ID of the card itself
     */
    @JsonIgnore
    public Map<Integer, String> getLeaderCards() throws MalformedMessageException{
        return MessageUtilities.instance().getMapIntegerString(leaderCards);
    }

    /**
     * @return all the resources (products) selected by the player
     */
    @JsonIgnore
    public List<ResQuantity> getChosenProducts() throws MalformedMessageException {
        return MessageUtilities.instance().getResQuantityList(chosenProducts);
    }

    /**
     * @return all the resources (materials) selected by the player
     */
    @JsonIgnore
    public List<ResQuantity> getChosenMaterials() throws MalformedMessageException{
        return MessageUtilities.instance().getResQuantityList(chosenMaterials);
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
     */
    @JsonIgnore
    public List<ResQuantity> getStrongBox() throws MalformedMessageException {
        return MessageUtilities.instance().getResQuantityList(strongBox);
    }
}
