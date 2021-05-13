package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.PlayerAction;
import it.polimi.ingsw.Messages.Enumerations.TraySelection;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

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


    public Map<Integer, String> getSelectedLeaderCards() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerString(toXML(), "leaderCards");
    }

    public List<Resource> getResourcesInit() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResources(toXML(), "resources");
    }

    public List<Integer> getShelvesInit() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getShelves(toXML(), "resources");
    }

    //MARKET_SELECTION
    public TraySelection getSelectedMarketTray() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getTray(toXML(),"tray");
    }
    public int getSelectedMarketNumber() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getInteger(toXML(),"number");
    }

    //ACTION
    public List<PlayerAction> getMarbleActions() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getActions(toXML(),"marblesActions");
    }
    public List<Marble> getMarbleFromAction() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMarbleFromAction(toXML(), "marblesActions");
    }
    public List<Integer> getMarblesSelectedShelves() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getShelvesActions(toXML(),"marblesActions");
    }

    //BUY_CARD
    public int getTargetSlotDevelopmentCard() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getInteger(toXML(),"slot");
    }
    public int getDevelopmentCardLevel() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getInteger(toXML(),"level");
    }
    public CardColor getDevelopmentCardColor() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getCardColor(toXML(),"color");
    }
    public String getDevelopmentCrdID() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getString(toXML(),"ID");
    }
    public List<Integer> getSelectedWarehouseShelves() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getShelves(toXML(),"warehouse");
    }
    public List<Integer> getSelectedWarehouseQuantity() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getQuantity(toXML(),"warehouse");
    }
    public List<ResQuantity> getSelectedStrongBoxQuantity() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResQuantityList(toXML(),"strongBox");
    }

    //DO_PRODUCTION
    public boolean isPersonalProduction() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getBoolean(toXML(),"personalProduction");
    }
    public Map<Integer, String> getActivatedDevelopmentCards() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerString(toXML(), "developmentCards");
    }
    public List<ResQuantity> getChosenProducts() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResQuantityList(toXML(),"chosenProducts");
    }
    public List<ResQuantity> getChosenMaterials() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getResQuantityList(toXML(),"chosenMaterials");
    }

    //LEADER_ACTION
    public PlayerAction getLeaderCardAction() throws MalformedMessageException{
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getAction(toXML(),"action");
    }

    //SELECTED_TURN
    public TurnType getTurnTypeSelection() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getTurnType(toXML(),"turnType");
    }

    //SWAP
    public int getSourceSwap() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getInteger(toXML(),"source");
    }
    public int getTargetSwap() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getInteger(toXML(),"target");
    }

    //UPDATE_LEADER_CARDS
    public Map<Integer, Boolean> getLeaderCardStatus() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        return parser.getMapIntegerBoolean(toXML(),"leaderStatus");
    }
}
