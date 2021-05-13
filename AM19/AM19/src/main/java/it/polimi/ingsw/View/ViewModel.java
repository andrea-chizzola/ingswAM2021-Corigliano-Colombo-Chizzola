package it.polimi.ingsw.View;

import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.Boards.FaithTrack.VaticanReportSection;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.*;

/**
 * this class contains some of the information of the Model inside the client
 */
public class ViewModel {

    /**
     * this attribute represents the name of the Client
     */
    private String personalNickname;

    /**
     * this attribute represents the personal production of the players
     */
    private Production personalProduction;

    /**
     * this attribute represents the list of players in the current game
     */
    private List<String> nicknames;
    /**
     * this attribute represents the file used for configuration
     */
    private String configurationFile;

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
    private Map<String, List<String>> leadersID;

    /**
     * this attribute represents the state of the players' LeaderCards
     */
    private Map<String, List<ItemStatus>> leadersStatus;

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
     * this attribute represents the progressive number of the current player
     */
    private String currentPlayer;

    /**
     * this attribute represents the current turn type of the player
     */
    private String selectedTurn;

    /**
     * this attribute represent the available turns of the current player
     */
    private List<String> availableTurns;

    public ViewModel(String file){
        // GESTIRE CODICE DEL SERVER! I PARSER POTREBBERO FALLIRE! Il client in quel caso deve terminare con errore!
        //trasformare i get delle mappe in get or default

        this.configurationFile = file;

        nRows = ConfigurationParser.parseMarketRows(file);
        nColumns = ConfigurationParser.parseMarketColumns(file);
        slotNumber = ConfigurationParser.getNumSlots(file);
        shelves = ConfigurationParser.getCapacityWarehouse(file);
        trackLength = ConfigurationParser.parseTrackLength(file);
        trackPoints = ConfigurationParser.parseTrack(file);
        personalProduction = ConfigurationParser.parsePersonalProduction(file);

        personalNickname = "";
        currentPlayer = "";
        selectedTurn = "";

        market = new LinkedList<>();
        possibleWhites = new LinkedList<>();
        slots = new HashMap<>();
        warehouse = new HashMap<>();
        strongbox = new HashMap<>();
        faithPoints = new HashMap<>();
        sections = new HashMap<>();
        decks = new HashMap<>();
        selectedMarbles = new LinkedList<>();
        leadersID = new HashMap<>();
        leadersStatus = new HashMap<>();
        actionToken = Optional.empty();
        lorenzoFaith = Optional.empty();
        lorenzoSections = Optional.empty();
        availableTurns = new LinkedList<>();
    }

    public String getPersonalNickname() {
        return personalNickname;
    }

    public void setPersonalNickname(String personalNickname) {
        this.personalNickname = personalNickname;
    }

    public Production getPersonalProduction() {
        return personalProduction;
    }

    public String getConfigurationFile() {
        return configurationFile;
    }

    public Map<Integer, String> getDecks() {
        return new HashMap<>(decks);
    }

    public void setDecks(Map<Integer, String> decks) {
        this.decks = new HashMap<>(decks);
    }

    public List<Marble> getMarket() {
        return market;
    }

    public void setMarket(List<Marble> market) {
        this.market = new LinkedList<>(market);
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

    public int getnRows() {
        return nRows;
    }
    public int getnColumns() {
        return nColumns;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public List<Integer> getTrackPoints() {
        return new LinkedList<>(trackPoints);
    }

    public Map<String, Integer> getFaithPoints() {
        return new HashMap<>(faithPoints);
    }

    public void setFaithPoints(Map<String, Integer> faithPoints) {
        this.faithPoints = new HashMap<>(faithPoints);
    }

    public List<VaticanReportSection> getSections(String player) {
        return new LinkedList<>(sections.get(player));
    }

    public void setSections(List<VaticanReportSection> sections, String player) {
       this.sections.put(player, new LinkedList<>(sections));
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public List<String> getSlots(String player) {
        return new LinkedList<>(slots.get(player));
    }

    public void setSlots(List<String> slots, String player) {
        this.slots.put(player, new LinkedList<>(slots));
    }

    public Map<Integer, String> getLeadersID(String player) {
        Map<Integer, String> leaders = new HashMap<>();
        List<String> selection = leadersID.get(player);
        for(int i=0; i<selection.size(); i++){
            leaders.put(i, selection.get(i));
        }
        return leaders;
    }

    public void setLeadersID(List<String> leadersID, String player) {
        this.leadersID.put(player, new LinkedList<>(leadersID));
    }

    public Map<Integer,ItemStatus> getLeadersStatus(String player) {

        Map<Integer, ItemStatus> leaders = new HashMap<>();
        List<ItemStatus> selection = leadersStatus.get(player);
        for(int i=0; i<selection.size(); i++){
            leaders.put(i, selection.get(i));
        }
        return leaders;
    }

    public void setLeadersStatus(List<ItemStatus> leadersStatus, String player) {
        this.leadersStatus.put(player, new LinkedList<>(leadersStatus));
    }

    public LinkedList<ResQuantity> getWarehouse(String player) {
        return new LinkedList<>(warehouse.get(player));
    }

    public void setWarehouse(List<ResQuantity> warehouse, String player) {
        this.warehouse.put(player, new LinkedList<>(warehouse));
    }

    public List<Integer> getShelves() {
        return shelves;
    }

    public List<ResQuantity> getStrongbox(String player) {
        return new LinkedList<>(this.strongbox.get(player));
    }

    public void setStrongbox(List<ResQuantity> strongbox, String player) {
        this.strongbox.put(player, new LinkedList<>(strongbox));
    }

    public Optional<String> getActionToken() {
        return actionToken;
    }

    public void setActionToken(String actionToken) {
        this.actionToken = Optional.of(actionToken);
    }

    public Optional<Integer> getLorenzoFaith() {
        return lorenzoFaith;
    }

    public void setLorenzoFaith(int lorenzoFaith) {
        this.lorenzoFaith = Optional.of(lorenzoFaith);
    }

    public Optional<List<VaticanReportSection>> getLorenzoSections() {
        if (lorenzoSections.isEmpty()) return Optional.empty();
        else return Optional.of(new LinkedList<>(lorenzoSections.get()));
    }

    public void setLorenzoSections(List<VaticanReportSection> lorenzoSections) {
        this.lorenzoSections = Optional.of(new LinkedList<>(lorenzoSections));
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
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

    public void setAvailableTurns(List<String> availableTurns) {
        this.availableTurns = new LinkedList<>(availableTurns);
    }

    public List<String> getNicknames() {
        return new LinkedList<>(nicknames);
    }

    public void setNicknames(List<String> nicknames) {
        this.nicknames = new LinkedList<>(nicknames);
    }

    public void removeNickname(String nickname) {
        if(nicknames.contains(nickname)) return;
        nicknames.remove(nickname);
    }


    //la logica di controllo del turno, se vuoi metterla, va qui
}
