package it.polimi.ingsw.Model.Boards;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.Enumerations.*;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.Decks.DevelopmentDeck;
import it.polimi.ingsw.Model.Decks.LeaderDeck;
import it.polimi.ingsw.Model.Boards.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Boards.FaithTrack.VaticanReportSection;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleWhite;
import it.polimi.ingsw.Model.MarketBoard.MarketBoard;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;
import it.polimi.ingsw.Model.Resources.ResourceColor;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.*;

/**
 * public class representing the game board containing the personal boards related to each player
 */
public class GameBoard implements GameBoardHandler {

    /**
     * indicates the number of players
     */
    private int nPlayers;//forse si può togliere, non viene usato

    /**
     * contains a reference for each board associated to the players
     */
    private ArrayList<Board> players;

    /**
     * arraylist of the disconnected players
     */
    private ArrayList<Board> disconnectedPlayers;

    /**
     * indicates the current player
     */
    private Board currentPlayer;

    /**
     * the virtual view
     */
    private View virtualView;

    /**
     * indicates if the end of the game has been triggered
     */
    private boolean isEndGameStarted;

    /**
     * indicates if the game is ended
     */
    private boolean gameEnded;

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

    /**
     * it represents the marbles taken by the current player from the marketBoard
     */
    private ArrayList<Marble> marblesMarket;

    /**
     * it represents the turns that the current player can do
     */
    private List<TurnType> correctTurns;

