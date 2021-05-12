package it.polimi.ingsw.Messages;

/**
 * This interface is used to observe the connection
 */
public interface ConnectionListener {

    /**
     * this method is used to notify the controller about the new messages
     * @param message is the content of the String message
     * @param nickname is the ID of a player
     */
    void onReceivedMessage(String message, String nickname);
}

