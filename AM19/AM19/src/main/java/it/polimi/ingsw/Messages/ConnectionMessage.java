package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;

public class ConnectionMessage extends Message {
    /**
     * creates a new message
     *
     * @param body        represents the body of the message
     * @param messageType represents the type of the message
     */
    public ConnectionMessage(String body, MessageType messageType) {
        super(body, messageType);
    }


    /**
     *
     * @return the player's nickname
     * @throws MalformedMessageException if the file does not contain this information
     */
    public String getNickname() throws MalformedMessageException {

        return MessageParser.getMessageTag(toXML(), "nickname");

    }

    /**
     *
     * @return the player's decision to create a game or join an existing one
     * @throws MalformedMessageException if the file does not contain this information
     */
    public String getGameHost() throws MalformedMessageException{

        return MessageParser.getMessageTag(toXML(), "gameHost");

    }

    /**
     *
     * @return the number of players required for the new game
     * @throws MalformedMessageException MalformedMessageException if the file does not contain this information
     */
    public String getPlayersNumber() throws MalformedMessageException{

        return MessageParser.getMessageTag(toXML(), "playersNumber");

    }
}
