package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;

public class ReplyMessage extends Message {
    /**
     * creates a new message
     * @param body represents the body of the message
     */
    public ReplyMessage(String body) {
        super(body, Message.MessageType.REPLY);
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

    /**
     * this method returns the content of the tag "player" in the message
     * @return a String that represent the content
     * @throws MalformedMessageException is the message is not correctly formed
     */
    public String getPlayer() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getString(toXML(), "player");
    }
}
