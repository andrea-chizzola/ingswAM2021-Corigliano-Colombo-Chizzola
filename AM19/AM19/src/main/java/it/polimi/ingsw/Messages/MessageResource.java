package it.polimi.ingsw.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.LinkedList;
import java.util.List;

/**
 * MessageResource represents the resources selected by the player during the initialization
 */
public class MessageResource extends Message {

    /**
     * It contains all the information about the resources selected and the shelf in this way: numberOfShelf:ResourceName...
     */
    //1:coins:2:servants:2:servants
    @JsonProperty("resources")
    private String resources;


    /**
     * it sets MessageType as RESOURCE
     * @param resources It contains all the information about the resources selected and the shelf in this way: numberOfShelf:ResourceName...
     */
    @JsonCreator
    public MessageResource(@JsonProperty("resources") String resources) {
        super("Selection of resources during initialization.", MessageType.RESOURCE);
        this.resources = resources;
    }

    /**
     * @return the resources selected
     */
    @JsonIgnore
    public List<Resource> getResources() throws MalformedMessageException {
        return MessageUtilities.instance().getResources(resources);
    }

    /**
     * @return the shelves where the resources are put
     */
    @JsonIgnore
    public List<Integer> getShelves() throws MalformedMessageException {
        return MessageUtilities.instance().getShelves(resources);
    }
}
