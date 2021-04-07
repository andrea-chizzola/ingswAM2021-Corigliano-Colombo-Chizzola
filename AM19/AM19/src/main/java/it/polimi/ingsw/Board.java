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

    //modifications and turn attributes/methods missing


    public Board(String nickname, GameBoard gameBoard, ArrayList<Integer> trackPoints, ArrayList<VaticanReportSection> sections) {

        this.nickname = nickname;
        this.gameBoard = gameBoard;

        leaders = new ArrayList<LeaderCard>();
        faithTrack = new FaithTrack(trackPoints, sections);
        slots = new ArrayList<Slot>();
        strongBox = new StrongBox();
        warehouse = new Warehouse();
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


}
