package it.polimi.ingsw.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * message to be used to swap two shelves of the warehouse
 */
public class MessageSwap extends Message {

    /**
     * the number of the first shelf
     */
    @JsonProperty("source")
    private int source;

    /**
     * the number of the second shelf
     */
    @JsonProperty("target")
    private int target;

    /**
     * it sets MessageType as SWAP
     * @param source the number of the first shelf
     * @param target the number of the second shelf
     */
    @JsonCreator
    public MessageSwap(@JsonProperty("source") int source, @JsonProperty("target") int target) {
        super("Swapping two shelves of the warehouse.", MessageType.SWAP);
        this.source = source;
        this.target = target;
    }

    /**
     * @return the number of the first shelf
     */
    @JsonIgnore
    public int getSource() {
        return source;
    }

    /**
     * @return the number of the second shelf
     */
    @JsonIgnore
    public int getTarget() {
        return target;
    }
}
