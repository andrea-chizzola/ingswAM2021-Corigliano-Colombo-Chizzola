package it.polimi.ingsw.Client.ClientController;

import it.polimi.ingsw.Client.MessageSender;
import it.polimi.ingsw.Client.ReducedModel.ReducedBoard;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.Boards.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Client.ServerConnectionListener;
import it.polimi.ingsw.View.PlayerInteractions.PlayerInteraction;
import it.polimi.ingsw.View.View;

import java.util.*;

public class ClientController implements ServerConnectionListener, InteractionObserver {

    /**
     * this attribute represents the reduced model of the Client
     */
    private final ReducedGameBoard model;

    /**
     * this attribute represents the view of the Client
     */
    private final View view;

    /**
     * this attribute represents a sender of messages
     */
    private final MessageSender messageSender;

    /**
     * this attribute is true if the login has been successful
     */
    private boolean loggedIn;

    /**
     * this attribute is true if the client is active
     */
    private boolean isActive;

    /**
     * this attribute contains all the messages used during the game
     */
    private final LinkedList<String> receivedMessages;

    /**
     * this attribute is true if a player interaction is available
     */
    private boolean availableInteraction;

    /**
     * this attribute contains the most recent interaction of the player
     */
    private PlayerInteraction interaction;

    /**
     * this method is the constructor of the class
     * @param model is a reference to the model in the client
     * @param view is a reference to the view used in the client
     */
    public ClientController(ReducedGameBoard model, View view, MessageSender messageSender){
        this.view = view;
        this.model = model;
        this.messageSender = messageSender;
        loggedIn = false;
        isActive = true;
        availableInteraction = false;
        receivedMessages = new LinkedList<>();
    }

