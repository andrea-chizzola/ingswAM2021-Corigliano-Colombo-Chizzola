package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Model.Boards.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;

import java.util.List;

public class GameStatusMessage extends Message {
    /**
     * creates a new message
     * @param body represents the body of the message
     */
    public GameStatusMessage(String body) {
        super(body, MessageType.GAME_STATUS);
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

    /**
     * this method returns the status contained in the message
     * @return a TurnType that represent the content
     * @throws MalformedMessageException is the message is not correctly formed
     */
    public TurnType getStatus() throws MalformedMessageException{
        String status = MessageParser.getMessageTag(toXML(), "state");
        TurnType turn;
        try{
            turn = TurnType.valueOf(status);
        }catch(IllegalArgumentException e){
            throw new MalformedMessageException();
        }
        return turn;
    }

    /**
     * @return a list of turn types
     * @throws MalformedMessageException if the message is not correctly formed.
     */
    public List<String> getTurnTypes() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getListString(toXML(), "turns");
    }

    /**
     * @return a list of marbles that represents the selected marbles
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<Marble> getSelectedMarbles() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMarbleList(toXML(),"marbles");
    }

    /**
     * @return a list of marbles that represents the possible transformations of a white marble
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<Marble> getCandidateWhite() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMarbleList(toXML(),"candidates");
    }
}
