package it.polimi.ingsw.Client.ReducedModel;

import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;

import java.util.*;

public class ReducedGameBoard {

    /**
     * this attribute represents the name of the current player
     */
    private String currentPlayer;

    /**
     * this attribute represents the name of the player that owns the client
     */
    private String personalNickname;

    /**
     * this attribute represent the list of the players that are currentely playing
     */
    private List<String> nicknames;

    /**
     * this attribute represents the id and position of the top card in each shared deck
     */
    private Map<Integer, String> decks;

    /**
     * this attribute represents the state of the market board
     */
    private List<Marble> market;

    /**
     * this attribute represents the marble selected by a player from the market board
     */
    private List<Marble> selectedMarbles;

    /**
     * this attribute represents the possible transformations of the white marbles for a player
     */
    private List<Marble> possibleWhites;

    /**
     * this attribute represents the top action token on the board
     */
    private Optional<String> actionToken;

    /**
     * this attribute represents the faith obtained by Lorenzo
     */
    private Optional<Integer> lorenzoFaith;

    /**
     * this attribute represent the state of Lorenzo's sections
     */
    private Optional<List<ItemStatus>> lorenzoSections;

    /**
     * this attribute represents the type of turn selected by the player
     */
    private String selectedTurn;

    /**
     * this attribute represents the available turns for a player
     */
    private List<String> availableTurns;

    /**
     * this attribute represents the current state of the model (which is on the server)
     */
    private TurnType modelState;

    /**
     * this attribute represents a reduced version of the players boards.
     * The key of the map are the nicknames of the players, the value are the status of the boards.
     */
    private Map<String, ReducedBoard> boards;

    /**
     * this attribute represents an object that contains the configuration of the game
     */
    private ReducedConfiguration reducedConfiguration;


    public ReducedGameBoard(String file){

        personalNickname = "";
        currentPlayer = "";
        selectedTurn = "";
        modelState = TurnType.INITIALIZATION_LEADERS;

        market = new LinkedList<>();
        possibleWhites = new LinkedList<>();
        decks = new HashMap<>();
        selectedMarbles = new LinkedList<>();
        actionToken = Optional.empty();
        lorenzoFaith = Optional.empty();
        lorenzoSections = Optional.empty();
        availableTurns = new LinkedList<>();
        nicknames = new LinkedList<>();
        reducedConfiguration = new ReducedConfiguration(file);
        boards = new HashMap<>();

    }

    /**
     *
     * @return the name of the current player
     */
    public synchronized String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * this method is used to set the name of the current player
     * @param currentPlayer is the name of the current player
     */
    public synchronized void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * this method is used to get the nickname of the player who owns the client
     * @return the name of the player who owns the client
     */
    public synchronized String getPersonalNickname() {
        return personalNickname;
    }

    /**
     * this method is used to set the name of the player who set the client
     * @param personalNickname is the name of the player who owns the client
     */
    public synchronized void setPersonalNickname(String personalNickname) {
        this.personalNickname = personalNickname;
    }

    /**
     * this method is used to get the name of the players playing
     * @return the name of the active players
     */
    public synchronized List<String> getNicknames() {
        return new LinkedList<>(nicknames);
    }

