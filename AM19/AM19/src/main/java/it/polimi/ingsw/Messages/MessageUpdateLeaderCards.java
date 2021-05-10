package it.polimi.ingsw.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.Exceptions.MalformedMessageException;

import java.util.HashMap;
import java.util.Map;

public class MessageUpdateLeaderCards extends Message {

    //pos:status:pos:status...
    @JsonProperty("leaderStatus")
    private String leaderStatus;

    //pos:ID:pos:ID...
    @JsonProperty("leaderID")
    private String leaderID;

    @JsonCreator
    public MessageUpdateLeaderCards(@JsonProperty("leaderStatus") String leaderStatus, @JsonProperty("leaderID") String leaderID) {
        super("Leader cards initialization managing.", MessageType.UPDATE_LEADER_CARDS);
        this.leaderStatus = leaderStatus;
        this.leaderID = leaderID;
    }

    @JsonIgnore
    public Map<Integer, Boolean> getLeaderStatus() throws MalformedMessageException {
        return MessageUtilities.instance().getMapIntegerBoolean(leaderStatus);
    }

    @JsonIgnore
    public Map<Integer, String> getLeaderID() throws MalformedMessageException{
        return MessageUtilities.instance().getMapIntegerString(leaderID);
    }
}
