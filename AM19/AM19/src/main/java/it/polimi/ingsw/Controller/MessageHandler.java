package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Messages.Enumerations.PlayerAction;
import it.polimi.ingsw.Messages.Enumerations.TraySelection;
import it.polimi.ingsw.Model.Boards.GameBoardHandler;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;
import it.polimi.ingsw.View.View;


import java.util.*;


/**
 * This class handles the messages and makes the model evolve
 */
public class MessageHandler {

    /**
     * the model of the game
     */
    private GameBoardHandler gameBoard;

    /**
     * Virtual view
     */
    private View virtualView;

    /**
     * constructor
     * @param gameBoard the gameBoardHandler
     * @param virtualView the virtual view
     */
    public MessageHandler(GameBoardHandler gameBoard, View virtualView) {

        this.gameBoard = gameBoard;
        this.virtualView = virtualView;
    }

    /**
     * This method handles the current state and sets the next one
     * @param message the message received
     * @param nickname the nickname of the player that sent the message
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    public void messageHandler(String message, String nickname) {

        ActionMessage actionMessage;
        try {
            if (!gameBoard.isCurrentPlayer(nickname)) {

                virtualView.reply(false,"Not current player", nickname);
                return;
            }

            Message.MessageType type = MessageUtilities.instance().getType(message);

            switch (type) {
                //initialization of leader cards
                case UPDATE_LEADER_CARDS:
                    actionMessage = new ActionMessage(message, Message.MessageType.UPDATE_LEADER_CARDS);
                    updateLeaderCardsHandler(actionMessage, nickname);
                    break;
                //initialization resources
                case RESOURCE:
                    actionMessage = new ActionMessage(message, Message.MessageType.RESOURCE);
                    resourceHandler(actionMessage, nickname);
                    break;
                //EXIT
                case EXIT:
                    exitHandler(nickname);
                    break;
                //SWAP
                case SWAP:
                    actionMessage = new ActionMessage(message, Message.MessageType.SWAP);
                    swapHandler(actionMessage, nickname);
                    break;
                //selection of row or column
                case MARKET_SELECTION:
                    actionMessage = new ActionMessage(message, Message.MessageType.MARKET_SELECTION);
                    marketSelectionHandler(actionMessage, nickname);
                    break;
                //actions performed on the marbles taken from the market
                case ACTION_MARBLE:
                    actionMessage = new ActionMessage(message, Message.MessageType.ACTION_MARBLE);
                    actionHandler(actionMessage, nickname);
                    break;
                //manage leader cards
                case LEADER_ACTION:
                    actionMessage = new ActionMessage(message, Message.MessageType.LEADER_ACTION);
                    leaderActionHandler(actionMessage, nickname);
                    break;
                //buy a development card
                case BUY_CARD:
                    actionMessage = new ActionMessage(message, Message.MessageType.BUY_CARD);
                    buyCardHandler(actionMessage, nickname);
                    break;
                //do production (unique message)
                case DO_PRODUCTION:
                    actionMessage = new ActionMessage(message, Message.MessageType.DO_PRODUCTION);
                    doProductionHandler(actionMessage, nickname);
                    break;

                default:
                    virtualView.reply(false,"Wrong message", nickname);
                    break;
            }
        }
        catch (MalformedMessageException e){
            virtualView.reply(false,"Wrong message", nickname);
        }
    }

    /**
     * Handler of the message MessageConnection
     * @param nickName nickname of the player
     * @return true if the message was correct, false otherwise
     */
    public void disconnection(String nickName){
        gameBoard.disconnectPlayer(nickName);
    }

    /**
     * Handler of the message MessageConnection
     * @param nickName nickname of the player
     * @return true if the message was correct, false otherwise
     */
    public void reconnection(String nickName){
        gameBoard.reconnectPlayer(nickName);
    }

    /**
     * Handler of the message MessageUpdateLeaderCards
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     */
    private void updateLeaderCardsHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        Map<Integer,Boolean> leaderStatus = actionMessage.getLeaderCardStatus();

