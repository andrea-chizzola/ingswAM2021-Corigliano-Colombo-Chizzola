package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;

public class ReplyMessage extends Message {
    /**
     * creates a new message
     *
     * @param body        represents the body of the message
     * @param messageType represents the type of the message
     */
    public ReplyMessage(String body, MessageType messageType) {
        super(body, messageType);
    }

    /**
     * this method checks if the message is a confirm or an error
     * @return true if the message is a confirm, false otherwise
     * @throws MalformedMessageException when the message is not correctly formed
     */
    public boolean isOk() throws MalformedMessageException {
        String check = MessageParser.getMessageTag(toXML(), "correct");
        return Boolean.parseBoolean(check);
    }
}
