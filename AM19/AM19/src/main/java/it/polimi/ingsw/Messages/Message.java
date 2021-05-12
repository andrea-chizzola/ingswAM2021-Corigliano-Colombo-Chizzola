package it.polimi.ingsw.Messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
/*
    public enum MessageType {CONNECTION, RECONNECTION, REPLY, GAME_STATUS, UPDATE_GAME, LEADER_UPDATE, RESOURCE_SLOT, UPDATE_BOARD, MARKET_SELECTION,
                             MARBLE_SELECTION, ACTION, LEADER_ACTION, CARD, RESOURCE_SELECTION, CARD_UPDATE, PRODUCTION, PING, PONG, DISCONNECTION}
*/
    public enum MessageType {
        MARBLE_SELECTION,
        UPDATE_BOARD,
        CONNECTION, //not used in controller
        RECONNECTION, //if a pleyer is reconnected
        REPLY,//not used in controller
        RESOURCE, //initialization resources
        SELECTED_TURN, //select turn type
        BUY_CARD, //buy a development card
        DO_PRODUCTION, //do production (unique message)
        SWAP, //select turn type
        UPDATE_LEADER_CARDS,  //initialization of leader cards
        GAME_STATUS,//not used in controller
        UPDATE_GAME,//not used in controller
        LEADER_UPDATE,//not used in controller
        RESOURCE_SLOT,//not used in controller
        BOX_UPDATE,//not used in controller
        MARKET_SELECTION, //selection of row or column
        ACTION, //actions performed on the marbles taken from the market
        LEADER_ACTION, //manage leader cards
        CARD,//not used in controller
        RESOURCE_SELECTION,//not used in controller
        CARD_UPDATE,//not used in controller
        PRODUCTION,//not used in controller
        PING,//not used in controller
        PONG,//not used in controller
        DISCONNECTION//if a player is disconnected,
    }

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
