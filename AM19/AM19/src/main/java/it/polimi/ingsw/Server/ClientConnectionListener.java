package it.polimi.ingsw.Server;

/**
 * interface containing the necessary methods to notify the observers of the connection between server and client
 */
public interface ClientConnectionListener {

    /**
     * Notifies the listener that a new message was received
     * @param message represents the message received
     * @param socketId represents the sending player's socket id
     */
    void onReceivedMessage(String message, String socketId);

    /**
     * adds a new connection to the active ones
     * @param socketId represents the id associated to the connection
     * @param connection represents the client's socket connection
     */
    void addActiveConnection(String socketId, ClientConnectionHandler connection);

    /**
     * Notifies the listener that a player did not answer to the ping message
     * @param socketId represents the player's socket id
     */
    void onMissingPong(String socketId);

}
