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

    private boolean started;

    public Login(){
        started = false;
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
        view.newPlayer();
    }

    @Override
    public ClientStates nextState() {
        if(!started) return this;
        else return new Initialization_Leader();
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
        view.selectLeaderAction(model.getLeadersID(model.getCurrentPlayer()));
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
        /*String currentPlayer = model.getCurrentPlayer();
        int playerNumber, initializationResources;
        playerNumber = model.getNicknames().indexOf(currentPlayer);
        initializationResources = ConfigurationParser.getCapacityWarehouse(model.getConfigurationFile());

        view.getResourcesAction();*/
        //NEEDED METHOD IMPLEMENTED BY MARCO.
        //NOTE: in case of exceptions (the player number is not right or the player name is missing, the Client terminates
        //and notify "Missing Update".
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
        view.showMarblesUpdate(model.getSelectedMarbles(), model.getPossibleWhites(), model.getCurrentPlayer());
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
        view.buyCardAction();
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

        private boolean isTurn(String action){
            return map.containsKey(action);
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
        nextState = view.selectTurnAction(model.getAvailableTurns(), model.getCurrentPlayer());
    }

    //qui dovresti prendere lo stato dalla ViewModel
    @Override
    public ClientStates nextState() {
        StateMapper map = new StateMapper();
        if(!map.isTurn(nextState)){
            //EXIT FROM CLIENT. WRONG MESSAGE UPDATE.
        }
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