    /**
     * this method is used to set the nicknames of the active players
     * @param nicknames is a list that contains the names of the currently active players
     */
    public synchronized void initializeNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
        for(String name : nicknames){
            boards.put(name, new ReducedBoard());
        }
    }

    /**
     * this method is used to get the IDs of the top cards in the shared decks
     * @return a map that contains the positions and the IDs of the top cards in the shared decks
     */
    public synchronized Map<Integer, String> getDecks() {
        return new HashMap<>(decks);
    }

    /**
     * this method is used to set the IDs of the top cards in each shared deck
     * @param decks is a map that contains the positions and the IDs of each top card in the shared decks
     */
    public synchronized void setDecks(Map<Integer, String> decks) {
        this.decks = new HashMap<>(decks);
    }

    /**
     *
     * @return a list that contains the current state of the market board
     */
    public synchronized List<Marble> getMarket() {
        return new LinkedList<>(market);
    }

    /**
     * this method is used to set the current state of the market board
     * @param market is a list that contains the current state of the marketboard
     */
    public synchronized void setMarket(List<Marble> market) {
        this.market = new LinkedList<>(market);
    }

    /**
     *
     * @return the marbles selected by a player during a marble selection sequence
     */
    public synchronized List<Marble> getSelectedMarbles() {
        return new LinkedList<>(selectedMarbles);
    }

    /**
     * this method is used to set the marble selected by a player during a marble selection sequence
     * @param selectedMarbles is a list that contains the marbles selected
     */
    public synchronized void setSelectedMarbles(List<Marble> selectedMarbles) {
        this.selectedMarbles = new LinkedList<>(selectedMarbles);
    }

    /**
     * this method is used to get the possible transformations of a white marble
     * @return a list that contains the possible transformations of a white marble
     */
    public synchronized List<Marble> getPossibleWhites() {
        return new LinkedList<>(possibleWhites);
    }

    /**
     * this attribute is used to set the possible transformations of a white marble
     * @param possibleWhites is a list that contains the possible transformations of a white marble
     */
    public synchronized void setPossibleWhites(List<Marble> possibleWhites) {
        this.possibleWhites = possibleWhites;
    }

    /**
     *
     * @return an optional that contains the current top action token
     */
    public synchronized Optional<String> getActionToken() {
        return actionToken;
    }

    /**
     * this method is used to set the current top action token
     * @param actionToken is the ID of the current action token
     */
    public synchronized void setActionToken(String actionToken) {
        this.actionToken = Optional.of(actionToken);
    }

    /**
     *
     * @return an optional that contains the faith obtained by Lorenzo
     */
    public synchronized Optional<Integer> getLorenzoFaith() {
        return lorenzoFaith;
    }

    /**
     * this method is used to set the value of the faith obtained by Lorenzo
     * @param lorenzoFaith is the amount of faith points obtained by Lorenzo
     */
    public synchronized void setLorenzoFaith(int lorenzoFaith) {
        this.lorenzoFaith = Optional.of(lorenzoFaith);
    }

    /**
     * this method is used to set the state of Lorenzo's sections
     * @param lorenzoSections is a list that represents the status of Lorenzo's sections
     */
    public synchronized void setLorenzoSections(List<ItemStatus> lorenzoSections) {
        this.lorenzoSections = Optional.of(new LinkedList<>(lorenzoSections));
    }

    /**
     * this method is used to get the status of Lorenzo's vatican report sections
     * @return an optional that contains the state of Lorenzo's vatican report sections
     */
    public synchronized Optional<List<ItemStatus>> getLorenzoSections() {
        if (lorenzoSections.isEmpty()) return Optional.empty();
        else return Optional.of(new LinkedList<>(lorenzoSections.get()));
    }

    /**
     *
     * @return the kind of turn selected by the player
     */
    public synchronized String getSelectedTurn() {
        return selectedTurn;
    }

    /**
     * this method is used to set the type of turn selected by the player
     * @param selectedTurn is the selected turn type
     */
    public synchronized void setSelectedTurn(String selectedTurn) {
        this.selectedTurn = selectedTurn;
    }

    /**
     *
     * @return the list of currently available turns
     */
    public synchronized List<String> getAvailableTurns() {
        return new LinkedList<>(availableTurns);
    }

    /**
     * this method is used to set the types of currently available turns
     * @param availableTurns represents the list of currently available turns
     */
    public synchronized void setAvailableTurns(List<String> availableTurns) {
        this.availableTurns = availableTurns;
    }

    /**
     *
     * @return the state of the model
     */
    public synchronized TurnType getModelState() {
        return modelState;
    }

    /**
     * this method is used to set the current state of the model
     * @param modelState represents the current state of the model
     */
    public synchronized void setModelState(TurnType modelState) {
        this.modelState = modelState;
    }

    /**
     * this method is used to get the ReducedBoard of a player
     * @return the requested board
     */
    public synchronized ReducedBoard getBoard(String player) throws IllegalArgumentException{
        if(!boards.containsKey(player)) throw new IllegalArgumentException("Non existent player");
        return boards.get(player);
    }

    /**
     * this method is used to remove a nickname from the list of active players
     * @param nickname is the nickname of the player to be removed
     */
    public synchronized void removeNickname(String nickname) {
        if(nicknames.contains(nickname)) return;
        nicknames.remove(nickname);
    }

    /**
     *
     * @return the configuration information of the game
     */
    public ReducedConfiguration getConfiguration() {
        return reducedConfiguration;
    }

}
