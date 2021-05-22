package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;

import java.io.Serializable;

public abstract class Message {
    public enum MessageType {
        UPDATE_LEADER_CARDS,  //initialization of leader cards
        RESOURCE, //initialization resources
        SELECTED_TURN, //select turn type
        EXIT,

        BUY_CARD, //buy a development card
        DO_PRODUCTION, //do production (unique message)
        LEADER_ACTION, //manage leader cards
        SWAP, //swap resources in the warehouse
        MARKET_SELECTION, //selection of row or column
        ACTION_MARBLE, //actions performed on the marbles taken from the market

        CONNECTION, //not used in controller
        RECONNECTION, //if a player is reconnected
        DISCONNECTION, //if a player is disconnected

        REPLY,//not used in controller

        GAME_STATUS, //used to update the name of the current player
        END_GAME,
        BOX_UPDATE,
        SLOTS_UPDATE,
        DECKS_UPDATE,
        FAITH_UPDATE,
        TOKEN_UPDATE,
        MARKET_UPDATE,
        START_GAME,
        SELECTED_MARBLES;

    }

    /**
     * Contains the body of the message
     */
    private String XMLString;

    /**
     * Represents the type of the message
     */
    private MessageType messageType;

    /**
     * creates a new message
     * @param XMLstring represents the content of the message
     * @param messageType represents the type of the message
     */
    public Message(String XMLstring, MessageType messageType) {
        this.XMLString = XMLstring;
        this.messageType = messageType;
    }

    /**
     * @return returns the body of the message
     */
    public String toXML() {
        return XMLString;
    }

    /**
     * @return returns the type of the message
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     *
     * @return the body of the message
     * @throws MalformedMessageException if the file does not contain this information
     */
    public String getBody() throws MalformedMessageException{
        return MessageParser.getMessageTag(toXML(), "body");
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString() {
        return "Message{" +
                "body='" + XMLString + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
