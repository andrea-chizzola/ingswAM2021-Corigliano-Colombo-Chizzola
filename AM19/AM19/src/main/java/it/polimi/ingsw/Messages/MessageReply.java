package it.polimi.ingsw.Messages;

public class MessageReply extends Message{


    /**
     * Represents all the possible reply types
     */
    //public enum ReplyType {CORRECT, CONNECTION_ERROR, NICKNAME_TAKEN, LOBBY_ERROR, LEADER_ERROR, RESOURCE_ERROR, TURN_ERROR, PRODUCTION_ERROR}

    /**
     * Indicates if the previous action performed by the client was successful or not
     */
    private final boolean error;

    /**
     * represents the type of the reply
     */
    //private ReplyType replyType;

    /**
     * creates a new reply message indicating that the previous action was performed correctly
     * @param message represents the body of the reply message
     */
    public MessageReply(String message, boolean error){

        super(message, MessageType.REPLY);
        this.error = error;

    }

    /**
     * @return returns true if the reply message contains an error message
     */
    public boolean isError() {
        return error;
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString() {
        return "MessageReply{" +
                "error=" + error +
                "\n" +
                super.toString() +
                '}';
    }
}
