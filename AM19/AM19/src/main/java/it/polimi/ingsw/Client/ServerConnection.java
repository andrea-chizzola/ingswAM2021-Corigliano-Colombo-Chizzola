package it.polimi.ingsw.Client;


/**
 * interface containing the necessary methods to handle the connection between client and server
 */
public interface ServerConnection{

    /**
     * Closes the connection between client and server
     */
    void closeConnection();

    /**
     * sends a new message to the server
     * @param message represents the message sent
     */
    void send(String message);

}
