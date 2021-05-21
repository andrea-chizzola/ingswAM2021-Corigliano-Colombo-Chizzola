package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.View;

import java.util.*;

public class ClientController implements ClientConnectionListener {

    /**
     * this attribute represents the reduced model of the Client
     */
    private ReducedGameBoard model;

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

    /**
     * this attribute is true if the client is active
     */
    private boolean isActive;

    /**
     * this attribute contains all the messages used during the game
     */
    private LinkedList<String> receivedMessages;

    /**
     * this method is the constructor of the class
     * @param model is a reference to the model in the client
     * @param view is a reference to the view used in the client
     */
    public ClientController(ReducedGameBoard model, View view){
        this.view = view;
        this.model = model;
        notStarted = false;
        alreadyUpdated = false;
        loggedIn = false;
        availableSwap = true;
        isActive = true;
        receivedMessages = new LinkedList<>();
    }

    /**
     * this method creates a thread that will run the actions of the client
     */
    public void runController(){
        new Thread(() -> {
            firstInteraction();
            while(isActive) {
                synchronized (this) {
                    while (receivedMessages.size() == 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            System.err.println("[CLIENT] Error occurred while suspending thread");
                            e.printStackTrace();
                        }
                    }

                    try {
                        checkStart();
                        messageHandler();

                        //CODE FOR TESTS
                        //this.notifyAll();
                        //-----------------
                    } catch (MalformedMessageException e) {
                        System.out.println("[CLIENT] Malformed message received. No action performed.");
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * this method is used to perform the first interaction of the client, i.e. the login
     */
    private synchronized void firstInteraction(){
        if(!notStarted){
            view.newPlayer();
            notStarted = false;
        }
    }

    /**
     * this method is used to check if the client has been correctly started
     */
    private void checkStart(){
        if(notStarted){
            //termino il client. Il login non è mai avvenuto
            System.out.println("[Client] the controller has not been started. Closing connection...");
        }
    }

    /**
     * this method is used to check if the login has been successful
     * @param reply is a message of type reply
     * @throws MalformedMessageException if the message is not well formed
     */
    private void loginHandler(ReplyMessage reply) throws MalformedMessageException {
        if(!loggedIn && !reply.isOk()){
            //termino il client. Il login non è mai avvenuto
            System.out.println("[Client] Cannot connect you to a game. Closing connection...");
        }
        loggedIn = true;
    }

    /**
     * this method is used to notify the controller of the arrival of a new message from the server
     * @param message is the content of the String message
     */
    @Override
    public synchronized void onReceivedMessage(String message) {
        receivedMessages.add(message);
        this.notifyAll();

        //CODE FOR TESTS
        /*try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //----------------

    }

    /**
     *this method is used to notify of the arrival of a new pong message
     */
    @Override
    public synchronized void onMissingPong() {
        try {
            String message = MessageFactory.buildGameStatus(false,
                    "ERROR: Missed pong", model.getPersonalNickname(), TurnType.WRONG_STATE);
            receivedMessages.add(message);

            this.notifyAll();
        } catch (MalformedMessageException e) {
            System.out.println("[CLIENT] cannot create the missing pong message");
            e.printStackTrace();
        }
    }

    /**
     * this method is used to manage a message coming from the server
     * @throws MalformedMessageException if the message is not well formed
     */
    protected void messageHandler() throws MalformedMessageException {
        MessageUtilities parser = MessageUtilities.instance();
        Message.MessageType type = parser.getType(receivedMessages.getFirst());
        if (type == Message.MessageType.GAME_STATUS)
            statusHandler(new GameStatusMessage(receivedMessages.getFirst()));
        else if (type == Message.MessageType.REPLY) {
            loginHandler(new ReplyMessage(receivedMessages.getFirst()));
        } else {
            updateHandler(new UpdateMessage(receivedMessages.getFirst(), type));
        }
        receivedMessages.remove(0);
    }

    /**
     * this method is used to manage a message of type GameStatusMessage
     * @param message is the message to be managed
     * @throws MalformedMessageException if the message is not well formed
     */
    private void statusHandler(GameStatusMessage message) throws MalformedMessageException {
        String self = model.getPersonalNickname();
        TurnType status = message.getStatus();

        if(status == TurnType.INITIALIZATION_LEADERS || status == TurnType.TURN_SELECTION){
            model.setCurrentPlayer(message.getPlayer());
        }

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

    /**
     * this method is used to update the state of the view, retrieving information from a GameStatusMessage
     * @param type is the type of update contained in the GameStatusMessage
     * @param message is the received GameStatusMessage
     * @throws MalformedMessageException if the message is not well formed
     */
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
            default:
                break;
                //END CONNECTION BECAUSE OF WRONG MESSAGE FROM SERVER
        }
        alreadyUpdated = false;
        model.setModelState(type);
    }

    /**
     * this method is used to manage a turn selection
     * @param message is the GameStatusMessage received
     * @throws MalformedMessageException if the message is not well formed
     */
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

    /**
     * this method is used to manage a the selection of the target slots during a marble management
     * @param message is the GameStatusMessage coming from the server
     * @throws MalformedMessageException if the message is not well formed
     */
    private void manageMarbleHandler(GameStatusMessage message) throws MalformedMessageException {

        if(!alreadyUpdated) {
            List<Marble> selected = message.getSelectedMarbles();
            List<Marble> candidates = message.getCandidateWhite();
            model.setSelectedMarbles(selected);
            model.setPossibleWhites(candidates);
        }
        view.showMarblesUpdate(model.getSelectedMarbles(), model.getPossibleWhites(), model.getCurrentPlayer());
    }

    /**
     * this method is used to manage a swap action;
     */
    private void swapHandler(){
        if(availableSwap)
            availableSwap = view.swapAction();
        if(!availableSwap) {
            view.selectMarketAction();
            availableSwap = true;
        }
    }

    /**
     * this method is used to manage graphical updates coming from the server
     * @param message is the UpdateMessage coming from the server
     * @throws MalformedMessageException if the message is not well formed
     */
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
                isActive = false;
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
            case START_GAME: {
                startGameHandler(message);
                break;
            }
            default:
                //END CONNECTION. WRONG SEQUENCE OF MESSAGES OF UNKNOWN MESSAGE
                break;
        }
    }

    /**
     * this method is used to retrieve the names of the players from the first update message
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the server is not well formed
     */
    private void startGameHandler(UpdateMessage message) throws MalformedMessageException {
        List<String> nicknames = message.getNicknames();
        model.initializeNicknames(nicknames);
        view.initialize();
    }

    /**
     * this method is used to manage the disconnection of a player
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the server is not well formed
     */
    private void disconnectionUpdate(UpdateMessage message) throws MalformedMessageException {
        String disconnected = message.getPlayer();
        if(model.getNicknames().contains(disconnected)) {
            view.showDisconnection(disconnected);
            model.removeNickname(disconnected);
        }

    }

    /**
     * this method is used to manage the end of the game
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the server is not well formed
     */
    private void endGameUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<String, Integer> map = message.getEndGamePoints();
        view.showEndGame(map);

        //Here the connection should be closed (client side)
    }

    /**
     * this method is used to manage a box update
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the server is not well formed
     */
    private void boxUpdate(UpdateMessage message) throws MalformedMessageException {
        String player = message.getPlayer();
        List<ResQuantity> warehouse = message.getWarehouseUpdate();
        List<ResQuantity> strongbox = message.getStrongboxUpdate();

        model.getBoard(player).setWarehouse(warehouse);
        model.getBoard(player).setStrongbox(strongbox);
        view.showBoxes(warehouse, strongbox, player);
    }

    /**
     * this method is used to manage the update of one's personal slots
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the server is not well formed
     */
    private void slotsUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<Integer, String> slots = message.getSlotsUpdate();
        String player = message.getPlayer();
        model.getBoard(player).setSlots(slots);
        view.showSlotsUpdate(slots, player);
    }

    /**
     * this method is used to manage the update of the decks
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the message is not well formed
     */
    private void decksUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<Integer, String> decks = message.getDecksUpdate();
        model.setDecks(decks);
        view.showDecksUpdate(decks);
    }

