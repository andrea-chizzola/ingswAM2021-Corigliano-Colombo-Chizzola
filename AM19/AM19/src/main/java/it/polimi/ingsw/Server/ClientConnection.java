package it.polimi.ingsw.Server;

import it.polimi.ingsw.Messages.Message;

/**
 * interface containing the necessary methods to handle the connection between client and server
 */
public interface ClientConnection {

    /**
     * Closes the connection between the server and the client
     */
    void closeConnection();

    /**
     * Adds a new listener to the socket connection between server and client
     * @param listener represents the new listener
     */
    void addListener(ConnectionListener listener);

    /**
     *
     * @return returns the socket's listener
     */
    ConnectionListener getListener();

    /**
     * sends a new message to the client
     * @param message represents the message sent
     */
    void send(String message);

    /**
     * sends a new message to the client asynchronously
     * @param message represents the message sent
     */
    void asyncSend(String message);


}
