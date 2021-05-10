package it.polimi.ingsw.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * message to be used to manage a leader card
 */
public class MessageLeaderAction extends Message{

    /**
     * the position of the leader card
     */
    @JsonProperty("leaderPosition")
    private int leaderPosition;

    /**
     * the ID of the leader card
     */
    @JsonProperty("ID")
    private String ID;

    /**
     * the action be performed on the card
     */
    @JsonProperty("action")
    private MessageAction.Action action;

    /**
     * it sets MessageType as LEADER_ACTION
     * @param leaderPosition the position of the leader card
     * @param ID the ID of the leader card
     * @param action the action be performed on the card
     */
    @JsonCreator
    public MessageLeaderAction(@JsonProperty("leaderPosition") int leaderPosition, @JsonProperty("ID") String ID,
                               @JsonProperty("action")MessageAction.Action action) {

        super("Leader card managing.", MessageType.LEADER_ACTION);
        this.leaderPosition = leaderPosition;
        this.ID = ID;
        this.action = action;
    }

    /**
     * @return the position of the leader card
     */
    @JsonIgnore
    public int getLeaderPosition() {
        return leaderPosition;
    }

    /**
     * @return the ID of the leader card
     */
    @JsonIgnore
    public String getID() {
        return ID;
    }

    /**
     * @return the action be performed on the card
     */
    @JsonIgnore
    public MessageAction.Action getAction() {
        return action;
    }
}