    /**
     * this method creates a thread that will run the actions of the client
     */
    public void runController(){
        new Thread(() -> {

            while(isActive) {

                synchronized (this) {
                    while (receivedMessages.size() == 0 && !availableInteraction) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            System.err.println("[CLIENT] Error occurred while suspending thread");
                            e.printStackTrace();
                        }
                    }
                }

                manageInteraction();
                messageHandler();

            }
        }).start();
    }


    /**
     * this method is used to notify the controller of the arrival of a new message from the server
     * @param message is the content of the String message
     */
    @Override
    public synchronized void onReceivedMessage(String message) {
        receivedMessages.add(message);
        this.notifyAll();
    }

    /**
     *this method is used to notify of the arrival of a new pong message
     */
    @Override
    public synchronized void onMissingPong() {
        try {
            String message = MessageFactory.buildReply(
                    false, "ERROR: Missed pong", model.getPersonalNickname());
            receivedMessages.add(message);
            this.notifyAll();

        } catch (MalformedMessageException e) {
            System.out.println("[CLIENT] cannot create the missing pong message");
            close();
        }
    }

    /**
     * this method checks if a message is available. In that case, it manages the message.
     */
    protected void messageHandler(){
        String message;
        MessageUtilities parser = MessageUtilities.instance();

        synchronized(this){
            if(receivedMessages.size()<=0) return;
            message = receivedMessages.removeFirst();
        }

        try {
            Message.MessageType type = parser.getType(message);
            if (type == Message.MessageType.REPLY) {
                replyHandler(new ReplyMessage(message));
            } else if (type == Message.MessageType.GAME_STATUS) {
                gameStatusHandler(new GameStatusMessage(message));
            } else {
                updateHandler(new UpdateMessage(message, type));
            }
        } catch (MalformedMessageException e) {
            System.out.println("[CLIENT] cannot read the received message. Closing Connection...");
            close();
        }
    }

    /**
     * this method checks if a player interaction is available. In that case, it manages the interaction
     */
    private void manageInteraction(){
        PlayerInteraction toHandle;
        synchronized(this){
            if(!availableInteraction) return;
            toHandle = interaction;
            availableInteraction = false;
        }
        toHandle.manageInteraction(view);
    }

    /**
     * this method is used to manage a reply message coming from the server
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the message is not correctly formed
     */
    public void replyHandler(ReplyMessage message) throws MalformedMessageException {

        loginHandler(message);
        view.reply(message.isOk(), message.getBody(), message.getPlayer());
        if(!message.isOk()) actionHandler(model.getModelState());

    }

    /**
     * this method is used to check if the login has been successful
     * @param reply is a message of type reply
     * @throws MalformedMessageException if the message is not well formed
     */
    private void loginHandler(ReplyMessage reply) throws MalformedMessageException {
        if(!loggedIn && !reply.isOk()){
            System.out.println("[Client] Cannot connect you to a game. Closing connection...");
            close();
        }
        loggedIn = true;
    }

    /**
     * this method is used to manage a message of type GameStatusMessage
     * @param message is the message to be managed
     * @throws MalformedMessageException if the message is not well formed
     */
    private void gameStatusHandler(GameStatusMessage message) throws MalformedMessageException {
        String self = model.getPersonalNickname();
        String body = message.getBody();
        model.setCurrentPlayer(message.getPlayer());
        view.showGameStatus(body, self, message.getStatus());

        if (model.getCurrentPlayer().equals(self)){
            model.setModelState(message.getStatus());
            selectionHandler(message.getStatus(), message);
            actionHandler(message.getStatus());
        }
    }

    /**
     * this method is used to call the interaction methods of the view
     * @param type is the type of update contained in the GameStatusMessage
     */
    private void actionHandler(TurnType type){
        String current = model.getCurrentPlayer();
        switch(type){
            case INITIALIZATION_LEADERS: {
                view.selectLeaderAction();
                break;
            }
            case INITIALIZATION_RESOURCE: {
                view.getResourcesAction();
                break;
            }
            case MANAGE_MARBLE: {
                view.showMarblesUpdate(model.getSelectedMarbles(), model.getPossibleWhites(), current);
                break;
            }
            case TURN_SELECTION: {
                view.showAvailableTurns(model.getAvailableTurns(), current);
                break;
            }
            default:
                break;
        }
    }

    /**
     * this method is used to update the state of the view, retrieving information from a GameStatusMessage
     * @param type is the type of update contained in the GameStatusMessage
     * @param message is the received GameStatusMessage
     * @throws MalformedMessageException if the message is not well formed
     */
    private void selectionHandler(TurnType type, GameStatusMessage message) throws MalformedMessageException {
        switch(type){
            case MANAGE_MARBLE: {
                manageMarbleHandler(message);
                break;
            }
            case TURN_SELECTION: {
                turnSelectionHandler(message);
                break;
            }
            default:
                break;
        }
        model.setModelState(type);
    }

    /**
     * this method is used to update the available types of turns
     * @param message is the GameStatusMessage received
     * @throws MalformedMessageException if the message is not well formed
     */
    private void turnSelectionHandler(GameStatusMessage message) throws MalformedMessageException {

        String current = message.getPlayer();
        model.setCurrentPlayer(current);

        List<String> turns = message.getTurnTypes();
        model.setAvailableTurns(new LinkedList<>(turns));
    }

    /**
     * this method is used to update the marble selected during a take resources turn
     * @param message is the GameStatusMessage coming from the server
     * @throws MalformedMessageException if the message is not well formed
     */
    private void manageMarbleHandler(GameStatusMessage message) throws MalformedMessageException {

        List<Marble> selected = message.getSelectedMarbles();
        List<Marble> candidates = message.getCandidateWhite();
        model.setSelectedMarbles(selected);
        model.setPossibleWhites(candidates);
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
        }

    }

    /**
     * this method is used to manage the end of the game
     * @param message is the message coming from the server
     * @throws MalformedMessageException if the server is not well formed
     */
    private void endGameUpdate(UpdateMessage message) throws MalformedMessageException {
        Map<String, Integer> map = message.getEndGamePoints();
        String winner = message.getWinner();
        view.showEndGame(map,winner);
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
        ReducedBoard board = model.getBoard(player);

        board.setWarehouse(warehouse);
        board.setStrongbox(strongbox);
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
        ReducedBoard board = model.getBoard(player);
        board.setSlots(slots);
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
        lorenzoFaith.ifPresent(model::setLorenzoFaith);
        lorenzoSections.ifPresent(model::setLorenzoSections);

        Map<String, List<ItemStatus>> sections = new HashMap<>();
        Map<String, Integer> faith = message.getFaithPoints();

        for(String name : faith.keySet()){
            ReducedBoard board = model.getBoard(name);
            sections.put(name, message.getSections(name));
            board.setSections(message.getSections(name));
            board.setFaithPoints(faith.get(name));
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
        ReducedBoard board = model.getBoard(player);
        board.setLeadersID(cards);
        board.setLeadersStatus(status);
        view.showLeaderCards(cards, status, player);
    }

    /**
     * this method is used to notify a performed interaction
     * @param interaction is the notified interaction
     */
    @Override
    public synchronized void updateInteraction(PlayerInteraction interaction) {
        this.interaction = interaction;
        availableInteraction = true;
        notifyAll();
    }

    /**
     * this method is used to notify a performed interaction
     * @param message is the representation of the interaction
     */
    public void updateInteraction(String message){
        if(!loggedIn){
            messageSender.firstMessage(message);
        }
        else{
            messageSender.sendMessage(message);
        }
    }

    /**
     * this method is used to notify the nickname chosen by the player
     * @param nickname is the nickname chosen by the player
     */
    @Override
    public void updatePersonalNickname(String nickname) {
        model.setPersonalNickname(nickname);
        model.setCurrentPlayer(nickname);
    }

    /**
     * this method is used to notify the SOLO game
     *
     * @param message is the representation of the interaction
     */
    @Override
    public void updateInteractionSolo(String message) {
        messageSender.firstMessageSolo(message);
    }

    /**
     * this method is used to close the client
     */
    @Override
    public void close(){
        messageSender.close();
    }
}

