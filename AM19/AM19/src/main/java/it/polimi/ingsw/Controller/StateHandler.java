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
 * This class represents the FSM which implements the logic of the controller
 */
public class StateHandler {

    /**
     * the model of the game
     */
    private GameBoardHandler gameBoard;

    /**
     * Virtual view
     */
    private View virtualView;

    /**
     * the possible current state
     */
    private LinkedList<Message.MessageType> currentStates;

    /**
     * the number of players whose initialization is missing
     */
    private int numberPlayersInit;

    /**
     * constructor
     * @param gameBoard the gameBoardHandler
     * @param virtualView the virtual view
     */
    /*public StateHandler(GameBoardHandler gameBoard, View virtualView) {

        this.gameBoard = gameBoard;
        this.numberPlayersInit = gameBoard.getNumPlayers();
        this.virtualView = virtualView;
        this.currentStates = new LinkedList<>();
        this.currentStates.add(Message.MessageType.UPDATE_LEADER_CARDS);
        this.gameBoard.setStartPossibleTurn();
        virtualView.showAnswer(true,"start initialization", gameBoard.currentPlayer());
        gameBoard.CurrentPlayer();
    }

    public boolean disconnection(String nickName){
        return disconnectionHandler(nickName);
    }

    public boolean reconnection(String nickName){
        return reconnectionHandler(nickName);
    }*/


    /**
     * This method handles the current state and sets the next one
     * @param message the message received
     * @param nickname the nickname of the player that sent the message
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    /*public boolean stateHandler(String message, String nickname) {

        ActionMessage actionMessage;
        try {

            if (!gameBoard.isCurrentPlayer(nickname)) {
                virtualView.showAnswer(false,"It's not your turn!", nickname);
                return false;
            }

            Message.MessageType type = MessageUtilities.instance().getType(message);

            if (currentStates.stream().noneMatch(messageType -> messageType.equals(type))) {
                virtualView.showAnswer(false, "Message not expected!", nickname);
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
                    virtualView.showAnswer(false,"Message not expected!", nickname);
                    return false;

            }
        }
        catch (MalformedMessageException e){
            virtualView.showAnswer(false,"Malformed message", nickname);
            return false;
        }
    }*/

    /**
     * Handler of the message MessageUpdateLeaderCards
     * @param actionMessage the message from the player
     * @return true if there are no errors, false otherwise
     */
    /*private boolean updateLeaderCardsHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        Map<Integer,Boolean> leaderStatus = actionMessage.getLeaderCardStatus();

        if(!gameBoard.initializeLeaderCard(leaderStatus)) {
            virtualView.showAnswer(false,"Error, incorrect update leader cards!", nickname);
            return false;
        }

        currentStates.clear();
        currentStates.add(Message.MessageType.RESOURCE);
        virtualView.getResourcesAction();
        return true;
    }*/


    /**
     * Handler of the message MessageResource
     * @param actionMessage the message from the player
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    /*private boolean resourceHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        List<Resource> lista = actionMessage.getResourcesInit();
        List<Integer> intlist = actionMessage.getShelvesInit();

        if(!gameBoard.insertResources(actionMessage.getResourcesInit(),actionMessage.getShelvesInit())) {
           virtualView.showAnswer(false,"Error, incorrect initialization resources selection!", nickname);
           return false;
       }

       numberPlayersInit--;
        System.out.println("DEBUG ENTRO");
       if(numberPlayersInit == 0){
           currentStates.clear();
           currentStates.add(Message.MessageType.SELECTED_TURN);
           virtualView.showAnswer(true,"resources selected correctly", nickname);
           gameBoard.CurrentPlayer();
           return true;
       }
        System.out.println("DEBUG ESCO");

       gameBoard.endTurnMove();
       currentStates.clear();
       currentStates.add(Message.MessageType.UPDATE_LEADER_CARDS);
       virtualView.showAnswer(true,"resources selected correctly", nickname);
       gameBoard.CurrentPlayer();
       return true;
    }*/

    /**
     * Handler of the message MessageSelectedTurn
     * @param actionMessage the message from the player
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    /*private boolean selectedTurnHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        if(!gameBoard.isPossibleTurn(actionMessage.getTurnTypeSelection())){
            virtualView.showAnswer(false, "You can't play this turn now!", nickname);
            return false;
        }
        switch (actionMessage.getTurnTypeSelection()){
            case MANAGE_LEADER:
                currentStates.clear();
                currentStates.add(Message.MessageType.LEADER_ACTION);
                virtualView.leaderAction();
                return true;
            case TAKE_RESOURCES:
                currentStates.clear();
                currentStates.add(Message.MessageType.MARKET_SELECTION);
                currentStates.add(Message.MessageType.SWAP);
                virtualView.selectMarketAction();
                return true;
            case BUY_CARD:
                currentStates.clear();
                currentStates.add(Message.MessageType.BUY_CARD);
                virtualView.buyCardAction();
                return true;
            case DO_PRODUCTION:
                currentStates.clear();
                currentStates.add(Message.MessageType.DO_PRODUCTION);
                virtualView.doProductionsAction();
                return true;
            case EXIT:
                gameBoard.endTurnMove();

                if(gameBoard.isGameEnded()) {

                    //chusura di tutto
                    return true;
                }

                gameBoard.setStartPossibleTurn();
                currentStates.clear();
                currentStates.add(Message.MessageType.SELECTED_TURN);
                gameBoard.CurrentPlayer();

                return true;
            default:
                //non deve mai capitare
                return false;
        }
    }*/

