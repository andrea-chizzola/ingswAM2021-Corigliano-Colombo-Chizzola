package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Boards.*;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Cards.Colors.Blue;
import it.polimi.ingsw.Model.Cards.Colors.Green;
import it.polimi.ingsw.Model.Cards.Colors.Purple;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.View.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private GameBoard gameBoard;
    private Board board;
    private final String file = "defaultConfiguration.xml";
    private DevelopmentCard dev1;
    private DevelopmentCard dev2;
    private DevelopmentCard dev3;

    @BeforeEach
    void setUp() {


        ArrayList<String> names = new ArrayList<>();
        names.add("firstPlayer");
        names.add("secondPlayer");
        names.add("thirdPlayer");

        gameBoard = new GameBoard(names, file);
        gameBoard.giveLeaderCards(file);
        board = gameBoard.getPlayers().get(0);

        View view = new ViewForTest();
        gameBoard.attachView(view);

        //creation of a DevelopmentCard
        Requirements requirements1 = new ResourceReqDev(new LinkedList<>());
        SpecialEffect effect1 = new Production(new LinkedList<>(), new LinkedList<>(), 0, 0);
        dev1 = new DevelopmentCard(0, effect1, requirements1, new Green(), 1, "1", "test1");

        //creation of a DevelopmentCard
        Requirements requirements2 = new ResourceReqDev(new LinkedList<>());
        SpecialEffect effect2 = new Production(new LinkedList<>(), new LinkedList<>(), 0, 0);
        dev2 = new DevelopmentCard(0, effect2, requirements2, new Green(), 1, "2", "test3");

        //creation of a DevelopmentCard
        Requirements requirements3 = new ResourceReqDev(new LinkedList<>());
        SpecialEffect effect3 = new Production(new LinkedList<>(), new LinkedList<>(), 0, 0);
        dev3 = new DevelopmentCard(0, effect2, requirements3, new Purple(), 2, "3", "test4");

        //Initialization of board slots of firstPlayer
        try{
            Slot slot1 = board.getSlot(1);
            slot1.insertCard(dev1);
            slot1.insertCard(dev3);
            Slot slot2 = board.getSlot(2);
            slot2.insertCard(dev2);
        }
        catch(IllegalSlotException e){
            e.printStackTrace();
        }

        //Initialization of Warehouse and Strongbox of firstPlayer
        Warehouse warehouse = board.getWarehouse();
        StrongBox strongBox = board.getStrongBox();

        warehouse.insertResource(1, new Coin());
        warehouse.insertResource(2, new Servant());
        warehouse.insertResource(3, new Shield());
        warehouse.insertResource(3, new Shield());

        strongBox.addResource(new Shield(),3);
        strongBox.addResource(new Stone(), 4);
    }

    /**
     * tests the correct activation of the vatican report
     */

    @Test
    @DisplayName("Add faith activation")
    void addFaithActivation(){
        Board player1 = gameBoard.getPlayers().get(0);
        Board player2 = gameBoard.getPlayers().get(1);
        Board player3 = gameBoard.getPlayers().get(2);

        player1.addFaith(7); //1->7
        player2.addFaith(6); //2->6
        player3.addFaith(3); //3->4

        //no VaticanSections have been activated.
        assertFalse(player1.getFaithTrack().getSection(1).isDiscarded());
        assertFalse(player1.getFaithTrack().getSection(1).getStatus());

        assertFalse(player2.getFaithTrack().getSection(1).isDiscarded());
        assertFalse(player2.getFaithTrack().getSection(1).getStatus());

        assertFalse(player3.getFaithTrack().getSection(1).isDiscarded());
        assertFalse(player3.getFaithTrack().getSection(1).getStatus());

        //now the first player reaches the end of the first VaticanSection
        player1.addFaith(1); //1->8

        assertFalse(player1.getFaithTrack().getSection(1).isDiscarded());
        assertTrue(player1.getFaithTrack().getSection(1).getStatus());

        //the VaticanSection of the second player has also been activated
        assertFalse(player2.getFaithTrack().getSection(1).isDiscarded());
        assertTrue(player2.getFaithTrack().getSection(1).getStatus());

        //the pope Favor in the VaticanReport of the third player has been discarded.
        assertTrue(player3.getFaithTrack().getSection(1).isDiscarded());
        assertFalse(player3.getFaithTrack().getSection(1).getStatus());

        player1.addFaith(7); //1->15
        player2.addFaith(6); //2->12
        player3.addFaith(4); //3->8

        //we now check if the second section works properly
        assertFalse(player1.getFaithTrack().getSection(2).isDiscarded());
        assertFalse(player1.getFaithTrack().getSection(2).getStatus());
        assertFalse(player2.getFaithTrack().getSection(2).isDiscarded());
        assertFalse(player2.getFaithTrack().getSection(2).getStatus());
        assertFalse(player3.getFaithTrack().getSection(2).isDiscarded());
        assertFalse(player3.getFaithTrack().getSection(2).getStatus());

        gameBoard.getPlayers().get(0).addFaith(1); //1->16

        assertFalse(player1.getFaithTrack().getSection(2).isDiscarded());
        assertTrue(player1.getFaithTrack().getSection(2).getStatus());
        assertFalse(player2.getFaithTrack().getSection(2).isDiscarded());
        assertTrue(player2.getFaithTrack().getSection(2).getStatus());
        assertTrue(player3.getFaithTrack().getSection(1).isDiscarded() );
        assertFalse(player3.getFaithTrack().getSection(1).getStatus());
        assertTrue(player3.getFaithTrack().getSection(2).isDiscarded());
        assertFalse(player3.getFaithTrack().getSection(2).getStatus());

        gameBoard.getPlayers().get(2).addFaith(9); //3->17

        assertFalse(player1.getFaithTrack().getSection(2).isDiscarded());
        assertTrue(player1.getFaithTrack().getSection(2).getStatus());
        assertFalse(player2.getFaithTrack().getSection(2).isDiscarded());
        assertTrue(player2.getFaithTrack().getSection(2).getStatus());
        assertTrue(player3.getFaithTrack().getSection(1).isDiscarded());
        assertFalse(player3.getFaithTrack().getSection(1).getStatus());
        assertTrue(player3.getFaithTrack().getSection(2).isDiscarded());
        assertFalse(player3.getFaithTrack().getSection(2).getStatus());

    }

    /**
     * tests the correct behavior of the addFaith method in case a player passes more than one section
     */

    @Test
    @DisplayName("Multiple activation")
    void addFaithMultipleActivation(){

        gameBoard.getPlayers().get(0).addFaith(7); //1>7
        gameBoard.getPlayers().get(1).addFaith(6); //2>6
        gameBoard.getPlayers().get(2).addFaith(3); //3>4

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());

        gameBoard.getPlayers().get(0).addFaith(9); //1>16

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
        assertTrue(gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).getStatus());

        gameBoard.getPlayers().get(2).addFaith(13); //3>17

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
        assertTrue(gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).getStatus());

    }

    /**
     * tests the correct behavior of the addFaithToOthers method
     */

    @Test
    void addFaithToOthers(){

        gameBoard.getPlayers().get(0).addFaithToOthers(1);

        assertEquals(0, gameBoard.getPlayers().get(0).getFaithTrack().getPosition());
        assertEquals(1, gameBoard.getPlayers().get(1).getFaithTrack().getPosition());
        assertEquals(2, gameBoard.getPlayers().get(2).getFaithTrack().getPosition());

        gameBoard.endTurnMove();

        gameBoard.getPlayers().get(1).addFaithToOthers(1);

        assertEquals(1, gameBoard.getPlayers().get(0).getFaithTrack().getPosition());
        assertEquals(1, gameBoard.getPlayers().get(1).getFaithTrack().getPosition());
        assertEquals(3, gameBoard.getPlayers().get(2).getFaithTrack().getPosition());

        gameBoard.endTurnMove();

        gameBoard.getPlayers().get(2).addFaithToOthers(1);

        assertEquals(2, gameBoard.getPlayers().get(0).getFaithTrack().getPosition());
        assertEquals(2, gameBoard.getPlayers().get(1).getFaithTrack().getPosition());
        assertEquals(3, gameBoard.getPlayers().get(2).getFaithTrack().getPosition());

    }

    /**
     * tests the correct initialization of the board
     */
    @Test
    void getNickname() {
        assertEquals("firstPlayer", gameBoard.getPlayers().get(0).getNickname());
    }

    /**
     * tests the correct change of nickname
     */
    @Test
    void setNickname() {
        gameBoard.getPlayers().get(0).setNickname("newNickname");
        assertEquals("newNickname", gameBoard.getPlayers().get(0).getNickname());
    }

    /**
     * tests the IndexOutOfBounds exception is thrown in case of nonexistent Leader card
     */
    @Test
    void getLeaderCardException() {

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> gameBoard.getPlayers().get(0).getLeaderCard(5));
        assertEquals(exception.getMessage(), "Nonexistent Leader card");

    }

    /**
     * tests the IndexOutOfBounds exception is thrown in case of nonexistent Leader card
     */
    @Test
    void removeLeaderCardException() {

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> gameBoard.getPlayers().get(0).removeLeaderCard(5));
        assertEquals(exception.getMessage(), "Nonexistent Leader card");

    }


    @Test
    public void checkCardsTest1(){
        //the board contains enough cards
        try{
            assertTrue(board.checkCards(new Green(),1,2));
            assertTrue(board.checkCards(new Purple(), 2, 1));
        }
        catch(InvalidActionException e){
            fail();
        }
    }

    @Test
    public void checkCardsTest2(){
        //the board does not contains enough cards
            assertThrows(InvalidActionException.class, () -> board.checkCards(new Purple(),2,2));
    }

    @Test
    public void checkCardsTest3(){
        //the board does not contains the cards
        assertThrows(InvalidActionException.class, () -> board.checkCards(new Blue(),2,2));
    }

    @Test
    public void checkCardsTest4(){
        //slots are empty (i.e. the board contains no cards).
        board = gameBoard.getPlayers().get(2);
        assertThrows(InvalidActionException.class, () -> board.checkCards(new Blue(),2,2));
    }

    @Test
    public void getResourceStatusTest1(){
        Map<Resource, Integer> returned = board.getResourceStatus(); //the map returned by the method
        Map<Resource, Integer> copy = new HashMap<>(); //a map of the resources that we have inserted in the
                                                        //player's board
        copy.put(new Coin(), 1);
        copy.put(new Servant(), 1);
        copy.put(new Shield(),5);
        copy.put(new Stone(), 4);

        for(Resource r: returned.keySet()){
            assertEquals(returned.get(r), copy.get(r));
        }

    }

    @Test
    public void getResourceStatusTest2(){
        //The player's Warehouse and Strongbox are still empty
        board = gameBoard.getPlayers().get(2);

        assertTrue(board.getResourceStatus().isEmpty());
    }

    @Test
    public void slotGettersTest(){
        assertDoesNotThrow(() -> board.getSlot(1));
        assertDoesNotThrow(() -> board.getSlot(2));
        assertDoesNotThrow(() -> board.getSlot(3));
    }

    @Test
    void getDevelopmentCardBoundsException(){

        Exception exception;
        exception = assertThrows(IllegalSlotException.class, () -> board.getSlot(4));
        assertEquals(exception.getMessage(), "This slot doesn't exist");

    }

    @Test
    void removeLeaderCardTest1(){
        //Each player receives two leader cards
        //gameBoard.giveLeaderCards(file);
        LeaderCard leader1 = board.getLeaderCard(1);
        LeaderCard leader2 = board.getLeaderCard(2);

        //The first player discard the first card, and gains a FaithPoint
        board.removeLeaderCard(1);

        assertEquals(1, board.getFaithTrack().getPosition());
        assertEquals(board.getLeaderCard(1), leader2);
        assertThrows(IndexOutOfBoundsException.class, () -> board.getLeaderCard(4));

    }

    @Test
    void removeLeaderCardTest2(){
        //Each player receives two leader cards
        //gameBoard.giveLeaderCards(file);

        //The first player discard all their cards
        board.removeLeaderCard(1);
        board.removeLeaderCard(1);
        board.removeLeaderCard(1);
        board.removeLeaderCard(1);

        assertEquals(4, board.getFaithTrack().getPosition());
        assertThrows(IndexOutOfBoundsException.class, () -> board.getLeaderCard(1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getLeaderCard(2));

    }

    @Test
    void removeLeaderCardTest3(){
        //Each player receives two leader cards
        //gameBoard.giveLeaderCards(file);

        //The first player discard more cards than the ones he owns
        board.removeLeaderCard(1);
        board.removeLeaderCard(1);
        board.removeLeaderCard(1);
        board.removeLeaderCard(1);

        assertThrows(IndexOutOfBoundsException.class, () -> board.removeLeaderCard(2));

    }

    @Test
    void initializeLeaderCards(){
        Map<Integer,Boolean> map = new HashMap<>();
        LeaderCard leaderCard2 = null;
        LeaderCard leaderCard1 = null;
        try {
            leaderCard1 = gameBoard.getCurrentPlayer().getLeaderCard(3);
             leaderCard2 = gameBoard.getCurrentPlayer().getLeaderCard(4);
        }catch (IndexOutOfBoundsException e){
            fail();
        }

        map.put(1,false);
        map.put(3,true);
        map.put(4,true);
        map.put(2,false);

        try {
            //assertTrue(gameBoard.initializeLeaderCard(map));
            gameBoard.initializeLeaderCard(map);
        } catch (InvalidActionException e) {
            fail();
        }
        assertTrue(gameBoard.getCurrentPlayer().isLeadersInitialized());

        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(1).getId(),leaderCard1.getId());
        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(2).getId(),leaderCard2.getId());

    }

    @Test
    void initializeLeaderCards1(){
        Map<Integer,Boolean> map = new HashMap<>();
        LeaderCard leaderCard1 = null;
        LeaderCard leaderCard2 = null;
        LeaderCard leaderCard3 = null;
        LeaderCard leaderCard4 = null;
        try {
            leaderCard1 = gameBoard.getCurrentPlayer().getLeaderCard(1);
            leaderCard2 = gameBoard.getCurrentPlayer().getLeaderCard(2);
            leaderCard3 = gameBoard.getCurrentPlayer().getLeaderCard(3);
            leaderCard4 = gameBoard.getCurrentPlayer().getLeaderCard(4);
        }catch (IndexOutOfBoundsException e){
            fail();
        }

        map.put(1,true);
        map.put(3,true);
        map.put(4,true);
        map.put(2,false);

        assertThrows(InvalidActionException.class, () -> gameBoard.initializeLeaderCard(map));
        //assertFalse(gameBoard.initializeLeaderCard(map));
        assertFalse(gameBoard.getCurrentPlayer().isLeadersInitialized());

        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(1).getId(),leaderCard1.getId());
        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(2).getId(),leaderCard2.getId());
        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(3).getId(),leaderCard3.getId());
        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(4).getId(),leaderCard4.getId());

    }

    @Test
    void initializeLeaderCards2(){
        Map<Integer,Boolean> map = new HashMap<>();
        LeaderCard leaderCard1 = null;
        LeaderCard leaderCard2 = null;
        LeaderCard leaderCard3 = null;
        LeaderCard leaderCard4 = null;
        try {
            leaderCard1 = gameBoard.getCurrentPlayer().getLeaderCard(1);
            leaderCard2 = gameBoard.getCurrentPlayer().getLeaderCard(2);
            leaderCard3 = gameBoard.getCurrentPlayer().getLeaderCard(3);
            leaderCard4 = gameBoard.getCurrentPlayer().getLeaderCard(4);
        }catch (IndexOutOfBoundsException e){
            fail();
        }

        map.put(1,false);
        map.put(3,true);
        map.put(4,true);


        assertThrows(InvalidActionException.class, () -> gameBoard.initializeLeaderCard(map));
        //assertFalse(gameBoard.initializeLeaderCard(map));
        assertFalse(gameBoard.getCurrentPlayer().isLeadersInitialized());

        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(1).getId(),leaderCard1.getId());
        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(2).getId(),leaderCard2.getId());
        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(3).getId(),leaderCard3.getId());
        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(4).getId(),leaderCard4.getId());

    }

    @Test
    void initializeLeaderCards3(){
        Map<Integer,Boolean> map = new HashMap<>();
        LeaderCard leaderCard1 = null;
        LeaderCard leaderCard2 = null;
        LeaderCard leaderCard3 = null;
        LeaderCard leaderCard4 = null;
        try {
            leaderCard1 = gameBoard.getCurrentPlayer().getLeaderCard(1);
            leaderCard2 = gameBoard.getCurrentPlayer().getLeaderCard(2);
            leaderCard3 = gameBoard.getCurrentPlayer().getLeaderCard(3);
            leaderCard4 = gameBoard.getCurrentPlayer().getLeaderCard(4);
        }catch (IndexOutOfBoundsException e){
            fail();
        }

        map.put(1,false);
        //0 is out of bound
        map.put(0,true);
        map.put(4,true);
        map.put(2,false);

        assertThrows(InvalidActionException.class, () -> gameBoard.initializeLeaderCard(map));
        //assertFalse(gameBoard.initializeLeaderCard(map));
        assertFalse(gameBoard.getCurrentPlayer().isLeadersInitialized());

        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(1).getId(),leaderCard1.getId());
        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(2).getId(),leaderCard2.getId());
        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(3).getId(),leaderCard3.getId());
        assertEquals(gameBoard.getCurrentPlayer().getLeaderCard(4).getId(),leaderCard4.getId());

    }
}