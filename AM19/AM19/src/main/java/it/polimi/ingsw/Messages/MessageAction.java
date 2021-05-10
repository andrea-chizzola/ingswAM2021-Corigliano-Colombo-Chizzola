package it.polimi.ingsw.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Model.MarketBoard.Marble;

import java.util.LinkedList;
import java.util.List;

/**
 * message to be used to insert or discard the marbles taken from the market
 */

@JsonPropertyOrder({"marblesActions"})
public class MessageAction extends Message {

    public enum Action {INSERT, DISCARD}

    /**
     * It contains the choices of the player in this way: marbleName:actionType:numberOfShelf...
     */
    //MarbleBlue:insert:2:MarbleYellow:discard:0:MarbleBlue:insert:2
    @JsonProperty("marblesActions")
    private String marblesActions;

    /**
     * it sets MessageType as ACTION
     * @param marblesActions a String which contains the choices of the player in this way: marbleName:actionType:numberOfShelf...
     */
    @JsonCreator
    public MessageAction(@JsonProperty("marbleActions") String marblesActions) {
        super("Marbles managing.", MessageType.ACTION);
        this.marblesActions = marblesActions;

    }

    /**
     * @return the marbles taken from the market
     */
    @JsonIgnore
    public List<Marble> getMarbles() throws MalformedMessageException {
        return MessageUtilities.instance().getMarbles(marblesActions);
    }

    /**
     * @return the actions to be performed on each marble
     */
    @JsonIgnore
    public List<Action> getActions() throws MalformedMessageException {
        return MessageUtilities.instance().getActions(marblesActions);
    }

    /**
     * @return ArrayList of Integer which represents all the shelves selected
     */
    @JsonIgnore
    public List<Integer> getShelves() throws MalformedMessageException {
        return MessageUtilities.instance().getShelvesActions(marblesActions); }

}
