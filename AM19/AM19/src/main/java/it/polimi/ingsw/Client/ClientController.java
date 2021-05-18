package it.polimi.ingsw.Client;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.View.ViewModel;

import java.util.*;

public class ClientController implements ClientConnectionListener {

    /**
     * this attribute represents the reduced model of the Client
     */
    private ViewModel model;

    /**
     * this attribute represents the view of the Client
     */
    private View view;

    /**
     * this attribute is true if the controller has not been started
     */
    private boolean notStarted;

    /**
     * this attribute is true if the login has been successful
     */
    private boolean loggedIn;

    /**
     * this attribute is true if the update has already been received
     */
    private boolean alreadyUpdated;

    /**
     * this attribute is true if the player don't want to perform other swaps
     */
    private boolean availableSwap;

    public ClientController(ViewModel model, View view){
        this.view = view;
        this.model = model;
        notStarted = false;
        alreadyUpdated = false;
        loggedIn = false;
        availableSwap = true;
    }

    public synchronized void start(){
        if(!notStarted){
            view.initialize();
            view.newPlayer();
            notStarted = false;
        }
    }

    @Override
    public synchronized void onReceivedMessage(String message, String nickname) {
       try{
           checkStart();
           MessageUtilities parser = MessageUtilities.instance();
           Message.MessageType type = parser.getType(message);
           if(type == Message.MessageType.GAME_STATUS)
               messageHandler(new GameStatusMessage(message));
           else {
               updateHandler(new UpdateMessage(message, type));

           }
       }
       catch(MalformedMessageException e){
           System.out.println("[CLIENT] Malformed message received. No action performed.");
           e.printStackTrace();
       }
    }

    @Override
    public synchronized void onMissingPong(String nickname) {
        view.showGameStatus(false, "ERROR: Missed pong", model.getPersonalNickname(), TurnType.WRONG_STATE);
    }

    private void checkStart(){
        if(notStarted){
            //termino il client. Il login non è mai avvenuto
            System.out.println("[Client] the controller has not been started. Closing connection...");
        }
    }

    private void messageHandler(GameStatusMessage message) throws MalformedMessageException {
        String self = model.getPersonalNickname();
        TurnType status = message.getStatus();

        //da mettere in un sottometodo
        if(status == TurnType.INITIALIZATION_LEADERS || status == TurnType.TURN_SELECTION){
            model.setCurrentPlayer(message.getPlayer());
            System.out.println("CIAO");
        }

        loginHandler(message.isOk());

        view.showGameStatus(message.isOk(), message.getBody(), self, status);
        if (model.getCurrentPlayer().equals(self)){

            if (message.isOk()) {
                model.setModelState(message.getStatus());
                actionHandler(message.getStatus(), message);
                return;
            }

            if(message.getStatus() != TurnType.WRONG_STATE)
                model.setModelState(message.getStatus());

            alreadyUpdated = true;
            actionHandler(model.getModelState(), message);


        }
    }

    private void loginHandler(boolean reply){
        if(!loggedIn && !reply){
            //termino il client. Il login non è mai avvenuto
            System.out.println("[Client] Cannot connect you to a game. Closing connection...");
        }
        loggedIn = true;
    }

    private void actionHandler(TurnType type, GameStatusMessage message) throws MalformedMessageException {
        switch(type){
            case INITIALIZATION_LEADERS: {
                view.selectLeaderAction();
                break;
            }
            case INITIALIZATION_RESOURCE: {
                view.getResourcesAction();
                break;
            }
            case SWAP: {
                swapHandler();
                break;
            }
            case TAKE_RESOURCES: {
                view.selectMarketAction();
                break;
            }
            case MANAGE_MARBLE: {
                manageMarbleHandler(message);
                break;
            }
            case MANAGE_LEADER: {
                view.leaderAction();
                break;
            }
            case BUY_CARD: {
                view.buyCardAction();
                break;
            }
            case DO_PRODUCTION: {
                view.doProductionsAction();
                break;
            }
            case TURN_SELECTION: {
                turnSelectionHandler(message);
                break;
            }
                //aggiungi wrong state
            default:
                break;
                //END CONNECTION BECAUSE OF WRONG MESSAGE FROM SERVER
        }
        alreadyUpdated = false;
        model.setModelState(type);
    }

    private void turnSelectionHandler(GameStatusMessage message) throws MalformedMessageException {

        String current = message.getPlayer();
        model.setCurrentPlayer(current);
        availableSwap = true;

        if(!alreadyUpdated) {
            List<String> turns = message.getTurnTypes();
            model.setAvailableTurns(new LinkedList<>(turns));
        }

        if(current.equals(model.getPersonalNickname())){
            view.selectTurnAction(model.getAvailableTurns(), model.getCurrentPlayer());
        }
        else model.setCurrentPlayer(current);
    }

