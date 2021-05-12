package it.polimi.ingsw.View;

import it.polimi.ingsw.Messages.ItemStatus;
import it.polimi.ingsw.Model.ActionTokens.Action;
import it.polimi.ingsw.Model.Boards.FaithTrack.VaticanReportSection;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.xmlParser.ConfigurationParser;
import it.polimi.ingsw.xmlParser.FaithTrackParser;

import java.util.*;

/**
 * this class contains some of the information of the Model inside the client
 */
public class ViewModel {

    /**
     * this attribute represents the current state of the shared decks
     */
    private Map<Integer, String> decks;

    /**
     * this attribute represent the current content of the MarketBoard
     */
    private List<Marble> market;

    /**
     * this attribute represents the marbles that a player has selected from the MarketBoard
     */
    private List<Marble> selectedMarbles;

    /**
     * this attribute represents available transformations of a white marble
     */
    private List<Marble> possibleWhites;

    /**
     * this attribute represents the number of rows of the MarketBoard
     */
    private int nRows;

    /**
     * this attribute represents the number of columns of the MarketBoard
     */
    private int nColumns;

    /**
     * this attribute represents the length of a FaithTrack
     */
    private int trackLength;

    /**
     * this attribute represents the points inside a FaithTrack
     */
    private List<Integer> trackPoints;

    /**
     * this attribute represents the faith obtained by each player
     */
    private Map<String, Integer> faithPoints;

    /**
     * this attribute represent the state of the players' VaticanSections
     */
    private Map<String, List<VaticanReportSection>> sections;

    /**
     * this attribute represents the number of slots of each player
     */
    private int slotNumber;

    /**
     * this attribute represent the top card in each of the player's slots
     */
    private Map<String, List<String>> slots;

    /**
     * this attribute represents the list of leader cards owned by each player
     */
    private Map<String, List<String>> leaders;

    /**
     * this attribute represent the state of each player warehouse
     */
    private Map<String, LinkedList<ResQuantity>> warehouse;

    /**
     * this attribute represent the size of the default shelves
     */

    private List<Integer> shelves;
    /**
     * this attribute represent the state of each player strongbox
     */
    private Map<String, LinkedList<ResQuantity>> strongbox;

    /**
     * this attribute represent the top action token in the case of single player game
     */
    private Optional<String> actionToken;

    /**
     * this attribute represent the faith obtained by Lorenzo in the case of single player game
     */
    private Optional<Integer> lorenzoFaith;

    /**
     * this attribute represents the state of Lorenzo's vatican report sections
     */
    private Optional<List<VaticanReportSection>> lorenzoSections;

    /**
     * this attribute represent the nicknames of the players
     */
    private List<String> nicknames;

    /**
     * this attribute represents the progressive number of the current player
     */
    private int currentPlayer;

    /**
     * this attribute represents the personal production of the players
     */
    private Production personalProduction;

    /**
     * this attribute represents the file used for configuration
     */
    private String configurationFile;

    /**
     * this attribute represents the current turn type of the player
     */
    private String selectedTurn;

    /**
     * this attribute represent the available turns of the current player
     */
    private List<String> availableTurns;

    public ViewModel(String file, List<String> nicknames){
        // GESTIRE CODICE DEL SERVER! I PARSER POTREBBERO FALLIRE! Il client in quel caso deve terminare con errore!
        decks = new HashMap<>();
        nRows = ConfigurationParser.parseMarketRows(file);
        nColumns = ConfigurationParser.parseMarketColumns(file);
        market = new LinkedList<>();
        slotNumber = ConfigurationParser.getNumSlots(file);
        slots = new HashMap<>();
        warehouse = new HashMap<>();
        strongbox = new HashMap<>();
        shelves = ConfigurationParser.getCapacityWarehouse(file);

        this.nicknames = new LinkedList<>(nicknames);
        currentPlayer = 0;

        trackLength = ConfigurationParser.parseTrackLength(file);
        trackPoints = ConfigurationParser.parseTrack(file);
        faithPoints = new HashMap<>();

        sections = new HashMap<>();
        for(String player : nicknames){
            sections.put(player, ConfigurationParser.parseReportSection(file));
        }

        personalProduction = ConfigurationParser.parsePersonalProduction(file);
        this.configurationFile = file;
    }


