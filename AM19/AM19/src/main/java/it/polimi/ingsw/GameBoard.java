package it.polimi.ingsw;

import java.util.ArrayList;

/**
 * public class representing the game board containing the personal boards related to each player
 */
public class GameBoard {

    /**
     * indicates the number of players
     */
    private int nPlayers;

    /**
     * contains a reference for each board associated to the players
     */
    private ArrayList<Board> players;

    /**
     * indicates the current player
     */
    private Board currentPlayer;

    /**
     * represents the market board
     */
    private MarketBoard marketBoard;

    /**
     * represents the current game mode (single player or multiplayer)
     */
    private CustomMode customMode;

    /**
     * contains all the development cards, divided by level and color is smaller decks
     */
    private DevelopmentDeck developmentDeck;

    /**
     * contains the leader cards chosen by each player
     */
    private LeaderDeck leaderDeck;

    //TEMPORARY CONSTRUCTOR
    public GameBoard(ArrayList<String> nicknames, ArrayList<Integer> trackPoints, ArrayList<ArrayList<VaticanReportSection>> sections, ArrayList<Action> actions) {  //fix board test

        nPlayers = nicknames.size();

        players = new ArrayList<Board>(nicknames.size());

        for(int i = 0; i < nicknames.size(); i++){

            players.add(new Board(nicknames.get(i), this, trackPoints, sections.get(i)));

        }

        currentPlayer = players.get(0);
        marketBoard = new MarketBoard();

        if(nicknames.size() > 1){
            customMode = new MultiplePlayer();
        }else {

            customMode = new SinglePlayer(this, trackPoints, sections.get(1), actions);

        }

        //decks missing

    }

    /**
     * @return returns the development cards deck
     */
    public DevelopmentDeck getDevelopmentDeck() {
        return developmentDeck;
    }

    /**
     * sets a new current player
     * @param currentPlayer represents the new current player
     */
    public void setCurrentPlayer(Board currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return returns the current player
     */
    public Board getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * decides what to do at the end of the turn
     */
    public void endTurnMove() throws LorenzoWonException {        //invece di next player

        try {

            customMode.endTurnAction(this);

        }catch(LorenzoWonException e){

            throw new LorenzoWonException(e.getMessage());

        }
    }

    /**
     * @return returns an ArrayList containing all the players
     */
    public ArrayList<Board> getPlayers(){
        return players;
    }

    /**
     * @return returns the number of players
     */
    public int getNumPlayers(){

        return nPlayers;

    }

    /**
     * @return returns the market board
     */
    public MarketBoard getMarketBoard(){

        return marketBoard;

    }


    /**
     * starts the vatican report regarding the section indicated
     * @param section indicates the section whose report has to start
     * @param currentTrack indicates the track of the player who started the report
     */
    public void startVaticanReport(int section, FaithTrack currentTrack){

        for(Board board : players){

            if(board.getFaithTrack() == currentTrack){
                board.getFaithTrack().getSection(section).activateFavor();
            }else {
                if (board.getFaithTrack().isInsideSection(section) || (!board.getFaithTrack().isBeforeSection(section) && !board.getFaithTrack().isInsideSection(section))){
                    if(!board.getFaithTrack().getSection(section).isDiscarded() && !board.getFaithTrack().getSection(section).getStatus()) {
                        board.getFaithTrack().getSection(section).activateFavor();
                    }
                } else board.getFaithTrack().getSection(section).discard();
            }
        }

    }

    public void checkStartVaticanReport(FaithTrack currentTrack){

        for(VaticanReportSection section : currentTrack.getSections()){
            if(currentTrack.isEndSection(currentTrack.getSections().indexOf(section) + 1) || (!currentTrack.isBeforeSection(currentTrack.getSections().indexOf(section) + 1) && !currentTrack.isInsideSection(currentTrack.getSections().indexOf(section) + 1))){
                if(!currentTrack.getSections().get(currentTrack.getSections().indexOf(section) + 1).isDiscarded() && !currentTrack.getSections().get(currentTrack.getSections().indexOf(section) + 1).getStatus()){
                    startVaticanReport(currentTrack.getSections().indexOf(section) + 1, currentTrack);
                }
            }
        }

    }

    /**
     * adds faith to the other players when a resource is discarded
     * @param amount equals the amount of resources discarded
     */
    public void addFaithToOthers(int amount) throws LorenzoWonException {

        try {
            customMode.addFaithToOthers(amount, this);
        }catch(LorenzoWonException e){

            throw new LorenzoWonException(e.getMessage());

        }

    }


}
