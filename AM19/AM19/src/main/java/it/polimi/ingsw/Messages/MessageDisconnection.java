package it.polimi.ingsw.Messages;

public class MessageDisconnection extends Message{

    /**
     * creates a new message requesting to be disconnected from the game
     * @param nickName represents the nickname of the player who wants to leave the match
     */
    public MessageDisconnection(String nickName){ super(nickName, Message.MessageType.DISCONNECTION); }

    @Override
    public String toString() {
        return "MessageDisconnection:" + "\n" +
                super.toString();
    }
}
