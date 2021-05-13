package it.polimi.ingsw.Client;

import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.View.ViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * this interface implements the methods of a state of the Client
 */
public interface ClientStates {

    /**
     * @return true if the state is TurnSelection, else otherwise
     */
    boolean isTurnSelection();

    /**
     * @return true if the state is the beginning of an initialization
     */
    boolean isInitialization();

    /**
     * this method execute the actions of a specific state
     */
    void handleState(View view, ViewModel model);

    /**
     * @return the next state in the sequence
     */
    ClientStates nextState();
}

class Login implements ClientStates{

    @Override
    public boolean isTurnSelection() {
        return false;
    }

    @Override
    public boolean isInitialization() {
        return false;
    }

    @Override
    public void handleState(View view, ViewModel model) {
        view.newPlayer();
    }

    @Override
    public ClientStates nextState() {
        return new Initialization_Leader();
    }
}

class Initialization_Leader implements ClientStates{

    @Override
    public boolean isTurnSelection() {
        return false;
    }

    @Override
    public boolean isInitialization() {
        return true;
    }

    @Override
    public void handleState(View view, ViewModel model) {
        view.selectLeaderAction(model.getLeaders(model.getCurrentPlayer()));
    }

    @Override
    public ClientStates nextState() {
        return new Initialization_Resource();
    }
}

class Initialization_Resource implements ClientStates{


    @Override
    public boolean isTurnSelection() {
        return false;
    }

    @Override
    public boolean isInitialization() {
        return false;
    }

    @Override
    public void handleState(View view, ViewModel model) {
        //DA AGGIUNGERE IL NUMERO DI RISORSE NEI PARSER.
        //view.getResourcesAction();
        //implementa dopo che è stato messo i numero di risorse del turno
    }

    @Override
    public ClientStates nextState() {
        return new Turn_Selection();
    }
}

class Take_Resource implements ClientStates{

    @Override
    public boolean isTurnSelection() {
        return false;
    }

    @Override
    public boolean isInitialization() {
        return false;
    }

    @Override
    public void handleState(View view, ViewModel model) {
        view.selectMarketAction();
    }

    @Override
    public ClientStates nextState() {
        return new Manage_Marble();
    }
}

class Manage_Marble implements ClientStates{

    @Override
    public boolean isTurnSelection() {
        return false;
    }

    @Override
    public boolean isInitialization() {
        return false;
    }

    @Override
    public void handleState(View view, ViewModel model) {
        //bisogna salvare le selected marbles nel ViewModel, con anche le trasformazioni possibili
        view.marbleAction(model.getSelectedMarbles(), model.getPossibleWhites());
    }

    @Override
    public ClientStates nextState() {
        return new Turn_Selection();
    }

}

class Manage_Leader implements ClientStates{

    @Override
    public boolean isTurnSelection() {
        return false;
    }

    @Override
    public boolean isInitialization() {
        return false;
    }

    @Override
    public void handleState(View view, ViewModel model) {
        view.leaderAction();
    }

    @Override
    public ClientStates nextState() {
        return new Turn_Selection();
    }

}

class Buy_Card implements ClientStates{

    @Override
    public boolean isTurnSelection() {
        return false;
    }

    @Override
    public boolean isInitialization() {
        return false;
    }

    @Override
    public void handleState(View view, ViewModel model) {
        view.buyCardAction(model.getDecks());
    }

    @Override
    public ClientStates nextState() {
        return new Turn_Selection();
    }


}

class Do_Production implements ClientStates{

    @Override
    public boolean isTurnSelection() {
        return false;
    }

    @Override
    public boolean isInitialization() {
        return false;
    }

    @Override
    public void handleState(View view, ViewModel model) {
        view.doProductionsAction();
    }

    @Override
    public ClientStates nextState() {
        return new Turn_Selection();
    }

}

//bisogna salvare il turno selezionato dal player nel client model o nel client controller, e poi prelevarlo se ho OK.
//E' più pulito nel model

class Turn_Selection implements ClientStates{

    private String nextState;

    private class StateMapper{
        private Map<TurnType, Supplier<ClientStates>> map;
        private StateMapper(){
            map = new HashMap<>();
            map.put(TurnType.MANAGE_LEADER, Manage_Leader::new);
            map.put(TurnType.BUY_CARD, Buy_Card::new);
            map.put(TurnType.TAKE_RESOURCES, Take_Resource::new);
            map.put(TurnType.DO_PRODUCTION, Do_Production::new);
        }
        private ClientStates getState(String state){
            return map.get((TurnType.valueOf(state))).get();
        }
    }

    @Override
    public boolean isTurnSelection() {
        return false;
    }

    @Override
    public boolean isInitialization() {
        return false;
    }

    @Override
    public void handleState(View view, ViewModel model) {
        int player = model.getCurrentPlayer();
        nextState = view.selectTurnAction(model.getAvailableTurns(), model.getNicknames().get(player));
    }

    //qui dovresti prendere lo stato dalla ViewModel
    @Override
    public ClientStates nextState() {
        StateMapper map = new StateMapper();
        return map.getState(nextState);
    }

}

class Swap implements ClientStates{

    @Override
    public boolean isTurnSelection() {
        return true;
    }

    @Override
    public boolean isInitialization() {
        return false;
    }

    @Override
    public void handleState(View view, ViewModel model) {
        view.swapAction();
    }

    @Override
    public ClientStates nextState() {
        return new Take_Resource();
    }

}