        try {
            gameBoard.initializeLeaderCard(leaderStatus);
        }
        catch (InvalidActionException e){
            virtualView.reply(false,e.getMessage(), nickname);
        }
    }

    /**
     * Handler of the message MessageResource
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     */
    private void resourceHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        try {
            gameBoard.insertResources(actionMessage.getResourcesInit(),actionMessage.getShelvesInit());
        }
        catch (InvalidActionException e){
            virtualView.reply(false,e.getMessage(), nickname);
        }
    }


    /**
     * Handler of the message Exit
     * @param nickname the nickname of the player
     */
    private void exitHandler(String nickname){

        try {
            gameBoard.exit();
        }
        catch (InvalidActionException e){
            virtualView.reply(false,e.getMessage(), nickname);
        }
    }

    /**
     * Handler of the message MessageSwap
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private void swapHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        try {
            gameBoard.swapWarehouse(actionMessage.getSourceSwap(), actionMessage.getTargetSwap());
        }
        catch (InvalidActionException e) {
            virtualView.reply(false,e.getMessage(), nickname);
        }
    }

    /**
     * Handler of the message MessageMarketSelection
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     */
    private void marketSelectionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        try {
            if(actionMessage.getSelectedMarketTray().equals(TraySelection.ROW))
                gameBoard.takeMarketRow(actionMessage.getSelectedMarketNumber());

            if(actionMessage.getSelectedMarketTray().equals(TraySelection.COLUMN))
                gameBoard.takeMarketColumn(actionMessage.getSelectedMarketNumber());

        }
        catch (InvalidActionException e){
            virtualView.reply(false,e.getMessage(), nickname);
        }
    }

    /**
     * Handler of the message MessageAction
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     */
    private void actionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        List<Marble> messageMarbles = actionMessage.getMarbleFromAction();
        List<PlayerAction> actions = actionMessage.getMarbleActions();
        List<Integer> shelves = actionMessage.getMarblesSelectedShelves();

        try {
            gameBoard.actionMarbles(messageMarbles,actions,shelves);
        }
        catch (InvalidActionException e){
            virtualView.reply(false,e.getMessage(), nickname);
        }
    }

    /**
     * Handler of the message MessageLeaderAction
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     */
    private void leaderActionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        int pos = actionMessage.getSelectedLeaderCards().keySet().iterator().next();

        try {
            if(actionMessage.getLeaderCardAction().equals(PlayerAction.INSERT))
                gameBoard.activateCard(pos);
            if(actionMessage.getLeaderCardAction().equals(PlayerAction.DISCARD))
                gameBoard.removeCard(pos);
        }
        catch (InvalidActionException e){
            virtualView.reply(false,e.getMessage(), nickname);
        }
    }

    /**
     * Handler of the message MessageBuyCard
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private void buyCardHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        ArrayList<Integer> shelves = new ArrayList<>(actionMessage.getSelectedWarehouseShelves());
        ArrayList<Integer> quantity = new ArrayList<>(actionMessage.getSelectedWarehouseQuantity());
        ArrayList<ResQuantity> strongbox = createCorrectStrongbox(actionMessage.getSelectedStrongBoxQuantity());

        try {
            gameBoard.buyCard(actionMessage.getTargetSlotDevelopmentCard(), actionMessage.getDevelopmentCardColor(), actionMessage.getDevelopmentCardLevel(), shelves,quantity,strongbox);
        }
        catch (InvalidActionException e){

            virtualView.reply(false,e.getMessage(), nickname);
        }
    }

    /**
     * Handler of the message MessageDoProduction
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private void doProductionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        ArrayList<Production> productions = new ArrayList<>();
        int numberChoiceProducts = 0;
        int numberChoiceMaterials = 0;

        if(actionMessage.getSelectedLeaderCards().size()==0 && actionMessage.getActivatedDevelopmentCards().size()==0 && !actionMessage.isPersonalProduction()){
            virtualView.reply(false,"No productions selected", nickname);
        }

        //check development cards
        for(int i : actionMessage.getActivatedDevelopmentCards().keySet()){
            try {
                Production production = gameBoard.getDevProduction(i);
                productions.add(production);
            }
            catch (InvalidActionException e){
                virtualView.reply(false,e.getMessage(), nickname);
            }
        }

        //check leader cards
        for(int i : actionMessage.getSelectedLeaderCards().keySet()){
            try {
                Production production = gameBoard.getLeaderProduction(i);
                productions.add(production);
                numberChoiceProducts += production.getCustomProducts();
                numberChoiceMaterials += production.getCustomMaterials();
            }
            catch (InvalidActionException e){
                virtualView.reply(false,e.getMessage(), nickname);
            }
        }

        //personal board production
        if(actionMessage.isPersonalProduction()){
            numberChoiceProducts += gameBoard.getBoardProduction().getCustomProducts();
            numberChoiceMaterials += gameBoard.getBoardProduction().getCustomMaterials();
            productions.add(gameBoard.getBoardProduction());
        }

        //check of number chosen resources
        if(numberChoiceMaterials != actionMessage.getChosenMaterials().stream().mapToInt(ResQuantity::getQuantity).sum()) {
            virtualView.reply(false,"Wrong number chosen materials", nickname);
        }
        if(numberChoiceProducts != actionMessage.getChosenProducts().stream().mapToInt(ResQuantity::getQuantity).sum()) {
            virtualView.reply(false,"Wrong number chosen products", nickname);
        }

        //creating new production
        LinkedList<ResQuantity> materials = new LinkedList<>(actionMessage.getChosenMaterials());
        LinkedList<ResQuantity> products = new LinkedList<>(actionMessage.getChosenProducts());
        Production choiceProduction = new Production(materials,products,0,0);

        productions.add(choiceProduction);

        //selected resources
        ArrayList<Integer> shelves = new ArrayList<>(actionMessage.getSelectedWarehouseShelves());
        ArrayList<Integer> quantity = new ArrayList<>(actionMessage.getSelectedWarehouseQuantity());
        ArrayList<ResQuantity> strongbox = createCorrectStrongbox(actionMessage.getSelectedStrongBoxQuantity());

        try {
            gameBoard.doProduction(productions, shelves,quantity,strongbox);
        }
        catch (InvalidActionException e){
            virtualView.reply(false,e.getMessage(), nickname);
        }
    }

    /**
     * This method creates an ArrayList or resQuantity that meets the requirements for the parameter strongbox that has to be passed in DoProduction and BuyCard
     * @param strongbox
     * @return an ArrayList or resQuantity that meets the requirements for the parameter strongbox
     */
    private ArrayList<ResQuantity> createCorrectStrongbox(List<ResQuantity> strongbox){

        Map<Resource,Integer> map = new HashMap<>();
        ArrayList<ResQuantity> list = new ArrayList<>();

        for(ResQuantity resQuantity : strongbox){
            if(map.containsKey(resQuantity.getResource())){
                map.put(resQuantity.getResource(), map.get(resQuantity.getResource()) + resQuantity.getQuantity());}
            else{map.put(resQuantity.getResource(), resQuantity.getQuantity());}
        }

        for (Resource resource : map.keySet()){
            list.add(new ResQuantity(resource,map.get(resource)));
        }
        return list;
    }

}
