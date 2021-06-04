package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.PlayerAction;
import it.polimi.ingsw.Messages.Enumerations.TraySelection;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionMessage extends Message {
    /**
     * creates a new message
     *
     * @param body        represents the body of the message
     * @param messageType represents the type of the message
     */
    public ActionMessage(String body, MessageType messageType) {
        super(body, messageType);
    }


    /**
     * @return Map(Integer-String) which represent the position and ID of the selected leader cards
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public Map<Integer, String> getSelectedLeaderCards() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerString(toXML(), "leaderCards");
    }

    /**
     * @return List of Resource which represents the resources selected during initialization
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<Resource> getResourcesInit() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResources(toXML(), "resources");
    }

    /**
     * @return List of Integer which represents the shelves associated with each resource selected during initialization
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<Integer> getShelvesInit() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getShelves(toXML(), "resources");
    }

    //MARKET_SELECTION

    /**
     * @return TraySelection which represents the selection of the market row/column
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public TraySelection getSelectedMarketTray() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getTray(toXML(),"tray");
    }

    /**
     * @return the number of the row/column selected
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public int getSelectedMarketNumber() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getInteger(toXML(),"number");
    }

    //ACTION

    /**
     * @return List of PlayerAction which indicates for each marble the action to be performed
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<PlayerAction> getMarbleActions() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getActions(toXML(),"marblesActions");
    }

    /**
     * @return List of Marble which represents all the marbles selected by the player
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<Marble> getMarbleFromAction() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMarbleFromAction(toXML(), "marblesActions");
    }

    /**
     * @return List of Integer which represents for each marble the shelf of the warehouse where the resource has to be put
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<Integer> getMarblesSelectedShelves() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getShelvesActions(toXML(),"marblesActions");
    }

    //BUY_CARD

    /**
     * @return the slot selected by the player
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public int getTargetSlotDevelopmentCard() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getInteger(toXML(),"slot");
    }

    /**
     * @return the level of the development card selected by the player
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public int getDevelopmentCardLevel() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getInteger(toXML(),"level");
    }

    /**
     * @return CardColor which represents the color of the development card selected by the player
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public CardColor getDevelopmentCardColor() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getCardColor(toXML(),"color");
    }

    /**
     * @return the ID of the development card
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public String getDevelopmentCrdID() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getString(toXML(),"ID");
    }

    /**
     * @return List of Integer which represents the shelves selected by the player from which to take resources
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<Integer> getSelectedWarehouseShelves() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getShelves(toXML(),"warehouse");
    }

    /**
     * @return List of Integer which represents the number of resources selected by the player from each shelf
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<Integer> getSelectedWarehouseQuantity() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getQuantity(toXML(),"warehouse");
    }

    /**
     * @return List of ResQuantity which represents the resources and quantities selected from the strongBox by the player
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<ResQuantity> getSelectedStrongBoxQuantity() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResQuantityList(toXML(),"strongBox");
    }

    //DO_PRODUCTION

    /**
     * @return true if the personal production is active, false otherwise
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public boolean isPersonalProduction() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getBoolean(toXML(),"personalProduction");
    }

    /**
     * @return  Map(Integer-String) which represent the position and ID of the selected development cards for the production
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public Map<Integer, String> getActivatedDevelopmentCards() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerString(toXML(), "developmentCards");
    }

    /**
     * @return List of ResQuantity which represents the chosen products selected by the player during the production
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<ResQuantity> getChosenProducts() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResQuantityList(toXML(),"chosenProducts");
    }

    /**
     * @return List of ResQuantity which represents the chosen materials selected by the player during the production
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public List<ResQuantity> getChosenMaterials() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResQuantityList(toXML(),"chosenMaterials");
    }

    //LEADER_ACTION

    /**
     * @return PlayerAction which represents the action to be performed on the leader card
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public PlayerAction getLeaderCardAction() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getAction(toXML(),"action");
    }


    //SWAP

    /**
     * @return the number of the first shelf to swap
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public int getSourceSwap() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getInteger(toXML(),"source");
    }

    /**
     * @return the number of the first shelf to swap
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public int getTargetSwap() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getInteger(toXML(),"target");
    }

    //UPDATE_LEADER_CARDS
    /**
     * @return Map(Integer-String) which represent the position and ID of the selected leader cards during initialization
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public Map<Integer, Boolean> getLeaderCardStatus() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();

        Map<Integer, ItemStatus> map = parser.getMapIntegerItemStatus(toXML(),"leaderStatus");
        Map<Integer,Boolean> mapBoolean = new HashMap<>();

        for(int i : map.keySet()){
            mapBoolean.put(i,map.get(i).getBoolValue());
        }
        return mapBoolean;
    }
}
