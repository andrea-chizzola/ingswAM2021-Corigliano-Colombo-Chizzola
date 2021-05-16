package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.ConnectionListener;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
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
     * this state represents the current state of the Client
     */
    private ClientStates state;

    /**
     * this attribute is true if the player has not logged in
     */
    private boolean loginPhase;


    public ClientController(ViewModel model, View view){
        this.view = view;
        this.model = model;
        this.state = new Login();
        loginPhase = false;
    }

    public void start(){
        if(!loginPhase){
            state.handleState(view, model);
            loginPhase = true;
        }
    }

    protected void setState(ClientStates state){
        this.state = state;
    }

    @Override
    public synchronized void onReceivedMessage(String message) {
       try{
           messageHandler(message);
       }
       catch(MalformedMessageException e){
           System.out.println("[CLIENT] malformed message received. No action performed.");
       }
    }

    @Override
    public synchronized void onMissingPong() {
        view.showAnswer(false, "ERROR: Missed pong", model.getPersonalNickname());
    }

    private void messageHandler(String message) throws MalformedMessageException {

        MessageUtilities parser = MessageUtilities.instance();

        Message.MessageType type = parser.getType(message);
        if(type == Message.MessageType.REPLY)
            stateHandler(new ReplyMessage(message));
        else if(type == Message.MessageType.GAME_STATUS)
            turnSelectionHandler(new UpdateMessage(message, type));
        else updateHandler(new UpdateMessage(message, type));
    }

    private void stateHandler(ReplyMessage message) throws MalformedMessageException {
        String recipient = message.getPlayer(), self = model.getPersonalNickname();

        if(self.equals(recipient)) {
            view.showAnswer(message.isOk(), message.getBody(), self);

            if (message.isOk() && model.getCurrentPlayer().equals(self))
                state = state.nextState();

            if(!state.isInitialization() && !state.isTurnSelection()) state.handleState(view, model);
        }
    }

    private void turnSelectionHandler(UpdateMessage message) throws MalformedMessageException {

        String current = message.getCurrentPlayer();
        List<String> turns = message.getTurnTypes();
        model.setAvailableTurns(new LinkedList<>(turns));

        if(current.equals(model.getPersonalNickname()) && (state.isTurnSelection() || state.isInitialization())){
            model.setCurrentPlayer(current);
            state.handleState(view, model);
        }
        else model.setCurrentPlayer(current);
    }

    //gestione update dello ViewModel. Chiama i setter del model usando i setter. Qui gestisco anche il messaggio
    //di disconnessione proveniente dal server. Tra i turni della CLI, devo dare anche la possibilità di sconnettersi
    private void updateHandler(UpdateMessage message) throws MalformedMessageException{
        switch(message.getMessageType()){
            case DISCONNECTION:
                disconnectionUpdate(message);
            case END_GAME:
                endGameUpdate(message);
            case BOX_UPDATE:
                boxUpdate(message);
            case SLOTS_UPDATE:
                slotsUpdate(message);
            case DECKS_UPDATE:
                decksUpdate(message);
            case FAITH_UPDATE:
                faithUpdate(message);
            case TOKEN_UPDATE:
                tokenUpdate(message);
            case MARKET_UPDATE:
                marketUpdate(message);
            case SELECTED_MARBLES:
                selectedMarble(message);
            default:
                //END CONNECTION. WRONG SEQUENCE OF MESSAGES OF UNKNOWN MESSAGE
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
        warehouse = message.getWarehouseUpdate();
        strongbox = message.getStrongboxUpdate();
        view.showBoxes(warehouse, strongbox, model.getCurrentPlayer());
    }

    private void slotsUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<Integer, String> slots = message.getSlotsUpdate();
        view.showSlotsUpdate(slots, model.getCurrentPlayer());
    }

    private void decksUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<Integer, String> decks = message.getDecksUpdate();
        view.showDecksUpdate(decks);
    }

    private void faithUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<String, Integer> faith = message.getFaithPoints();
        Map<String, List<ItemStatus>> sections = new HashMap<>();
        List<String> nicknames = message.getNicknames();
        for(String name : faith.keySet()){
            sections.put(name, message.getSections(name));
        }
        Optional<List<ItemStatus>> lorenzoSections = message.getLorenzoSections();
        Optional<Integer> lorenzoFaith = message.getLorenzoFaith();
        model.setNicknames(nicknames);
        view.showFaithUpdate(faith, sections, lorenzoFaith, lorenzoSections);

    }

    private void tokenUpdate(UpdateMessage message) throws MalformedMessageException {
        String token = message.getTopToken();
        view.showTopToken(Optional.of(token));
    }

    private void marketUpdate(UpdateMessage message) throws MalformedMessageException {
        List<Marble> tray = message.getMarketUpdate();
        view.showMarketUpdate(tray);
    }

    private void selectedMarble(UpdateMessage message) throws MalformedMessageException {
        List<Marble> selected = message.getSelectedMarbles();
        List<Marble> candidates = message.getCandidateWhite();
        view.showMarblesUpdate(selected, candidates, model.getCurrentPlayer());
    }

}