    /**
     * Handler of the message MessageSwap
     * @param actionMessage the message from the player
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    /*private boolean swapHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{
        try {
            gameBoard.swapWarehouse(actionMessage.getSourceSwap(), actionMessage.getTargetSwap());
            virtualView.swapAction();
            return true;
        }
        catch (InvalidActionException e){
            virtualView.showAnswer(false,e.getMessage(), nickname);
            return false;
        }
        //gli stati futuri possibili rimangono sia swap sia MarketSelection
    }*/

    /**
     * Handler of the message MessageMarketSelection
     * @param actionMessage the message from the player
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    /*private boolean marketSelectionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        try {
            //può essere null l'attributo enumerativo del messaggio??
            if(actionMessage.getSelectedMarketTray().equals(TraySelection.ROW))
                gameBoard.takeMarketRow(actionMessage.getSelectedMarketNumber());
            if(actionMessage.getSelectedMarketTray().equals(TraySelection.COLUMN))
                gameBoard.takeMarketColumn(actionMessage.getSelectedMarketNumber());

            currentStates.clear();
            currentStates.add(Message.MessageType.ACTION_MARBLE);
            virtualView.showAnswer(true,"Correct market selection", nickname);
            return true;

        }
        catch (InvalidActionException e){
            virtualView.showAnswer(false,e.getMessage(), nickname);
            return false;
        }

    }*/

    /**
     * Handler of the message MessageAction
     * @param actionMessage the message from the player
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    /*private boolean actionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{
        List<Marble> messageMarbles = actionMessage.getMarbleFromAction();
        List<PlayerAction> actions = actionMessage.getMarbleActions();
        List<Integer> shelves = actionMessage.getMarblesSelectedShelves();

        if(!gameBoard.actionMarbles(messageMarbles,actions,shelves)){
            virtualView.showAnswer(false,"Wrong marbles selection!", nickname);
            gameBoard.CurrentPlayer();
            return false;
        }

        gameBoard.setMiddlePossibleTurn();
        currentStates.clear();
        currentStates.add(Message.MessageType.SELECTED_TURN);
        virtualView.showAnswer(true,"Correct marbles selection!", nickname);
        gameBoard.CurrentPlayer();
        return true;
    }*/

    /**
     * Handler of the message MessageLeaderAction
     * @param actionMessage the message from the player
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    /*private boolean leaderActionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        int pos = actionMessage.getSelectedLeaderCards().keySet().iterator().next();
        try {
            if(actionMessage.getLeaderCardAction().equals(PlayerAction.INSERT))
                gameBoard.activateCard(pos);
            if(actionMessage.getLeaderCardAction().equals(PlayerAction.DISCARD))
                gameBoard.removeCard(pos);
            currentStates.clear();
            currentStates.add(Message.MessageType.SELECTED_TURN);
            virtualView.showAnswer(true,"Correct leader action", nickname);
            gameBoard.CurrentPlayer();
            return true;
        }
        catch (InvalidActionException e){

            currentStates.clear();
            currentStates.add(Message.MessageType.SELECTED_TURN);
            virtualView.showAnswer(false,e.getMessage(), nickname);
            gameBoard.CurrentPlayer();
            return false;
        }
    }*/

    /**
     * Handler of the message MessageBuyCard
     * @param actionMessage the message from the player
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    /*private boolean buyCardHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        ArrayList<Integer> shelves = new ArrayList<>(actionMessage.getSelectedWarehouseShelves());
        ArrayList<Integer> quantity = new ArrayList<>(actionMessage.getSelectedWarehouseQuantity());
        ArrayList<ResQuantity> strongbox = createCorrectStrongbox(actionMessage.getSelectedStrongBoxQuantity());

        try {
            gameBoard.buyCard(actionMessage.getTargetSlotDevelopmentCard(), actionMessage.getDevelopmentCardColor(), actionMessage.getDevelopmentCardLevel(), shelves,quantity,strongbox);
            gameBoard.setMiddlePossibleTurn();
            currentStates.clear();
            currentStates.add(Message.MessageType.SELECTED_TURN);
            virtualView.showAnswer(true,"Correct buy card", nickname);
            gameBoard.CurrentPlayer();
            return true;
        }
        catch (InvalidActionException e){

            currentStates.clear();
            currentStates.add(Message.MessageType.SELECTED_TURN);
            virtualView.showAnswer(false,e.getMessage(), nickname);
            gameBoard.CurrentPlayer();
            return false;
        }
    }*/

