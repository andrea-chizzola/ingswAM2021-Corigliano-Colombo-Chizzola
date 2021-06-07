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
     * this method is used to send the first String message to the MessageSender
     * @param message is the content of the message
     */
    void firstMessage(String message);

    /**
     * this method is used to send the first String message to the MessageSender in case of a local match
     * @param message is the content of the message
     */
    void firstMessageSolo(String message);

    /**
     * this method is used to close the client
     */
    void close();
}