    /**
     * this method is used to manage the update of the fatih tracks
     * @param message is the message coming from the server
     * @throws MalformedMessageException is the message is not well formed
     */
    private void faithUpdate(UpdateMessage message) throws MalformedMessageException {

        Optional<List<ItemStatus>> lorenzoSections = message.getLorenzoSections();
        Optional<Integer> lorenzoFaith = message.getLorenzoFaith();
        lorenzoFaith.ifPresent(integer -> model.setLorenzoFaith(integer));
        lorenzoSections.ifPresent(itemStatuses -> model.setLorenzoSections(itemStatuses));

        Map<String, List<ItemStatus>> sections = new HashMap<>();
        Map<String, Integer> faith = message.getFaithPoints();

        for(String name : faith.keySet()){
            sections.put(name, message.getSections(name));
            model.getBoard(name).setSections(message.getSections(name));
            model.getBoard(name).setFaithPoints(faith.get(name));
        }
        view.showFaithUpdate(faith, sections, lorenzoFaith, lorenzoSections);
    }

    /**
     * this method is used to manage the update of the top token
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the message is not well formed
     */
    private void tokenUpdate(UpdateMessage message) throws MalformedMessageException {
        String token = message.getTopToken();
        if(token.length() != 0) {
            model.setActionToken(token);
            view.showTopToken(Optional.of(token));
        }
    }

    /**
     * this method is used to manage the update of the market
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the message is not well formed
     */
    private void marketUpdate(UpdateMessage message) throws MalformedMessageException {
        List<Marble> tray = message.getMarketUpdate();
        model.setMarket(tray);
        view.showMarketUpdate(tray);
    }

    /**
     * this method is used to manage the update of the leader cards of a player
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the message is not well formed
     */
    private void leadersUpdate(UpdateMessage message) throws MalformedMessageException{
        Map<Integer, ItemStatus> status = message.getLeaderCardsStatus();
        Map<Integer, String> cards = message.getLeaderCardsUpdate();
        String player = message.getPlayer();
        model.getBoard(player).setLeadersID(cards);
        model.getBoard(player).setLeadersStatus(status);
        view.showLeaderCards(cards, status, player);
    }
}