    public GameBoard(ArrayList<String> nicknames, String file) {

        developmentDeck = new DevelopmentDeck(file);
        leaderDeck = new LeaderDeck(file);
        marketBoard = new MarketBoard(file);

        nPlayers = nicknames.size();
        players = new ArrayList<>(nicknames.size());

        LinkedList<Integer> nInitializationResources =  ConfigurationParser.getInitializationResources(file);
        LinkedList<Integer> nInitializationFaith =  ConfigurationParser.getInitializationFaith(file);

        for(int i = 0; i < nicknames.size(); i++){
            players.add(new Board(nicknames.get(i),
                    this,
                    file));
        }

        for(int i = 0; i<players.size(); i++){
            players.get(i).setNumberResourcesInitialization(nInitializationResources.get(i));
            players.get(i).addFaith(nInitializationFaith.get(i));
        }

        currentPlayer = players.get(0);
        isEndGameStarted = false;
        gameEnded = false;

        if(nicknames.size() > 1) customMode = new MultiplePlayer() ;
        else {
            customMode = new SinglePlayer(this, file);
        }

        disconnectedPlayers = new ArrayList<>();

        correctTurns = new ArrayList<>();
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
     * this sets isGameEnded to true if the game is finished
     */
    public void setGameEnded() {
        gameEnded = true;
    }


    /**
     * @return returns an ArrayList containing all the players
     */
    public ArrayList<Board> getPlayers(){
        return players;
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
                if (board.getFaithTrack().isInsideSection(section) || board.getFaithTrack().isAfterSection(section)){
                    if(!board.getFaithTrack().getSection(section).isDiscarded() && !board.getFaithTrack().getSection(section).getStatus()) {
                        board.getFaithTrack().getSection(section).activateFavor();
                    }
                } else board.getFaithTrack().getSection(section).discard();
            }
        }

    }

    /**
     * checks if a new vatican report has to start
     * @param currentTrack indicates the track of a player who changed his position in the faith track
     */
    public void checkStartVaticanReport(FaithTrack currentTrack){

        for(VaticanReportSection section : currentTrack.getSections()){

            int sectionNumber = currentTrack.getSections().indexOf(section) + 1;

            if(currentTrack.isEndSection(sectionNumber) || currentTrack.isAfterSection(sectionNumber)){
                if(!currentTrack.getSections().get(currentTrack.getSections().indexOf(section)).isDiscarded())
                    if(!currentTrack.getSections().get(currentTrack.getSections().indexOf(section)).getStatus()){
                    startVaticanReport(sectionNumber, currentTrack);
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
     * @return returns a map (nickName - totalPoints)
     */
    public Map<String,Integer> getTotalPoints(){
        Map<String,Integer> map = new HashMap<>();
        for(Board board : players){
            map.put(board.getNickname(),board.getTotalPoints());
        }
        for(Board board : disconnectedPlayers){
            map.put(board.getNickname(),board.getTotalPoints());
        }
        return map;
    }

    /**
     * @return true if all the players' boards have been initialized, false otherwise
     */
    private boolean isAllInitialized(){

        for(Board board : players){
            if(!board.isLeadersInitialized() || !board.isResourcesInitialized())
                return false;
        }
        return true;
    }

    /**
     * check if it is possible to play a turn
     * @param turn the type of turn
     * @throws InvalidActionException if it is not possible to play the turn
     */
    private void checkTurn(TurnType turn) throws InvalidActionException{
        if(!isAllInitialized())
            throw new InvalidActionException("Initialization not ended!");
        if(!correctTurns.contains(turn))
            throw new InvalidActionException("You can't do this action now!");
    }

    public void setStartTurns(){
        correctTurns.clear();
        correctTurns.add(TurnType.SWAP);
        correctTurns.add(TurnType.TAKE_RESOURCES);
        correctTurns.add(TurnType.MANAGE_LEADER);
        correctTurns.add(TurnType.BUY_CARD);
        correctTurns.add(TurnType.DO_PRODUCTION);
    }

    public void setMiddleTurns(){
        correctTurns.clear();
        correctTurns.add(TurnType.SWAP);
        correctTurns.add(TurnType.MANAGE_LEADER);
        correctTurns.add(TurnType.EXIT);
    }

    /**
     * this method manages the end of the turn of a player and sets the new current player
     */
    public void endTurnMove(){

        customMode.endTurnAction(this);
        if(gameEnded){
            virtualView.showEndGame(getTotalPoints());
        }
        virtualView.showFaithUpdate(showFaith(),showSections(),customMode.showFaithLorenzo(),customMode.showSectionsLorenzo());
        if(players.size() == 1 && disconnectedPlayers.size()==0) {
            virtualView.showTopToken(customMode.showTopToken());
            virtualView.showDecksUpdate(developmentDeck.showDeck());
        }
        setStartTurns();
    }

    //methods of interface GameBoardHandler

    /**
     * this method attaches the view to the model
     * @param virtualView the observer of the model
     */
    @Override
    public void attachView(View virtualView){

        this.virtualView = virtualView;
    }

    /**
     * This method distributes the leader cards and updates the view
     * @param file configuration file
     */
    @Override
    public void initializeGame(String file){
        giveLeaderCards(file);
        virtualView.showFaithUpdate(showFaith(),showSections(),customMode.showFaithLorenzo(),customMode.showSectionsLorenzo());
        virtualView.showDecksUpdate(developmentDeck.showDeck());
        virtualView.showMarketUpdate(marketBoard.showMarket());

        for(Board board : players) {
            virtualView.showLeaderCards(board.showLeaderPosition(), board.showLeaderStatus(), board.getNickname());
        }

        virtualView.showGameStatus(true,"get resources",currentPlayer.getNickname(),TurnType.INITIALIZATION_LEADERS);
    }


    /**
     * this method checks if the nickname belongs to the current player
     * @param nickname the nickname of the player
     * @return true if the nickname belongs to the current player, false otherwise
     */
    @Override
    public boolean isCurrentPlayer(String nickname) {
        return nickname.equals(currentPlayer.getNickname());
    }


    /**
     * This method allows the current player to end his turn.
     * @throws InvalidActionException if the player can't do this action.
     */
    public void exit() throws InvalidActionException{

        checkTurn(TurnType.EXIT);
        endTurnMove();
        showAvailableTurns();
    }


    /**
     * this method gets the production of the development card present in the indicated slot
     * @param slot the number of the slot
     * @return the production of the card present in the slot
     * @throws InvalidActionException if the slot does not exist or if it is empty
     */
    @Override
    public Production getDevProduction(int slot) throws InvalidActionException {
        try {
            Production production = currentPlayer.getSlot(slot).getTop().getSpecialEffect().getProduction();
            return production;
        }
        catch (IllegalSlotException e) {
            throw new InvalidActionException(e.getMessage());
        }
    }

    /**
     * this method gets the production of the development card present in the indicated position
     * @param position the position of the leader card
     * @return the production of the card present in the indicated position
     * @throws InvalidActionException if the position is not correct
     */
    @Override
    public Production getLeaderProduction(int position) throws InvalidActionException {
        try {
            if(!currentPlayer.getLeaderCard(position).getStatus())
                throw new InvalidActionException("You are doing a production of an inactive leader card!");
            Production production = currentPlayer.getLeaderCard(position).getSpecialEffect().getProduction();
            return production;
        }
        catch (IndexOutOfBoundsException e){
            throw new InvalidActionException(e.getMessage());
        }
    }

    /**
     * this method discards the leader card in the indicated position without adding faith points to the player's faithTrack
     * @param leaderStatus the position of the leader card
     * @throws InvalidActionException if the position is not correct
     */
    @Override
    public void initializeLeaderCard(Map<Integer,Boolean> leaderStatus) throws InvalidActionException{

        if(currentPlayer.isLeadersInitialized())
            throw new InvalidActionException("You have already initialized your cards!");
        if(!currentPlayer.discardLeaderCard(leaderStatus))
            throw new InvalidActionException("Wrong choice of leader cards!");

        virtualView.showLeaderCards(currentPlayer.showLeaderPosition(), currentPlayer.showLeaderStatus(), currentPlayer.getNickname());
        virtualView.showGameStatus(true,"get resources",currentPlayer.getNickname(),TurnType.INITIALIZATION_RESOURCE);
    }


    /**
     * This method allows the current player to manage resources during the initialization.
     * @param resources the resources selected by the player
     * @param shelves the shelves where the resources have to be inserted
     * @return true if the operation is successful, false otherwise
     */
    @Override
    public void insertResources(List<Resource> resources, List<Integer> shelves) throws InvalidActionException{

        if(!(currentPlayer.isLeadersInitialized() && !currentPlayer.isResourcesInitialized()))
            throw new InvalidActionException("You can't initialize your resources now!");

        if (resources.size() != currentPlayer.getNumberResourcesInitialization())
            throw new InvalidActionException("Wrong number of selected resources!");
        //metto in warehouse
        if (resources.stream().anyMatch(resource -> resource.getColor().equals(ResourceColor.RED)))
            throw new InvalidActionException("Wrong number of selected resources!");

        if (!currentPlayer.getWarehouse().checkInsertMultipleRes(resources, shelves))
            throw new InvalidActionException("Wrong selected resources!");

        for (int i = 0; i < resources.size(); i++) {
            currentPlayer.getWarehouse().insertResource(shelves.get(i), resources.get(i));
        }

        endInsertResources();
    }

    private void endInsertResources(){

        virtualView.showBoxes(currentPlayer.getWarehouse().showWarehouse(), currentPlayer.getStrongBox().showStrongBox(), currentPlayer.getNickname());
        currentPlayer.setResourcesInitialized();
        endTurnMove();

        if(!isAllInitialized()) {
            virtualView.showGameStatus(true,"Initialize leader cards",currentPlayer.getNickname(),TurnType.INITIALIZATION_LEADERS);
            return;
        }
        showAvailableTurns();
    }


    /**
     * @return the production associated with the personal board
     */
    @Override
    public Production getBoardProduction() {
        return currentPlayer.getPersonalProduction();
    }

    /**
     * This method manages the disconnection of a player
     * @param nickname the nickname of the player disconnected
     * @return true if the disconnection is successful, false otherwise
     */
    @Override
    public void disconnectPlayer(String nickname) {

        if(!isAllInitialized())
            virtualView.showEndGame(getTotalPoints());
        if(currentPlayer.getNickname().equals(nickname))
            endTurnMove();

        for(int i=0; i<players.size(); i++){
            if(players.get(i).getNickname().equals(nickname)){
                disconnectedPlayers.add(players.get(i));
                virtualView.showDisconnection(players.get(i).getNickname());
                players.remove(i);
            }
        }
        showAvailableTurns();
    }

    /**
     * This method manages the reconnection of a player
     * @param nickname the nickname of the player reconnected
     * @return true if the reconnection is successful, false otherwise
     */
    @Override
    public void reconnectPlayer(String nickname) {

        for(int i=0; i<disconnectedPlayers.size(); i++){

            Board board = disconnectedPlayers.get(i);

            if(board.getNickname().equals(nickname)){

                players.add(board);
                disconnectedPlayers.remove(i);
            }
        }
    }

    /**
     * This method allows to take a specific row from the MarketBoard of the current player.
     * @param row the row the player wants to take
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void takeMarketRow(int row) throws InvalidActionException {

        checkTurn(TurnType.TAKE_RESOURCES);
        try {
            Marble white = new MarbleWhite();
            marblesMarket = getMarketBoard().takeRow(row-1);
            correctTurns.clear();
            correctTurns.add(TurnType.MANAGE_MARBLE);
            virtualView.showMarketUpdate(marketBoard.showMarket());
            virtualView.showMarblesUpdate(marblesMarket, white.whiteTransformations(currentPlayer), currentPlayer.getNickname());
        }
        catch (IllegalMarketException e){throw new InvalidActionException(e.getMessage());}

    }

    /**
     * This method allows to take a specific column from the MarketBoard of the current player.
     * @param column the column the player wants to take
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void takeMarketColumn(int column) throws InvalidActionException {

        checkTurn(TurnType.TAKE_RESOURCES);
        try {
            Marble white = new MarbleWhite();
            marblesMarket = getMarketBoard().takeColumn(column-1);
            correctTurns.clear();
            correctTurns.add(TurnType.MANAGE_MARBLE);
            virtualView.showMarketUpdate(marketBoard.showMarket());
            virtualView.showMarblesUpdate(marblesMarket, white.whiteTransformations(currentPlayer), currentPlayer.getNickname());
        }
        catch (IllegalMarketException e){throw new InvalidActionException(e.getMessage());}

    }

    /**
     * This method manages the actions that have to be performed on each marble
     * @param marblesPlayer the marbles selected by the player
     * @param actions the actions to be performed on each marble
     * @param shelves the shelves where the resources associated to the marbles have to be inserted
     * @return true if the operation is successful, false if choices of the player are not allowed
     */
    public void actionMarbles(List<Marble> marblesPlayer, List<PlayerAction> actions, List<Integer> shelves) throws InvalidActionException{

        checkTurn(TurnType.MANAGE_MARBLE);

        Marble marblePlayer;
        if(!checkMarbles(marblesMarket,marblesPlayer,actions,shelves)) {
            throw new InvalidActionException("Wrong marbles selected!");
        }
        for(int i=0; i<marblesMarket.size(); i++){

            marblePlayer = marblesPlayer.get(i);

            if(actions.get(i).equals(PlayerAction.INSERT))
                marblePlayer.addResource(currentPlayer,shelves.get(i));

            if(actions.get(i).equals(PlayerAction.DISCARD))
                marblePlayer.discard(currentPlayer);
        }

        virtualView.showBoxes(currentPlayer.getWarehouse().showWarehouse(), currentPlayer.getStrongBox().showStrongBox(), currentPlayer.getNickname());
        virtualView.showFaithUpdate(showFaith(),showSections(),customMode.showFaithLorenzo(),customMode.showSectionsLorenzo());
        setMiddleTurns();
        showAvailableTurns();
    }


    /**
     * This method checks the correctness of the choice of the player
     * @param marbles the marbles of the selected row/column
     * @param marblesPlayer the marbles selected by the player
     * @param actions the actions to be performed on each marble
     * @param shelves the shelves where the resources associated to the marbles have to be inserted
     * @return true if the marbles selected and the actions to be performed on them are correct, false otherwise
     */
    private boolean checkMarbles(List<Marble> marbles, List<Marble> marblesPlayer, List<PlayerAction> actions, List<Integer> shelves){
        Marble marble;
        Marble marblePlayer;
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Integer> shelf = new ArrayList<>();

        if(marbles.size() != marblesPlayer.size())
            return false;

        for(int i=0; i<marbles.size(); i++){
            marble = marbles.get(i);
            marblePlayer = marblesPlayer.get(i);

            if(!marble.checkMarble(marblePlayer,currentPlayer))
                return false;

            if(actions.get(i).equals(PlayerAction.INSERT)) {
                resources.add(marblePlayer.getResourceAssociated());
                shelf.add(shelves.get(i));
            }
        }

        if(!currentPlayer.getWarehouse().checkInsertMultipleRes(resources,shelf)) {
            return false;
        }
        return true;
    }


    /**
     * This method allows two swap the content of two shelves of the warehouse of the current player.
     * @param source the number of the first shelf
     * @param target the number of the second shelf
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void swapWarehouse(int source, int target) throws InvalidActionException {

        checkTurn(TurnType.SWAP);
        try {
            currentPlayer.getWarehouse().swap(source,target);
            virtualView.showBoxes(currentPlayer.getWarehouse().showWarehouse(), currentPlayer.getStrongBox().showStrongBox(), currentPlayer.getNickname());
            showAvailableTurns();
        } catch (IllegalShelfException e) {
            throw new InvalidActionException(e.getMessage());
        }
    }


    /**
     * This method allows to buy the development card with color 'color' and level 'level' on the top of the deck
     * and it allows to add that card in the slot with number 'slot' of the current player.
     * @param slot the number of the slot in which the player wants to add the card
     * @param color the color of the card the player wants to add
     * @param level the level of the card the player wants to add
     * @param shelves ArrayList of Integer which represents all the shelves selected
     * @param quantity ArrayList of Integer which represents the amount of resources selected for each shelf
     * @param strongbox ArrayList of ResQuantity which represents all the resources selected from the strongBox
     *                  It is important that each resource is present at most once
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void buyCard(int slot, CardColor color, int level, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException {
        Slot slot1;
        Board board = currentPlayer;
        DevelopmentCard card;

        checkTurn(TurnType.BUY_CARD);

        try {
            card = getDevelopmentDeck().readTop(color,level);
            card.checkReq(board,shelves,quantity,strongbox);
        }catch (IllegalArgumentException e){throw new InvalidActionException(e.getMessage());}

        try {
            slot1 = board.getSlot(slot);
            slot1.insertCard(card);
            board.checkDevCard(card.getCardColor());
        }
        catch (IllegalSlotException e){throw new InvalidActionException(e.getMessage());}
        try {
            getDevelopmentDeck().getTop(color, level);
        }catch (IllegalArgumentException e){throw new InvalidActionException("Problem!");}

        ResQuantity.useResources(board,shelves,quantity,strongbox);
        virtualView.showBoxes(currentPlayer.getWarehouse().showWarehouse(), currentPlayer.getStrongBox().showStrongBox(), currentPlayer.getNickname());
        virtualView.showSlotsUpdate(currentPlayer.showSlot(), currentPlayer.getNickname());
        virtualView.showDecksUpdate(developmentDeck.showDeck());
        setMiddleTurns();
        showAvailableTurns();
    }

    /**
     * This method allows the current player to activate the production of all the productions passed as parameters.
     * @param productions ArrayList of Production which represents all the productions the player wants to activate
     * @param shelves ArrayList of Integer which represents all the shelves selected
     * @param quantity ArrayList of Integer which represents the amount of resources selected for each shelf
     * @param strongbox ArrayList of ResQuantity which represents all the resources selected from the strongBox
     *                  It is important that each resource is present at most once
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void doProduction(ArrayList<Production> productions, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException {
        Map<Resource,Integer> resourceStatus;
        Board board = currentPlayer;

        checkTurn(TurnType.DO_PRODUCTION);

        try {
            resourceStatus = ResQuantity.createReqMap(board,shelves,quantity,strongbox);

            for(Production production : productions)
                production.checkProduction(resourceStatus);
        }
        catch (InvalidActionException e){throw e;}

        //It checks if the player has selected more resources than required, it is important because all the resources selected will be subtracted from the deposits
        if(resourceStatus.values().stream().mapToInt(Integer::intValue).sum() != 0)
            throw new InvalidActionException("Too many resources selected!");

        ResQuantity.useResources(board, shelves, quantity, strongbox);

        for(Production production : productions)
            production.addProducts(board);

        virtualView.showBoxes(currentPlayer.getWarehouse().showWarehouse(), currentPlayer.getStrongBox().showStrongBox(), currentPlayer.getNickname());
        virtualView.showFaithUpdate(showFaith(),showSections(),customMode.showFaithLorenzo(),customMode.showSectionsLorenzo());
        setMiddleTurns();
        showAvailableTurns();
    }

    /**
     * This method allows the current player to activate the leader card in position with number 'position'.
     * @param position the position of the leader card
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void activateCard(int position) throws InvalidActionException {
        LeaderCard card;
        Board board = currentPlayer;

        checkTurn(TurnType.MANAGE_LEADER);

        try {
            card = board.getLeaderCard(position);
        }
        catch (IndexOutOfBoundsException e){throw new InvalidActionException(e.getMessage());}

        //if status == true no actions have to be done
        if(card.getStatus() == false){
            try {
                card.checkReq(board);
            }
            catch (InvalidActionException | ResourcesExpectedException e){throw new InvalidActionException(e.getMessage());}

            card.getSpecialEffect().applyEffect(board);
            card.setStatus(true);
        }
        virtualView.showLeaderCards(currentPlayer.showLeaderPosition(), currentPlayer.showLeaderStatus(), currentPlayer.getNickname());
        showAvailableTurns();
    }

    /**
     * This method allows the current player to remove the leader card in position with number 'position'.
     * @param position the position of the leader card
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void removeCard(int position) throws InvalidActionException {

        checkTurn(TurnType.MANAGE_LEADER);
        try{
            currentPlayer.removeLeaderCard(position);
            virtualView.showLeaderCards(currentPlayer.showLeaderPosition(), currentPlayer.showLeaderStatus(), currentPlayer.getNickname());
            virtualView.showFaithUpdate(showFaith(),showSections(),customMode.showFaithLorenzo(),customMode.showSectionsLorenzo());
            showAvailableTurns();
        }
        catch (IndexOutOfBoundsException e){throw new InvalidActionException(e.getMessage());}
    }


    /**
     *
     */
    public void showAvailableTurns() {
        LinkedList<String> turns = new LinkedList<>();
        for(TurnType turnType : correctTurns){
            turns.add(turnType.toString());
        }
        virtualView.showAvailableTurns(turns,currentPlayer.getNickname());
    }


    /**
     * @return map(String-Integer) which represents nickname and total points
     */
    private Map<String, Integer> showFaith(){
        Map<String,Integer> map = new HashMap<>();
        for(Board board : players){
            map.put(board.getNickname(),board.getFaithTrack().getPosition());
        }
        for(Board board : disconnectedPlayers){
            map.put(board.getNickname(),board.getFaithTrack().getPosition());
        }
        return map;
    }

    /**
     * @return map(String, List of ItemStatus) which represents the sections of the players
     */
    private Map<String, List<ItemStatus>> showSections(){
        Map<String,List<ItemStatus>> map = new HashMap<>();
        List<VaticanReportSection> vatican;

        for(Board board : players){

            vatican = board.getFaithTrack().getSections();
            map.put(board.getNickname(),createListItems(vatican));
        }
        for(Board board : disconnectedPlayers){

            vatican = board.getFaithTrack().getSections();
            map.put(board.getNickname(),createListItems(vatican));
        }
        return map;
    }


    public List<ItemStatus> createListItems(List<VaticanReportSection> vatican){
        List<ItemStatus> list = new ArrayList<>();
        for(VaticanReportSection vaticanReportSection : vatican) {
            if (vaticanReportSection.isDiscarded())
                list.add(ItemStatus.DISCARDED);
            if (vaticanReportSection.getStatus())
                list.add(ItemStatus.ACTIVE);
            if (!vaticanReportSection.isDiscarded() && !vaticanReportSection.getStatus())
                list.add(ItemStatus.INACTIVE);
        }
        return list;
    }

}
