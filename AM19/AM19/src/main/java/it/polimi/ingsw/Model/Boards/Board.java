package it.polimi.ingsw.Model.Boards;

import it.polimi.ingsw.Exceptions.IllegalSlotException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.Boards.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Resources.Resource;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * public class representing the personal board associated to a player
 */
public class Board {

    /**
     * represents the nickname associated to the player who owns the Board
     */
    private String nickname;

    /**
     * represents the game board, where all the personal boards are placed
     */
    private GameBoard gameBoard;

    /**
     * contains the Leader Cards owned by the player
     */
    private LinkedList<LeaderCard> leaders;

    /**
     * represents the Faith Track associated to the player
     */
    private FaithTrack faithTrack;

    /**
     * contains the development cards owned by the player
     */
    private ArrayList<Slot> slots;

    /**
     * represents the strongbox associated to the player
     */
    private StrongBox strongBox;

    /**
     * represents the warehouse associated to the player
     */
    private Warehouse warehouse;

    /**
     * represents the active discounts and possible transformations of the white marble.
     */
    private Modifications modification;

    public Board(String nickname, GameBoard gameBoard, String file) {

        this.nickname = nickname;
        this.gameBoard = gameBoard;
        faithTrack = ConfigurationParser.parseFaithTrack(file);

        int nSlots = ConfigurationParser.getNumSlots(file);
        slots = new ArrayList<>();
        for(int i=0; i<nSlots; i++){
            slots.add(new Slot());
        }

        leaders = new LinkedList<>();
        strongBox = new StrongBox();
        warehouse = new Warehouse(file);
        modification = new Modifications();
    }

    /**
     * adds the picked leader cards to the list
     * @param leaders contains the leaders picked
     */
    public void setLeaders(LinkedList<LeaderCard> leaders) {
        this.leaders = new LinkedList<>(leaders);
    }

    /**
     * @return returns the game board
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * @return returns the nickname associated to the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * associates a nickname to the board
     * @param nickname represents the player's nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * This method gets the slot with number 'slot'
     * @param slot the number of the slot
     * @return the slot with number slot
     * @throws IllegalSlotException
     */
    public Slot getSlot(int slot) throws IllegalSlotException{
        if(slot > 0 && slot <= slots.size()) {
            return slots.get(slot - 1);
        }else throw new IllegalSlotException("This slot doesn't exist");
    }

    /**
     * @return returns an ArrayList containing the slots related to the board
     */
    public ArrayList<Slot> getSlots() {
        return slots;
    }

    /**
     * checks if the current player currently has seven development cards. If so the End Game is triggered
     */
    public void checkDevCard(CardColor cardColor){

        int cardNumber = 0;

        for(Slot slot : getSlots()){
            cardNumber = cardNumber + slot.getNumberOfCards();
        }

        if(cardNumber >= 7){
            gameBoard.setEndGameStarted(true);
        }
        if(gameBoard.getPlayers().size() == 1 && !gameBoard.getDevelopmentDeck().isColorAvailable(cardColor)){
            gameBoard.setEndGameStarted(true);
        }

    }

    /**
     * @param position indicates the selected leader card
     * @return returns the leader card associated to the indicated position
     * @throws IndexOutOfBoundsException if the leader card doesn't exist
     */
    public LeaderCard getLeaderCard(int position) throws IndexOutOfBoundsException{

        if(position > 0 && position <= leaders.size()){

            return leaders.get(position - 1);

        }else throw new IndexOutOfBoundsException("Nonexistent Leader card");

    }

    /**
     * removes the selected leader card from the list
     * @param position indicates the position of the card that has to be removed
     * @throws IndexOutOfBoundsException if if the leader card doesn't exist
     */
    public void removeLeaderCard(int position) throws IndexOutOfBoundsException{

        if(position > 0 && position <= leaders.size()){

            leaders.remove(position - 1);


            this.addFaith(1);


        }else throw new IndexOutOfBoundsException("Nonexistent Leader card");

    }


    /**
     * @return returns the total victory points once the game is ended according to the cards and resources left
     */
    public int getTotalPoints(){

        int resourcePoints = (strongBox.calculateTotalResources() + warehouse.calculateTotalResources()) / 5;
        int trackPoints = faithTrack.calculatePoints();

        int devPoints = 0;

        for(Slot slot : slots){

            devPoints = devPoints + slot.countPoints();

        }

        int leadPoints = 0;

        for(LeaderCard leaderCard : leaders){

            leadPoints = leadPoints + leaderCard.getVictoryPoint();

        }

        return resourcePoints + trackPoints + devPoints + leadPoints;

    }

    /**
     * @return returns the number of resources remaining to the player
     */
    public int getTotalResources(){

        return strongBox.calculateTotalResources() + warehouse.calculateTotalResources();

    }

    /**
     * moves the faith marker ahead in the faith track and starts a vatican report if a player reached or passed the end of a section
     * @param amount indicates how many positions have to be added to the faith marker
     */
    public void addFaith(int amount){

        faithTrack.addFaith(amount);

        gameBoard.checkStartVaticanReport(faithTrack);

        if(faithTrack.isEndTrack()) {

            gameBoard.setEndGameStarted(true);

        }

    }

    /**
     * calls the GameBoard's method that adds faith to other players
     * @param amount equals the amount of resources discarded
     */
    public void addFaithToOthers(int amount){

        gameBoard.addFaithToOthers(amount);

    }

    /**
     * @return returns the faith track related to the board
     */
    public FaithTrack getFaithTrack(){
        return  faithTrack;
    }

    /**
     * @return returns the warehouse associated to the current player
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * @return returns the strongbox associated to the current player
     */
    public StrongBox getStrongBox() {
        return strongBox;
    }

    /**
     *
     * @return returns the modification associated to the current player
     */
    public Modifications getModifications() { return modification; }


    /**
     * This method returns a Map which contains information about all the resources present in strongbox and warehouse.
     * @return Map<Resource,Integer>, Integer represents the amount of the resource (the key)
     */
    public Map<Resource,Integer> getResourceStatus(){

        Map<Resource,Integer> warehouse = this.warehouse.getAllResources();
        Map<Resource,Integer> strongBox = this.strongBox.getResources();

        Map<Resource, Integer> resourceStatus = Stream.concat(warehouse.entrySet().stream(), strongBox.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)));

        return resourceStatus;
    }


    /**
     * This method checks if there are at least 'quantity' development cards which have color equals to 'cardColor' and level equals to 'level'.
     * @param cardColor the color of the cards searched
     * @param level the level of the cards searched
     * @param quantity the quantity required
     * @return true if the requirements are met
     * @throws InvalidActionException if the requirements are not met.
     */
    public boolean checkCards(CardColor cardColor, int level, int quantity) throws InvalidActionException{

       int match = 0;
       for(Slot slot : slots)
           for(DevelopmentCard card : slot.getCards()){
               if(card.getCardColor().equals(cardColor) && card.getCardLevel() == level)
                   match++;
           }

       if(match < quantity)
           throw new InvalidActionException("Card requirements not met!");

       return true;
    }
}
