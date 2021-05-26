package it.polimi.ingsw.Client;

/**
 * this interface implements a method to notify an object with a String message
 */
public interface MessageSender {

    /**
     * this method is used to send a String message to the MessageSender
     * @param message is the content of the message
     */
    void sendMessage(String message);

    /**
     * this method is used to notify the willingness to disconnect to the client
     */
    //void notifyDisconnection();

    /**
     * this method is used to send the first String message to the MessageSender
     * @param message is the content of the message
     */
    void firstMessage(String message);
}