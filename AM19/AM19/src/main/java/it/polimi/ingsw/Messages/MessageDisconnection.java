package it.polimi.ingsw.Messages;

public class MessageDisconnection extends Message{

    /**
     * represents the nickname of the player who wants to be disconnected
     */
    private String nickname;

    /**
     * creates a new message requesting to be disconnected from the game
     * @param nickname represents the nickname of the player who wants to leave the match
     */
    public MessageDisconnection(String nickname){

        super("Disconnection request", Message.MessageType.DISCONNECTION);
        this.nickname = nickname;

    }

    /**
     * @return returns the nickname of the player asking to be disconnected
     */
    public String getNickName() {
        return nickname;
    }

    @Override
    public String toString() {
        return "MessageDisconnection{" +
                "nickName='" + nickname + '\'' +
                "\n" +
                super.toString() +
                '}';
    }
}
