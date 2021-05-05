package it.polimi.ingsw.Messages;

public class MessageReconnectionRequest extends Message{

    /**
     * represents the nickname chosen by the player
     */
    private String nickName;

    /**
     * creates a new message requesting to be reconnected to an existing game
     * @param nickName represents the nickname of the player who wants to rejoin the match
     */
    public MessageReconnectionRequest(String nickName) {

        super("Reconnection request", MessageType.RECONNECTION);
        this.nickName = nickName;

    }

    /**
     * @return returns the nickname chosen by the player
     */
    public String getNickName() {
        return nickName;
    }

    @Override
    public String toString() {
        return "MessageReconnectionRequest{" +
                "nickName='" + nickName + '\'' +
                "\n" +
                super.toString() +
                '}';
    }
}
