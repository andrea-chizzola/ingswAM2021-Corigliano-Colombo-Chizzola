package it.polimi.ingsw.Messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    /**
     * Represents all the possible message types
     */
    public enum MessageType {CONNECTION, RECONNECTION, REPLY, GAME_STATUS, UPDATE_GAME, LEADER_UPDATE, RESOURCE_SLOT, UPDATE_BOARD, MARKET_SELECTION,
                             MARBLE_SELECTION, ACTION, LEADER_ACTION, CARD, RESOURCE_SELECTION, CARD_UPDATE, PRODUCTION, PING, PONG, DISCONNECTION}

    /**
     * Represents all the possible message errors
     */
    public enum ErrorType {CONNECTION_ERROR, NICKNAME_TAKEN, LOBBY_ERROR, LEADER_ERROR, RESOURCE_ERROR, TURN_ERROR, PRODUCTION_ERROR}

    /**
     * Represents the status of a message
     */
    public enum Status {ERROR, OK}

    /**
     * Contains the body of the message
     */
    private String body;

    /**
     * Represents the type of the message
     */
    private MessageType messageType;

    /**
     * Represents the type of error related to the message
     */
    private ErrorType errorType;

    /**
     * Represents the status of the message: ERROR if it's an error message, OK otherwise
     */
    private Status status;

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
     * creates a new error message
     * @param body represents the body of the message
     * @param messageType represents the body of the message
     * @param errorType represents the type of the message
     */
    public Message(String body, MessageType messageType, ErrorType errorType) {
        this.body = body;
        this.messageType = messageType;
        this.errorType = errorType;
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
     * @return returns the error type of the message
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * @return returns the status of the message
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString() {
        return "Message{" +
                "body='" + body + '\'' +
                ", messageType=" + messageType +
                ", errorType=" + errorType +
                ", status=" + status +
                '}';
    }
}
