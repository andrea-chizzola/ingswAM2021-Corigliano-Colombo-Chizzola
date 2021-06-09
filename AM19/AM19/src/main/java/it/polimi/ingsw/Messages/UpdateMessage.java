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

    /**
     * @return true if Lorenzo is the winner, false otherwise
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public String getWinner() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getString(toXML(),"winner");
    }

    /**
     * @return List of ResQuantity which represents the resources and quantities present the Warehouse
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<ResQuantity> getWarehouseUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResQuantityList(toXML(), "warehouse");
    }

    /**
     * @return List of ResQuantity which represents the resources and quantities present the strongBox
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<ResQuantity> getStrongboxUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResQuantityList(toXML(), "strongbox");
    }

    /**
     * @return Map(Integer-String) which represent the position and ID of the development cards present in the slots
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public Map<Integer, String> getSlotsUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerString(toXML(), "devCards");
    }

    /**
     * @return Map(Integer-String) which represent the number and ID of the development cards on the top of the development decks
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public Map<Integer, String> getDecksUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerString(toXML(), "devCards");
    }

    /**
     * @return the ID of the current top token
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public String getTopToken() throws MalformedMessageException {
        return MessageParser.getMessageTag(toXML(), "token");
    }

    /**
     * @return List of Marble which represents the status of the MarketBoard
     *         the list contains the rows of the market in series and the last element of the list represents the slide
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<Marble> getMarketUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
            return parser.getMarbleList(toXML(),"market");
    }


    /**
     * @return Map(String-Integer) which represent the nickname and the faith points of each player
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public Map<String, Integer> getFaithPoints() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapStringInteger(toXML(), "faith");
    }

    /**
     * @return an Optional Integer which represents, if present, the faith points of Lorenzo
     * @throws MalformedMessageException if the message is not correctly formed
     */
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

    /**
     * gets the sections of the player with the nickname passed as parameter
     * @param nickname the nickname of the player
     * @return List of ItemStatus which represents the status of each section
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<ItemStatus> getSections(String nickname) throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getStatusList(toXML(), nickname+"Sections");
    }

    /**
     * @return Optional List of ItemStatus which represents, if present, the status of each section of Lorenzo
     */
    public Optional<List<ItemStatus>> getLorenzoSections(){
        MessageUtilities parser = MessageUtilities.instance();
        List<ItemStatus> list;

        try{
            list = parser.getStatusList(toXML(), "LorenzoSections");
        }
        catch(MalformedMessageException e){
            return Optional.empty();
        }

        return Optional.of(list);
    }

    /**
     * @return Map(Integer-String) which represent the number and ID of the leader cards owned by the player
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public Map<Integer, String> getLeaderCardsUpdate() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerString(toXML(), "leaderCards");
    }

    /**
     * @return Map(Integer-ItemStatus) which represent the number and status of the leader cards owned by the player
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public Map<Integer, ItemStatus> getLeaderCardsStatus() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerItemStatus(toXML(), "leaderStatus");
    }

    /**
     * this method returns the content of the tag "player" in the message
     * @return a String that represent the content
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public String getPlayer() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getString(toXML(), "player");
    }


    /**
     * @return List of String which contains the nicknames of all the players
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<String> getNicknames() throws MalformedMessageException{
        String players = MessageParser.getMessageTag(toXML(), "activePlayers");
        String[] string = players.split(splitter);
        List<String> nicknames = new LinkedList<>();

        for(int i=0; i<string.length;i++){
            nicknames.add(string[i]);
        }
        return nicknames;
    }


}

