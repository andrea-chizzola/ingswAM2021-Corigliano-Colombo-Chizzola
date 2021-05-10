package it.polimi.ingsw.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * message to be used to indicate the choice of the turn type
 */
@JsonPropertyOrder({"turnType"})
public class MessageSelectedTurn extends Message {

    /**
     * ENUM that represents the possible turns
     */
    public enum TurnType{TAKE_RESOURCES, MANAGE_LEADER, BUY_CARD, DO_PRODUCTION, EXIT}

    /**
     * the type of turn selected
     */
    @JsonProperty("turnType")
    private TurnType turnType;

    /**
     * it sets MessageType as SELECTED_TURN
     * @param turnType the type of turn selected
     */
    @JsonCreator
    public MessageSelectedTurn(@JsonProperty("turnType") TurnType turnType) {
        super("Selection of the type of turn.", MessageType.SELECTED_TURN);
        this.turnType = turnType;
    }

    /**
     * @return the type of turn selected
     */
    @JsonIgnore
    public TurnType getTurnType() {
        return turnType;
    }
}
