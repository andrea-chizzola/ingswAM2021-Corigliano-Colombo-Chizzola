package it.polimi.ingsw.Client;

/**
 * interface containing the necessary methods to notify the observers of the connection between client and server
 */
public interface ServerConnectionListener {

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

