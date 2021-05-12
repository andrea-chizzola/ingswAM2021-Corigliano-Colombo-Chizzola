package it.polimi.ingsw.Client;

import it.polimi.ingsw.Server.ConnectionListener;

/**
 * interface containing the necessary methods to handle the connection between client and server
 */
public interface ServerConnection {

    /**
     * Closes the connection between client and server
     */
    void closeConnection();

    /**
     * Adds a new listener to the socket connection between client and server
     * @param listener represents the new listener
     */
    void addListener(ConnectionListener listener);

    /**
     *
     * @return returns the socket's listener
     */
    ConnectionListener getListener();

    /**
     * sends a new message to the server
     * @param message represents the message sent
     */
    void send(String message);

    /**
     * sends a new message to the sever asynchronously
     * @param message represents the message sent
     */
    void asyncSend(String message);

}
