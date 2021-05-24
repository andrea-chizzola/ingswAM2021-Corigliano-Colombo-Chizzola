package it.polimi.ingsw.Client;

/**
 * this interface implements a method to notify an object with a String message
 */
public interface ViewObserver {

    /**
     * this method is used to send a String message to a Client
     * @param message is the content of the message
     */
    void update(String message);

    /**
     * this method is used to notify the willingness to disconnect to the client
     */
    void notifyDisconnection();
}
