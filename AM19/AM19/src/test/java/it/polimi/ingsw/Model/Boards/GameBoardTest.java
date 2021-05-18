package it.polimi.ingsw.Model.Boards;

import it.polimi.ingsw.Controller.ViewForTest;
import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.IllegalSlotException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Cards.Colors.Green;
import it.polimi.ingsw.Model.Cards.Colors.Purple;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.xmlParser.ConfigurationParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

        gameBoard.endTurnMove();
        List<Resource> resources = new LinkedList<>();
        List<Integer> shelves = new LinkedList<>();

        resources.add(coin);
        shelves.add(1);

        assertTrue(gameBoard.insertResources(resources, shelves));

        try {
            assertEquals(gameBoard.getCurrentPlayer().getWarehouse().getResource(1).getColor(), coin.getColor());
            assertEquals(gameBoard.getCurrentPlayer().getWarehouse().getQuantity(1), 1);
        }catch (IllegalShelfException e){
            fail();
        }

    }

    @Test
    void insertResources1(){

        assertTrue(gameBoard.isCurrentPlayer(gameBoard.getPlayers().get(0).getNickname()));
        assertTrue(gameBoard.getCurrentPlayer().getNumberResourcesInitialization() == 0);
        List<Resource> resources = new LinkedList<>();
        List<Integer> shelves = new LinkedList<>();

        resources.add(coin);
        shelves.add(1);

        assertFalse(gameBoard.insertResources(resources, shelves));

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

        assertFalse(gameBoard.insertResources(resources, shelves));

        assertThrows(IllegalShelfException.class, () -> gameBoard.getCurrentPlayer().getWarehouse().getQuantity(1));
        assertThrows(IllegalShelfException.class, () -> gameBoard.getCurrentPlayer().getWarehouse().getResource(1));

    }


    @Test
    void disconnectionTest(){
        assertTrue(gameBoard.disconnectPlayer("firstPlayer"));
        assertTrue(gameBoard.getPlayers().size()==3);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");
        assertEquals(gameBoard.getPlayers().get(1).getNickname(),"thirdPlayer");
        assertEquals(gameBoard.getPlayers().get(2).getNickname(),"fourthPlayer");
    }

    @Test
    void disconnectionTest2(){
        assertTrue(gameBoard.disconnectPlayer("firstPlayer"));
        assertTrue(gameBoard.disconnectPlayer("thirdPlayer"));
        assertTrue(gameBoard.getPlayers().size()==2);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");
        assertEquals(gameBoard.getPlayers().get(1).getNickname(),"fourthPlayer");
    }

    @Test
    void reconnectionTest1(){
        assertTrue(gameBoard.disconnectPlayer("thirdPlayer"));
        assertTrue(gameBoard.disconnectPlayer("firstPlayer"));
        assertTrue(gameBoard.getPlayers().size()==2);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");
        assertEquals(gameBoard.getPlayers().get(1).getNickname(),"fourthPlayer");
        assertTrue(gameBoard.reconnectPlayer("firstPlayer"));
        assertTrue(gameBoard.getPlayers().size()==3);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");
        assertEquals(gameBoard.getPlayers().get(1).getNickname(),"fourthPlayer");
        assertEquals(gameBoard.getPlayers().get(2).getNickname(),"firstPlayer");
    }

    @Test
    void reconnectionTest2(){
        assertTrue(gameBoard.disconnectPlayer("thirdPlayer"));
        assertTrue(gameBoard.disconnectPlayer("firstPlayer"));
        assertTrue(gameBoard.disconnectPlayer("fourthPlayer"));
        assertTrue(gameBoard.getPlayers().size()==1);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");

        assertTrue(gameBoard.reconnectPlayer("fourthPlayer"));
        assertTrue(gameBoard.reconnectPlayer("thirdPlayer"));
        assertTrue(gameBoard.reconnectPlayer("firstPlayer"));

        assertTrue(gameBoard.getPlayers().size()==4);
        assertEquals(gameBoard.getPlayers().get(0).getNickname(),"secondPlayer");
        assertEquals(gameBoard.getPlayers().get(1).getNickname(),"fourthPlayer");
        assertEquals(gameBoard.getPlayers().get(2).getNickname(),"thirdPlayer");
        assertEquals(gameBoard.getPlayers().get(3).getNickname(),"firstPlayer");
    }

}