    /**
     * Handler of the message MessageDoProduction
     * @param actionMessage the message from the player
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    /*private boolean doProductionHandler(ActionMessage actionMessage, String nickname) throws MalformedMessageException{

        ArrayList<Production> productions = new ArrayList<>();
        int numberChoiceProducts = 0;
        int numberChoiceMaterials = 0;

        //controllo produzioni carte development
        for(int i : actionMessage.getActivatedDevelopmentCards().keySet()){
            try {
                Production production = gameBoard.getDevProduction(i);
                productions.add(production);
            }
            catch (InvalidActionException e){

                currentStates.clear();
                currentStates.add(Message.MessageType.SELECTED_TURN);
                virtualView.showAnswer(false,e.getMessage(), nickname);
                gameBoard.CurrentPlayer();

                return false;
            }
        }

        //controllo produzioni carte leader
        for(int i : actionMessage.getSelectedLeaderCards().keySet()){
            try {
                Production production = gameBoard.getLeaderProduction(i);
                productions.add(production);
                numberChoiceProducts += production.getCustomProducts();
                numberChoiceMaterials += production.getCustomMaterials();
            }
            catch (InvalidActionException e){

                currentStates.clear();
                currentStates.add(Message.MessageType.SELECTED_TURN);
                virtualView.showAnswer(false,e.getMessage(), nickname);
                gameBoard.CurrentPlayer();
                return false;
            }
        }

        //gestisco la produzione della board
        if(actionMessage.isPersonalProduction()){
            numberChoiceProducts += gameBoard.getBoardProduction().getCustomProducts();
            numberChoiceMaterials += gameBoard.getBoardProduction().getCustomMaterials();
            //importante non inizializzare mai le production con array vuoti
            productions.add(gameBoard.getBoardProduction());
        }

        //controllo che le quantità di risorse a scelta siano corrette
        //controllo che lo stream() faccia zero nel caso di lista vuota
        if(numberChoiceMaterials != actionMessage.getChosenMaterials().stream().mapToInt(ResQuantity::getQuantity).sum()) {
            virtualView.showAnswer(false, "Wrong chosen materials!", nickname);
            gameBoard.CurrentPlayer();
            return false;
        }
        if(numberChoiceProducts != actionMessage.getChosenProducts().stream().mapToInt(ResQuantity::getQuantity).sum()) {
            virtualView.showAnswer(false, "Wrong chosen products!", nickname);
            gameBoard.CurrentPlayer();
            return false;
        }

        //creo produzioni a scelta
        LinkedList<ResQuantity> materials = new LinkedList<>(actionMessage.getChosenMaterials());
        LinkedList<ResQuantity> products = new LinkedList<>(actionMessage.getChosenProducts());
        Production choiceProduction = new Production(materials,products,0,0);

        productions.add(choiceProduction);

        //creo arraylist di risorse selezionate
        ArrayList<Integer> shelves = new ArrayList<>(actionMessage.getSelectedWarehouseShelves());
        ArrayList<Integer> quantity = new ArrayList<>(actionMessage.getSelectedWarehouseQuantity());
        ArrayList<ResQuantity> strongbox = createCorrectStrongbox(actionMessage.getSelectedStrongBoxQuantity());

        try {
            gameBoard.doProduction(productions, shelves,quantity,strongbox);
            gameBoard.setMiddlePossibleTurn();
            currentStates.clear();
            currentStates.add(Message.MessageType.SELECTED_TURN);
            virtualView.showAnswer(true,"Correct do production", nickname);
            gameBoard.CurrentPlayer();
            return true;
        }
        catch (InvalidActionException e){

            currentStates.clear();
            currentStates.add(Message.MessageType.SELECTED_TURN);
            virtualView.showAnswer(false,e.getMessage(), nickname);
            gameBoard.CurrentPlayer();
            return false;
        }

    }*/

    /**
     * Handler of the message MessageConnection
     * @param nickname
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    //mancano da gestire le disconnessioni durante l'inizializzazione
    /*private boolean disconnectionHandler(String nickname) {

        if(gameBoard.isCurrentPlayer(nickname)){

            virtualView.showDisconnection(nickname);
            gameBoard.endTurnMove();

            if(gameBoard.isGameEnded()) {
                //chusura di tutto
            }

            gameBoard.setStartPossibleTurn();
            currentStates.clear();
            currentStates.add(Message.MessageType.SELECTED_TURN);

        }
        virtualView.showDisconnection(nickname);
        return gameBoard.disconnectPlayer(nickname);
    }

    /**
     * Handler of the message MessageConnection
     * @param nickname
     * @return true if the message was correct and the state has successful evolved, false otherwise
     */
    /*private boolean reconnectionHandler(String nickname){
        return gameBoard.reconnectPlayer(nickname);
    }*/



    /**
     * This method creates an ArrayList or resQuantity that meets the requirements for the parameter strongbox that has to be passed in DoProduction and BuyCard
     * @param strongbox
     * @return an ArrayList or resQuantity that meets the requirements for the parameter strongbox
     */
    /*private ArrayList<ResQuantity> createCorrectStrongbox(List<ResQuantity> strongbox){

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
    }*/

}
