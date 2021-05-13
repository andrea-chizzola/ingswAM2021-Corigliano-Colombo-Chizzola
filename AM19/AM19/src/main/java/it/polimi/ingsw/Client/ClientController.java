package it.polimi.ingsw.Client;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.ConnectionListener;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.View.ViewModel;

public class ClientController implements ConnectionListener {

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

    //all'instaurarsi della connessione, il Client notifica il ClientController. Così parte il login
    public ClientController(ViewModel model, View view){
        this.view = view;
        this.model = model;
        this.state = new Login();
    }

    public void setState(ClientStates state){
        this.state = state;
    }

    @Override
    public synchronized void onReceivedMessage(String message, String nickname) {
       try{
           messageHandler(message, nickname);
       }
       catch(MalformedMessageException e){
           e.printStackTrace();
       }
    }

    private void messageHandler(String message, String nickname) throws MalformedMessageException {

        /*if(model.getNicknames().stream().noneMatch(messageType -> messageType.equals(nickname)))
            throw new MalformedMessageException("Cannot find the player ID");
        Message.MessageType type = parser.getType(message);
        if(type == Message.MessageType.REPLY) stateHandler(message, nickname, type);
        else if(type == Message.MessageType.GAME_STATUS) turnHandler(nickname);
        else updateHandler(message, nickname, type);*/
    }

    private void turnHandler(String message){
        /*int current = message.getPlayerNumber();

        //the first if manage both the Initialization and the Turn Selection
        if(current == model.getCurrentPlayer() && (state.isTurnSelection() || state.isInitialization())){
            state.handleState(view, model);
        }
        else model.setCurrentPlayer(current);*/
    }

    //gestione update dello ViewModel. Chiama i setter del model usando i setter. Qui gestisco anche il messaggio
    //di disconnessione proveniente dal server. Tra i turni della CLI, devo dare anche la possibilità di sconnettersi
    private void updateHandler(String message, String id, Message.MessageType type) throws MalformedMessageException{
        /*switch(type){
            case UPDATE_LEADER_CARDS:
            case UPDATE_MARKET:
            case UPDATE_LORENZO:
            case UPDATE_FAITH:
            case UPDATE_DECKS:
            case UPDATE_CARDSLOTS:
            case UPDATE_BOXES:
        }*/

    }

    private void stateHandler(String message, String nickname, Message.MessageType type) throws MalformedMessageException {
        int current = model.getCurrentPlayer();
        if(!model.getNicknames().get(current).equals(nickname)){
            return;
        }
        //if the received message is a replyOk, the controller can do its actions and proceed to the next state.
        /*view.showAnswer(message.isOk(), message.getBody());
        if(message.isOk()) state = state.nextState();
        state.handleState(view, model);*/
    }
}
