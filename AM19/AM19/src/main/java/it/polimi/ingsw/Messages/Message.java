package it.polimi.ingsw.Messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    /**
     * Represents all the possible message types
     */
    public enum MessageType {CONNECTION, RECONNECTION, REPLY, GAME_STATUS, UPDATE_GAME, LEADER_UPDATE, RESOURCE_SLOT, UPDATE_BOARD, MARKET_SELECTION,
                             MARBLE_SELECTION, ACTION, LEADER_ACTION, CARD, RESOURCE_SELECTION, CARD_UPDATE, PRODUCTION, PING, PONG, DISCONNECTION}

    /**
     * Contains the body of the message
     */
    private String body;

    /**
     * Represents the type of the message
     */
    private MessageType messageType;

    /**
     * creates a new message
     * @param body represents the body of the message
     * @param messageType represents the body of the message
     */
    public Message(String body, MessageType messageType) {

        this.body = body;
        this.messageType = messageType;

    }

    /**
     * @return returns the body of the message
     */
    public String getBody() {
        return body;
    }

    /**
     * @return returns the type of the message
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString() {
        return "Message{" +
                "body='" + body + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
