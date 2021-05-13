package it.polimi.ingsw.Messages;

/**
 * This interface is used to observe the connection
 */
public interface ClientConnectionListener {

    /**
     * this method is used to notify the controller about the new messages
     * @param message is the content of the String message
     * @param nickname is the ID of a player
     */
    void onReceivedMessage(String message, String nickname);

    /**
     * Notifies the listener that a player did not answer to the ping message
     * @param nickname represents the player's nickname
     */
    void onMissingPong(String nickname);
}