    public Map<Integer, String> getDecks() {
        return decks;
    }

    public void setDecks(Map<Integer, String> decks) {
        this.decks = decks;
    }

    public List<Marble> getMarket() {
        return market;
    }

    public void setMarket(List<Marble> market) {
        this.market = market;
    }

    public int getnRows() {
        return nRows;
    }

    public void setnRows(int nRows) {
        this.nRows = nRows;
    }

    public int getnColumns() {
        return nColumns;
    }

    public void setnColumns(int nColumns) {
        this.nColumns = nColumns;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(int trackLength) {
        this.trackLength = trackLength;
    }

    public List<Integer> getTrackPoints() {
        return trackPoints;
    }

    public void setTrackPoints(List<Integer> trackPoints) {
        this.trackPoints = trackPoints;
    }

    public Map<String, Integer> getFaithPoints() {
        return faithPoints;
    }

    public void setFaithPoints(Map<String, Integer> faithPoints) {
        this.faithPoints = faithPoints;
    }

    public Map<String, List<VaticanReportSection>> getSections() {
        return sections;
    }

    public void setSections(Map<String, List<VaticanReportSection>> sections) {
        this.sections = sections;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Map<String, List<String>> getSlots() {
        return slots;
    }

    public void setSlots(Map<String, List<String>> slots) {
        this.slots = slots;
    }

    public List<String> getLeaders(int playerNumber) {
        String player = nicknames.get(playerNumber);
        return new LinkedList<>(leaders.get(player));
    }

    public void setLeaders(Map<String, List<String>> leaders) {
        this.leaders = leaders;
    }

    public Map<String, LinkedList<ResQuantity>> getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Map<String, LinkedList<ResQuantity>> warehouse) {
        this.warehouse = warehouse;
    }

    public List<Integer> getShelves() {
        return shelves;
    }

    public void setShelves(List<Integer> shelves) {
        this.shelves = shelves;
    }

    public Map<String, LinkedList<ResQuantity>> getStrongbox() {
        return strongbox;
    }

    public void setStrongbox(Map<String, LinkedList<ResQuantity>> strongbox) {
        this.strongbox = strongbox;
    }

    public Optional<String> getActionToken() {
        return actionToken;
    }

    public void setActionToken(Optional<String> actionToken) {
        this.actionToken = actionToken;
    }

    public Optional<Integer> getLorenzoFaith() {
        return lorenzoFaith;
    }

    public void setLorenzoFaith(Optional<Integer> lorenzoFaith) {
        this.lorenzoFaith = lorenzoFaith;
    }

    public Optional<List<VaticanReportSection>> getLorenzoSections() {
        return lorenzoSections;
    }

    public void setLorenzoSections(Optional<List<VaticanReportSection>> lorenzoSections) {
        this.lorenzoSections = lorenzoSections;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Production getPersonalProduction() {
        return personalProduction;
    }

    public void setPersonalProduction(Production personalProduction) {
        this.personalProduction = personalProduction;
    }

    public String getConfigurationFile() {
        return configurationFile;
    }

    public void setConfigurationFile(String configurationFile) {
        this.configurationFile = configurationFile;
    }

    public List<Marble> getSelectedMarbles() {
        return new LinkedList<>(selectedMarbles);
    }

    public void setSelectedMarbles(List<Marble> selectedMarbles) {
        this.selectedMarbles = new LinkedList<>(selectedMarbles);
    }

    public List<Marble> getPossibleWhites() {
        return new LinkedList<>(possibleWhites);
    }

    public void setPossibleWhites(List<Marble> possibleWhites) {
        this.possibleWhites = new LinkedList<>(possibleWhites);
    }


    public String getSelectedTurn() {
        return selectedTurn;
    }

    public void setSelectedTurn(String selectedTurn) {
        this.selectedTurn = selectedTurn;
    }

    public List<String> getAvailableTurns() {
        return new LinkedList<>(availableTurns);
    }

    /*public void initializeTurns(){
        //da prendere da configuration file
    }

    public void */
}
