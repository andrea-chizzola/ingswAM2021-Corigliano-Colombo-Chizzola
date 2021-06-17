package it.polimi.ingsw.Client;

/**
 * This interface is used to observe the connection
 */
public interface ConnectionListener {

    /**
     * this method is used to notify the controller about the new messages
     * @param message is the content of the String message
     */
    void onReceivedMessage(String message);

    /**
     * Notifies the listener that a player did not answer to the ping message
     */
    void onMissingPong();
}

