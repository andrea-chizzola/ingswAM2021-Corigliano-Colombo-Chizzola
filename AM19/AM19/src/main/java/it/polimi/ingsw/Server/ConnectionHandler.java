package it.polimi.ingsw.Server;

/**
 * interface containing the necessary methods to handle the connection between client and server
 */
public interface ConnectionHandler {

    /**
     * Closes the connection between the server and the client
     */
    void closeConnection();

    /**
     * sends a new message to the client
     * @param message represents the message sent
     */
    void send(String message);

}
