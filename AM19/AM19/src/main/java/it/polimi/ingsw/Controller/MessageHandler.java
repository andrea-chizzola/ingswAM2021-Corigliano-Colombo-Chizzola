package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Messages.Enumerations.PlayerAction;
import it.polimi.ingsw.Messages.Enumerations.TraySelection;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
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
     * the possible current messages
     */
    private LinkedList<Message.MessageType> currentMessage;

    /**
     * the number of players whose initialization is missing
     */
    private int numberPlayersInit;

    /**
     * constructor
     * @param gameBoard the gameBoardHandler
     * @param virtualView the virtual view
     */
    public MessageHandler(GameBoardHandler gameBoard, View virtualView) {

        this.gameBoard = gameBoard;
        this.numberPlayersInit = gameBoard.getNumPlayers();
        this.virtualView = virtualView;
        this.currentMessage = new LinkedList<>();
        this.currentMessage.add(Message.MessageType.UPDATE_LEADER_CARDS);
        this.gameBoard.setStartPossibleTurn();

        virtualView.selectLeaderAction();

    }


    /**
     * This method handles the current state and sets the next one
     * @param message the message received
     * @param nickname the nickname of the player that sent the message
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    public boolean messageHandler(String message, String nickname) {

        ActionMessage actionMessage;
        try {

            if (!gameBoard.isCurrentPlayer(nickname)) {
                virtualView.showGameStatus(false,"It's not your turn!", nickname,TurnType.WRONG_STATE);
                return false;
            }

            Message.MessageType type = MessageUtilities.instance().getType(message);

            if (currentMessage.stream().noneMatch(messageType -> messageType.equals(type))) {
                virtualView.showGameStatus(false, "Message not expected!", nickname, TurnType.WRONG_STATE);
                return false;
            }

            switch (type) {
                //initialization of leader cards
                case UPDATE_LEADER_CARDS:
                    actionMessage = new ActionMessage(message, Message.MessageType.UPDATE_LEADER_CARDS);
                    return updateLeaderCardsHandler(actionMessage, nickname);
                //initialization resources
                case RESOURCE:
                    actionMessage = new ActionMessage(message, Message.MessageType.RESOURCE);
                    return resourceHandler(actionMessage, nickname);
                //select turn type
                case SELECTED_TURN:
                    actionMessage = new ActionMessage(message, Message.MessageType.SELECTED_TURN);
                    return selectedTurnHandler(actionMessage, nickname);
                //select turn type
                case SWAP:
                    actionMessage = new ActionMessage(message, Message.MessageType.SWAP);
                    return swapHandler(actionMessage, nickname);
                //selection of row or column
                case MARKET_SELECTION:
                    actionMessage = new ActionMessage(message, Message.MessageType.MARKET_SELECTION);
                    return marketSelectionHandler(actionMessage, nickname);
                //actions performed on the marbles taken from the market
                case ACTION_MARBLE:
                    actionMessage = new ActionMessage(message, Message.MessageType.ACTION_MARBLE);
                    return actionHandler(actionMessage, nickname);
                //manage leader cards
                case LEADER_ACTION:
                    actionMessage = new ActionMessage(message, Message.MessageType.LEADER_ACTION);
                    return leaderActionHandler(actionMessage, nickname);
                //buy a development card
                case BUY_CARD:
                    actionMessage = new ActionMessage(message, Message.MessageType.BUY_CARD);
                    return buyCardHandler(actionMessage, nickname);
                //do production (unique message)
                case DO_PRODUCTION:
                    actionMessage = new ActionMessage(message, Message.MessageType.DO_PRODUCTION);
                    return doProductionHandler(actionMessage, nickname);
                //if a player is disconnected
                case DISCONNECTION:
                    return disconnectionHandler(nickname);
                //if a pleyer is reconnected
                case RECONNECTION:
                    return reconnectionHandler(nickname);

                default:
                    virtualView.showGameStatus(false,"Message not expected!", nickname, TurnType.WRONG_STATE);
                    return false;
            }
        }
        catch (MalformedMessageException e){
            virtualView.showGameStatus(false,"Malformed message", nickname, TurnType.WRONG_STATE);
            return false;
        }
    }

    /**
     * Handler of the message MessageConnection
     * @param nickName nickname of the player
     * @return true if the message was correct, false otherwise
     */
    public boolean disconnection(String nickName){
        return disconnectionHandler(nickName);
    }

    /**
     * Handler of the message MessageConnection
     * @param nickName nickname of the player
     * @return true if the message was correct, false otherwise
     */
    public boolean reconnection(String nickName){
        return reconnectionHandler(nickName);
    }

    /**
     * Handler of the message MessageUpdateLeaderCards
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if there are no errors, false otherwise
     */
    private boolean updateLeaderCardsHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        Map<Integer,Boolean> leaderStatus = actionMessage.getLeaderCardStatus();

        if(!gameBoard.initializeLeaderCard(leaderStatus)) {
            virtualView.showGameStatus(false,"Error, incorrect update leader cards!", nickname, TurnType.INITIALIZATION_LEADERS);
            return false;
        }

        currentMessage.clear();
        currentMessage.add(Message.MessageType.RESOURCE);
        virtualView.getResourcesAction();
        return true;
    }


    /**
     * Handler of the message MessageResource
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private boolean resourceHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        if(!gameBoard.insertResources(actionMessage.getResourcesInit(),actionMessage.getShelvesInit())) {
           virtualView.showGameStatus(false,"Error, incorrect initialization resources selection!", nickname, TurnType.INITIALIZATION_RESOURCE);
           return false;
        }

        numberPlayersInit--;

        currentMessage.clear();
        if(numberPlayersInit == 0){
            currentMessage.add(Message.MessageType.SELECTED_TURN);
            gameBoard.endTurnMove();
            gameBoard.currentPlayer();
        }
        else{
            currentMessage.add(Message.MessageType.UPDATE_LEADER_CARDS);
            gameBoard.endTurnMove();
            virtualView.selectLeaderAction();
        }

        return true;
    }

    /**
     * Handler of the message MessageSelectedTurn
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private boolean selectedTurnHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        if(!gameBoard.isPossibleTurn(actionMessage.getTurnTypeSelection())){
            virtualView.showGameStatus(false, "You can't play this turn now!", nickname,TurnType.TURN_SELECTION);
            return false;
        }
        switch (actionMessage.getTurnTypeSelection()){
            case MANAGE_LEADER:
                currentMessage.clear();
                currentMessage.add(Message.MessageType.LEADER_ACTION);
                virtualView.leaderAction();
                return true;
            case TAKE_RESOURCES:
                currentMessage.clear();
                currentMessage.add(Message.MessageType.MARKET_SELECTION);
                currentMessage.add(Message.MessageType.SWAP);
                virtualView.swapAction();
                return true;
            case BUY_CARD:
                currentMessage.clear();
                currentMessage.add(Message.MessageType.BUY_CARD);
                virtualView.buyCardAction();
                return true;
            case DO_PRODUCTION:
                currentMessage.clear();
                currentMessage.add(Message.MessageType.DO_PRODUCTION);
                virtualView.doProductionsAction();
                return true;
            case EXIT:
                gameBoard.endTurnMove();

                if(gameBoard.isGameEnded()) {
                    //chusura di tutto
                    return true;
                }

                gameBoard.setStartPossibleTurn();
                currentMessage.clear();
                currentMessage.add(Message.MessageType.SELECTED_TURN);
                gameBoard.currentPlayer();
                return true;

            default:
                return false;
        }
    }

    /**
     * Handler of the message MessageSwap
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private boolean swapHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{
        try {
            gameBoard.swapWarehouse(actionMessage.getSourceSwap(), actionMessage.getTargetSwap());
            virtualView.swapAction();
            return true;
        }
        catch (InvalidActionException e){
            virtualView.showGameStatus(false,e.getMessage(), nickname, TurnType.SWAP);
            return false;
        }
    }

    /**
     * Handler of the message MessageMarketSelection
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private boolean marketSelectionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        try {
            if(actionMessage.getSelectedMarketTray().equals(TraySelection.ROW))
                gameBoard.takeMarketRow(actionMessage.getSelectedMarketNumber());

            if(actionMessage.getSelectedMarketTray().equals(TraySelection.COLUMN))
                gameBoard.takeMarketColumn(actionMessage.getSelectedMarketNumber());

            currentMessage.clear();
            currentMessage.add(Message.MessageType.ACTION_MARBLE);

            return true;
        }
        catch (InvalidActionException e){
            virtualView.showGameStatus(false,e.getMessage(), nickname,TurnType.TAKE_RESOURCES);
            return false;
        }
    }

    /**
     * Handler of the message MessageAction
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private boolean actionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{
        List<Marble> messageMarbles = actionMessage.getMarbleFromAction();
        List<PlayerAction> actions = actionMessage.getMarbleActions();
        List<Integer> shelves = actionMessage.getMarblesSelectedShelves();

        if(!gameBoard.actionMarbles(messageMarbles,actions,shelves)){
            virtualView.showGameStatus(false,"Wrong marbles selection!", nickname,TurnType.MANAGE_MARBLE);
            return false;
        }

        gameBoard.setMiddlePossibleTurn();
        currentMessage.clear();
        currentMessage.add(Message.MessageType.SELECTED_TURN);
        gameBoard.currentPlayer();
        return true;
    }

    /**
     * Handler of the message MessageLeaderAction
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private boolean leaderActionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        int pos = actionMessage.getSelectedLeaderCards().keySet().iterator().next();
        try {
            if(actionMessage.getLeaderCardAction().equals(PlayerAction.INSERT))
                gameBoard.activateCard(pos);
            if(actionMessage.getLeaderCardAction().equals(PlayerAction.DISCARD))
                gameBoard.removeCard(pos);
            currentMessage.clear();
            currentMessage.add(Message.MessageType.SELECTED_TURN);
            gameBoard.currentPlayer();
            return true;
        }
        catch (InvalidActionException e){
            currentMessage.clear();
            currentMessage.add(Message.MessageType.SELECTED_TURN);
            virtualView.showGameStatus(false,e.getMessage(), nickname,TurnType.TURN_SELECTION);
            return false;
        }
    }

    /**
     * Handler of the message MessageBuyCard
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private boolean buyCardHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        ArrayList<Integer> shelves = new ArrayList<>(actionMessage.getSelectedWarehouseShelves());
        ArrayList<Integer> quantity = new ArrayList<>(actionMessage.getSelectedWarehouseQuantity());
        ArrayList<ResQuantity> strongbox = createCorrectStrongbox(actionMessage.getSelectedStrongBoxQuantity());

        try {
            gameBoard.buyCard(actionMessage.getTargetSlotDevelopmentCard(), actionMessage.getDevelopmentCardColor(), actionMessage.getDevelopmentCardLevel(), shelves,quantity,strongbox);
            gameBoard.setMiddlePossibleTurn();
            currentMessage.clear();
            currentMessage.add(Message.MessageType.SELECTED_TURN);
            gameBoard.currentPlayer();
            return true;
        }
        catch (InvalidActionException e){

            currentMessage.clear();
            currentMessage.add(Message.MessageType.SELECTED_TURN);
            virtualView.showGameStatus(false,e.getMessage(), nickname, TurnType.TURN_SELECTION);
            gameBoard.currentPlayer();
            return false;
        }
    }

    /**
     * Handler of the message MessageDoProduction
     * @param actionMessage the message from the player
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private boolean doProductionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        ArrayList<Production> productions = new ArrayList<>();
        int numberChoiceProducts = 0;
        int numberChoiceMaterials = 0;

        //check development cards
        for(int i : actionMessage.getActivatedDevelopmentCards().keySet()){
            try {
                Production production = gameBoard.getDevProduction(i);
                productions.add(production);
            }
            catch (InvalidActionException e){

                currentMessage.clear();
                currentMessage.add(Message.MessageType.SELECTED_TURN);
                virtualView.showGameStatus(false,e.getMessage(), nickname, TurnType.TURN_SELECTION);

                return false;
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

                currentMessage.clear();
                currentMessage.add(Message.MessageType.SELECTED_TURN);
                virtualView.showGameStatus(false,e.getMessage(), nickname, TurnType.TURN_SELECTION);
                return false;
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
            currentMessage.clear();
            currentMessage.add(Message.MessageType.SELECTED_TURN);
            virtualView.showGameStatus(false,"wrong chosen materials!", nickname, TurnType.TURN_SELECTION);
            return false;
        }
        if(numberChoiceProducts != actionMessage.getChosenProducts().stream().mapToInt(ResQuantity::getQuantity).sum()) {
            currentMessage.clear();
            currentMessage.add(Message.MessageType.SELECTED_TURN);
            virtualView.showGameStatus(false,"wrong chosen products!", nickname, TurnType.TURN_SELECTION);
            return false;
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
            gameBoard.setMiddlePossibleTurn();
            currentMessage.clear();
            currentMessage.add(Message.MessageType.SELECTED_TURN);
            gameBoard.currentPlayer();
            return true;
        }
        catch (InvalidActionException e){

            currentMessage.clear();
            currentMessage.add(Message.MessageType.SELECTED_TURN);
            virtualView.showGameStatus(false,e.getMessage(), nickname, TurnType.TURN_SELECTION);
            return false;
        }

    }

    /**
     * Handler of the message MessageDisconnection
     * @param nickname the nickname of the player
     * @return true if the message was correct, false otherwise
     */
    private boolean disconnectionHandler(String nickname) {

        if(numberPlayersInit != 0){
            //va rivisto ,può dare problemi
            virtualView.showEndGame(new HashMap<>());
        }
        if(gameBoard.isCurrentPlayer(nickname)){

            virtualView.showDisconnection(nickname);
            gameBoard.endTurnMove();

            if(gameBoard.isGameEnded()) {
                //chusura di tutto
            }

            gameBoard.setStartPossibleTurn();
            currentMessage.clear();
            currentMessage.add(Message.MessageType.SELECTED_TURN);
            //chiamato sul nuovo current player, ho gia fatto la end turn move
            gameBoard.currentPlayer();

        }
        virtualView.showDisconnection(nickname);
        return gameBoard.disconnectPlayer(nickname);
    }

    /**
     * Handler of the message MessageConnection
     * @param nickname
     * @return true if the message was correct, false otherwise
     */
    private boolean reconnectionHandler(String nickname){
        return gameBoard.reconnectPlayer(nickname);
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
