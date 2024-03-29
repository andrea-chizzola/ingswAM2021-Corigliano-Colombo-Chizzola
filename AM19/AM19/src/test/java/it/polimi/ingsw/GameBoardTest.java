package it.polimi.ingsw;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.ViewForTest;
import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.View.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    private GameBoard gameBoard;
    private Board board;
    private final String file = "defaultConfiguration.xml";
    private static final Coin coin = new Coin();
    private static final Servant servant = new Servant();
    private static final Stone stone = new Stone();
    private static final Shield shield = new Shield();
    private static final Faith faith = new Faith();


    @BeforeEach
    void setUp() {

        ArrayList<String> names = new ArrayList<>();
        names.add("firstPlayer");
        names.add("secondPlayer");
        names.add("thirdPlayer");
        names.add("fourthPlayer");

        gameBoard = new GameBoard(names, file);
        View view = new ViewForTest();
        gameBoard.attachView(view);
        gameBoard.giveLeaderCards(file);

    }

    @Test
    void testInitialization(){
        LinkedList<Integer> expectedRes = new LinkedList<>();
        expectedRes.add(0);
        expectedRes.add(1);
        expectedRes.add(1);
        expectedRes.add(2);

        for(int i=0; i<expectedRes.size(); i++){
            assertEquals(gameBoard.getPlayers().get(i).getNumberResourcesInitialization(),expectedRes.get(i));
        }
        LinkedList<Integer> expectedFaith = new LinkedList<>();
        expectedFaith.add(0);
        expectedFaith.add(0);
        expectedFaith.add(1);
        expectedFaith.add(1);

        for(int i=0; i<expectedFaith.size(); i++){
            assertEquals(gameBoard.getPlayers().get(i).getFaithTrack().getPosition(),expectedFaith.get(i));
        }
    }

    @Test
    void insertResources(){

        //the second player has one resource to initialize
        gameBoard.endTurnMove();
        List<Resource> resources = new LinkedList<>();
        List<Integer> shelves = new LinkedList<>();

        Map<Integer,Boolean> map = new HashMap<>();

        map.put(1,false);
        map.put(3,true);
        map.put(4,true);
        map.put(2,false);

        try {
            gameBoard.initializeLeaderCard(map);
        } catch (InvalidActionException e) {
            fail();
        }

        resources.add(coin);
        shelves.add(1);

        //assertTrue(gameBoard.insertResources(resources, shelves));
        try {
            gameBoard.insertResources(resources, shelves);
        }catch (InvalidActionException e){
            fail();
        }

        try {
            assertEquals(gameBoard.getPlayers().get(1).getWarehouse().getResource(1).getColor(), coin.getColor());
            assertEquals(gameBoard.getPlayers().get(1).getWarehouse().getQuantity(1), 1);
        }catch (IllegalShelfException e){
            fail();
        }
        assertEquals(gameBoard.getCurrentPlayer().getNickname(),gameBoard.getPlayers().get(2).getNickname());

    }

    @Test
    void insertResources1(){

        assertTrue(gameBoard.isCurrentPlayer(gameBoard.getPlayers().get(0).getNickname()));
        assertEquals(gameBoard.getCurrentPlayer().getNumberResourcesInitialization(), 0);
        List<Resource> resources = new LinkedList<>();
        List<Integer> shelves = new LinkedList<>();

        resources.add(coin);
        shelves.add(1);

        //assertFalse(gameBoard.insertResources(resources, shelves));
        assertThrows(InvalidActionException.class, () -> gameBoard.insertResources(resources, shelves));

        assertTrue(gameBoard.isCurrentPlayer(gameBoard.getPlayers().get(0).getNickname()));
        assertThrows(IllegalShelfException.class, () -> gameBoard.getCurrentPlayer().getWarehouse().getQuantity(1));
        assertThrows(IllegalShelfException.class, () -> gameBoard.getCurrentPlayer().getWarehouse().getResource(1));

    }

    @Test
    void insertResources2(){

        gameBoard.endTurnMove();
        assertTrue(gameBoard.isCurrentPlayer(gameBoard.getPlayers().get(1).getNickname()));
        List<Resource> resources = new LinkedList<>();
        List<Integer> shelves = new LinkedList<>();

        resources.add(faith);
        shelves.add(1);

        //assertFalse(gameBoard.insertResources(resources, shelves));
        assertThrows(InvalidActionException.class, () -> gameBoard.insertResources(resources, shelves));

        assertThrows(IllegalShelfException.class, () -> gameBoard.getCurrentPlayer().getWarehouse().getQuantity(1));
        assertThrows(IllegalShelfException.class, () -> gameBoard.getCurrentPlayer().getWarehouse().getResource(1));

    }


    @Test
    void disconnectionTest(){
        for(Board board : gameBoard.getPlayers()){
            board.setResourcesInitialized();
            board.setLeadersInitialized();
        }
        gameBoard.disconnectPlayer("firstPlayer");
        assertEquals(gameBoard.getPlayers().size(), 3);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");
        assertEquals(gameBoard.getPlayers().get(1).getNickname(),"thirdPlayer");
        assertEquals(gameBoard.getPlayers().get(2).getNickname(),"fourthPlayer");
    }

    @Test
    void disconnectionTest2(){
        for(Board board : gameBoard.getPlayers()){
            board.setResourcesInitialized();
            board.setLeadersInitialized();
        }
        gameBoard.disconnectPlayer("firstPlayer");
        gameBoard.disconnectPlayer("thirdPlayer");
        assertEquals(gameBoard.getPlayers().size(), 2);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");
        assertEquals(gameBoard.getPlayers().get(1).getNickname(),"fourthPlayer");
    }

    @Test
    void reconnectionTest1(){
        for(Board board : gameBoard.getPlayers()){
            board.setResourcesInitialized();
            board.setLeadersInitialized();
        }
        gameBoard.disconnectPlayer("thirdPlayer");
        gameBoard.disconnectPlayer("firstPlayer");
        assertEquals(gameBoard.getPlayers().size(), 2);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");
        assertEquals(gameBoard.getPlayers().get(1).getNickname(),"fourthPlayer");
        try {
            gameBoard.reconnectPlayer("firstPlayer");
        } catch (InvalidActionException e) {
            fail();
        }
        assertEquals(gameBoard.getPlayers().size(), 3);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");
        assertEquals(gameBoard.getPlayers().get(1).getNickname(),"fourthPlayer");
        assertEquals(gameBoard.getPlayers().get(2).getNickname(),"firstPlayer");
    }

    @Test
    void reconnectionTest2(){
        for(Board board : gameBoard.getPlayers()){
            board.setResourcesInitialized();
            board.setLeadersInitialized();
        }
        gameBoard.disconnectPlayer("thirdPlayer");
        gameBoard.disconnectPlayer("firstPlayer");
        gameBoard.disconnectPlayer("fourthPlayer");
        assertEquals(gameBoard.getPlayers().size(), 1);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");

        try {
            gameBoard.reconnectPlayer("fourthPlayer");
            gameBoard.reconnectPlayer("thirdPlayer");
            gameBoard.reconnectPlayer("firstPlayer");
        }catch (InvalidActionException e){
            fail();
        }


        assertEquals(gameBoard.getPlayers().size(), 4);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");
        assertEquals(gameBoard.getPlayers().get(1).getNickname(),"fourthPlayer");
        assertEquals(gameBoard.getPlayers().get(2).getNickname(),"thirdPlayer");
        assertEquals(gameBoard.getPlayers().get(3).getNickname(),"firstPlayer");
    }

}