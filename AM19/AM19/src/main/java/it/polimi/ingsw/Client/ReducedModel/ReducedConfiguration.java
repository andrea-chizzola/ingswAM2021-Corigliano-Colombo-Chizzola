package it.polimi.ingsw.Client.ReducedModel;

import it.polimi.ingsw.Exceptions.IllegalIDException;
import it.polimi.ingsw.Model.ActionTokens.Action;
import it.polimi.ingsw.Model.Boards.FaithTrack.VaticanReportSection;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.xmlParser.ConfigurationParser;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * this class contains the most important information about the initialization of the game.
 * This is an immutable class.
 */
public class ReducedConfiguration {
    /**
     * this attribute represents the name of the configuration file
     */

    private final String configurationFile;
    /**
     * this attribute represents the number of rows in the market board
     */
    private final int nRows;

    /**
     * this attribute represents the number of columns in the market board
     */
    private final int nColumns;

    /**
     * this attribute represents the length of the faith track
     */
    private final int trackLength;

    /**
     * this attribute represents the value of each position of the faith track
     */
    private final List<Integer> trackPoints;

    /**
     * this attribute represents the number of available slots in each board
     */
    private final int slotNumber;

    /**
     * this attribute represents the number of shelves of each warehouse
     */
    private final List<Integer> shelves;

    /**
     * this attribute represents the list of existing leader cards
     */
    private final List<LeaderCard> leaderCardList;

    /**
     * this attribute represents the list of existing leader cards
     */
    private final List<DevelopmentCard> developmentCardList;

    /**
     * thia ttribute represents the list of existing development cards
     */
    private final List<Action> actionTokenList;

    /**
     * this attribute represents the structure of a personal production
     */
    private final Production personalProduction;

    /**
     * this attribute represents the structure of a player's vatican report sections
     */
    private final List<VaticanReportSection> sections;

    /**
     * this attribute represents the quantity of initial resources of each player
     */
    private final List<Integer> initialResources;

    /**
     * this attribute represents the number of leaders given to each player at the beginning of the game
     */
    private final int numLeader;

    public ReducedConfiguration(String file){
        //TODO GESTIRE CODICE DEL SERVER! I PARSER POTREBBERO FALLIRE! Il client in quel caso deve terminare con errore!
        this.configurationFile = file;
        nRows = ConfigurationParser.parseMarketRows(file);
        nColumns = ConfigurationParser.parseMarketColumns(file);
        slotNumber = ConfigurationParser.getNumSlots(file);
        shelves = ConfigurationParser.getCapacityWarehouse(file);
        trackLength = ConfigurationParser.parseTrackLength(file);
        trackPoints = ConfigurationParser.parseTrack(file);
        personalProduction = ConfigurationParser.parsePersonalProduction(file);
        leaderCardList = ConfigurationParser.parseLeaderCard(file);
        developmentCardList = ConfigurationParser.parseDevelopmentCard(file);
        actionTokenList = ConfigurationParser.parseActionTokens(file);
        sections = ConfigurationParser.parseReportSection(file);
        initialResources = ConfigurationParser.getInitializationResources(file);
        numLeader = ConfigurationParser.getNumLeader(file);

    }

    /**
     *
     * @return the name of the configuration file used in the game
     */
    public String getConfigurationFile() {
        return configurationFile;
    }

    /**
     *
     * @return the number of rows of the market board
     */
    public int getnRows() {
        return nRows;
    }

    /**
     *
     * @return the number of columns of the marketboard
     */
    public int getnColumns() {
        return nColumns;
    }

    /**
     *
     * @return the length of the faith track
     */
    public int getTrackLength() {
        return trackLength;
    }

    /**
     *
     * @return the points of each position of the faithtrack
     */
    public List<Integer> getTrackPoints() {
        return new LinkedList<>(trackPoints);
    }

    /**
     *
     * @return the number of available slots for each player
     */
    public int getSlotNumber() {
        return slotNumber;
    }

    /**
     *
     * @return a list containing the capacity of each shelves in the warehouse
     */
    public List<Integer> getShelves() {
        return new LinkedList<>(shelves);
    }

    /**
     *
     * @return a list containing all the existent leader cards
     */
    public List<LeaderCard> getLeaderCardList() {
        return new LinkedList<>(leaderCardList);
    }

    /**
     * this method is used to retrieve a leader card by its ID
     * @param id is the id of the leader card
     * @return the requested leader card.
     * @throws IllegalArgumentException if ID does not correspond to any leader cards
     */
    public LeaderCard getLeaderCard(String id) throws IllegalIDException{
        List<LeaderCard> card = leaderCardList.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .collect(Collectors.toList());
        if(card.size() == 0) throw new IllegalIDException("Non existent element");
        return card.get(0);
    }

    /**
     *
     * @return a list containing all the existent development cards
     */
    public List<DevelopmentCard> getDevelopmentCardList() {
        return new LinkedList<>(developmentCardList);
    }

    /**
     * this method is used to retrieve a leader card by its ID
     * @param id is the id of the development card
     * @return the requested development card.
     * @throws IllegalArgumentException if ID does not correspond to any development cards
     */
    public DevelopmentCard getDevelopmentCard(String id) throws IllegalIDException{
        List<DevelopmentCard> card = developmentCardList.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .collect(Collectors.toList());
        if(card.size() == 0) throw new IllegalIDException("Non existent element");
        return card.get(0);
    }

    /**
     *
     * @return a list containing all the existent action tokens
     */
    public List<Action> getActionTokenList() {
        return new LinkedList<>(actionTokenList);
    }

    /**
     * this method is used to retrieve an action token by its ID
     * @param id is the id of the action token
     * @return the requested ActionToken.
     * @throws IllegalArgumentException if ID does not correspond to any action tokens
     */
    public Action getActionTokenCard(String id) throws IllegalIDException{
        List<Action> tokens = actionTokenList.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .collect(Collectors.toList());
        if(tokens.size() == 0) throw new IllegalIDException("Non existent element");
        return tokens.get(0);
    }

    /**
     *
     * @return the structure of the personal production
     */
    public Production getPersonalProduction() {
        return personalProduction;
    }

    /**
     *
     * @return the points associated to each vatican section, following the order of the sections
     */
    public List<Integer> getSectionsPoints(){
        List<Integer> result = new LinkedList<>();
        for(int i=0; i<sections.size(); i++){
            result.add(sections.get(i).getPopeFavourValue());
        }
        return result;
    }

    /**
     *
     * @return the starting point associated to each vatican section, following the order of the sections
     */
    public List<Integer> getSectionsStart(){
        List<Integer> result = new LinkedList<>();
        for(int i=0; i<sections.size(); i++){
            result.add(sections.get(i).getStart());
        }
        return result;
    }

    /**
     *
     * @return the ending poin associated to each vatican section, following the order of the sections
     */
    public List<Integer> getSectionsEnd(){
        List<Integer> result = new LinkedList<>();
        for(int i=0; i<sections.size(); i++){
            result.add(sections.get(i).getEnd());
        }
        return result;
    }


    /**
     *
     * @return a list that contains the quantity of initial resources of each player
     */
    public List<Integer> getInitialResources() {
        return new LinkedList<>(initialResources);
    }

    /**
     *
     * @return the number of leader given to each player at the beginning of the game
     */
    public int getNumLeader() {
        return numLeader;
    }

}
