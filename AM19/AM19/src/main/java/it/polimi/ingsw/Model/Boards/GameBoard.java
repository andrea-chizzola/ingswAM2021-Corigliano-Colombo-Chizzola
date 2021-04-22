package it.polimi.ingsw.Model.Boards;

import it.polimi.ingsw.Model.Decks.DevelopmentDeck;
import it.polimi.ingsw.Model.Decks.LeaderDeck;
import it.polimi.ingsw.Model.Boards.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Boards.FaithTrack.VaticanReportSection;
import it.polimi.ingsw.Model.MarketBoard.MarketBoard;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

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
     * indicates if the end of the game has been triggered
     */
    private boolean isEndGameStarted;

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

    public GameBoard(ArrayList<String> nicknames, String file) {  //fix board test

        developmentDeck = new DevelopmentDeck(file);
        leaderDeck = new LeaderDeck(file);
        marketBoard = new MarketBoard(file);

        nPlayers = nicknames.size();
        players = new ArrayList<>(nicknames.size());

        for(int i = 0; i < nicknames.size(); i++){
            players.add(new Board(nicknames.get(i),
                    this,
                    file));
        }

        currentPlayer = players.get(0);
        isEndGameStarted = false;

        if(nicknames.size() > 1) customMode = new MultiplePlayer() ;
        else {
            customMode = new SinglePlayer(this, file);
        }

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
     * sets isEndGameStarted to true if the end of the game has been triggered, false otherwise
     * @param endGameStarted indicates if the end of the game has been triggered
     */
    public void setEndGameStarted(boolean endGameStarted) {
        isEndGameStarted = endGameStarted;
    }

    /**
     * @return returns true if the end of the game has been triggered, false otherwise
     */
    public boolean isEndGameStarted() {
        return isEndGameStarted;
    }

    /**
     * decides what to do at the end of the turn
     */
    public void endTurnMove(){        //invece di next player

        customMode.endTurnAction(this);

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
                if(!currentTrack.getSections().get(currentTrack.getSections().indexOf(section)).isDiscarded() && !currentTrack.getSections().get(currentTrack.getSections().indexOf(section)).getStatus()){
                    startVaticanReport(currentTrack.getSections().indexOf(section) + 1, currentTrack);
                }
            }
        }

    }

    /**
     * adds faith to the other players when a resource is discarded
     * @param amount equals the amount of resources discarded
     */
    public void addFaithToOthers(int amount){

        customMode.addFaithToOthers(amount, this);

    }

    /**
     * this method gives a number of leader cards to each player
     * @param file is the name of the XML file that contains the number of initial leader cards of each player
     */
    public void giveLeaderCards(String file){
        int num = ConfigurationParser.getNumLeader(file);
        for(Board board : players){
            board.setLeaders(leaderDeck.extract(num));
        }
    }

    /**
     * @return returns the message showed to the players when the match is over
     */
    public String findWinnerMessage(){
        return customMode.findWinnerMessage(getPlayers());
    }

}
