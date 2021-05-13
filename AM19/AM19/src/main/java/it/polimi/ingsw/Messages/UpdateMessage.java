package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;

import java.util.*;

import static java.lang.Integer.parseInt;

public class UpdateMessage extends Message {

    private static final String splitter = ":";

    /**
     * creates a new message
     *
     * @param body        represents the body of the message
     * @param messageType represents the type of the message
     */
    public UpdateMessage(String body, MessageType messageType) {
        super(body, messageType);
    }

    /**
     * @return the name of the current player (if present in the file)
     * @throws MalformedMessageException if the file does not contain this information
     */
    public String getCurrentPlayer() throws MalformedMessageException{
        return MessageParser.getMessageTag(toXML(), "currentPlayer");
    }

    /**
     * @return a map that contains the points obtained by the players
     * @throws MalformedMessageException if the message does not contain this information
     */
    public Map<String, Integer> getEndGamePoints() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapStringInteger(toXML(),"points");
    }

    public List<ResQuantity> getWarehouseUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResQuantityList(toXML(), "warehouse");
    }

    public List<ResQuantity> getStrongboxUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResQuantityList(toXML(), "strongbox");
    }

    public Map<Integer, String> getSlotsUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerString(toXML(), "devCards");
    }

    public Map<Integer, String> getDecksUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerString(toXML(), "devCards");
    }

    public String getTopToken() throws MalformedMessageException {
        return MessageParser.getMessageTag(toXML(), "token");
    }

    public List<Marble> getMarketUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMarbleList(toString(),"market");
    }

    public List<Marble> getSelectedMarbles() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMarbleList(toString(),"marbles");
    }

    public List<Marble> getCandidateWhite() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMarbleList(toString(),"candidates");
    }

    public Map<String, Integer> getFaithPoints() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapStringInteger(toXML(), "faith");
    }

    public Optional<Integer> getLorenzoFaith() throws MalformedMessageException{
        String faithString;
        int faith;

        try{
            faithString = MessageParser.getMessageTag(toXML(), "LorenzoFaith");
        }catch(MalformedMessageException e){
            return Optional.empty();
        }

        try{
            faith = Integer.parseInt(faithString);
        }catch(NumberFormatException e){
            throw new MalformedMessageException("parseInt failed!");
        }
        return Optional.of(faith);
    }

    public List<ItemStatus> getSections(String nickname) throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getStatusList(toXML(), nickname+"Sections");
    }

    public Optional<List<ItemStatus>> getLorenzoSections(){
        MessageUtilities parser = MessageUtilities.instance();
        List<ItemStatus> list;

        try{
            list = parser.getStatusList(toXML(), "nicknameSections");
        }
        catch(MalformedMessageException e){
            return Optional.empty();
        }

        return Optional.of(list);
    }

    public Map<Integer, String> getLeaderCardsUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerString(toXML(), "leaderCards");
    }

    public Map<Integer, ItemStatus> getLeaderCardsStatus() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerItemStatus(toXML(), "leaderStatus");
    }



}

