package it.polimi.ingsw.Messages;

public class MessageReconnectionRequest extends Message{

    /**
     * represents the nickname chosen by the player
     */
    private String nickname;

    /**
     * creates a new message requesting to be reconnected to an existing game
     * @param nickname represents the nickname of the player who wants to rejoin the match
     */
    public MessageReconnectionRequest(String nickname) {

        super("Reconnection request", MessageType.RECONNECTION);
        this.nickname = nickname;

    }

    /**
     * @return returns the nickname chosen by the player
     */
    public String getNickName() {
        return nickname;
    }

    @Override
    public String toString() {
        return "MessageReconnectionRequest{" +
                "nickName='" + nickname + '\'' +
                "\n" +
                super.toString() +
                '}';
    }
}