    private void manageMarbleHandler(GameStatusMessage message) throws MalformedMessageException {

        if(!alreadyUpdated) {
            List<Marble> selected = message.getSelectedMarbles();
            List<Marble> candidates = message.getCandidateWhite();
            model.setSelectedMarbles(selected);
            model.setPossibleWhites(candidates);
        }
        view.showMarblesUpdate(model.getSelectedMarbles(), model.getPossibleWhites(), model.getCurrentPlayer());
    }

    private void swapHandler(){
        if(availableSwap)
            availableSwap = view.swapAction();
        if(!availableSwap) {
            view.selectMarketAction();
            availableSwap = true;
        }
    }

    private void updateHandler(UpdateMessage message) throws MalformedMessageException{

        switch(message.getMessageType()){
            case DISCONNECTION: {
                disconnectionUpdate(message);
                break;
            }
            case UPDATE_LEADER_CARDS: {
                leadersUpdate(message);
                break;
            }
            case END_GAME: {
                endGameUpdate(message);
                break;
            }
            case BOX_UPDATE: {
                boxUpdate(message);
                break;
            }
            case SLOTS_UPDATE: {
                slotsUpdate(message);
                break;
            }
            case DECKS_UPDATE: {
                decksUpdate(message);
                break;
            }
            case FAITH_UPDATE: {
                faithUpdate(message);
                break;
            }
            case TOKEN_UPDATE: {
                tokenUpdate(message);
                break;
            }
            case MARKET_UPDATE: {
                marketUpdate(message);
                break;
            }
            default:
                //END CONNECTION. WRONG SEQUENCE OF MESSAGES OF UNKNOWN MESSAGE
                break;
        }
    }

    private void disconnectionUpdate(UpdateMessage message) throws MalformedMessageException {
        String disconnected = message.getPlayer();
        if(model.getNicknames().contains(disconnected)) {
            view.showDisconnection(disconnected);
            model.removeNickname(disconnected);
        }

    }

    private void endGameUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<String, Integer> map = message.getEndGamePoints();
        view.showEndGame(map);

        //Here the connection should be closed (client side)
    }

    private void boxUpdate(UpdateMessage message) throws MalformedMessageException {
        List<ResQuantity> warehouse, strongbox;
        String player = message.getPlayer();
        warehouse = message.getWarehouseUpdate();
        strongbox = message.getStrongboxUpdate();
        model.setWarehouse(warehouse, player);
        model.setStrongbox(strongbox, player);
        view.showBoxes(warehouse, strongbox, player);
    }

    private void slotsUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<Integer, String> slots = message.getSlotsUpdate();
        String player = message.getPlayer();
        model.setSlots(slots, player);
        view.showSlotsUpdate(slots, player);
    }

    private void decksUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<Integer, String> decks = message.getDecksUpdate();
        model.setDecks(decks);
        view.showDecksUpdate(decks);
    }

    private void faithUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<String, Integer> faith = message.getFaithPoints();
        Map<String, List<ItemStatus>> sections = new HashMap<>();
        List<String> nicknames = message.getNicknames();
        for(String name : faith.keySet()){
            sections.put(name, message.getSections(name));
            model.setSections(message.getSections(name), model.getCurrentPlayer());
        }
        Optional<List<ItemStatus>> lorenzoSections = message.getLorenzoSections();
        Optional<Integer> lorenzoFaith = message.getLorenzoFaith();
        model.setFaithPoints(faith);
        model.setNicknames(nicknames);
        lorenzoFaith.ifPresent(integer -> model.setLorenzoFaith(integer));
        lorenzoSections.ifPresent(itemStatuses -> model.setLorenzoSections(itemStatuses));

        view.showFaithUpdate(faith, sections, lorenzoFaith, lorenzoSections);

    }

    private void tokenUpdate(UpdateMessage message) throws MalformedMessageException {
        String token = message.getTopToken();
        if(token.length() != 0) {
            model.setActionToken(token);
            view.showTopToken(Optional.of(token));
        }
    }

    private void marketUpdate(UpdateMessage message) throws MalformedMessageException {
        List<Marble> tray = message.getMarketUpdate();
        model.setMarket(tray);
        view.showMarketUpdate(tray);
    }

    private void leadersUpdate(UpdateMessage message) throws MalformedMessageException{
        Map<Integer, ItemStatus> status = message.getLeaderCardsStatus();
        Map<Integer, String> cards = message.getLeaderCardsUpdate();
        String player = message.getPlayer();
        model.setLeadersID(cards, player);
        model.setLeadersStatus(status, player);
        view.showLeaderCards(cards, status, player);
    }
}

