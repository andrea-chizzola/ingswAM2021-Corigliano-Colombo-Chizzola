package it.polimi.ingsw.Server;

/**
 * interface containing the necessary methods to notify the observers of the connection between server and client
 */
public interface ConnectionListener {

    /**
     * Notifies the listener that a new message was received
     * @param message represents the message received
     * @param Id represents the sending player's id
     */
    void onReceivedMessage(String message, String Id);

    /**
     * Notifies the listener that a player did not answer to the ping message
     * @param playerId represents the player's ID
     */
    void onMissingPong(String playerId);

}
