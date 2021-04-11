package it.polimi.ingsw;

import java.util.ArrayList;

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
    private ArrayList<LeaderCard> leaders;

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
    //modifications and turn attributes/methods missing


    public Board(String nickname, GameBoard gameBoard, ArrayList<Integer> trackPoints, ArrayList<VaticanReportSection> sections) {

        this.nickname = nickname;
        this.gameBoard = gameBoard;

        leaders = new ArrayList<>();
        faithTrack = new FaithTrack(trackPoints, sections);
        slots = new ArrayList<>();
        strongBox = new StrongBox();
        warehouse = new Warehouse();
        modification = new Modifications();
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
     * @param slot indicates the selected slot
     * @return returns the card currently at the top of the slot
     * @throws IndexOutOfBoundsException if the slot doesn't exist
     * @throws IllegalSlotException if the slot is currently empty
     */
    public DevelopmentCard getDevelopmentCard(int slot) throws IndexOutOfBoundsException, IllegalSlotException {

        try{

            if(slot > 0 && slot <= slots.size()) {
                return slots.get(slot - 1).getTop();
            }else throw new IndexOutOfBoundsException("This slot doesn't exist");

        } catch (IllegalSlotException e) {

            throw new IllegalSlotException(e.getMessage());

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
     * moves the faith marker ahead in the faith track and starts a vatican report if a player reached or passed the end of a section
     * @param amount indicates how many positions have to be added to the faith marker
     */
    public void addFaith(int amount){

        faithTrack.addFaith(amount);

        for(VaticanReportSection section : faithTrack.getSections()){

            if((!faithTrack.isBeforeSection(faithTrack.getSections().indexOf(section) + 1) && !faithTrack.isInsideSection(faithTrack.getSections().indexOf(section) + 1)) || (faithTrack.isEndSection(faithTrack.getSections().indexOf(section) + 1))){
                if(!section.isDiscarded() && !section.getStatus()){
                    gameBoard.startVaticanReport(faithTrack.getSections().indexOf(section) + 1, faithTrack);
                }
            }

        }

    }

    /**
     * calls the GameBoard's method that adds faith to other plyers
     * @param amount equals the amount of resources discarded
     */
    public void addFaithToOthers(int amount){

        gameBoard.addFaithToOthers(amount, this);

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


}
