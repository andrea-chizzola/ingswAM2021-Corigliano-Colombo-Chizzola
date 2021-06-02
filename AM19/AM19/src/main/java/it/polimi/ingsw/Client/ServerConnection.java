package it.polimi.ingsw.Client;

import it.polimi.ingsw.Messages.ClientConnectionListener;

/**
 * interface containing the necessary methods to handle the connection between client and server
 */
public interface ServerConnection extends Runnable{

    /**
     * Closes the connection between client and server
     */
    void closeConnection();

    /**
     *
     * @return returns the socket's listener
     *//*
    ClientConnectionListener getListener();*/

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
